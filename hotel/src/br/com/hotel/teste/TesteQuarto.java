package br.com.hotel.teste;

import br.com.hotel.bean.QuartoBean;
import br.com.hotel.modelo.Categoria;

public class TesteQuarto {
	
	public static void main(String[] args) {
		
		//Quarto q1 = new Quarto(1, 101, new Categoria(1, "TEste", 120.00, 2));
		
		QuartoBean qb = new QuartoBean();
		qb.getListaCategorias();
		
		//System.out.println(q1);
		for(Categoria a : qb.getListaCategorias()){
			System.out.println(a.getCategoria());
		}
		
	}

}
