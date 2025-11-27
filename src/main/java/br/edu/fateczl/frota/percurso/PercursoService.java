package br.edu.fateczl.frota.percurso;

import br.edu.fateczl.frota.caminhao.Caminhao;
import br.edu.fateczl.frota.caminhao.CaminhaoRepository;
import br.edu.fateczl.frota.caminhaokm.CaminhaoKm;
import br.edu.fateczl.frota.caminhaokm.CaminhaoKmRepository;
import br.edu.fateczl.frota.entrega.Entrega;
import br.edu.fateczl.frota.entrega.EntregaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PercursoService {

    @Autowired
    private PercursoRepository percursoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private CaminhaoKmRepository caminhaoKmRepository;

    @Transactional
    public PercursoDTO iniciarPercurso(IniciarPercursoDTO dto) {
        Caminhao caminhao = caminhaoRepository.findById(dto.caminhaoId()).orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));

        Entrega entrega = entregaRepository.findById(dto.entregaId()).orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));

        Percurso percurso = new Percurso();
        percurso.setCaminhao(caminhao);
        percurso.setEntrega(entrega);
        percurso.setKmSaida(dto.kmSaida());
        percurso.setDataSaida(LocalDateTime.now());

        Percurso salvo = percursoRepository.save(percurso);
        return toDTO(salvo);
    }

    @Transactional
    public PercursoDTO finalizarPercurso(FinalizarPercursoDTO dto) {
        Percurso percurso = percursoRepository.findById(dto.percursoId()).orElseThrow(() -> new EntityNotFoundException("Percurso não encontrado"));

        percurso.setKmChegada(dto.kmChegada());
        percurso.setCombustivelGasto(dto.combustivelGasto());
        percurso.setDataChegada(LocalDateTime.now());

        CaminhaoKm caminhaoKm = caminhaoKmRepository.findByCaminhaoId(percurso.getCaminhao().getId()).orElseGet(() -> {
            CaminhaoKm novo = new CaminhaoKm();
            novo.setCaminhao(percurso.getCaminhao());
            novo.setKmAtual(0.0);
            return novo;
        });

        caminhaoKm.setKmAtual(dto.kmChegada());
        caminhaoKmRepository.save(caminhaoKm);

        Percurso salvo = percursoRepository.save(percurso);
        return toDTO(salvo);
    }

    public List<PercursoDTO> listarPorCaminhao(Long caminhaoId) {
        return percursoRepository.findByCaminhaoId(caminhaoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Double calcularTotalKm(Long caminhaoId) {
        Double total = percursoRepository.calcularTotalKmPercorridos(caminhaoId);
        return total != null ? total : 0.0;
    }

    private PercursoDTO toDTO(Percurso percurso) {
        return new PercursoDTO(percurso.getId(), percurso.getCaminhao().getId(), percurso.getEntrega().getId(), percurso.getKmSaida(), percurso.getKmChegada(), percurso.getCombustivelGasto(), percurso.getDataSaida(), percurso.getDataChegada());
    }

}