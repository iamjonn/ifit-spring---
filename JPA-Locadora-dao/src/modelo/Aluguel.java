package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Aluguel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	//autogerado
	
	private String datainicio;
	private String datafim;
	private double diaria;
	private boolean finalizado=false;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Carro carro;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Cliente cliente;

	
	public Aluguel() {}

	public double getDiaria() {
		return diaria;
	}

	public void setDiaria(double diaria) {
		this.diaria = diaria;
	}

	public Aluguel(String datainicio, String datafim, double diaria) {
		this.datainicio = datainicio;
		this.datafim = datafim;
		this.diaria = diaria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(String datainicio) {
		this.datainicio = datainicio;
	}

	public String getDatafim() {
		return datafim;
	}

	public void setDatafim(String datafim) {
		this.datafim = datafim;
	}


	public double getValor() {
		return getDias()*diaria;
	}

	public long getDias() {
		LocalDate data1 = LocalDate.parse(this.datainicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate data2 = LocalDate.parse(this.datafim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return  ChronoUnit.DAYS.between(data1, data2);
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Carro getCarro() {
		return carro;
	}


	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "id=" + id + ", "+ datainicio + " a " + datafim + ", valor=" + getValor()
				+ ", finalizado=" + finalizado + ", carro=" + carro.getPlaca() + ", cliente=" + cliente.getCpf();
	}

}
