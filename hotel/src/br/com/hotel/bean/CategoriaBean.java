package br.com.hotel.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Categoria;

@ManagedBean
@ViewScoped
public class CategoriaBean {
	
	private Categoria categoria = new Categoria();
	
	public CategoriaBean() {
		categoria= new Categoria();
	}

	

	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}



	public String gravar() {
		System.out.println("Gravando categoria " + this.categoria.getCategoria());
		new DAO<Categoria>(Categoria.class).adiciona(this.categoria);
		this.categoria = new Categoria();
		return "categoriaQuarto?faces-redirect=true";
	}

	

}
