package br.edu.fateczl.frota.caminhao;

import br.edu.fateczl.frota.marca.Marca;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroCaminhao(

        @NotBlank(message = "Modelo é obrigatório, insira seu valor")
        String modelo,

        @NotBlank(message = "Placa é obrigatória, insira seu valor")
        String placa,

        @NotNull(message = "Marca não pode ser NULL, insira seu valor")
        Marca marca,

        @NotNull(message = "Carga máxima é obrigatória, insira seu valor")
        @Positive(message = "Carga máxima deve ser positiva, Digite novamente")
        Double cargaMaxima,

        @NotNull(message = "Ano é obrigatório, insira seu valor")
        @Min(value = 2000, message = "Ano deve ser a partir de 2000, Digite novamente")
        Integer ano) {

}