package br.edu.fateczl.frota.caminhao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaminhao(

        Long id,

        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotBlank(message = "Placa é obrigatória")
        String placa,

        @Min(value = 2000, message = "Ano deve ser a partir de 2000")
        Integer ano,

        @Positive(message = "Carga máxima deve ser positiva")
        Double cargaMaxima,

        Long marcaId,

        @Positive(message = "Comprimento deve ser positivo")
        Double comprimento,

        @Positive(message = "Largura deve ser positiva")
        Double largura,

        @Positive(message = "Altura deve ser positiva")
        Double altura

) { }