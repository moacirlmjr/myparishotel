package br.com.hotel.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String categoria;
	private Double valor;
	private Integer capacidade;

	public Categoria() {

	}

	public Categoria(Integer id, String categoria, Double valor,
			Integer capacidade) {
		super();
		this.id = id;
		this.categoria = categoria;

		this.valor = valor;
		this.capacidade = capacidade;
	}

	public Categoria(String categoria, Double valor, Integer capacidade) {
		super();
		this.categoria = categoria;
		this.valor = valor;
		this.capacidade = capacidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

}
