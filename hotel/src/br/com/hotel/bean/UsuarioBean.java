package br.com.hotel.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Usuario;

@ManagedBean
public class UsuarioBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public List<Usuario> getUsuarios() {
		return new DAO<Usuario>(Usuario.class).listaTodos();
	}

	public String gravar() {
		System.out.println("Gravando usuario " + this.usuario.getNome());
		new DAO<Usuario>(Usuario.class).adiciona(this.usuario);
		this.usuario = new Usuario();
		return "/public/login.xhtml";
	}

	public String formUsuario() {
		HttpSession ss = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		ss.setAttribute("usuario", usuario);
		return "usuario.xhtml?faces-redirect=true";
	}

}
