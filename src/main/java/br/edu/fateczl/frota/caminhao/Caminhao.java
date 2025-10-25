package br.edu.fateczl.frota.caminhao;

import br.edu.fateczl.frota.marca.Marca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_caminhoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caminhao_id")
    private Long id;

    private String modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", referencedColumnName = "marca_id")
    private Marca marca;

    private String placa;

    @Column(name = "carga_maxima")
    private Double cargaMaxima;

    private Integer ano;

    private Double comprimento;

    private Double largura;

    private Double altura;

    private static final double FATOR_CUBAGEM = 300.0;

    private double metragemCubica;

    public Caminhao(CadastroCaminhao dados, Marca marca) {
        this.modelo = dados.modelo();
        this.placa = dados.placa();
        this.cargaMaxima = dados.cargaMaxima();
        this.marca = marca;
        this.ano = dados.ano();
    }

    public Caminhao(AtualizacaoCaminhao dados, Marca marca) {
        this.modelo = dados.modelo();
        this.placa = dados.placa();
        this.cargaMaxima = dados.cargaMaxima();
        this.marca = marca;
        this.ano = dados.ano();
    }

    public void atualizarInformacoes(AtualizacaoCaminhao dados, Marca marca) {
        if (dados.modelo() != null) this.modelo = dados.modelo();
        if (dados.placa() != null) this.placa = dados.placa();
        if (dados.cargaMaxima() != 0) this.cargaMaxima = dados.cargaMaxima();
        if (marca != null) this.marca = marca;
        if (dados.ano() != 0) this.ano = dados.ano();
    }

}