package br.edu.fateczl.frota.caixa;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;


//  Talvez seja bom usar
//  @Autowired
//  private CaixaMapper caixaMapper;

    @Transactional
    public String criarCaixa(CaixaDTO dto) {

        Caixa entidade = new Caixa();
        entidade.setAltura(dto.altura());
        entidade.setLargura(dto.largura());
        entidade.setComprimento(dto.comprimento());
        entidade.setLimitePeso(dto.limitePeso());
        caixaRepository.save(entidade);

        return "Caixa criada com sucesso!";

    }

    public CaixaDTO buscarCaixaPorId(Long id) {

        Caixa caixa = caixaRepository.findById(id).orElseThrow(() -> new RuntimeException("Caixa não encontrada"));

        return new CaixaDTO(caixa.getId(), caixa.getAltura(), caixa.getLargura(), caixa.getComprimento(), caixa.getLimitePeso());

    }

    @Transactional
    public String atualizarCaixa(CaixaDTO dto) {

        Caixa caixa = caixaRepository.findById(dto.id()).orElseThrow(() -> new RuntimeException("Caixa não encontrada"));

        if (dto.altura() != null) caixa.setAltura(dto.altura());
        if (dto.largura() != null) caixa.setLargura(dto.largura());
        if (dto.comprimento() != null) caixa.setComprimento(dto.comprimento());
        if (dto.limitePeso() != null) caixa.setLimitePeso(dto.limitePeso());
        caixaRepository.save(caixa);

        return "Caixa atualizada com sucesso!";

    }

    @Transactional
    public String excluirCaixa(Long id) {

        caixaRepository.deleteById(id);
        return "Caixa excluida com sucesso!";

    }

    public List<CaixaDTO> listarCaixas() {

        List<Caixa> listaEntidades = caixaRepository.findAll();
        List<CaixaDTO> resposta = new ArrayList<>();

        for (Caixa i : listaEntidades) {
            resposta.add(new CaixaDTO(i.getId(), i.getAltura(), i.getLargura(), i.getComprimento(), i.getLimitePeso()));
        }

        return resposta;

    }

}