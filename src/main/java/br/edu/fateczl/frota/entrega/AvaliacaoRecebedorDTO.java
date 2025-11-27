package br.edu.fateczl.frota.entrega;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AvaliacaoRecebedorDTO(

        @NotNull(message = "Entrega é obrigatória")
        Long entregaId,

        @NotNull @Min(1) @Max(5)
        Integer nota,

        @Size(max = 500, message = "Comentário não pode ter mais de 500 caracteres")
        String comentario

) {}