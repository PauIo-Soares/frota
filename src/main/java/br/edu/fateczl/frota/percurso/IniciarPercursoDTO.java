package br.edu.fateczl.frota.percurso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IniciarPercursoDTO(
        @NotNull(message = "Caminhão é obrigatório")
        Long caminhaoId,

        @NotNull(message = "Entrega é obrigatória")
        Long entregaId,

        @NotNull @Positive(message = "Quilometragem de saída deve ser positiva")
        Double kmSaida
) {}
