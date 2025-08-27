package requisito;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import daojpa.AluguelDAO;
import daojpa.CarroDAO;
import daojpa.ClienteDAO;
import daojpa.DAO;
import daojpa.UsuarioDAO;
//import daodb4o.DAO;
//import daodb4o.DAOAluguel;
//import daodb4o.DAOCarro;
//import daodb4o.DAOCliente;
//import daodb4o.DAOUsuario;
import modelo.Aluguel;
import modelo.Carro;
import modelo.Cliente;
import modelo.Usuario;

public class Fachada {
	private Fachada() {}

	private static CarroDAO carroDAO = new CarroDAO();  
	private static AluguelDAO aluguelDAO = new AluguelDAO(); 
	private static ClienteDAO clienteDAO = new ClienteDAO(); 
	private static UsuarioDAO usuarioDAO = new UsuarioDAO(); 
	public static Usuario logado;	//contem o usuario que logou na TelaLogin.java

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}


	public static void cadastrarCarro(String placa, String modelo) throws Exception{
		DAO.begin();
		Carro carro = carroDAO.read(placa);
		if (carro!=null) {
			DAO.rollback();
			throw new Exception("carro ja cadastrado:" + placa);
		}
		carro = new Carro(placa, modelo);

		carroDAO.create(carro);
		DAO.commit();
	}

	public static void alugarCarro(String cpf, String placa, double diaria, String data1, String data2) throws Exception{
		DAO.begin();
		Carro car =  carroDAO.read(placa);
		if(car==null)  {
			DAO.rollback();
			throw new Exception ("carro incorreto para aluguel "+placa);
		}
		if(car.isAlugado())  {
			DAO.rollback();
			throw new Exception ("carro ja esta alugado:"+placa);
		}

		Cliente cli = clienteDAO.read(cpf);
		if(cli==null)  {
			DAO.rollback();
			throw new Exception ("cliente incorreto para aluguel " + cpf);
		}

		Aluguel aluguel = new Aluguel(data1,data2, diaria);
		aluguel.setCarro(car);
		aluguel.setCliente(cli);
		car.adicionar(aluguel);
		car.setAlugado(true);
		cli.adicionar(aluguel);

		aluguelDAO.create(aluguel);
		DAO.commit();
	}

	public static void devolverCarro(String placa) throws Exception{
		DAO.begin();
		Carro car =  carroDAO.read(placa);
		if(car==null)  {
			DAO.rollback();
			throw new Exception ("carro incorreto para devolucao");
		}
		if(!car.isAlugado())  {
			DAO.rollback();
			throw new Exception ("carro nao pode ser devolvido - nao esta alugado");
		}
			// obter o ultimo aluguel do carro
		Aluguel alug = car.getAlugueis().get(car.getAlugueis().size()-1);
		alug.setFinalizado(true);
		car.setAlugado(false);
		DAO.commit();
	}

	public static void excluirCarro(String placa) throws Exception{
		DAO.begin();
		Carro carro =  carroDAO.read(placa);
		if(carro==null)  {
			DAO.rollback();
			throw new Exception ("carro incorreto para exclusao " + placa);
		}
		if(carro.isAlugado())  {
			DAO.rollback();
			throw new Exception ("carro alugado nao pode ser excluido " + placa);
		}

		//remover o aluguel dos clientes que alugaram o carro
		for (Aluguel a : carro.getAlugueis()) {
			Cliente cli = a.getCliente();
			cli.remover(a);
			//o aluguel orfao sera apagado 
		}

		carroDAO.delete(carro);
		DAO.commit();
	}

	public static void cadastrarCliente(String nome, String cpf) throws Exception{
		DAO.begin();
		Cliente cli = clienteDAO.read(cpf);
		if (cli!=null) {
			DAO.rollback();
			throw new Exception("Pessoa ja cadastrado:" + cpf);
		}
		cli = new Cliente(nome, cpf);

		clienteDAO.create(cli);
		DAO.commit();
	}
	public static void excluirCliente(String cpf) throws Exception{
		DAO.begin();
		Cliente cli =  clienteDAO.read(cpf);
		if(cli==null)  {
			DAO.rollback();
			throw new Exception ("cliente incorreto para exclusao " + cpf);
		}

		//cliente tem aluguel em aberto
		if(!cli.getAlugueis().isEmpty()) {
			Aluguel ultimo = cli.getAlugueis().getLast();
			if(!ultimo.isFinalizado())  {
				DAO.rollback();
				throw new Exception ("Nao pode excluir cliente com aluguel nao finalizado: " + cpf);
			}
		}

		//remover o aluguel dos carros alugados pelo cliente 
		for (Aluguel a : cli.getAlugueis()) {
			Carro carro = a.getCarro();
			carro.remover(a);
		}

		clienteDAO.delete(cli);
		DAO.commit();
	}

	public static void excluirAluguel(int id) throws Exception{
		DAO.begin();
		Aluguel aluguel =  aluguelDAO.read(id);
		if(aluguel==null)  {
			DAO.rollback();
			throw new Exception ("aluguel incorreto para exclusao " + id);
		}
		if(! aluguel.isFinalizado())  {
			DAO.rollback();
			throw new Exception ("aluguel nao finalizado nao pode ser excluido " + id);
		}
		
		//remover o cliente e carro do aluguel
		Cliente cli = aluguel.getCliente();
		Carro carro = aluguel.getCarro();
		cli.remover(aluguel);
		carro.remover(aluguel);
		
		aluguelDAO.delete(aluguel);
		DAO.commit();
	}

	public static List<Cliente>  listarClientes(){
		List<Cliente> resultados =  clienteDAO.readAll();
		return resultados;
	} 

	public static List<Carro>  listarCarros(){
		List<Carro> resultados =  carroDAO.readAll();
		return resultados;
	}

	public static List<Aluguel> listarAlugueis(){
		List<Aluguel> resultados =  aluguelDAO.readAll();
		return resultados;
	}

	public static List<Usuario>  listarUsuarios(){
		List<Usuario> resultados =  usuarioDAO.readAll();
		return resultados;
	} 

	public static List<Aluguel> alugueisModelo(String modelo){	
		List<Aluguel> resultados =  aluguelDAO.alugueisModelo(modelo);
		return resultados;
	}

	public static List<Aluguel> alugueisFinalizados(){	
		List<Aluguel> resultados =  aluguelDAO.alugueisFinalizados();
		return resultados;
	}

	public static List<Carro>  carrosNAlugueis(int n){	
		List<Carro> resultados =  carroDAO.carrosNAlugueis(n);
		return resultados;
	}

	public static Carro localizarCarro(String placa){
		return carroDAO.read(placa);
	}
	public static Cliente localizarCliente(String cpf){
		return clienteDAO.read(cpf);
	}


	//------------------Usuario------------------------------------
	public static Usuario cadastrarUsuario(String nome, String senha) throws Exception{
		DAO.begin();
		Usuario usu = usuarioDAO.read(nome);
		if (usu!=null) {
			DAO.rollback();
			throw new Exception("Usuario ja cadastrado:" + nome);
		}
		usu = new Usuario(nome, senha);

		usuarioDAO.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario usu = usuarioDAO.read(nome);
		if (usu==null) {
			return null;
		}
		if (! usu.getSenha().equals(senha)) {
			return null;
		}
		return usu;
	}
}
