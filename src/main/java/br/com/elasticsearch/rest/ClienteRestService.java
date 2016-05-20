package br.com.elasticsearch.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.elasticsearch.model.Cliente;

@RestController
@RequestMapping(value="cliente")
public class ClienteRestService {

	private static final Logger logger = LogManager.getLogger(ClienteRestService.class);

	private static Map<Long, Cliente> clientes = new HashMap<Long, Cliente>();

	private static long contadorErroCaotico;

	static {
		Cliente cliente1 = Cliente.builder().id(1l)
				.nome("Michel").email("michel.tds@gmail.com").build();
		
		Cliente cliente2 = Cliente.builder().id(2l)
				.nome("Francisco").email("francisco@gmail.com").build();
		
		Cliente cliente3 = Cliente.builder().id(3l)
				.nome("Medeiros").email("medeiros.tds@gmail.com").build();
		
		Cliente cliente4 = Cliente.builder().id(4l)
				.nome("Ana").email("anamedeiros.tds@gmail.com").build();
		
		Cliente cliente5 = Cliente.builder().id(5l)
				.nome("Beatriz").email("beatriz.tds@gmail.com").build();
		
		Cliente cliente6 = Cliente.builder().id(6l)
				.nome("Silva").email("silva@gmail.com").build();
		
		clientes.put(cliente1.getId(), cliente1);
		clientes.put(cliente2.getId(), cliente2);
		clientes.put(cliente3.getId(), cliente3);
		clientes.put(cliente4.getId(), cliente4);
		clientes.put(cliente5.getId(), cliente5);
		clientes.put(cliente6.getId(), cliente6);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Cliente> getClientes() {
		logger.info("Clientes retornados: " + clientes.values().size() + " clientes");
		return clientes.values();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Cliente getCliente(@PathVariable long id) {
		Cliente cli = null;
		for (Cliente c : clientes.values()) {
			if(c.getId() == id) {
				cli = c;
			}
		}
		logger.info("Cliente retornado: " + cli.getNome());
		
		return cli;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void addCliente(@RequestBody Cliente cliente) {
		logger.warn("O cliente " + cliente.getId() + " foi adicionado!");
		logger.warn("Nome: " + cliente.getNome());
		clientes.put(cliente.getId(), cliente);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void mergeCliente(@RequestBody Cliente cliente) {
		contadorErroCaotico ++;
		if(contadorErroCaotico % 7 == 0){
			throw new RuntimeException("Ocorreu um erro caótico");
		}
		logger.warn("Clientes atualizado: " + cliente.getNome());
		logger.info("O cliente " + cliente.getId() + " foi alterado!");
		Cliente temp = clientes.get(cliente.getId());
		temp.setNome(cliente.getNome());
		temp.setEmail(cliente.getEmail());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteCliente(@PathVariable("id") long id) {
		logger.warn("Clientes excluído: " + id);
		clientes.remove(id);
	}

	
}
