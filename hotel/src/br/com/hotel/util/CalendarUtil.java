package br.com.hotel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class CalendarUtil {

	public static Calendar parseData(String data) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Calendar aumentaDias(Calendar data, int qtde) {
		Calendar nova_data = data;
		nova_data.add(Calendar.DAY_OF_YEAR, qtde);
		return nova_data;
	}
	
	public static Calendar diminuiDias(Calendar data, int qtde) {
		Calendar nova_data = data;
		nova_data.add(Calendar.DAY_OF_YEAR, -qtde);
		return nova_data;
	}

	public static Calendar retornaDiaDeHoje() {
		Calendar hoje = Calendar.getInstance();
		return hoje;
	}
	
	public static int getTotalDiasHospedagem(Calendar dataIni, Calendar dataFim){
		
		DateTime datafim = new DateTime(dataFim.getTimeInMillis());
		DateTime dataini = new DateTime(dataIni.getTimeInMillis());
		int dias = Days.daysBetween(dataini, datafim).getDays();
		return dias;
		
	}
	
	

}
