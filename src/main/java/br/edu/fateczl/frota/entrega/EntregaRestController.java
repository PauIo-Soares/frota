package br.edu.fateczl.frota.entrega;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entregas")
public class EntregaRestController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    public ResponseEntity<EntregaDTO> criar(@RequestBody @Valid CriarEntregaDTO dto) {
        EntregaDTO criada = entregaService.criarEntrega(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregaDTO> buscar(@PathVariable Long id) {
        EntregaDTO entrega = entregaService.buscarPorId(id);
        return ResponseEntity.ok(entrega);
    }

    @GetMapping
    public ResponseEntity<List<EntregaDTO>> listar() {
        List<EntregaDTO> entregas = entregaService.listarTodas();
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<EntregaDTO>> listarPorCliente(@PathVariable Long clienteId) {
        List<EntregaDTO> entregas = entregaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EntregaDTO>> listarPorStatus(@PathVariable String status) {
        List<EntregaDTO> entregas = entregaService.listarPorStatus(status);
        return ResponseEntity.ok(entregas);
    }

    @PutMapping("/{id}/atribuir-caminhao/{caminhaoId}")
    public ResponseEntity<EntregaDTO> atribuirCaminhao(@PathVariable Long id, @PathVariable Long caminhaoId) {
        EntregaDTO entrega = entregaService.atribuirCaminhao(id, caminhaoId);
        return ResponseEntity.ok(entrega);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EntregaDTO> atualizarStatus(@PathVariable Long id, @RequestParam String novoStatus) {
        EntregaDTO entrega = entregaService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(entrega);
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> registrarFeedback(@RequestBody @Valid FeedbackClienteDTO dto) {
        entregaService.registrarFeedback(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/avaliacao")
    public ResponseEntity<Void> registrarAvaliacao(@RequestBody @Valid AvaliacaoRecebedorDTO dto) {
        entregaService.registrarAvaliacao(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/otimizar-carga")
    public ResponseEntity<Map<Long, List<EntregaDTO>>> otimizarCarga() {
        Map<Long, List<EntregaDTO>> distribuicao = entregaService.otimizarCargaCaminhao();
        return ResponseEntity.ok(distribuicao);
    }

}