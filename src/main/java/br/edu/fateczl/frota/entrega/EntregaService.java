package br.edu.fateczl.frota.entrega;

import br.edu.fateczl.frota.caminhao.Caminhao;
import br.edu.fateczl.frota.caminhao.CaminhaoRepository;
import br.edu.fateczl.frota.cliente.Cliente;
import br.edu.fateczl.frota.cliente.ClienteRepository;
import br.edu.fateczl.frota.solicitacao.SolicitacaoEntity;
import br.edu.fateczl.frota.solicitacao.SolicitacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Transactional
    public EntregaDTO criarEntrega(CriarEntregaDTO dto) {
        SolicitacaoEntity solicitacao = solicitacaoRepository.findById(dto.solicitacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada"));

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Entrega entrega = new Entrega();
        entrega.setSolicitacao(solicitacao);
        entrega.setCliente(cliente);
        entrega.setStatus(Entrega.StatusEntrega.COLETA);
        entrega.setHorarioRetiradaSolicitado(dto.horarioRetiradaSolicitado());

        Entrega salva = entregaRepository.save(entrega);
        return toDTO(salva);
    }

    @Transactional
    public EntregaDTO atribuirCaminhao(Long entregaId, Long caminhaoId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));

        Caminhao caminhao = caminhaoRepository.findById(caminhaoId)
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));

        entrega.setCaminhao(caminhao);
        Entrega atualizada = entregaRepository.save(entrega);
        return toDTO(atualizada);
    }

    @Transactional
    public EntregaDTO atualizarStatus(Long entregaId, String novoStatus) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));

        Entrega.StatusEntrega status = Entrega.StatusEntrega.valueOf(novoStatus);
        entrega.setStatus(status);

        LocalDateTime agora = LocalDateTime.now();
        switch (status) {
            case COLETA -> entrega.setDataColeta(agora);
            case EM_PROCESSAMENTO -> entrega.setDataProcessamento(agora);
            case A_CAMINHO_DA_ENTREGA -> entrega.setDataEmTransito(agora);
            case ENTREGUE -> entrega.setDataEntregue(agora);
        }

        Entrega atualizada = entregaRepository.save(entrega);
        return toDTO(atualizada);
    }

    @Transactional
    public void registrarFeedback(FeedbackClienteDTO dto) {
        Entrega entrega = entregaRepository.findById(dto.entregaId())
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));

        entrega.setFeedbackCliente(dto.feedbackCliente());
        entregaRepository.save(entrega);
    }

    @Transactional
    public void registrarAvaliacao(AvaliacaoRecebedorDTO dto) {
        Entrega entrega = entregaRepository.findById(dto.entregaId())
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));

        entrega.setNotaRecebedor(dto.nota());
        entrega.setComentarioRecebedor(dto.comentario());
        entregaRepository.save(entrega);
    }

    public List<EntregaDTO> listarPorCliente(Long clienteId) {
        return entregaRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EntregaDTO> listarPorStatus(String status) {
        Entrega.StatusEntrega statusEnum = Entrega.StatusEntrega.valueOf(status);
        return entregaRepository.findByStatus(statusEnum).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EntregaDTO> listarTodas() {
        return entregaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EntregaDTO buscarPorId(Long id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));
        return toDTO(entrega);
    }

    // Otimização de carga - agrupa entregas para lotar caminhão
    public Map<Long, List<EntregaDTO>> otimizarCargaCaminhao() {
        List<Entrega> entregasPendentes = entregaRepository.findEntregasPendentes();
        List<Caminhao> caminhoes = caminhaoRepository.findAll();

        Map<Long, List<EntregaDTO>> distribuicao = new HashMap<>();

        for (Caminhao caminhao : caminhoes) {
            List<EntregaDTO> entregasCaminhao = new ArrayList<>();
            double pesoTotal = 0.0;
            double volumeTotal = 0.0;
            double volumeMaximo = caminhao.getMetragemCubica();

            for (Entrega entrega : entregasPendentes) {
                SolicitacaoEntity solicitacao = entrega.getSolicitacao();
                double pesoEntrega = solicitacao.getPeso();
                double volumeEntrega = solicitacao.getAltura() *
                        solicitacao.getComprimento() *
                        solicitacao.getLargura();

                if (pesoTotal + pesoEntrega <= caminhao.getCargaMaxima() &&
                        volumeTotal + volumeEntrega <= volumeMaximo &&
                        entrega.getCaminhao() == null) {

                    pesoTotal += pesoEntrega;
                    volumeTotal += volumeEntrega;
                    entregasCaminhao.add(toDTO(entrega));
                }
            }

            if (!entregasCaminhao.isEmpty()) {
                distribuicao.put(caminhao.getId(), entregasCaminhao);
            }
        }

        return distribuicao;
    }

    private EntregaDTO toDTO(Entrega entrega) {
        return new EntregaDTO(
                entrega.getId(),
                entrega.getSolicitacao().getId(),
                entrega.getCaminhao() != null ? entrega.getCaminhao().getId() : null,
                entrega.getCliente().getId(),
                entrega.getStatus().name(),
                entrega.getHorarioRetiradaSolicitado(),
                entrega.getDataColeta(),
                entrega.getDataProcessamento(),
                entrega.getDataEmTransito(),
                entrega.getDataEntregue()
        );
    }
}