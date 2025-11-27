package br.edu.fateczl.frota.caminhaokm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaminhaoKmRepository extends JpaRepository<CaminhaoKm, Long> {
    Optional<CaminhaoKm> findByCaminhaoId(Long caminhaoId);

    @Query("SELECT ck FROM CaminhaoKm ck WHERE (ck.kmAtual - ck.kmUltimaManutencao) >= 10000 OR ck.kmUltimaManutencao IS NULL")
    List<CaminhaoKm> findCaminhoesQueNecessitamManutencao();

    @Query("SELECT ck FROM CaminhaoKm ck WHERE (ck.kmAtual - ck.kmUltimaTrocaPneus) >= 70000 OR ck.kmUltimaTrocaPneus IS NULL")
    List<CaminhaoKm> findCaminhoesQueNecessitamTrocaPneus();
}