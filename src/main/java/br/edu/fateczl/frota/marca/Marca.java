package br.edu.fateczl.frota.marca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tb_marcas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marca_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String pais;

    public Marca(DadosCadastroMarca dados) {
        this.nome = dados.nome();
        this.pais = dados.pais();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMarca dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
            this.pais = dados.pais();
        }
    }

}