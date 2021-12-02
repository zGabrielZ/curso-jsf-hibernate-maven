package br.com.gabrielfereira.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.gabrielfereira.dao.LinguagemDao;
import br.com.gabrielfereira.entidade.Linguagem;

public class LinguagemService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LinguagemDao linguagemDao;
	
	public List<SelectItem> getLinguagems(){
		List<Linguagem> linguagems = linguagemDao.listagem(Linguagem.class);
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for(Linguagem linguagem : linguagems) {
			selectItems.add(new SelectItem(linguagem, linguagem.getNome()));
		}
		return selectItems;
	}
	
	public List<Linguagem> findLinguagemByIdPessoa(Integer idPessoa){
		return linguagemDao.findLinguagemByIdPessoa(idPessoa);
	}
	
	public Linguagem buscarPorId(Integer id) {
		return linguagemDao.pesquisarPorId(id, Linguagem.class);
	}
 
}
