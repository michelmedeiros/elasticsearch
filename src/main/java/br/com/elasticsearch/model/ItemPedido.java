package br.com.elasticsearch.model;

import lombok.Data;

@Data
public class ItemPedido {

	private long idProduto;

	private long quantidade;

	@Override
	public boolean equals(Object obj) {

		ItemPedido item = (ItemPedido) obj;

		return idProduto == item.getIdProduto() ? true : false;
	}

}
