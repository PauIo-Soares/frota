package br.edu.fateczl.frota.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "CPF/CNPJ é obrigatório")
        String cpfCnpj,

        @NotBlank(message = "Endereço é obrigatório")
        String endereco,

        @NotBlank(message = "CEP é obrigatório")
        String cep
) {}
