package br.com.hotel.teste;

import java.util.Calendar;

import br.com.hotel.dao.DAO;
import br.com.hotel.enumerator.EstadiaStatus;
import br.com.hotel.modelo.Estadia;
import br.com.hotel.modelo.Quarto;
import br.com.hotel.modelo.Usuario;
import br.com.hotel.util.CalendarUtil;

public class TesteEstadia {

	public static void main(String[] args) {
		
		Quarto q = new DAO<Quarto>(Quarto.class).buscaPorId(2);
		Estadia novaEstadia = new Estadia();
		novaEstadia.setCamaExtra(true);
		Calendar hoje = CalendarUtil.retornaDiaDeHoje();
		novaEstadia.setDataInicio(hoje);
		novaEstadia.setDataFim(CalendarUtil.aumentaDias(hoje, 3));
		novaEstadia.setIsAtivo(true);
		novaEstadia.setQuarto(q);
		novaEstadia.setQuartoStatus(EstadiaStatus.RESERVADO);
		
		novaEstadia.setUsuario(new DAO<Usuario>(Usuario.class).buscaPorId(1));

		new DAO<Estadia>(Estadia.class).adiciona(novaEstadia);
		
	}

}
