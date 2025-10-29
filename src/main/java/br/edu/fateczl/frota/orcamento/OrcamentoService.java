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

    public Double calcularFrete(SolicitacaoRequest solicitacao) {

        Double valorFrete;
        Double volume = solicitacaoService.calcularVolume(solicitacao.altura(), solicitacao.comprimento(), solicitacao.largura());
        Double pesoCubado = solicitacaoService.calcularPesoCubado(volume);

        Double tipoPesoCobranca = Math.max(pesoCubado, solicitacao.peso());
        Double pesoCobranca = tipoPesoCobranca * PRECO_POR_KG;

        Double caixaCobranca = 1 * PRECO_POR_CAIXA;

        Double tipoCobranca = Math.max(pesoCobranca, caixaCobranca);

        valorFrete = tipoCobranca;

        Double distanciaKm = googleDistanciaService.calcularDistanciaEmKm(solicitacao.cepOrigem(), solicitacao.cepDestino());

        valorFrete += calcularPrecoDistancia(distanciaKm);
        valorFrete += calcularPrecoPedagios(distanciaKm);

        valorFrete = BigDecimal.valueOf(valorFrete).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return valorFrete;

    }

    private Double calcularPrecoDistancia(Double distanciaKm) {
        return distanciaKm * PRECO_POR_KM;
    }

    private Double calcularPrecoPedagios(Double distanciaKm) {

        Double qtdPedagiosEmMedia = (distanciaKm / 50.61);

        return qtdPedagiosEmMedia * PRECO_MEDIO_PEDAGIO_CAMINHAO;

    }

}