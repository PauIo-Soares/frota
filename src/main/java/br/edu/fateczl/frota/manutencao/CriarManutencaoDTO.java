package br.edu.fateczl.frota.manutencao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CriarManutencaoDTO(

        @NotNull(message = "Caminhão é obrigatório")
        Long caminhaoId,

        @NotNull(message = "Tipo de manutenção é obrigatório")
        String tipo,

        @NotNull @Positive
        Double kmRealizada,

        @NotNull @Positive
        Double custo,

        String observacoes

) {}