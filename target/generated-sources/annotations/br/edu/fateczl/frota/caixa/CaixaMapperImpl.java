package br.edu.fateczl.frota.caixa;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-30T16:30:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class CaixaMapperImpl implements CaixaMapper {

    @Override
    public Caixa toEntity(CaixaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Caixa caixa = new Caixa();

        caixa.setComprimento( dto.comprimento() );
        caixa.setLargura( dto.largura() );
        caixa.setAltura( dto.altura() );
        caixa.setLimitePeso( dto.limitePeso() );

        return caixa;
    }

    @Override
    public CaixaDTO toDto(Caixa entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        Double altura = null;
        Double largura = null;
        Double comprimento = null;
        Double limitePeso = null;

        id = entity.getId();
        altura = entity.getAltura();
        largura = entity.getLargura();
        comprimento = entity.getComprimento();
        limitePeso = entity.getLimitePeso();

        CaixaDTO caixaDTO = new CaixaDTO( id, altura, largura, comprimento, limitePeso );

        return caixaDTO;
    }

    @Override
    public void updateEntityFromDto(CaixaDTO dto, Caixa entity) {
        if ( dto == null ) {
            return;
        }

        entity.setComprimento( dto.comprimento() );
        entity.setLargura( dto.largura() );
        entity.setAltura( dto.altura() );
        entity.setLimitePeso( dto.limitePeso() );
    }
}
