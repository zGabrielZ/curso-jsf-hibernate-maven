package br.com.gabrielfereira.converter;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.Perfil;
import br.com.gabrielfereira.service.PerfilService;

@Named
@RequestScoped
public class PerfilConverter implements Converter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PerfilService perfilService;
	
	// Retorna um Objeto inteiro
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Perfil perfil = null;
		if(value != null) {
			if(value.contains("Selecione")) {
				return null;
			} else {
				perfil = perfilService.buscarPorId(new Integer(value));
			}
		}
		return perfil;
	}

	// Retorna apenas o codigo em string
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Perfil perfil = (Perfil) value;
			return String.valueOf(perfil.getId());
		}
		return null;
	}

}
