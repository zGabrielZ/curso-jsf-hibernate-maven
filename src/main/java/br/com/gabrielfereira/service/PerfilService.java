package br.com.gabrielfereira.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.gabrielfereira.dao.PerfilDao;
import br.com.gabrielfereira.entidade.Perfil;

public class PerfilService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PerfilDao perfilDao;
	
	public List<SelectItem> getPerfils(){
		List<Perfil> Perfils = perfilDao.listagem(Perfil.class);
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for(Perfil perfil : Perfils) {
			selectItems.add(new SelectItem(perfil, perfil.getNome()));
		}
		return selectItems;
	}
	
	public Perfil buscarPorId(Integer id) {
		return perfilDao.pesquisarPorId(id, Perfil.class);
	}
 
}
