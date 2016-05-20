package br.com.elasticsearch.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.elasticsearch.dto.ItemPedidoDTO;
import br.com.elasticsearch.model.ItemPedido;
import br.com.elasticsearch.model.Pedido;
import br.com.elasticsearch.model.StatusPedido;

@RestController
@RequestMapping(value="pedido")
public class PedidoRestService {

	private static List<Pedido> pedidosMock = new ArrayList<Pedido>();

	private static final Logger logger = LogManager.getLogger(PedidoRestService.class.getName());

	private static long contadorErroCaotico;

	@RequestMapping(method = RequestMethod.GET)
	public List<Pedido> buscarPedidos() {

		logger.info("foram buscados todos os pedidos!");

		return pedidosMock;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public List<Pedido> buscarPedidosPorCliente(@PathVariable("idCliente") long idCliente) {

		List<Pedido> pedidos = new ArrayList<Pedido>();

		for (Pedido pedido : pedidosMock) {

			if (pedido.getIdCliente() == idCliente)
				pedidos.add(pedido);
		}

		logger.info("cliente " + idCliente + " possui " + pedidos.size() + " pedidos");

		return pedidos;

	}

	@RequestMapping(value="item/adiciona", method = RequestMethod.POST)
	public void adicionaItemPedido(@RequestBody ItemPedidoDTO item) {

		contadorErroCaotico++;

		if ((contadorErroCaotico) % 7 == 0) {
			throw new RuntimeException("Ocorreu um erro caótico!");
		}

		// se for pedido novo, cria, senao somente adiciona o item

		long idCliente = 0;

		boolean pedidoNovo = true;
		List<ItemPedido> itens = new ArrayList<ItemPedido>();

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == item.getIdPedido()) {
				itens.add(item.getItem());
				idCliente = pedido.getIdCliente();
				pedidoNovo = false;
			}
		}
		

		if (pedidoNovo) {
			Pedido pedido = new Pedido();
			idCliente = item.getIdCliente();
			pedido.setId(item.getIdPedido());
			pedido.setDataPedido(new Date());
			pedido.setIdCliente(item.getIdCliente());
			itens.add(item.getItem());
			pedido.setItems(itens);
			pedido.setStatus(StatusPedido.ABERTO);

			pedidosMock.add(pedido);

		}

		logger.info("pedido " + item.getIdPedido() + " do cliente " + idCliente + " adicionou o produto "
				+ item.getItem().getIdProduto());

	}

	@RequestMapping(value="item/remove", method = RequestMethod.POST)
	public void removeItemPedido(@RequestBody ItemPedidoDTO item) {

		long idCliente = 0;

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == item.getIdPedido()) {

				pedido.getItems().remove(item.getItem());

				idCliente = pedido.getIdCliente();

			}

		}

		logger.info("pedido " + item.getIdPedido() + " do cliente " + idCliente + " removeu o produto "
				+ item.getItem().getIdProduto());

	}

	@RequestMapping(value="{idPedido}", method = RequestMethod.PUT)
	public void pagaPedido(@PathVariable("idPedido") long idPedido) {

		for (Pedido pedido : pedidosMock) {
			if (pedido.getId() == idPedido) {
				pedido.setStatus(StatusPedido.CONCLUIDO);
			}
		}
		logger.info("pedido " + idPedido + " efetivado");

	}

	@RequestMapping(value = "{idPedido}", method = RequestMethod.DELETE)
	public void cancelaPedido(@PathParam("idPedido") long idPedido) {

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == idPedido) {

				pedido.setStatus(StatusPedido.CANCELADO);

			}

		}

		logger.info("pedido " + idPedido + " cancelado");

	}

}
