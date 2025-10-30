package br.edu.fateczl.frota.solicitacao;

import br.edu.fateczl.frota.caixa.CaixaDTO;
import br.edu.fateczl.frota.caixa.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private CaixaService caixaService;

    private static final Double FATOR_CUBAGEM = 300.0;

    private List<CaixaDTO> mostrarCaixasCompativeis(SolicitacaoRequest produto){

        List<CaixaDTO> caixas = caixaService.listarCaixas();
        List<CaixaDTO> caixasCompativeis = new ArrayList<>();

        for (CaixaDTO caixa : caixas) {
            if(caixasCompativeis.size() == 3) break;
            if(produto.altura() > caixa.altura() ) continue;
            if(produto.largura() > caixa.largura()) continue;
            if(produto.comprimento() > caixa.comprimento()) continue;
            if(produto.peso() > caixa.limitePeso()) continue;
            caixasCompativeis.add(caixa);
        }

        return caixasCompativeis;

    }

    public List<CaixaDTO> processarSolicitacao(SolicitacaoRequest solicitacao) {
        return mostrarCaixasCompativeis(solicitacao);
    }

    public Double calcularVolume(Double altura, Double comprimento, Double largura) {

        return altura * comprimento * largura;

    }

    public Double calcularPesoCubado(Double volume) {

        return volume * FATOR_CUBAGEM;

    }

    public SolicitacaoEntity salvarSolicitacao(SolicitacaoEntity solicitacao) {

       return solicitacaoRepository.save(solicitacao);

    }

}