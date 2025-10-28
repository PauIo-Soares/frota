package br.edu.fateczl.frota.caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CaixaMapper caixaMapper;

    @PostMapping
    public String criarCaixa() {
        return caixaService.criarCaixa();
    }

    @GetMapping
    public void buscarCaixa() {
        caixaService.buscarCaixa();
    }

    @PostMapping
    public String atualizarCaixa() {
       return caixaService.atualizarCaixa();
    }

    @PostMapping
    public String excluirCaixa() {
       return caixaService.excluirCaixa();
    }

    @GetMapping
    public List<Caixa> listarCaixas() {
        return caixaService.listarCaixas();
    }

}