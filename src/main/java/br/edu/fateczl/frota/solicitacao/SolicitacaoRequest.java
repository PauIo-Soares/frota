package br.edu.fateczl.frota.solicitacao;

public record SolicitacaoRequest(
        // Long Id
        // Virar entity tabela
        Double peso,
        Double comprimento,
        Double largura,
        Double altura,
        String cepOrigem,
        String cepDestino

) { }