package br.edu.fateczl.frota.orcamento;

import br.edu.fateczl.frota.solicitacao.GoogleDistanciaService;
import br.edu.fateczl.frota.solicitacao.SolicitacaoRequest;
import br.edu.fateczl.frota.solicitacao.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class OrcamentoService {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @Autowired
    private GoogleDistanciaService googleDistanciaService;

    private static final Double PRECO_POR_KM = 1.50;
    private static final Double PRECO_POR_KG = 0.50;
    private static final Double PRECO_MEDIO_PEDAGIO_CAMINHAO = 30.00;
    private static final Double PRECO_POR_CAIXA = 10.0;
    private static final Double MEDIA_PEDAGIO_POR_KM = 944.44;

    public Double calcularFrete(SolicitacaoRequest solicitacao) {
        Double volume = solicitacaoService.calcularVolume(solicitacao.altura(), solicitacao.comprimento(), solicitacao.largura());

        Double pesoCubado = solicitacaoService.calcularPesoCubado(volume);
        Double pesoParaCobranca = Math.max(pesoCubado, solicitacao.peso());
        Double valorCobrancaPorPeso = pesoParaCobranca * PRECO_POR_KG;
        Double valorCobrancaPorCaixa = PRECO_POR_CAIXA;

        Double valorFrete = Math.max(valorCobrancaPorPeso, valorCobrancaPorCaixa);

        Double distanciaKm = googleDistanciaService.calcularDistanciaEmKm(solicitacao.cepOrigem(), solicitacao.cepDestino());

        valorFrete += calcularPrecoDistancia(distanciaKm);
        valorFrete += calcularPrecoPedagios(distanciaKm);

        Double valorFreteArredondado = BigDecimal.valueOf(valorFrete).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return valorFreteArredondado;

    }

    private Double calcularPrecoDistancia(Double distanciaKm) {
        return distanciaKm * PRECO_POR_KM;
    }

    private Double calcularPrecoPedagios(Double distanciaKm) {

        if (distanciaKm < MEDIA_PEDAGIO_POR_KM) return 0.0;

        Double qtdPedagios = distanciaKm / MEDIA_PEDAGIO_POR_KM;

        return qtdPedagios * PRECO_MEDIO_PEDAGIO_CAMINHAO;

    }

}