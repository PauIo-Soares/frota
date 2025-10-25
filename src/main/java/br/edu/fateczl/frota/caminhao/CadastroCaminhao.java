package br.edu.fateczl.frota.caminhao;

import br.edu.fateczl.frota.marca.Marca;
import jakarta.validation.constraints.NotBlank;

public record CadastroCaminhao(

        @NotBlank
        String modelo,

        String placa,

        Marca marca,

        Double cargaMaxima,

        Integer ano) {

}