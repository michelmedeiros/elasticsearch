package br.com.elasticsearch.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable {

	private static final long serialVersionUID = -518084418852628830L;

	private long id;

	private String nome;

	private String email;

}
