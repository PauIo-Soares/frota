package br.edu.fateczl.frota;

import java.time.LocalDateTime;

public record EntregaDTO(
        Long id,
        Long solicitacaoId,
        Long caminhaoId,
        Long clienteId,
        String status,
        LocalDateTime horarioRetiradaSolicitado,
        LocalDateTime dataColeta,
        LocalDateTime dataProcessamento,
        LocalDateTime dataEmTransito,
        LocalDateTime dataEntregue
) {}
