package br.com.hotel.modelo.to;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Estadia;
import br.com.hotel.util.CalendarUtil;
import br.com.hotel.util.StringHibernateUtil;

public class FinancasTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Double totalRecebido;
	public Integer totalPessoas;
	public Integer totalHospedagens;
	public Integer totalReservas;

	public FinancasTO() {
	}

	public Double getTotalRecebido() {

		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES);

		Double totalRec = 0.0;

		for (Estadia e : estadias) {
			totalRec = totalRec + e.getValorTotal();
		}

		totalRec = (double) Math.round(totalRec);

		this.totalRecebido = totalRec;

		return this.totalRecebido;

	}

	public Double getTotalRecebidoAtual() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataHoje", CalendarUtil.retornaDiaDeHoje());
		params.put("dataFimSemana",
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 7));

		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES_ATUAIS,
						params);

		Double totalRec = 0.0;

		for (Estadia e : estadias) {
			totalRec = totalRec + e.getValorTotal();
		}

		totalRec = (double) Math.round(totalRec);

		this.totalRecebido = totalRec;

		return this.totalRecebido;

	}

	public void setTotalRecebido(Double totalRecebido) {
		this.totalRecebido = totalRecebido;
	}

	public Integer getTotalPessoas() {
		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES);

		int totalPes = 0;

		for (Estadia e : estadias) {
			totalPes = totalPes + e.getNumeroDeHospedes();
		}

		this.totalPessoas = totalPes;

		return this.totalPessoas;
	}

	public Integer getTotalPessoasAtuais() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataHoje", CalendarUtil.retornaDiaDeHoje());
		params.put("dataFimSemana",
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 7));

		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES_ATUAIS,
						params);

		int totalPes = 0;

		for (Estadia e : estadias) {
			totalPes = totalPes + e.getNumeroDeHospedes();
		}

		this.totalPessoas = totalPes;

		return this.totalPessoas;
	}

	public void setTotalPessoas(Integer totalPessoas) {
		this.totalPessoas = totalPessoas;
	}

	public Integer getTotalHospedagens() {
		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES);

		int totalHosp = estadias.size();
		return totalHosp;
	}

	public Integer getTotalHospedagensAtuais() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataHoje", CalendarUtil.retornaDiaDeHoje());
		params.put("dataFimSemana",
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 7));

		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES_ATUAIS,
						params);

		int totalHosp = estadias.size();
		return totalHosp;
	}

	public void setTotalHospedagens(Integer totalHospedagens) {
		this.totalHospedagens = totalHospedagens;
	}

	public Integer getTotalReservas() {
		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_RESERVAS);
		this.totalReservas = estadias.size();
		return this.totalReservas;
	}

	public Integer getTotalReservasAtuais() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataHoje", CalendarUtil.retornaDiaDeHoje());
		params.put("dataFimSemana",
				CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 7));

		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_RESERVAS_ATUAIS,
						params);
		this.totalReservas = estadias.size();
		return this.totalReservas;
	}

	public void setTotalReservas(Integer totalReservas) {
		this.totalReservas = totalReservas;
	}

}