package br.com.gabrielfereira.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.gabrielfereira.dao.NivelProgramadorDao;
import br.com.gabrielfereira.entidade.NivelProgramador;

public class NivelProgramadorService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private NivelProgramadorDao nivelProgramadorDao;
	
	public List<SelectItem> getNivelProgramadors(){
		List<NivelProgramador> nivelProgramadors = nivelProgramadorDao.listagem(NivelProgramador.class);
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for(NivelProgramador nivelProgramador : nivelProgramadors) {
			selectItems.add(new SelectItem(nivelProgramador, nivelProgramador.getNome()));
		}
		return selectItems;
	}
	
	public NivelProgramador buscarPorId(Integer id) {
		return nivelProgramadorDao.pesquisarPorId(id, NivelProgramador.class);
	}
 
}
