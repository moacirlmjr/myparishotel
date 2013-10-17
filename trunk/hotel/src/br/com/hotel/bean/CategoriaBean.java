package br.com.hotel.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Categoria;
import br.com.hotel.util.JSFMessageUtil;

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
		try{
			new DAO<Categoria>(Categoria.class).adiciona(this.categoria);
			this.categoria = new Categoria();
		}catch(Exception e){
			e.printStackTrace();
		}
		JSFMessageUtil.sendInfoMessageToUser("Categoria Inserida com sucesso!!!");
		return "categoriaQuarto?faces-redirect=true";
	}

	

}
