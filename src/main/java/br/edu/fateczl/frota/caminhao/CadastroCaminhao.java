package br.edu.fateczl.frota.caminhao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroCaminhao(

        @NotNull(message = "Modelo não pode ser NULL, insira seu valor")
        @NotBlank(message = "Modelo é obrigatório, insira seu valor")
        String modelo,

        @NotNull(message = "Placa não pode ser NULL, insira seu valor")
        @NotBlank(message = "Placa é obrigatória, insira seu valor")
        String placa,

        @NotNull(message = "Marca não pode ser NULL, insira seu valor")
        Long marcaId,

        @NotNull(message = "Carga máxima é obrigatória, insira seu valor")
        @Positive(message = "Carga máxima deve ser positiva, Digite novamente")
        Double cargaMaxima,

        @NotNull(message = "Ano é obrigatório, insira seu valor")
        @Min(value = 2000, message = "Ano deve ser a partir de 2000, Digite novamente")
        Integer ano,

        @NotNull(message = "Comprimento é obrigatório, insira seu valor")
        @Positive(message = "Comprimento deve ser positivo, Digite novamente")
        Double comprimento,

        @NotNull(message = "Largura é obrigatória, insira seu valor")
        @Positive(message = "Largura deve ser positiva, Digite novamente")
        Double largura,

        @NotNull(message = "Altura é obrigatória, insira seu valor")
        @Positive(message = "Altura deve ser positiva, Digite novamente")
        Double altura

) { }