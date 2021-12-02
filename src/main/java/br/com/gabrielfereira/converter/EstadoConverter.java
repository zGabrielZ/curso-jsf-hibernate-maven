package br.com.gabrielfereira.converter;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.Estado;
import br.com.gabrielfereira.service.EstadoService;

@Named
@RequestScoped
public class EstadoConverter implements Converter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoService estadoService;
	
	// Retorna um Objeto inteiro
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		Estado estado = null;
		if(codigoEstado != null) {
			if(codigoEstado.contains("Selecione")) {
				return null;
			} else {
				estado = estadoService.buscarPorId(new Integer(codigoEstado));
			}
		}
		return estado;
	}
	

	// Retorna apenas o codigo em string
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		if(estado != null) {
			Estado novoEstado = (Estado) estado;
			return String.valueOf(novoEstado.getId());
		}
		return null;
	}

}
