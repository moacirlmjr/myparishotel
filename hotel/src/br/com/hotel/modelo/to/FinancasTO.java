package br.com.hotel.modelo.to;

import java.io.Serializable;
import java.util.List;

import br.com.hotel.dao.DAO;
import br.com.hotel.modelo.Estadia;
import br.com.hotel.util.StringHibernateUtil;

public class FinancasTO implements Serializable{
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

	public void setTotalPessoas(Integer totalPessoas) {
		this.totalPessoas = totalPessoas;
	}

	public Integer getTotalHospedagens() {
		List<Estadia> estadias = new DAO<Estadia>(Estadia.class)
				.findListResults(StringHibernateUtil.FIND_OCUPACOES);

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

	public void setTotalReservas(Integer totalReservas) {
		this.totalReservas = totalReservas;
	}

}