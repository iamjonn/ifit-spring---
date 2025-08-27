package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente {

	@Id
	private String cpf;
	private String nome;
	@OneToMany(mappedBy = "cliente", cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Aluguel> alugueis = new ArrayList<>();

	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public Cliente() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Aluguel> getAlugueis() {
		return alugueis;
	}

	public void adicionar(Aluguel a){
		alugueis.add(a);
	}

	public void remover(Aluguel a){
		alugueis.remove(a);
	}

	
	@Override
	public String toString() {
		String texto = "nome=" + nome + ", cpf=" + cpf;
		
		for(Aluguel a : alugueis)
			texto+= "\n    aluguel: " + a ;
		
		return texto;
	}


}
