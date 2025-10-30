package br.edu.fateczl.frota.orcamento;

import br.edu.fateczl.frota.solicitacao.GoogleDistanciaService;
import br.edu.fateczl.frota.solicitacao.SolicitacaoEntity;
import br.edu.fateczl.frota.solicitacao.SolicitacaoRequest;
import br.edu.fateczl.frota.solicitacao.SolicitacaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private SolicitacaoService solicitacaoService;

    @Autowired
    private GoogleDistanciaService googleDistanciaService;

    private static final Double PRECO_POR_KM = 1.50;
    private static final Double PRECO_POR_KG = 0.50;
    private static final Double PRECO_MEDIO_PEDAGIO_CAMINHAO = 30.00;
    private static final Double PRECO_POR_CAIXA = 10.0;
    private static final Double MEDIA_PEDAGIO_POR_KM = 944.44;

    @Transactional
    public Double calcularFrete(SolicitacaoRequest solicitacao) {
        Double volume = solicitacaoService.calcularVolume(solicitacao.altura(), solicitacao.comprimento(), solicitacao.largura());

        Double pesoCubado = solicitacaoService.calcularPesoCubado(volume);
        Double pesoParaCobranca = Math.max(pesoCubado, solicitacao.peso());
        Double valorCobrancaPorPeso = pesoParaCobranca * PRECO_POR_KG;
        Double valorCobrancaPorCaixa = PRECO_POR_CAIXA; // Futuramente da pra multiplicar quantidade de caixas por preco por caixa

        Double valorFrete = Math.max(valorCobrancaPorPeso, valorCobrancaPorCaixa);

        Double distanciaKm = googleDistanciaService.calcularDistanciaEmKm(solicitacao.cepOrigem(), solicitacao.cepDestino());

        valorFrete += calcularPrecoDistancia(distanciaKm);
        valorFrete += calcularPrecoPedagios(distanciaKm);

        Double valorFreteArredondado = BigDecimal.valueOf(valorFrete).setScale(2, RoundingMode.HALF_UP).doubleValue();

        salvarOrcamento(salvarSolicitacao(solicitacao), valorFreteArredondado);

        return valorFreteArredondado;

    }

    public void salvarOrcamento(SolicitacaoEntity solicitacao, Double valorFrete){

        OrcamentoEntity orcamentoEntity = new OrcamentoEntity();
        orcamentoEntity.setSolicitacao(solicitacao);
        orcamentoEntity.setValorFrete(valorFrete);
        orcamentoRepository.save(orcamentoEntity);

    }

    public SolicitacaoEntity salvarSolicitacao(SolicitacaoRequest solicitacao){

        SolicitacaoEntity solicitacaoEntity = new SolicitacaoEntity();
        solicitacaoEntity.setAltura(solicitacao.altura());
        solicitacaoEntity.setComprimento(solicitacao.comprimento());
        solicitacaoEntity.setLargura(solicitacao.largura());
        solicitacaoEntity.setPeso(solicitacao.peso());
        solicitacaoEntity.setCepOrigem(solicitacao.cepOrigem());
        solicitacaoEntity.setCepDestino(solicitacao.cepDestino());
        solicitacaoEntity.setCaixaEscolhida(solicitacao.caixaEscolhida());
        return solicitacaoService.salvarSolicitacao(solicitacaoEntity);

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