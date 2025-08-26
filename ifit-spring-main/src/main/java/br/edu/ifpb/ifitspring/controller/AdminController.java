package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.model.Treino;
import br.edu.ifpb.ifitspring.service.TreinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/treinos")
public class AdminController {

    @Autowired
    private TreinoService treinoService;

    @GetMapping
    public List<Treino> listarTodosOsTreinos() {
        return treinoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Treino buscarPorId(@PathVariable Long id) {
        return treinoService.buscarPorId(id);
    }
}
