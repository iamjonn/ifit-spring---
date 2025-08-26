package br.edu.ifpb.ifitspring.service;

import br.edu.ifpb.ifitspring.dto.TreinoDTO;
import br.edu.ifpb.ifitspring.model.Treino;
import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;

    public Treino criarTreino(TreinoDTO dto, Usuario usuario) {
        Treino treino = new Treino();
        treino.setNomeTreino(dto.nomeTreino());
        treino.setExercicios(dto.exercicios());
        treino.setUsuario(usuario);
        treino.setDataICriacao(LocalDate.now());
        return treinoRepository.save(treino);
    }

    public List<Treino> listarTodosPorUsuario(Usuario usuario) {
        return treinoRepository.findAllByUsuario(usuario);
    }

    public void deletarTreino(Long id, Usuario usuario) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

        if (!treino.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Você não tem permissão para excluir este treino");
        }

        treinoRepository.delete(treino);
    }

    public List<Treino> listarTodos() {
        return treinoRepository.findAll();
    }

    public Treino buscarPorId(Long id) {
        return treinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
    }
}
