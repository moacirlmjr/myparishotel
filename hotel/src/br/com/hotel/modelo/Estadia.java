package br.com.hotel.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.hotel.enumerator.EstadiaStatus;
import br.com.hotel.util.CalendarUtil;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Estadia.findAllEstadias", query = "select e from Estadia e order by dataInicio"),
		@NamedQuery(name = "Estadia.findOcupacoesAtuais", query = "select e from Estadia e where dataInicio between :dataHoje and :dataFimSemana and estadiaStatus = 'OCUPADO' order by dataInicio"),
		@NamedQuery(name = "Estadia.findOcupacoes", query = "select e from Estadia e where estadiaStatus = 'OCUPADO' or estadiaStatus = 'DESOCUPADO' order by dataInicio"),
		@NamedQuery(name = "Estadia.findReservasAtuais", query = "select e from Estadia e where dataInicio between :dataHoje and :dataFimSemana and estadiaStatus = 'RESERVADO' order by dataInicio"),
		@NamedQuery(name = "Estadia.findReservas", query = "select e from Estadia e where estadiaStatus = 'RESERVADO' or estadiaStatus = 'CANCELADO' order by dataInicio"),
		@NamedQuery(name = "Estadia.findReservasCanceladas", query = "select e from Estadia e where estadiaStatus = 'CANCELADO' order by dataInicio"),
		@NamedQuery(name = "Estadia.findByUser", query = "select e from Estadia e where usuario.id = :userid order by dataInicio desc"),
		@NamedQuery(name = "Estadia.findOcupacoesUsuario", query = "select e from Estadia e where (estadiaStatus = 'OCUPADO' or estadiaStatus = 'DESOCUPADO') and usuario_id = :uid order by dataInicio"),
		@NamedQuery(name = "Estadia.findReservasUsuario", query = "select e from Estadia e where estadiaStatus = 'RESERVADO' and usuario_id = :uid order by dataInicio") })
public class Estadia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Usuario usuario;

	@OneToOne
	private Quarto quarto;

	@Temporal(TemporalType.DATE)
	private Calendar dataInicio;

	@Temporal(TemporalType.DATE)
	private Calendar dataFim;

	@Enumerated(EnumType.STRING)
	private EstadiaStatus estadiaStatus;
	private Boolean isAtivo;

	private boolean CamaExtra;

	private int numeroDeHospedes;

	public Estadia() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quarto == null) ? 0 : quarto.hashCode());
		result = prime * result + id;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result
				+ ((dataInicio == null) ? 0 : dataInicio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estadia other = (Estadia) obj;
		if (quarto == null) {
			if (other.getQuarto() != null)
				return false;
		} else if (!usuario.getNome().equals(other.getUsuario().getNome()))
			return false;
		return false;

	}

	public Estadia(Usuario usuario, Quarto quarto, Calendar dataInicio,
			Calendar dataFim, boolean camaExtra) {
		super();
		this.usuario = usuario;
		this.quarto = quarto;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		CamaExtra = camaExtra;
		this.numeroDeHospedes = getNumeroDeHospedes();
	}

	public Estadia(int id, Usuario usuario, Quarto quarto, Calendar dataInicio,
			Calendar dataFim, EstadiaStatus estadiaStatus, Boolean isAtivo,
			boolean camaExtra) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.quarto = quarto;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.estadiaStatus = estadiaStatus;
		this.isAtivo = isAtivo;
		CamaExtra = camaExtra;
		this.numeroDeHospedes = getNumeroDeHospedes();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EstadiaStatus getQuartoStatus() {
		return estadiaStatus;
	}

	public void setQuartoStatus(EstadiaStatus estadiaStatus) {
		this.estadiaStatus = estadiaStatus;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Quarto getQuarto() {
		return quarto;
	}

	public void setQuarto(Quarto quarto) {
		this.quarto = quarto;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public int getNumeroDeHospedes() {
		if (CamaExtra) {
			numeroDeHospedes = this.quarto.getCategoria().getCapacidade() + 1;
		} else {
			numeroDeHospedes = this.quarto.getCategoria().getCapacidade();
		}
		return numeroDeHospedes;
	}

	public void setNumeroDeHospedes(int numeroDeHospedes) {
		this.numeroDeHospedes = numeroDeHospedes;
	}

	public boolean isCamaExtra() {
		return CamaExtra;
	}

	public void setCamaExtra(boolean camaExtra) {
		CamaExtra = camaExtra;
	}

	public Boolean getIsAtivo() {
		return isAtivo;
	}

	public void setIsAtivo(Boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

	public Integer getNumeroDias() {
		return CalendarUtil.getTotalDiasHospedagem(this.dataInicio,
				this.dataFim);
	}

	public Double getValorTotal() {
		if (CamaExtra) {
			BigDecimal valor = new BigDecimal(getNumeroDias()
					* this.quarto.getCategoria().getValor() * 1.3);
			valor.setScale(2, BigDecimal.ROUND_DOWN);
			double valorFormatado = valor.doubleValue();
			return valorFormatado;
		} else {
			BigDecimal valor = new BigDecimal(getNumeroDias()
					* this.quarto.getCategoria().getValor());
			valor.setScale(2, BigDecimal.ROUND_DOWN);
			double valorFormatado = valor.doubleValue();
			return valorFormatado;
		}

	}

	public EstadiaStatus getEstadiaStatus() {
		return estadiaStatus;
	}

	public void setEstadiaStatus(EstadiaStatus estadiaStatus) {
		this.estadiaStatus = estadiaStatus;
	}
	
	

}
