package br.edu.fateczl.frota.manutencao;

import br.edu.fateczl.frota.caminhaokm.CaminhaoKmDTO;
import br.edu.fateczl.frota.caminhao.CaminhaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manutencao")
public class ManutencaoWebController {

    @Autowired
    private ManutencaoService manutencaoService;

    @Autowired
    private CaminhaoService caminhaoService;

    @GetMapping
    public String listar(Model model) {
        List<CaminhaoKmDTO> manutencoesPendentes = manutencaoService.verificarManutencoesPendentes();
        List<CaminhaoKmDTO> pneusPendentes = manutencaoService.verificarTrocaPneusPendentes();

        model.addAttribute("manutencoesPendentes", manutencoesPendentes);
        model.addAttribute("pneusPendentes", pneusPendentes);
        model.addAttribute("caminhoes", caminhaoService.procurarTodos());

        return "manutencao/listagem";
    }

    @GetMapping("/caminhao/{caminhaoId}")
    public String historicoCaminhao(@PathVariable Long caminhaoId, Model model) {
        List<ManutencaoDTO> historico = manutencaoService.listarPorCaminhao(caminhaoId);
        CaminhaoKmDTO status = manutencaoService.buscarStatusCaminhao(caminhaoId);

        model.addAttribute("historico", historico);
        model.addAttribute("status", status);
        model.addAttribute("caminhaoId", caminhaoId);

        return "manutencao/historico";
    }

    @GetMapping("/formulario")
    public String formulario(@RequestParam(required = false) Long caminhaoId, Model model) {
        model.addAttribute("caminhoes", caminhaoService.procurarTodos());
        model.addAttribute("tipos", Manutencao.TipoManutencao.values());
        model.addAttribute("caminhaoId", caminhaoId);
        return "manutencao/formulario";
    }

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute CriarManutencaoDTO dto,
                            RedirectAttributes redirectAttributes) {
        try {
            ManutencaoDTO manutencao = manutencaoService.registrarManutencao(dto);
            redirectAttributes.addFlashAttribute("message",
                    "Manutenção registrada com sucesso!");
            return "redirect:/manutencao/caminhao/" + dto.caminhaoId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/manutencao/formulario";
        }
    }
}