package br.com.hotel.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="quarto")
public class Quarto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Integer numero;
	
	@OneToOne
	private Categoria categoria;

	public Quarto() {
		
	}

	public Quarto(int id, Integer numero, Categoria categoria) {
		super();
		this.id = id;
		this.numero = numero;
		this.categoria = categoria;
	}

	public Quarto(Integer numero, Categoria categoria) {
		super();
		this.numero = numero;
		this.categoria = categoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
