package br.com.gabrielfereira.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dataNascimentoConverter")
public class DataNascimentoConverter implements Converter{

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Date data = null;
		
		if(value != null && !value.equals("")) {
			// DD/MM/YYYY
			try {
				data = sdf.parse(value);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("frmPessoa:msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Formato da data incorreta, EXEMPLO : (DD/MM/YYYY) !", null));
			}
		}
		
		return data;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Date data = (Date) value;
		String dataFormatada = sdf.format(data);
		return dataFormatada;
	}

}
