package br.edu.fateczl.frota.entrega;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByClienteId(Long clienteId);
    List<Entrega> findByStatus(Entrega.StatusEntrega status);
    List<Entrega> findByCaminhaoId(Long caminhaoId);

    @Query("SELECT e FROM Entrega e WHERE e.status != 'ENTREGUE' ORDER BY e.horarioRetiradaSolicitado")
    List<Entrega> findEntregasPendentes();
}
