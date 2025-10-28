package br.edu.fateczl.frota.caixa;

public record CaixaDTO(
        Long id,
        Double altura,
        Double largura,
        Double comprimento,
        Double limitePeso
) {
}