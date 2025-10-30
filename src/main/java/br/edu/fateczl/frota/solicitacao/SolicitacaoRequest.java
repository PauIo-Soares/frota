package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.caixa.Caixa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SolicitacaoRequest(

        @NotNull(message = "O peso é obrigatório.")
        @Positive(message = "O peso deve ser um valor positivo.")
        Double peso,

        @NotNull(message = "O comprimento é obrigatório.")
        @Positive(message = "O comprimento deve ser um valor positivo.")
        Double comprimento,

        @NotNull(message = "A largura é obrigatória.")
        @Positive(message = "A largura deve ser um valor positivo.")
        Double largura,

        @NotNull(message = "A altura é obrigatória.")
        @Positive(message = "A altura deve ser um valor positivo.")
        Double altura,

        @NotBlank(message = "O CEP não pode estar vazio.")
        @NotNull(message = "O CEP de origem é obrigatório.")
        String cepOrigem,

        @NotBlank(message = "O CEP não pode estar vazio.")
        @NotNull(message = "O CEP de destino é obrigatório.")
        String cepDestino,

        Caixa caixaEscolhida

) { }