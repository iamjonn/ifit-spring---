/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Usuario;

public class UsuarioDAO extends DAO<Usuario>{

	public Usuario read(Object chave) {
		try {
			String nome = (String) chave;
			TypedQuery<Usuario> q = manager.createQuery("select u from Usuario u where u.nome=:n", Usuario.class);
			q.setParameter("n", nome);
			Usuario u = q.getSingleResult();
			return u;
		} catch (NoResultException e) {
			return null;
		}
	}


	//--------------------------------------------
	//  consultas
	//--------------------------------------------
	
}

