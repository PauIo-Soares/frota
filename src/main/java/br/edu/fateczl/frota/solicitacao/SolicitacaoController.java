package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.caixa.CaixaDTO;
import br.edu.fateczl.frota.orcamento.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/solicitacao")
public class SolicitacaoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @Autowired
    private SolicitacaoService solicitacaoService;

    @GetMapping
    public String mostrarFormulario() {
        return "solicitacao";
    }

    @PostMapping("/enviar")
    public String processarSolicitacao(@ModelAttribute @Valid SolicitacaoRequest solicitacao, Model model) {

        List<CaixaDTO> caixasCompativeis = solicitacaoService.processarSolicitacao(solicitacao);

        model.addAttribute("solicitacao", solicitacao);
        model.addAttribute("caixasCompativeis", caixasCompativeis);

        return "escolherCaixa";

    }

    @PostMapping("/finalizar")
    public String finalizarSolicitacao(@RequestParam Long caixaId, @ModelAttribute SolicitacaoRequest solicitacao, Model model) {

        double valorFrete = orcamentoService.finalizarSolicitacao(caixaId, solicitacao);
        model.addAttribute("valorFrete", valorFrete);

        return "resultadoFrete";

    }

}