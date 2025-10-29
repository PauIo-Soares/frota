package br.edu.fateczl.frota.orcamento;

import br.edu.fateczl.frota.solicitacao.SolicitacaoRequest;
import br.edu.fateczl.frota.solicitacao.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrcamentoService {

    @Autowired
    private SolicitacaoService solicitacaoService;

    private static final Double PRECO_POR_KM = 1.50;
    private static final Double PRECO_POR_KG = 0.50;
    private static final Double PRECO_POR_PEDAGIO = 20.50;
    private static final Double PRECO_POR_CAIXA = 10.0;

    public Double calcularFrete(SolicitacaoRequest solicitacao) {

        Double valorFrete;
        Double volume = solicitacaoService.calcularVolume(solicitacao.altura(), solicitacao.comprimento(), solicitacao.largura());
        Double pesoCubado = solicitacaoService.calcularPesoCubado(volume);

        Double tipoPesoCobranca = Math.max(pesoCubado, solicitacao.peso());
        Double pesoCobranca = tipoPesoCobranca * PRECO_POR_KG;

        Double caixaCobranca = 1 * PRECO_POR_CAIXA;

        Double tipoCobranca = Math.max(pesoCobranca, caixaCobranca);

        valorFrete = tipoCobranca;

        valorFrete += calcularPrecoDistancia();
        valorFrete += calcularPrecoPedagios();

        return valorFrete;

    }

    // Poderia chamar alguma funcao q calcula a distancia
    private Double calcularPrecoDistancia() {
        Double distanciaKm = 50.0;
        return distanciaKm * PRECO_POR_KM ;

    }

    // Poderia chamar alguma funçao q retorna qtd de pedagios
    private Double calcularPrecoPedagios() {
        Double qtdPedagios = 4.0;
        return qtdPedagios * PRECO_POR_PEDAGIO;
    }

}