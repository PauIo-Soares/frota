package br.edu.fateczl.frota;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PercursoDTO(
        Long id,
        Long caminhaoId,
        Long entregaId,

        @NotNull @Positive
        Double kmSaida,

        Double kmChegada,
        Double combustivelGasto,

        @NotNull
        LocalDateTime dataSaida,

        LocalDateTime dataChegada
) {}
