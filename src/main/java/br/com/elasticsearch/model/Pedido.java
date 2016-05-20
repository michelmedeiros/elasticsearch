package br.com.elasticsearch.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Pedido {

	private long id;

	private Date dataPedido;

	private long idCliente;

	private List<ItemPedido> items;

	private StatusPedido status;

	
	public List<ItemPedido> getItems() {

		if (items == null) {
			items = new ArrayList<ItemPedido>();
		}

		return items;
	}
	
	@Override
	public boolean equals(Object obj) {

		Pedido pedido = (Pedido) obj;

		return id == pedido.getId() ? true : false;
	}
	
	
}
