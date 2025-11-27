package br.edu.fateczl.frota.percurso;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/percursos")
public class PercursoRestController {

    @Autowired
    private PercursoService percursoService;

    @PostMapping("/iniciar")
    public ResponseEntity<PercursoDTO> iniciar(@RequestBody @Valid IniciarPercursoDTO dto) {
        PercursoDTO percurso = percursoService.iniciarPercurso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(percurso);
    }

    @PutMapping("/finalizar")
    public ResponseEntity<PercursoDTO> finalizar(@RequestBody @Valid FinalizarPercursoDTO dto) {
        PercursoDTO percurso = percursoService.finalizarPercurso(dto);
        return ResponseEntity.ok(percurso);
    }

    @GetMapping("/caminhao/{caminhaoId}")
    public ResponseEntity<List<PercursoDTO>> listarPorCaminhao(@PathVariable Long caminhaoId) {
        List<PercursoDTO> percursos = percursoService.listarPorCaminhao(caminhaoId);
        return ResponseEntity.ok(percursos);
    }

    @GetMapping("/caminhao/{caminhaoId}/total-km")
    public ResponseEntity<Double> calcularTotalKm(@PathVariable Long caminhaoId) {
        Double total = percursoService.calcularTotalKm(caminhaoId);
        return ResponseEntity.ok(total);
    }
}