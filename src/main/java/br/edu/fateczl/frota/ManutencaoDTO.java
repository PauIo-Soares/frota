package br.edu.fateczl.frota;

import java.time.LocalDateTime;

public record ManutencaoDTO(
        Long id,
        Long caminhaoId,
        String tipo,
        Double kmRealizada,
        LocalDateTime dataManutencao,
        Double custo,
        String observacoes
) {}
