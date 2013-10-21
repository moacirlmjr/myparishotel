package br.com.hotel.teste;

import java.util.List;

import br.com.hotel.bean.EstadiaBean;
import br.com.hotel.modelo.Estadia;

public class TestaDAO {
	
	public static void main(String[] args) {
		EstadiaBean eb = new EstadiaBean();
		
		List<Estadia> estadias = eb.getReservasAtuais();
		
		for (Estadia e: estadias){
			System.out.println(e.getUsuario().getNome());
		}
		
		
		
		
	}

}
