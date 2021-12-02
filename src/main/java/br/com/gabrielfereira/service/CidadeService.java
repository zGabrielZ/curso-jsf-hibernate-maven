package br.com.gabrielfereira.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.gabrielfereira.dao.CidadeDao;
import br.com.gabrielfereira.entidade.Cidade;

public class CidadeService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CidadeDao cidadeDao;
	
	public List<SelectItem> getCidadesPorEstado(Integer idEstado){
		List<Cidade> cidades = cidadeDao.getCidadesPorIdEstado(idEstado);
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for(Cidade cidade : cidades) {
			selectItems.add(new SelectItem(cidade, cidade.getNome()));
		}
		return selectItems;
	}
	
	public Cidade buscarPorId(Integer id) {
		return cidadeDao.pesquisarPorId(id, Cidade.class);
	}
 
}
