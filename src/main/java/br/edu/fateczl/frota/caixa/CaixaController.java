package br.edu.fateczl.frota.caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

//  Talvez seja bom usar
//  @Autowired
//  private CaixaMapper caixaMapper;

    @GetMapping
    public String carregarForm(Model model) {
        model.addAttribute("caixa", new CaixaDTO(null, null, null, null, null));
        model.addAttribute("mensagem", null);
        model.addAttribute("resultadoBusca", null);
        model.addAttribute("listaCaixas", null);
        return "caixas";
    }

    @PostMapping("/criar")
    public String criarCaixa(@ModelAttribute CaixaDTO caixa, Model model) {
        String mensagem = caixaService.criarCaixa(caixa);
        model.addAttribute("mensagem", mensagem);
        model.addAttribute("caixa", new CaixaDTO(null, null, null, null, null));
        return "caixas";
    }

    @PostMapping("/buscar")
    public String buscarCaixaPorId(@RequestParam Long id, Model model) {
        CaixaDTO caixa = caixaService.buscarCaixaPorId(id);
        model.addAttribute("resultadoBusca", caixa);
        model.addAttribute("caixa", new CaixaDTO(null, null, null, null, null));
        return "caixas";
    }

    @PostMapping("/atualizar")
    public String atualizarCaixa(@ModelAttribute CaixaDTO caixa, Model model) {
        String mensagem = caixaService.atualizarCaixa(caixa);
        model.addAttribute("mensagem", mensagem);
        return "caixas";
    }

    @PostMapping("/excluir")
    public String excluirCaixa(@RequestParam Long id, Model model) {
        String mensagem = caixaService.excluirCaixa(id);
        model.addAttribute("mensagem", mensagem);
        return "caixas";
    }

    @PostMapping("/listar")
    public String listarCaixas(Model model) {
        List<CaixaDTO> lista = caixaService.listarCaixas();
        model.addAttribute("listaCaixas", lista);
        return "caixas";
    }

}