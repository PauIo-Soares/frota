package br.edu.fateczl.frota.caminhaokm;

import br.edu.fateczl.frota.caminhao.Caminhao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_caminhao_km")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CaminhaoKm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caminhao_km_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id", nullable = false, unique = true)
    private Caminhao caminhao;

    @Column(name = "km_atual", nullable = false)
    private Double kmAtual;

    @Column(name = "km_ultima_manutencao")
    private Double kmUltimaManutencao;

    @Column(name = "km_ultima_troca_pneus")
    private Double kmUltimaTrocaPneus;

    @Transient
    public Double getKmParaProximaManutencao() {
        if (kmUltimaManutencao == null) return 10000.0;
        return 10000.0 - (kmAtual - kmUltimaManutencao);
    }

    @Transient
    public Double getKmParaProximaTrocaPneus() {
        if (kmUltimaTrocaPneus == null) return 70000.0;
        return 70000.0 - (kmAtual - kmUltimaTrocaPneus);
    }

    @Transient
    public boolean precisaManutencao() {
        if (kmUltimaManutencao == null) return kmAtual >= 10000.0;
        return (kmAtual - kmUltimaManutencao) >= 10000.0;
    }

    @Transient
    public boolean precisaTrocaPneus() {
        if (kmUltimaTrocaPneus == null) return kmAtual >= 70000.0;
        return (kmAtual - kmUltimaTrocaPneus) >= 70000.0;
    }

}