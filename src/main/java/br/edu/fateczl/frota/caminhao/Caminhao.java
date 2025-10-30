package br.edu.fateczl.frota.caminhao;

import br.edu.fateczl.frota.marca.Marca;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;


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

    @Column(nullable = false)
    private String modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", referencedColumnName = "marca_id", nullable = false)
    private Marca marca;

    @Column(nullable = false)
    private String placa;

    @Column(name = "carga_maxima", nullable = false)
    @Positive(message = "Carga máxima deve ser positiva")
    private Double cargaMaxima;

    @Column(nullable = false)
    @Min(value = 2000, message = "Ano deve ser a partir de 2000")
    private Integer ano;

    @Column(nullable = false)
    @Positive(message = "O comprimento deve ser positivo")
    private Double comprimento;

    @Column(nullable = false)
    @Positive(message = "A largura deve ser positiva")
    private Double largura;

    @Column(nullable = false)
    @Positive(message = "A altura deve ser positiva")
    private Double altura;

    private static final double FATOR_CUBAGEM = 300.0;

    @Transient
    private Double metragemCubica;

    public Double getMetragemCubica() {
        if (comprimento == null || largura == null || altura == null) {
            return 0.0;
        }
        return comprimento * largura * altura;
    }

    public Caminhao(CadastroCaminhao dados, Marca marca) {
        this.modelo = dados.modelo();
        this.marca = marca;
        this.placa = dados.placa();
        this.cargaMaxima = dados.cargaMaxima();
        this.ano = dados.ano();
        this.comprimento = dados.comprimento();
        this.largura = dados.largura();
        this.altura = dados.altura();
    }

    public Caminhao(AtualizacaoCaminhao dados, Marca marca) {
        this.modelo = dados.modelo();
        this.marca = marca;
        this.placa = dados.placa();
        this.cargaMaxima = dados.cargaMaxima();
        this.ano = dados.ano();
        this.comprimento = dados.comprimento();
        this.largura = dados.largura();
        this.altura = dados.altura();
    }

    public void atualizarInformacoes(AtualizacaoCaminhao dados, Marca marca) {
        if (dados.modelo() != null) this.modelo = dados.modelo();
        if (marca != null) this.marca = marca;
        if (dados.placa() != null) this.placa = dados.placa();
        if (dados.cargaMaxima() != null) this.cargaMaxima = dados.cargaMaxima();
        if (dados.ano() != null) this.ano = dados.ano();
        if (dados.comprimento() != null) this.comprimento = dados.comprimento();
        if (dados.largura() != null) this.largura = dados.largura();
        if (dados.altura() != null) this.altura = dados.altura();
    }

}