package br.edu.fateczl.frota.marca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public String carregaPaginaListagem(Model model) {

        model.addAttribute("lista", marcaService.procurarTodos());
        return "marca/listagem";

    }

    @GetMapping("/formulario")
    public String carregaPaginaFormulario(Long id, Model model) {

        if (id != null) {
            var marca = marcaService.procurarPorId(id);
            model.addAttribute("marca", marca);
        }

        return "marca/formulario";

    }

    @DeleteMapping
    public String excluir(Long id) {

        marcaService.apagarPorId(id);
        return "redirect:marca";

    }

    // Métoddo para gravar/atualizar o formulário
    @PostMapping
    public String cadastrar(@Valid DadosCadastroMarca dados) {

        marcaService.salvar(new Marca(dados));
        return "redirect:marca";

    }

    @PutMapping
    public String atualizar(DadosAtualizacaoMarca dados) {

        marcaService.atualizarMarca(dados);
        return "redirect:marca";

    }

}