package br.edu.fateczl.frota.motorista;

import br.edu.fateczl.frota.entrega.EntregaDTO;
import br.edu.fateczl.frota.entrega.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/motorista")
public class MotoristaRestController {

    @Autowired
    private EntregaService entregaService;

    @PutMapping("/entrega/{id}/iniciar-coleta")
    public ResponseEntity<Map<String, Object>> iniciarColeta(@PathVariable Long id) {
        try {
            EntregaDTO entrega = entregaService.atualizarStatus(id, "COLETA");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Coleta iniciada com sucesso!");
            response.put("entrega", entrega);
            response.put("proximoStatus", "EM_PROCESSAMENTO");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao iniciar coleta: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/entrega/{id}/processar")
    public ResponseEntity<Map<String, Object>> processarEntrega(@PathVariable Long id) {
        try {
            EntregaDTO entrega = entregaService.atualizarStatus(id, "EM_PROCESSAMENTO");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Entrega em processamento!");
            response.put("entrega", entrega);
            response.put("proximoStatus", "A_CAMINHO_DA_ENTREGA");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao processar: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/entrega/{id}/sair-entrega")
    public ResponseEntity<Map<String, Object>> sairParaEntrega(@PathVariable Long id) {
        try {
            EntregaDTO entrega = entregaService.atualizarStatus(id, "A_CAMINHO_DA_ENTREGA");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Saiu para entrega! A caminho do destino.");
            response.put("entrega", entrega);
            response.put("proximoStatus", "ENTREGUE");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao atualizar status: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/entrega/{id}/concluir")
    public ResponseEntity<Map<String, Object>> concluirEntrega(@PathVariable Long id) {
        try {
            EntregaDTO entrega = entregaService.atualizarStatus(id, "ENTREGUE");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Entrega concluída com sucesso!");
            response.put("entrega", entrega);
            response.put("dataEntrega", entrega.dataEntregue());
            response.put("status", "ENTREGUE");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao concluir entrega: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/entrega/{id}")
    public ResponseEntity<Map<String, Object>> buscarEntrega(@PathVariable Long id) {
        try {
            EntregaDTO entrega = entregaService.buscarPorId(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("entrega", entrega);

            String statusAtual = entrega.status();
            String proximaAcao = switch (statusAtual) {
                case "COLETA" -> "Processar mercadoria";
                case "EM_PROCESSAMENTO" -> "Sair para entrega";
                case "A_CAMINHO_DA_ENTREGA" -> "Concluir entrega";
                case "ENTREGUE" -> "Entrega já concluída";
                default -> "Status desconhecido";
            };

            response.put("proximaAcao", proximaAcao);
            response.put("podeAvancar", !statusAtual.equals("ENTREGUE"));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao buscar entrega: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/caminhao/{caminhaoId}/entregas")
    public ResponseEntity<Map<String, Object>> listarEntregasCaminhao(@PathVariable Long caminhaoId) {
        try {
            var entregas = entregaService.listarTodas().stream().filter(e -> e.caminhaoId() != null && e.caminhaoId().equals(caminhaoId)).toList();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("entregas", entregas);
            response.put("total", entregas.size());

            long pendentes = entregas.stream().filter(e -> !e.status().equals("ENTREGUE")).count();
            response.put("pendentes", pendentes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao listar entregas: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

}