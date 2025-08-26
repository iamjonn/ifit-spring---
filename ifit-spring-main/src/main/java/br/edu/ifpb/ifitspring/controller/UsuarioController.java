package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.dto.TreinoDTO;
import br.edu.ifpb.ifitspring.model.Treino;
import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.service.TreinoService;
import br.edu.ifpb.ifitspring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treinos")
public class UsuarioController {

    @Autowired
    private TreinoService treinoService;

    @PostMapping
    public Treino criarTreino(@RequestBody TreinoDTO dto, @AuthenticationPrincipal Usuario usuario) {
        return treinoService.criarTreino(dto, usuario);
    }

    @GetMapping
    public List<Treino> listarMeusTreinos(@AuthenticationPrincipal Usuario usuario) {
        return treinoService.listarTodosPorUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarTreino(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        treinoService.deletarTreino(id, usuario);
    }
}
