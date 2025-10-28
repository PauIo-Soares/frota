package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.orcamento.OrcamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/solicitacao")
public class SolicitacaoController {

    private OrcamentoService orcamentoService = new OrcamentoService();

    public void receberSolicitacao(SolicitacaoRequest request){

        System.out.println(orcamentoService.calcularFrete(request));

    }

}