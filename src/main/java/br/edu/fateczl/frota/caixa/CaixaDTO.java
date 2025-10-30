package br.edu.fateczl.frota.caixa;

import jakarta.validation.constraints.Positive;

public record CaixaDTO(

        Long id,

        @Positive(message = "A altura deve ser um valor positivo.")
        Double altura,

        @Positive(message = "A largura deve ser um valor positivo.")
        Double largura,

        @Positive(message = "O comprimento deve ser um valor positivo.")
        Double comprimento,

        @Positive(message = "O limite de peso deve ser um valor positivo.")
        Double limitePeso

) {
}