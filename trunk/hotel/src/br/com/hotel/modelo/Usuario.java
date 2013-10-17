package br.com.hotel.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@NamedQuery(name = "User.findUserByLogin", query = "select u from Usuario u where u.login = :login")
public class Usuario implements Serializable {

	public static final String FIND_BY_LOGIN = "User.findUserByLogin";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String telefone;
	private String login;
	private String senha;
	
	@OneToOne
	private Role role;

	public Usuario() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public Usuario(String nome, String email, String telefone, String login,
			String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.login = login;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isUser() {
		return (this.role.getRole() == "User" || this.role.getId() == 1);
	}

	public boolean isAdmin() {
		return (this.role.getRole() == "User" || this.role.getId() == 2);
	}
}
