/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Aluguel;

public class AluguelDAO  extends DAO<Aluguel>{

	public Aluguel read (Object chave){
		try{
			int id = (int) chave;
			TypedQuery<Aluguel> q = manager.createQuery("select a from Aluguel a where a.id = :n ",Aluguel.class);
			q.setParameter("n", id);

			return q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}

	//--------------------------------------------
	//  consultas
	//--------------------------------------------

	public List<Aluguel> alugueisModelo(String modelo){
		//alugueis contendo carro de modelo 'palio'
		TypedQuery<Aluguel> q = manager.createQuery("select a from Aluguel a where a.carro.modelo = :x", Aluguel.class);
		q.setParameter("x", modelo);
		
		return  q.getResultList();
	}

	
	public List<Aluguel> alugueisFinalizados(){
		TypedQuery<Aluguel> q = manager.createQuery("select a from Aluguel a where a.finalizado = true", Aluguel.class);
		
		return  q.getResultList();
	}
}
