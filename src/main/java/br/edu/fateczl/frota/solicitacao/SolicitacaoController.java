package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.orcamento.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/solicitacao")
public class SolicitacaoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @GetMapping
    public String mostrarFormulario() {
        return "solicitacao";
    }

    @PostMapping("/enviar")
    public String receberSolicitacao(@RequestParam Double peso, @RequestParam Double comprimento, @RequestParam Double largura, @RequestParam Double altura, @RequestParam String cepOrigem, @RequestParam String cepDestino, Model model) {
        SolicitacaoRequest request = new SolicitacaoRequest(peso, comprimento, largura, altura, cepOrigem, cepDestino);
        Double valorFrete = orcamentoService.calcularFrete(request);

        model.addAttribute("valorFrete", valorFrete);

        return "resultado";
    }

}