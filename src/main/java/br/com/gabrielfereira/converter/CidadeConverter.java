package br.com.gabrielfereira.converter;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.Cidade;
import br.com.gabrielfereira.service.CidadeService;

@Named
@RequestScoped
public class CidadeConverter implements Converter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CidadeService cidadeService;
	
	// Retorna um Objeto inteiro
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cidade cidade = null;
		if(value != null) {
			if(value.contains("Selecione")) {
				return null;
			} else {
				cidade = cidadeService.buscarPorId(new Integer(value));
			}
		}
		return cidade;
	}

	// Retorna apenas o codigo em string
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Cidade cidade = (Cidade) value;
			return String.valueOf(cidade.getId());
		}
		return null;
	}

}
