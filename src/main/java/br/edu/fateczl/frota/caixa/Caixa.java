package br.edu.fateczl.frota.caixa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_caixas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caixa_id")
    private Long id;

    @Column(nullable = false)
    private Double comprimento;

    @Column(nullable = false)
    private Double largura;

    @Column(nullable = false)
    private Double altura;

    @Column(name = "limite_peso", nullable = false)
    private Double limitePeso;

}