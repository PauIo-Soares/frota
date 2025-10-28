package br.edu.fateczl.frota.caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

//    Talvez seja bom usar
//    @Autowired
//    private CaixaMapper caixaMapper;

    @PostMapping
    public String criarCaixa(@RequestBody CaixaDTO caixa) {
        return caixaService.criarCaixa(caixa);
    }

    @GetMapping("/{id}")
    public CaixaDTO buscarCaixaPorId(@PathVariable Long id) {
        return caixaService.buscarCaixaPorId(id);
    }

    @PostMapping("/atualizar")
    public String atualizarCaixa(@RequestBody CaixaDTO caixa) {
        return caixaService.atualizarCaixa(caixa);
    }

    @PostMapping("/{id}")
    public String excluirCaixa(@PathVariable Long id) {
        return caixaService.excluirCaixa(id);
    }

    @GetMapping
    public List<CaixaDTO> listarCaixas() {
        return caixaService.listarCaixas();
    }

}