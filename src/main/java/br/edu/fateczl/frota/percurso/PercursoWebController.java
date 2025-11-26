package br.edu.fateczl.frota.percurso;

import br.edu.fateczl.frota.FinalizarPercursoDTO;
import br.edu.fateczl.frota.IniciarPercursoDTO;
import br.edu.fateczl.frota.PercursoDTO;
import br.edu.fateczl.frota.caminhao.CaminhaoService;
import br.edu.fateczl.frota.entrega.EntregaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/percurso")
public class PercursoWebController {

    @Autowired
    private PercursoService percursoService;

    @Autowired
    private CaminhaoService caminhaoService;

    @Autowired
    private EntregaService entregaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("caminhoes", caminhaoService.procurarTodos());
        return "percurso/listagem";
    }

    @GetMapping("/caminhao/{caminhaoId}")
    public String listarPorCaminhao(@PathVariable Long caminhaoId, Model model) {
        List<PercursoDTO> percursos = percursoService.listarPorCaminhao(caminhaoId);
        Double totalKm = percursoService.calcularTotalKm(caminhaoId);

        model.addAttribute("percursos", percursos);
        model.addAttribute("totalKm", totalKm);
        model.addAttribute("caminhaoId", caminhaoId);

        return "percurso/historico";
    }

    @GetMapping("/iniciar")
    public String formIniciar(Model model) {
        model.addAttribute("caminhoes", caminhaoService.procurarTodos());
        model.addAttribute("entregas", entregaService.listarPorStatus("COLETA"));
        return "percurso/iniciar";
    }

    @PostMapping("/iniciar")
    public String iniciar(@Valid @ModelAttribute IniciarPercursoDTO dto,
                          RedirectAttributes redirectAttributes) {
        try {
            PercursoDTO percurso = percursoService.iniciarPercurso(dto);
            redirectAttributes.addFlashAttribute("message",
                    "Percurso iniciado com sucesso!");
            return "redirect:/percurso/caminhao/" + dto.caminhaoId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/percurso/iniciar";
        }
    }

    @GetMapping("/finalizar/{id}")
    public String formFinalizar(@PathVariable Long id, Model model) {
        model.addAttribute("percursoId", id);
        return "percurso/finalizar";
    }

    @PostMapping("/finalizar")
    public String finalizar(@Valid @ModelAttribute FinalizarPercursoDTO dto,
                            RedirectAttributes redirectAttributes) {
        try {
            PercursoDTO percurso = percursoService.finalizarPercurso(dto);
            redirectAttributes.addFlashAttribute("message",
                    "Percurso finalizado com sucesso!");
            return "redirect:/percurso/caminhao/" + percurso.caminhaoId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/percurso/finalizar/" + dto.percursoId();
        }
    }
}