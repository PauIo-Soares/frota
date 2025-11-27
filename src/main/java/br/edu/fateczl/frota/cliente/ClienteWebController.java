package br.edu.fateczl.frota.cliente;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteWebController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        List<ClienteDTO> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "cliente/listagem";
    }

    @GetMapping("/formulario")
    public String formulario(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            ClienteDTO cliente = clienteService.buscarPorId(id);
            model.addAttribute("cliente", cliente);
        } else {
            model.addAttribute("cliente", new ClienteDTO(null, null, null, null, null, null, null));
        }
        return "cliente/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute ClienteDTO cliente, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Erro de validação nos dados");
            return "redirect:/cliente/formulario";
        }

        try {
            if (cliente.id() == null) {
                clienteService.criarCliente(cliente);
                redirectAttributes.addFlashAttribute("message", "Cliente criado com sucesso!");
            } else {
                clienteService.atualizar(cliente);
                redirectAttributes.addFlashAttribute("message", "Cliente atualizado com sucesso!");
            }
            return "redirect:/cliente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cliente/formulario";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.excluir(id);
            redirectAttributes.addFlashAttribute("message", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cliente";
    }

}