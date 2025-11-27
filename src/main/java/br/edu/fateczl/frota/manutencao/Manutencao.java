package br.edu.fateczl.frota.manutencao;

import br.edu.fateczl.frota.caminhao.Caminhao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_manutencoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manutencao_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoManutencao tipo;

    @Column(name = "km_realizada", nullable = false)
    private Double kmRealizada;

    @Column(name = "data_manutencao", nullable = false)
    private LocalDateTime dataManutencao;

    @Column(nullable = false)
    private Double custo;

    @Column(length = 500)
    private String observacoes;

    public enum TipoManutencao {
        PREVENTIVA_10K("Manutenção Preventiva 10.000 Km - Óleos, Filtros e Pastilhas"),
        TROCA_PNEUS("Troca de Pneus - 70.000 Km"),
        CORRETIVA("Manutenção Corretiva");

        private final String descricao;

        TipoManutencao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

}