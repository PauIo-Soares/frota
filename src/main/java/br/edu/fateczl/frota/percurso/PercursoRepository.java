package br.edu.fateczl.frota.percurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PercursoRepository extends JpaRepository<Percurso, Long> {
    List<Percurso> findByCaminhaoId(Long caminhaoId);
    List<Percurso> findByEntregaId(Long entregaId);

    @Query("SELECT SUM(p.kmChegada - p.kmSaida) FROM Percurso p WHERE p.caminhao.id = :caminhaoId AND p.kmChegada IS NOT NULL")
    Double calcularTotalKmPercorridos(Long caminhaoId);
}
