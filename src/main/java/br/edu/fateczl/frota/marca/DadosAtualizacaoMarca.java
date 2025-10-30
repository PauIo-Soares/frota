package br.edu.fateczl.frota.marca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMarca(

        @NotNull
        Long id,

        @NotBlank(message = "O nome da marca é obrigatório, insira seu valor")
        String nome,

        @NotBlank(message = "O nome do pais marca é obrigatório, insira seu valor")
        String pais
) {
}