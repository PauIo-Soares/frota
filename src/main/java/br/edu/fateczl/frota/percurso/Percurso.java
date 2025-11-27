package br.edu.fateczl.frota.percurso;

import br.edu.fateczl.frota.caminhao.Caminhao;
import br.edu.fateczl.frota.entrega.Entrega;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_percursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Percurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "percurso_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrega_id", nullable = false)
    private Entrega entrega;

    @Column(name = "km_saida", nullable = false)
    @Positive(message = "Quilometragem de saída deve ser positiva")
    private Double kmSaida;

    @Column(name = "km_chegada")
    @Positive(message = "Quilometragem de chegada deve ser positiva")
    private Double kmChegada;

    @Column(name = "combustivel_gasto")
    @Positive(message = "Combustível gasto deve ser positivo")
    private Double combustivelGasto;

    @Column(name = "data_saida", nullable = false)
    private LocalDateTime dataSaida;

    @Column(name = "data_chegada")
    private LocalDateTime dataChegada;

    @Transient
    public Double getKmPercorridos() {
        if (kmChegada != null && kmSaida != null) {
            return kmChegada - kmSaida;
        }
        return 0.0;
    }

}