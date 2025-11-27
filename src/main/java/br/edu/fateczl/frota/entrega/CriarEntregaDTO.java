package br.edu.fateczl.frota.entrega;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CriarEntregaDTO(

        @NotNull(message = "Solicitação é obrigatória")
        Long solicitacaoId,

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Horário de retirada é obrigatório")
        LocalDateTime horarioRetiradaSolicitado

) {}