package br.com.hotel.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.hotel.modelo.to.FinancasTO;

@ManagedBean
@ViewScoped
public class FinancasBean {

	private FinancasTO fto = new FinancasTO();

	public FinancasBean() {
		super();
	}

	public FinancasTO getFto() {
		return fto;
	}

	public void setFto(FinancasTO fto) {
		this.fto = fto;
	}

	public Double getTotalRecebido() {

		return this.fto.getTotalRecebido();

	}

	public void setTotalRecebido(Double totalRecebido) {
		this.fto.setTotalRecebido(totalRecebido);
	}

	public Integer getTotalPessoas() {
		return this.fto.getTotalPessoas();
	}

	public void setTotalPessoas(Integer totalPessoas) {
		this.fto.setTotalPessoas(totalPessoas);
	}

	public Integer getTotalHospedagens() {
		return this.fto.getTotalHospedagens();
	}

	public void setTotalHospedagens(Integer totalHospedagens) {
		this.fto.setTotalHospedagens(totalHospedagens);
	}

	public Integer getTotalReservas() {
		return this.fto.getTotalReservas();
	}

	public void setTotalReservas(Integer totalReservas) {
		this.fto.setTotalReservas(totalReservas);
	}
}
