package br.edu.fateczl.frota.manutencao;

import br.edu.fateczl.frota.CaminhaoKmDTO;
import br.edu.fateczl.frota.CriarManutencaoDTO;
import br.edu.fateczl.frota.ManutencaoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoRestController {

    @Autowired
    private ManutencaoService manutencaoService;

    @PostMapping
    public ResponseEntity<ManutencaoDTO> registrar(@RequestBody @Valid CriarManutencaoDTO dto) {
        ManutencaoDTO manutencao = manutencaoService.registrarManutencao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(manutencao);
    }

    @GetMapping("/caminhao/{caminhaoId}")
    public ResponseEntity<List<ManutencaoDTO>> listarPorCaminhao(@PathVariable Long caminhaoId) {
        List<ManutencaoDTO> manutencoes = manutencaoService.listarPorCaminhao(caminhaoId);
        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/caminhao/{caminhaoId}/status")
    public ResponseEntity<CaminhaoKmDTO> buscarStatus(@PathVariable Long caminhaoId) {
        CaminhaoKmDTO status = manutencaoService.buscarStatusCaminhao(caminhaoId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<CaminhaoKmDTO>> verificarPendentes() {
        List<CaminhaoKmDTO> pendentes = manutencaoService.verificarManutencoesPendentes();
        return ResponseEntity.ok(pendentes);
    }

    @GetMapping("/pneus-pendentes")
    public ResponseEntity<List<CaminhaoKmDTO>> verificarPneusPendentes() {
        List<CaminhaoKmDTO> pendentes = manutencaoService.verificarTrocaPneusPendentes();
        return ResponseEntity.ok(pendentes);
    }
}