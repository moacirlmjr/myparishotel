package br.com.hotel.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

}
