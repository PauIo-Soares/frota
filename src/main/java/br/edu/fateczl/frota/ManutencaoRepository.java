package br.edu.fateczl.frota;

import br.edu.fateczl.frota.manutencao.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByCaminhaoId(Long caminhaoId);
    List<Manutencao> findByCaminhaoIdOrderByDataManutencaoDesc(Long caminhaoId);

    @Query("SELECT m FROM Manutencao m WHERE m.caminhao.id = :caminhaoId AND m.tipo = :tipo ORDER BY m.dataManutencao DESC")
    List<Manutencao> findByCaminhaoAndTipo(Long caminhaoId, Manutencao.TipoManutencao tipo);
}
