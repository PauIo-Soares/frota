package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.caixa.Caixa;
import br.edu.fateczl.frota.caixa.CaixaDTO;
import br.edu.fateczl.frota.caixa.CaixaService;
import br.edu.fateczl.frota.orcamento.OrcamentoService;
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
    private CaixaService caixaService;

    @Autowired
    private SolicitacaoService solicitacaoService;

    @GetMapping
    public String mostrarFormulario() {
        return "solicitacao";
    }

    @PostMapping("/enviar")
    public String processarSolicitacao(@ModelAttribute SolicitacaoRequest solicitacao, Model model) {

        List<CaixaDTO> caixasCompativeis = solicitacaoService.processarSolicitacao(solicitacao);

        model.addAttribute("solicitacao", solicitacao);
        model.addAttribute("caixasCompativeis", caixasCompativeis);

        return "escolherCaixa";

    }

//  Arrumar
    @PostMapping("/finalizar")
    public String finalizarSolicitacao(@RequestParam Long caixaId, @ModelAttribute SolicitacaoRequest solicitacao, Model model) {

        CaixaDTO caixaEscolhida = caixaService.buscarCaixaPorId(caixaId);

        Caixa caixa = new Caixa(caixaEscolhida.id(), caixaEscolhida.comprimento(), caixaEscolhida.largura(), caixaEscolhida.altura(), caixaEscolhida.limitePeso());

        SolicitacaoRequest solicitacaoComCaixa = new SolicitacaoRequest(solicitacao.peso(), solicitacao.comprimento(), solicitacao.largura(), solicitacao.altura(), solicitacao.cepOrigem(), solicitacao.cepDestino(), caixa);

        double valorFrete = orcamentoService.calcularFrete(solicitacaoComCaixa);

        model.addAttribute("valorFrete", valorFrete);

        return "resultadoFrete";

    }

}