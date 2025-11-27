package br.edu.fateczl.frota.entrega;

import br.edu.fateczl.frota.caminhao.CaminhaoService;
import br.edu.fateczl.frota.cliente.ClienteService;
import br.edu.fateczl.frota.solicitacao.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/entrega")
public class EntregaWebController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private SolicitacaoService solicitacaoService;

    @Autowired
    private CaminhaoService caminhaoService;

    @GetMapping
    public String listar(Model model) {
        List<EntregaDTO> entregas = entregaService.listarTodas();
        model.addAttribute("entregas", entregas);
        return "entrega/listagem";
    }

    @GetMapping("/rastrear/{id}")
    public String rastrear(@PathVariable Long id, Model model) {
        EntregaDTO entrega = entregaService.buscarPorId(id);
        model.addAttribute("entrega", entrega);
        return "entrega/rastreamento";
    }

    @GetMapping("/formulario")
    public String formulario(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "entrega/formulario";
    }

    @PostMapping("/criar")
    public String criar(@Valid @ModelAttribute CriarEntregaDTO dto,
                        RedirectAttributes redirectAttributes) {
        try {
            EntregaDTO criada = entregaService.criarEntrega(dto);
            redirectAttributes.addFlashAttribute("message",
                    "Entrega #" + criada.id() + " criada com sucesso!");
            return "redirect:/entrega";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrega/formulario";
        }
    }

    @PostMapping("/{id}/atribuir-caminhao")
    public String atribuirCaminhao(@PathVariable Long id,
                                   @RequestParam Long caminhaoId,
                                   RedirectAttributes redirectAttributes) {
        try {
            entregaService.atribuirCaminhao(id, caminhaoId);
            redirectAttributes.addFlashAttribute("message",
                    "Caminhão atribuído com sucesso!");
            return "redirect:/entrega";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrega";
        }
    }

    @PostMapping("/{id}/atualizar-status")
    public String atualizarStatus(@PathVariable Long id,
                                  @RequestParam String status,
                                  RedirectAttributes redirectAttributes) {
        try {
            entregaService.atualizarStatus(id, status);
            redirectAttributes.addFlashAttribute("message",
                    "Status atualizado para: " + Entrega.StatusEntrega.valueOf(status).getDescricao());
            return "redirect:/entrega";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrega";
        }
    }

    @GetMapping("/otimizar")
    public String otimizarCarga(Model model) {
        Map<Long, List<EntregaDTO>> distribuicao = entregaService.otimizarCargaCaminhao();
        model.addAttribute("distribuicao", distribuicao);
        model.addAttribute("caminhoes", caminhaoService.procurarTodos());
        return "entrega/otimizacao";
    }

    @GetMapping("/{id}/feedback")
    public String formFeedback(@PathVariable Long id, Model model) {
        EntregaDTO entrega = entregaService.buscarPorId(id);
        model.addAttribute("entrega", entrega);
        return "entrega/feedback";
    }

    @PostMapping("/feedback")
    public String registrarFeedback(@Valid @ModelAttribute FeedbackClienteDTO dto,
                                    RedirectAttributes redirectAttributes) {
        try {
            entregaService.registrarFeedback(dto);
            redirectAttributes.addFlashAttribute("message",
                    "Feedback registrado com sucesso!");
            return "redirect:/entrega/rastrear/" + dto.entregaId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrega/" + dto.entregaId() + "/feedback";
        }
    }

    @GetMapping("/{id}/avaliacao")
    public String formAvaliacao(@PathVariable Long id, Model model) {
        EntregaDTO entrega = entregaService.buscarPorId(id);
        model.addAttribute("entrega", entrega);
        return "entrega/avaliacao";
    }

    @PostMapping("/avaliacao")
    public String registrarAvaliacao(@Valid @ModelAttribute AvaliacaoRecebedorDTO dto,
                                     RedirectAttributes redirectAttributes) {
        try {
            entregaService.registrarAvaliacao(dto);
            redirectAttributes.addFlashAttribute("message",
                    "Avaliação registrada com sucesso!");
            return "redirect:/entrega/rastrear/" + dto.entregaId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrega/" + dto.entregaId() + "/avaliacao";
        }
    }

}