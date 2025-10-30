package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.caixa.Caixa;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Positive;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_solicitacoes")
@Data
@NoArgsConstructor
public class SolicitacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitacao_id")
    private Long id;

    @Column(nullable = false)
    @Positive(message = "O peso deve ser positivo.")
    private Double peso;

    @Column(nullable = false)
    @Positive(message = "O comprimento deve ser positivo.")
    private Double comprimento;

    @Column(nullable = false)
    @Positive(message = "A largura deve ser positiva.")
    private Double largura;

    @Column(nullable = false)
    @Positive(message = "A altura deve ser positiva.")
    private Double altura;

    @Column(name = "cep_origem", nullable = false, length = 9)
    private String cepOrigem;

    @Column(name = "cep_destino", nullable = false, length = 9)
    private String cepDestino;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "caixa_id", referencedColumnName = "caixa_id", nullable = false)
    private Caixa caixaEscolhida;

}