package br.edu.fateczl.frota.orcamento;

import br.edu.fateczl.frota.solicitacao.SolicitacaoRequest;
import org.springframework.stereotype.Service;

@Service
public class OrcamentoService {

    // Fator cubagem talvez tenha q sair daqui, ate pq já está em caminahao
    private static final Double FATOR_CUBAGEM = 300.0;
    private static final Double PRECO_POR_KM = 1.50;
    private static final Double PRECO_POR_KG = 0.50;
    private static final Double PRECO_POR_PEDAGIO = 20.50;

    // Precisa receber a caixa tbem, temq colocar a Caixa no Solicitacao Request
    // Tem que ver se o preço da caixa é maior q o preço do pesoReal ou do pesoCubado se for maior q os 2 cobra o valor da caixa
    public Double calcularFrete(SolicitacaoRequest solicitacao) {

        Double valorFrete;
        Double volume = calcularVolume(solicitacao.altura(), solicitacao.comprimento(), solicitacao.largura());
        Double pesoCubado = calcularPesoCubado(volume);

        Double pesoCobranca = Math.max(pesoCubado, solicitacao.peso());
        valorFrete = pesoCobranca * PRECO_POR_KG;

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

    // Deve ir pra solicitacaoService pra nao ferir SOLID
    public Double calcularVolume(Double altura, Double comprimento, Double largura) {

        return altura * comprimento * largura;

    }

    // Deve ir pra solicitacaoService pra nao ferir SOLID
    public Double calcularPesoCubado(Double volume) {

        return volume * FATOR_CUBAGEM;

    }

}