package br.edu.ifpb.ifitspring.repository;

import br.edu.ifpb.ifitspring.model.Treino;
import br.edu.ifpb.ifitspring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findAllByUsuario(Usuario usuario);
}
