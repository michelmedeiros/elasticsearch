package br.com.elasticsearch.dto;

import br.com.elasticsearch.model.ItemPedido;
import lombok.Data;

@Data
public class ItemPedidoDTO {

	private long idPedido;

	private long idCliente;

	private ItemPedido item;
}
