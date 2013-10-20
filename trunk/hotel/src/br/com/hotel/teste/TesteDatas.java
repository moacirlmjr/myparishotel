package br.com.hotel.teste;

import java.text.SimpleDateFormat;

import br.com.hotel.util.CalendarUtil;

public class TesteDatas {
	
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		System.out.println(sdf.format(CalendarUtil.aumentaDias(CalendarUtil.retornaDiaDeHoje(), 20).getTime()));
		
		
	}

}
