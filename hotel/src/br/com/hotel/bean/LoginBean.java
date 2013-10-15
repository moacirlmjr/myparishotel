package br.com.hotel.bean;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Usuario;
import br.com.hotel.util.JSFMessageUtil;
import br.com.hotel.util.RequestUtil;

@SessionScoped
@ManagedBean
public class LoginBean {
	
	private Usuario usuario;

	public LoginBean() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String logOut() {
		RequestUtil.getRequest().getSession().removeAttribute("usuario");
		RequestUtil.getRequest().getSession().invalidate();
		return "logout";
	}

	public Usuario isValidLogin(String login, String senha) {

		Usuario user = findUserByLogin(login);
		if (user == null || !user.getSenha().equals(senha)) {
			return null;
		}

		return user;
	}

	public String validaLogin() {

		Usuario user = isValidLogin(this.usuario.getLogin(),
				this.usuario.getSenha());
		if (user != null) {
			this.usuario = user;
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			request.getSession().setAttribute("usuario", user);
			return user.isAdmin() ? "relatorioUsuarios" : "checkin";
		}

		JSFMessageUtil.sendErrorMessageToUser("Verifique seu email/password");
		return null;

	}
	
	 public Usuario findUserByLogin(String login){
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("login", login);     
	        Usuario user = new DAO<Usuario>(Usuario.class).findOneResult(Usuario.FIND_BY_LOGIN, parameters);
	        System.out.println();
	        return user;
	    }

}
