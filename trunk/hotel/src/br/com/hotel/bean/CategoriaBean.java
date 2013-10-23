package br.com.hotel.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Categoria;

@ManagedBean
@ViewScoped
public class CategoriaBean {

	private Categoria categoria = new Categoria();

	public CategoriaBean() {
		categoria = new Categoria();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public List<Categoria> getCategorias() {
		ArrayList<Categoria> categorias = (ArrayList<Categoria>) new DAO<Categoria>(
				Categoria.class).listaTodos();

		return categorias;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String gravar() {
		System.out.println("Gravando categoria "
				+ this.categoria.getCategoria());
		try {
			new DAO<Categoria>(Categoria.class).adiciona(this.categoria);
			this.categoria = new Categoria();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "relatorioCategoriaQuarto";
	}

}
