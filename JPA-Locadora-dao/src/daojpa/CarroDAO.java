/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Carro;

public class CarroDAO extends DAO<Carro>{

	public Carro read (Object chave){
		try{
			String placa = (String) chave;
			TypedQuery<Carro> q = manager.createQuery("select c from Carro c where c.placa=:pla",Carro.class);
			q.setParameter("pla", placa);
			Carro c =  q.getSingleResult();
			return c;
		}catch(NoResultException e){
			return null;
		}
	}

	public List<Carro> carrosNAlugueis(int n) {
		// carros com 3 alugueis
		TypedQuery<Carro> q = manager.createQuery("select c from Carro c where size(c.alugueis) = :x", Carro.class);
		q.setParameter("x", n);
		return q.getResultList();
	}
}

