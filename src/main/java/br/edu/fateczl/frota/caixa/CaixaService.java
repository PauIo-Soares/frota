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

    @Autowired
    private CaixaMapper caixaMapper;

    @Transactional
    public String criarCaixa(CaixaDTO dto) {

        Caixa entidade = caixaMapper.toEntity(dto);
        caixaRepository.save(entidade);

        return "Caixa criada com sucesso!";

    }

    public CaixaDTO buscarCaixaPorId(Long id) {

        Caixa caixa = caixaRepository.findById(id).orElseThrow(() -> new RuntimeException("Caixa não encontrada"));

        return caixaMapper.toDto(caixa);

    }

    @Transactional
    public String atualizarCaixa(CaixaDTO dto) {

        Caixa caixa = caixaRepository.findById(dto.id()).orElseThrow(() -> new RuntimeException("Caixa não encontrada"));
        caixaMapper.updateEntityFromDto(dto, caixa);
        caixaRepository.save(caixa);

        return "Caixa atualizada com sucesso!";

    }

    @Transactional
    public String excluirCaixa(Long id) {

        caixaRepository.deleteById(id);

        return "Caixa excluida com sucesso!";

    }

    public List<CaixaDTO> listarCaixas() {
        return caixaRepository.findAll().stream().map(caixaMapper::toDto).toList();
    }

}