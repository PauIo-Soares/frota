package br.edu.fateczl.frota;

import br.edu.fateczl.frota.solicitacao.SolicitacaoController;
import br.edu.fateczl.frota.solicitacao.SolicitacaoRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrotaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrotaApplication.class, args);

        SolicitacaoRequest sr = new SolicitacaoRequest(50.0, 10.0, 5.0, 6.0, "239", "485");
        SolicitacaoController sc = new SolicitacaoController();

        sc.receberSolicitacao(sr);

    }

}