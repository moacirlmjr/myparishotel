package br.com.hotel.teste;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.hotel.util.CalendarUtil;

public class TesteDatas {
	
	public static void main(String[] args) {
	
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar hoje = CalendarUtil.retornaDiaDeHoje();
		System.out.println(sdf.format(CalendarUtil.aumentaDias(hoje, 20).getTime()));
		
		
	}

}
