package br.edu.fateczl.frota.caminhaokm;

public record CaminhaoKmDTO(
        Long id,
        Long caminhaoId,
        Double kmAtual,
        Double kmUltimaManutencao,
        Double kmUltimaTrocaPneus,
        Double kmParaProximaManutencao,
        Double kmParaProximaTrocaPneus,
        Boolean precisaManutencao,
        Boolean precisaTrocaPneus
) {}