package br.edu.fateczl.frota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackClienteDTO(
        @NotNull(message = "Entrega é obrigatória")
        Long entregaId,

        @NotBlank(message = "Feedback é obrigatório")
        @Size(max = 500, message = "Feedback não pode ter mais de 500 caracteres")
        String feedbackCliente
) {}
