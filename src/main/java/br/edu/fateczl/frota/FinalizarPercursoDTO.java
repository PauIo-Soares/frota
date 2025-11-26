package br.edu.fateczl.frota;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FinalizarPercursoDTO(
        @NotNull(message = "Percurso é obrigatório")
        Long percursoId,

        @NotNull @Positive(message = "Quilometragem de chegada deve ser positiva")
        Double kmChegada,

        @NotNull @Positive(message = "Combustível gasto deve ser positivo")
        Double combustivelGasto
) {}
