package br.com.hotel.dao;

import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.hotel.enumerator.EstadiaStatus;
import br.com.hotel.modelo.Categoria;
import br.com.hotel.modelo.Quarto;
import br.com.hotel.modelo.Estadia;
import br.com.hotel.modelo.Role;
import br.com.hotel.modelo.Usuario;
import br.com.hotel.util.CalendarUtil;

public class PopulaBanco {

	public static void popula() {

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		// criando Roles
		Role roleUser = new Role("User");
		Role roleAdmin = new Role("Admin");
		em.persist(roleUser);
		em.persist(roleAdmin);

		em.getTransaction().commit();

		em.getTransaction().begin();

		// criando usuarios
		Usuario reginaldo = geraUsuario("Reginaldo Farias",
				"reginaldo@hotmail.com", "9898-9898", "reginaldo",
				"reginaldo123", roleUser);
		em.persist(reginaldo);

		Usuario samanta = geraUsuario("Samanta Souza", "samanta@hotmail.com",
				"7979-7979", "samanta", "samanta123", roleUser);
		em.persist(samanta);

		Usuario augusto = geraUsuario("Augusto Santos", "augusto@hotmail.com",
				"9876-5432", "augusto", "augusto123", roleUser);
		em.persist(augusto);

		Usuario claudio = geraUsuario("Claudio Silva", "claudio@gmail.com",
				"8765-4321", "claudio", "claudio123", roleUser);
		em.persist(claudio);

		Usuario mariano = geraUsuario("Mariano Santana", "mariano@gmail.com",
				"7654-3210", "mariano", "mariano123", roleUser);
		em.persist(mariano);

		Usuario admin = geraUsuario("Administrador do Sistema",
				"admin@admin.com", "7654-3210", "admin", "admin123", roleAdmin);
		em.persist(admin);
		em.getTransaction().commit();

		// criando categorias
		Categoria standard = new Categoria("STANDARD", 268.00, 2);
		Categoria vistaBosque = new Categoria("VISTA BOSQUE", 315.00, 4);
		Categoria vistaVale = new Categoria("VISTA VALE", 353.00, 4);
		Categoria suite = new Categoria("SUITE", 498.00, 2);

		em.getTransaction().begin();
		em.persist(standard);
		em.persist(vistaBosque);
		em.persist(vistaVale);
		em.persist(suite);
		em.getTransaction().commit();

		// criando quartos
		Quarto q1 = geraQuartos(1, standard);
		Quarto q2 = geraQuartos(2, standard);
		Quarto q3 = geraQuartos(3, vistaBosque);
		Quarto q4 = geraQuartos(4, vistaBosque);
		Quarto q5 = geraQuartos(5, vistaBosque);
		Quarto q6 = geraQuartos(6, vistaVale);
		Quarto q7 = geraQuartos(7, vistaVale);
		Quarto q8 = geraQuartos(8, vistaVale);
		Quarto q9 = geraQuartos(9, suite);
		Quarto q10 = geraQuartos(10, suite);

		em.getTransaction().begin();
		em.persist(q1);
		em.persist(q2);
		em.persist(q3);
		em.persist(q4);
		em.persist(q5);
		em.persist(q6);
		em.persist(q7);
		em.persist(q8);
		em.persist(q9);
		em.persist(q10);
		em.getTransaction().commit();

		// criando ocupacoes
		Estadia ocupacao1 = geraOcupacao(claudio, q1,
				CalendarUtil.retornaDiaDeHoje(),
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 2),
				true, true);
		Estadia ocupacao2 = geraOcupacao(reginaldo, q4,
				CalendarUtil.diminuiDias(CalendarUtil.retornaDiaDeHoje(), 1),
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 3),
				false, true);

		em.getTransaction().begin();
		em.persist(ocupacao1);
		em.persist(ocupacao2);
		em.getTransaction().commit();

		// criando reservas
		Estadia reserva1 = geraReseva(augusto, q10,
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 5),
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 7),
				false);
		em.getTransaction().begin();
		em.persist(reserva1);
		em.getTransaction().commit();

		em.close();

	}

	private static Usuario geraUsuario(String nome, String email,
			String telefone, String login, String senha, Role role) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setTelefone(telefone);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setRole(role);
		return usuario;
	}

	private static Quarto geraQuartos(int numero, Categoria categoria) {
		Quarto quarto = new Quarto();
		quarto.setCategoria(categoria);
		quarto.setNumero(numero);
		return quarto;
	}

	private static Estadia geraOcupacao(Usuario usuario, Quarto quarto,
			Calendar dataInicio, Calendar dataFim, boolean temCamaExtra,
			boolean isAtiva) {
		Estadia ocupacao = new Estadia();
		ocupacao.setUsuario(usuario);
		ocupacao.setQuarto(quarto);
		ocupacao.setQuartoStatus(EstadiaStatus.OCUPADO);
		ocupacao.setDataInicio(dataInicio);
		ocupacao.setDataFim(dataFim);
		ocupacao.setCamaExtra(temCamaExtra);
		ocupacao.setIsAtivo(isAtiva);
		int n1 = ((temCamaExtra==true) ? quarto.getCategoria().getCapacidade() +1 : quarto.getCategoria().getCapacidade());
		ocupacao.setNumeroDeHospedes(n1);
		return ocupacao;
	}

	private static Estadia geraReseva(Usuario usuario, Quarto quarto,
			Calendar dataInicio, Calendar dataFim, boolean temCamaExtra) {
		Estadia reserva = new Estadia();
		reserva.setUsuario(usuario);
		reserva.setQuarto(quarto);
		reserva.setQuartoStatus(EstadiaStatus.RESERVADO);
		reserva.setDataInicio(dataInicio);
		reserva.setDataFim(dataFim);
		reserva.setIsAtivo(false);
		reserva.setCamaExtra(temCamaExtra);
		int n1 = ((temCamaExtra==true) ? quarto.getCategoria().getCapacidade() +1 : quarto.getCategoria().getCapacidade());
		reserva.setNumeroDeHospedes(n1);
		return reserva;
	}

}
