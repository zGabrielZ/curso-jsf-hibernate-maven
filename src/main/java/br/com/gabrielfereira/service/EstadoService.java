package br.com.gabrielfereira.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.gabrielfereira.dao.EstadoDao;
import br.com.gabrielfereira.entidade.Estado;

public class EstadoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoDao estadoDao;
	
	public List<SelectItem> getEstados(){
		List<Estado> estados = estadoDao.listagem(Estado.class);
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for(Estado estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		return selectItems;
	}
	
	public Estado buscarPorId(Integer id) {
		return estadoDao.pesquisarPorId(id, Estado.class);
	}
 
}
