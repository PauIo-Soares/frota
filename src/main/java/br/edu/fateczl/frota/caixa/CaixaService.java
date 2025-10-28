package br.edu.fateczl.frota.caixa;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    @Transactional
    public String criarCaixa() {
//        caixaRepository.save();
        return "Caixa criada com sucesso!";
    }

    public void buscarCaixa() {
//        caixaRepository.findById();
    }

    @Transactional
    public String atualizarCaixa() {
//        caixaRepository.save();
        return "Caixa atualizada com sucesso!";
    }

    @Transactional
    public String excluirCaixa() {
//        caixaRepository.deleteById();
        return "Caixa excluida com sucesso!";
    }

    public List<Caixa> listarCaixas() {
        return caixaRepository.findAll();
    }

}