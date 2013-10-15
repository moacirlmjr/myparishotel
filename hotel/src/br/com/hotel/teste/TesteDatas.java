package br.com.hotel.teste;

import java.util.Calendar;

import br.com.hotel.util.CalendarUtil;

public class TesteDatas {
	
	public static void main(String[] args) {
		Calendar dataDehoje = CalendarUtil.aumentaDias(Calendar.getInstance(), 0);
		Calendar dataDepoisDeAmanha = CalendarUtil.aumentaDias(Calendar.getInstance(), 2);
		
		int dias = CalendarUtil.getTotalDiasHospedagem(dataDehoje, dataDepoisDeAmanha);
		
		System.out.println("dias: " + dias);

		
		
	}

}
