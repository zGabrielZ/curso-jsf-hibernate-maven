package br.com.gabrielfereira.converter;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.NivelProgramador;
import br.com.gabrielfereira.service.NivelProgramadorService;

@Named
@RequestScoped
public class NivelConverter implements Converter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private NivelProgramadorService nivelService;
	
	// Retorna um Objeto inteiro
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		NivelProgramador nivel = null;
		if(value != null) {
			if(value.contains("Selecione")) {
				return null;
			} else {
				nivel = nivelService.buscarPorId(new Integer(value));
			}
		}
		return nivel;
	}

	// Retorna apenas o codigo em string
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			NivelProgramador nivel = (NivelProgramador) value;
			return String.valueOf(nivel.getId());
		}
		return null;
	}

}
