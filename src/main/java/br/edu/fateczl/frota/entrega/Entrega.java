package br.edu.fateczl.frota.entrega;

import br.edu.fateczl.frota.caminhao.Caminhao;
import br.edu.fateczl.frota.cliente.Cliente;
import br.edu.fateczl.frota.solicitacao.SolicitacaoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_entregas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrega_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private SolicitacaoEntity solicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntrega status;

    @Column(name = "data_coleta")
    private LocalDateTime dataColeta;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @Column(name = "data_em_transito")
    private LocalDateTime dataEmTransito;

    @Column(name = "data_entregue")
    private LocalDateTime dataEntregue;

    @Column(name = "horario_retirada_solicitado")
    private LocalDateTime horarioRetiradaSolicitado;

    @Column(name = "feedback_cliente", length = 500)
    private String feedbackCliente;

    @Column(name = "nota_recebedor")
    private Integer notaRecebedor;

    @Column(name = "comentario_recebedor", length = 500)
    private String comentarioRecebedor;

    public enum StatusEntrega {
        COLETA("Coleta"),
        EM_PROCESSAMENTO("Em Processamento"),
        A_CAMINHO_DA_ENTREGA("A Caminho da Entrega"),
        ENTREGUE("Entregue");

        private final String descricao;

        StatusEntrega(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
    
}