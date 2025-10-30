package br.edu.fateczl.frota.caminhao;

import br.edu.fateczl.frota.marca.Marca;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-30T16:30:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class CaminhaoMapperImpl implements CaminhaoMapper {

    @Override
    public AtualizacaoCaminhao toAtualizacaoDto(Caminhao caminhao) {
        if ( caminhao == null ) {
            return null;
        }

        Long marcaId = null;
        Long id = null;
        String modelo = null;
        String placa = null;
        Integer ano = null;
        Double cargaMaxima = null;
        Double comprimento = null;
        Double largura = null;
        Double altura = null;

        marcaId = caminhaoMarcaId( caminhao );
        id = caminhao.getId();
        modelo = caminhao.getModelo();
        placa = caminhao.getPlaca();
        ano = caminhao.getAno();
        cargaMaxima = caminhao.getCargaMaxima();
        comprimento = caminhao.getComprimento();
        largura = caminhao.getLargura();
        altura = caminhao.getAltura();

        AtualizacaoCaminhao atualizacaoCaminhao = new AtualizacaoCaminhao( id, modelo, placa, ano, cargaMaxima, marcaId, comprimento, largura, altura );

        return atualizacaoCaminhao;
    }

    @Override
    public Caminhao toEntityFromAtualizacao(AtualizacaoCaminhao dto) {
        if ( dto == null ) {
            return null;
        }

        Caminhao caminhao = new Caminhao();

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
        caminhao.setCargaMaxima( dto.cargaMaxima() );
        caminhao.setAno( dto.ano() );
        caminhao.setComprimento( dto.comprimento() );
        caminhao.setLargura( dto.largura() );
        caminhao.setAltura( dto.altura() );

        return caminhao;
    }

    @Override
    public void updateEntityFromDto(AtualizacaoCaminhao dto, Caminhao caminhao) {
        if ( dto == null ) {
            return;
        }

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
        caminhao.setCargaMaxima( dto.cargaMaxima() );
        caminhao.setAno( dto.ano() );
        caminhao.setComprimento( dto.comprimento() );
        caminhao.setLargura( dto.largura() );
        caminhao.setAltura( dto.altura() );
    }

    private Long caminhaoMarcaId(Caminhao caminhao) {
        if ( caminhao == null ) {
            return null;
        }
        Marca marca = caminhao.getMarca();
        if ( marca == null ) {
            return null;
        }
        Long id = marca.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
