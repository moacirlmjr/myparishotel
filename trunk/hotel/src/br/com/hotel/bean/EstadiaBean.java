package br.com.hotel.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.primefaces.event.FlowEvent;

import br.com.hotel.dao.DAO;
import br.com.hotel.dao.JPAUtil;
import br.com.hotel.enumerator.EstadiaStatus;
import br.com.hotel.modelo.Categoria;
import br.com.hotel.modelo.Estadia;
import br.com.hotel.modelo.Quarto;
import br.com.hotel.modelo.Role;
import br.com.hotel.modelo.Usuario;
import br.com.hotel.util.CalendarUtil;
import br.com.hotel.util.MailUtil;

@ManagedBean
@SessionScoped
public class EstadiaBean implements Serializable{

	private Estadia estadia = new Estadia();
	private int numeroDeHospedes;
	private Integer numeroDias;
	private Integer categoriaId;
	private boolean renderizarQuartosDisponiveis;
	private List<Quarto> quartos;
	private Quarto selectQuarto;
	private boolean skip;
	private boolean cadastroPesquisaUsuario;
	private Usuario usuario;

	public EstadiaBean() {
		selectQuarto = new Quarto();
		categoriaId = 0;
		usuario = new Usuario();
		cadastroPesquisaUsuario = false;
		estadia.setDataInicio(Calendar.getInstance());
		quartos = new LinkedList<Quarto>();
		renderizarQuartosDisponiveis = false;
	}

	public Integer getNumeroDias() {
		return numeroDias;
	}

	public void setNumeroDias(Integer numeroDias) {
		this.numeroDias = numeroDias;
	}

	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}

	public Estadia getEstadia() {
		return this.estadia;
	}

	public List<Estadia> getReservasAtuais() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(true);
		Usuario user = (Usuario) httpSession.getAttribute("usuario");
		Map<String, Object> params = new HashMap<String, Object>();
		if (user.isAdmin()) {
			params.put("dataHoje", CalendarUtil.retornaDiaDeHoje());
			params.put("dataFimSemana", CalendarUtil.aumentaDias(
					CalendarUtil.retornaDiaDeHoje(), 7));
			return new DAO<Estadia>(Estadia.class).findListResults(
					"Estadia.findReservasAtuais", params);
		}
		params.put("usuario.id", user.getId());
		return new DAO<Estadia>(Estadia.class).findListResults(
				"Estadia.findByUser", params);
	}

	public List<Estadia> getOcupacoesAtuais() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataHoje", CalendarUtil.retornaDiaDeHoje());
		params.put("dataFimSemana",
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 7));
		return new DAO<Estadia>(Estadia.class).findListResults(
				"Estadia.findOcupacoesAtuais", params);
	}

	public List<Estadia> getReservasUsuario() {
		Map<String, Object> params = new HashMap<String, Object>();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		params.put("uid", user.getId());
		return new DAO<Estadia>(Estadia.class).findListResults(
				"Estadia.findReservasUsuario", params);
	}

	public List<Estadia> getOcupacoesUsuario() {
		Map<String, Object> params = new HashMap<String, Object>();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		params.put("uid", user.getId());
		System.out.println(user.getNome()
				+ "**********************************");
		return new DAO<Estadia>(Estadia.class).findListResults(
				"Estadia.findOcupacoesUsuario", params);
	}

	public List<Estadia> getOcupacoes() {

		return new DAO<Estadia>(Estadia.class)
				.findListResults("Estadia.findOcupacoes");

	}

	public List<Estadia> getReservas() {
		return new DAO<Estadia>(Estadia.class)
				.findListResults("Estadia.findReservas");
	}

	public String gravarReserva() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpSession httpSession = request.getSession(true);
			Usuario user = (Usuario) httpSession.getAttribute("usuario");

			user = new DAO<Usuario>(Usuario.class).buscaPorId(user.getId());
			this.estadia.setUsuario(user);
			this.estadia.setDataFim(CalendarUtil.aumentaDias(
					this.estadia.getDataInicio(), this.getNumeroDias()));
			this.estadia.setQuartoStatus(EstadiaStatus.RESERVADO);
			this.estadia.setIsAtivo(false);
			Quarto q = new DAO<Quarto>(Quarto.class).buscaPorId(selectQuarto
					.getId());
			this.estadia.setQuarto(q);
			new DAO<Estadia>(Estadia.class).adiciona(this.estadia);
			this.estadia = new Estadia();
			selectQuarto = new Quarto();

			categoriaId = 0;
			estadia.setDataInicio(Calendar.getInstance());
			quartos = new LinkedList<Quarto>();
		} catch (Exception e) {
			System.out.println("Deus nos acudaaaaaa!!!!!!");
			e.printStackTrace();
		}

		renderizarQuartosDisponiveis = false;
		return "relatorioReservasUsuario";
	}
	
	public void gravarUsuario(ActionEvent ev) {
		Role role = new DAO<Role>(Role.class).buscaPorId(1);
		this.usuario.setRole(role);
		this.usuario = new DAO<Usuario>(Usuario.class).adiciona(this.usuario);
		FacesContext fcCtxt = FacesContext.getCurrentInstance();
		FacesMessage mensagem = new FacesMessage();

        mensagem.setSummary("Usuário Cadastrado: " + usuario.getLogin());
        mensagem.setSeverity(FacesMessage.SEVERITY_INFO);

		fcCtxt.addMessage(null, mensagem);
	}
	
	public String gravarCheckin() {
		try {
			this.estadia.setUsuario(usuario);
			this.estadia.setDataFim(CalendarUtil.aumentaDias(
					this.estadia.getDataInicio(), this.getNumeroDias()));
			this.estadia.setQuartoStatus(EstadiaStatus.OCUPADO);
			this.estadia.setIsAtivo(true);
			Quarto q = new DAO<Quarto>(Quarto.class).buscaPorId(selectQuarto
					.getId());
			this.estadia.setQuarto(q);
			new DAO<Estadia>(Estadia.class).adiciona(this.estadia);
			
			this.estadia = new Estadia();
			this.usuario = new Usuario();
			selectQuarto = new Quarto();
			cadastroPesquisaUsuario = false;
			categoriaId = 0;
			estadia.setDataInicio(Calendar.getInstance());
			quartos = new LinkedList<Quarto>();
		} catch (Exception e) {
			System.out.println("Deus nos acudaaaaaa!!!!!!");
			e.printStackTrace();
		}

		renderizarQuartosDisponiveis = false;
		return "";
	}

	public void validaDataInicio(FacesContext context, UIComponent component,
			Object value) {

		Date dataInicio = (Date) value;

		Date hoje = Calendar.getInstance().getTime();

		if (hoje.before(dataInicio)) {
			// this.reserva.setDataInicio((Calendar) value);
		} else {
			throw new ValidatorException(new FacesMessage(
					"Data inicial eh menor ou igual a hoje"));
		}

	}

	public void pesquisarReserva(ActionEvent ae) {
		List<Estadia> listEstadias = new DAO<Estadia>(Estadia.class)
				.listaTodos();
		List<Quarto> listQuartos = new DAO<Quarto>(Quarto.class).listaTodos();
		renderizarQuartosDisponiveis = true;
		quartos = new LinkedList<Quarto>();
		for (Quarto q : listQuartos) {
			boolean quartoSemEstadia = true;
			for (Estadia es : listEstadias) {
				if (q.getId() == es.getQuarto().getId()
						&& es.getDataFim().getTimeInMillis() > estadia
								.getDataInicio().getTimeInMillis()) {
					quartoSemEstadia = false;
					break;
				}
			}
			if (quartoSemEstadia) {
				if (categoriaId != 0) {
					if (q.getCategoria().getId() == categoriaId)
						quartos.add(q);
				} else {
					quartos.add(q);
				}
			}
		}

	}

	public List<Categoria> getListaCategorias() {

		EntityManager em = new JPAUtil().getEntityManager();

		CriteriaQuery<Categoria> query = em.getCriteriaBuilder().createQuery(
				Categoria.class);
		query.select(query.from(Categoria.class));
		List<Categoria> lista = em.createQuery(query).getResultList();
		em.close();

		return lista;

	}

	public void cancelarCheckinUser(ActionEvent av) {
		this.estadia = new Estadia();
		this.usuario = new Usuario();
		selectQuarto = new Quarto();
		cadastroPesquisaUsuario = false;
		categoriaId = 0;
		estadia.setDataInicio(Calendar.getInstance());
		quartos = new LinkedList<Quarto>();
	}
	
	public void escolherUsuario(){
		FacesContext fcCtxt = FacesContext.getCurrentInstance();
		FacesMessage mensagem = new FacesMessage();

        mensagem.setSummary("Usuário Escolhido: " + usuario.getLogin());
        mensagem.setSeverity(FacesMessage.SEVERITY_INFO);

		fcCtxt.addMessage(null, mensagem);
	}
	
	public void escolherQuarto(){
		FacesContext fcCtxt = FacesContext.getCurrentInstance();
		FacesMessage mensagem = new FacesMessage();

        mensagem.setSummary("Quarto Escolhido: " + selectQuarto.getNumero() + " R$" + selectQuarto.getCategoria().getValor());
        mensagem.setSeverity(FacesMessage.SEVERITY_INFO);

		fcCtxt.addMessage(null, mensagem);
	}
	
	public String prepararCadastroUsuario() {
		usuario = new Usuario();
		cadastroPesquisaUsuario = true;
		return "" ;
	}
	
	public List<Usuario> getUsuarios() {
		return new DAO<Usuario>(Usuario.class).listaTodos();
	}

	public int getNumeroDeHospedes() {
		if (this.estadia.isCamaExtra() == true) {
			this.numeroDeHospedes = this.estadia.getQuarto().getCategoria()
					.getCapacidade() + 1;
		} else {
			this.numeroDeHospedes = this.estadia.getQuarto().getCategoria()
					.getCapacidade();
		}
		return this.numeroDeHospedes;
	}

	public void setNumeroDeHospedes(int numeroDeHospedes) {
		this.numeroDeHospedes = numeroDeHospedes;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public boolean isRenderizarQuartosDisponiveis() {
		return renderizarQuartosDisponiveis;
	}

	public void setRenderizarQuartosDisponiveis(
			boolean renderizarQuartosDisponiveis) {
		this.renderizarQuartosDisponiveis = renderizarQuartosDisponiveis;
	}

	public List<Quarto> getQuartos() {
		return quartos;
	}

	public void setQuartos(List<Quarto> quartos) {
		this.quartos = quartos;
	}

	public Quarto getSelectQuarto() {
		return selectQuarto;
	}

	public void setSelectQuarto(Quarto selectQuarto) {
		this.selectQuarto = selectQuarto;
	}

	public String transformaEmOcupacao() {
		this.estadia.setQuartoStatus(EstadiaStatus.OCUPADO);
		new DAO<Estadia>(Estadia.class).atualiza(this.estadia);
		return "relatorioOcupacoesAtuais";

	}

	public String liberaOcupacao() {
		this.estadia.setQuartoStatus(EstadiaStatus.DESOCUPADO);
		new DAO<Estadia>(Estadia.class).atualiza(this.estadia);
		return "relatorioOcupacoes";

	}
	
	public String excluiReserva() {
		new DAO<Estadia>(Estadia.class).remove(this.estadia);
		return "relatorioReservasUsuario";

	}
	
	public String liberaOcupacaoEmail() throws EmailException {
		MailUtil mu = new MailUtil();
		mu.enviaEmailSimples(this.estadia);
		return "relatorioOcupacoes";

	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {

		
		
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			if(event.getOldStep().equals("cadastro")){
				this.estadia = new Estadia();
				selectQuarto = new Quarto();
				renderizarQuartosDisponiveis = false;
				estadia.setDataInicio(Calendar.getInstance());
				quartos = new LinkedList<Quarto>();
			}
			return event.getNewStep();
		}
	}

	public boolean isCadastroPesquisaUsuario() {
		return cadastroPesquisaUsuario;
	}

	public void setCadastroPesquisaUsuario(boolean cadastroPesquisaUsuario) {
		this.cadastroPesquisaUsuario = cadastroPesquisaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
