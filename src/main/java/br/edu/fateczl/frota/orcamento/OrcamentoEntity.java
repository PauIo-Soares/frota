package br.edu.fateczl.frota.orcamento;

import br.edu.fateczl.frota.solicitacao.SolicitacaoEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_orcamentos")
@Data
@NoArgsConstructor
public class OrcamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orcamento_id")
    private Long id;

    @Column(name = "valor_frete", nullable = false)
    private Double valorFrete;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitacao_id", referencedColumnName = "solicitacao_id", nullable = false)
    private SolicitacaoEntity solicitacao;

}