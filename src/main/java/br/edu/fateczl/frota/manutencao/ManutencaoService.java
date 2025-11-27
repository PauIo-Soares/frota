package br.edu.fateczl.frota.manutencao;

import br.edu.fateczl.frota.caminhao.Caminhao;
import br.edu.fateczl.frota.caminhao.CaminhaoRepository;
import br.edu.fateczl.frota.caminhaokm.CaminhaoKm;
import br.edu.fateczl.frota.caminhaokm.CaminhaoKmDTO;
import br.edu.fateczl.frota.caminhaokm.CaminhaoKmRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private CaminhaoKmRepository caminhaoKmRepository;

    @Transactional
    public ManutencaoDTO registrarManutencao(CriarManutencaoDTO dto) {
        Caminhao caminhao = caminhaoRepository.findById(dto.caminhaoId()).orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));

        Manutencao manutencao = new Manutencao();
        manutencao.setCaminhao(caminhao);
        manutencao.setTipo(Manutencao.TipoManutencao.valueOf(dto.tipo()));
        manutencao.setKmRealizada(dto.kmRealizada());
        manutencao.setDataManutencao(LocalDateTime.now());
        manutencao.setCusto(dto.custo());
        manutencao.setObservacoes(dto.observacoes());

        CaminhaoKm caminhaoKm = caminhaoKmRepository.findByCaminhaoId(dto.caminhaoId()).orElseThrow(() -> new EntityNotFoundException("Registro de KM não encontrado"));

        if (manutencao.getTipo() == Manutencao.TipoManutencao.PREVENTIVA_10K) {
            caminhaoKm.setKmUltimaManutencao(dto.kmRealizada());
        } else if (manutencao.getTipo() == Manutencao.TipoManutencao.TROCA_PNEUS) {
            caminhaoKm.setKmUltimaTrocaPneus(dto.kmRealizada());
        }

        caminhaoKmRepository.save(caminhaoKm);
        Manutencao salva = manutencaoRepository.save(manutencao);
        return toDTO(salva);
    }

    public List<ManutencaoDTO> listarPorCaminhao(Long caminhaoId) {
        return manutencaoRepository.findByCaminhaoIdOrderByDataManutencaoDesc(caminhaoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CaminhaoKmDTO> verificarManutencoesPendentes() {
        List<CaminhaoKm> caminhoes = caminhaoKmRepository.findCaminhoesQueNecessitamManutencao();
        return caminhoes.stream().map(this::toKmDTO).collect(Collectors.toList());
    }

    public List<CaminhaoKmDTO> verificarTrocaPneusPendentes() {
        List<CaminhaoKm> caminhoes = caminhaoKmRepository.findCaminhoesQueNecessitamTrocaPneus();
        return caminhoes.stream().map(this::toKmDTO).collect(Collectors.toList());
    }

    public CaminhaoKmDTO buscarStatusCaminhao(Long caminhaoId) {
        CaminhaoKm caminhaoKm = caminhaoKmRepository.findByCaminhaoId(caminhaoId).orElseThrow(() -> new EntityNotFoundException("Registro de KM não encontrado"));
        return toKmDTO(caminhaoKm);
    }

    private ManutencaoDTO toDTO(Manutencao manutencao) {
        return new ManutencaoDTO(manutencao.getId(), manutencao.getCaminhao().getId(), manutencao.getTipo().name(), manutencao.getKmRealizada(), manutencao.getDataManutencao(), manutencao.getCusto(), manutencao.getObservacoes());
    }

    private CaminhaoKmDTO toKmDTO(CaminhaoKm caminhaoKm) {
        return new CaminhaoKmDTO(caminhaoKm.getId(), caminhaoKm.getCaminhao().getId(), caminhaoKm.getKmAtual(), caminhaoKm.getKmUltimaManutencao(), caminhaoKm.getKmUltimaTrocaPneus(), caminhaoKm.getKmParaProximaManutencao(), caminhaoKm.getKmParaProximaTrocaPneus(), caminhaoKm.precisaManutencao(), caminhaoKm.precisaTrocaPneus());
    }

}