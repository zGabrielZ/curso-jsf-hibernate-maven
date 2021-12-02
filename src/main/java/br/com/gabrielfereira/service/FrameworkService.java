package br.com.gabrielfereira.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import br.com.gabrielfereira.dao.FrameworkDao;
import br.com.gabrielfereira.entidade.Framework;

public class FrameworkService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FrameworkDao frameworkDao;
	
	public List<SelectItem> getFrameworks(){
		List<Framework> frameworks = frameworkDao.listagem(Framework.class);
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for(Framework framework : frameworks) {
			selectItems.add(new SelectItem(framework, framework.getNome()));
		}
		return selectItems;
	}
	
	public List<Framework> findFrameworkByIdPessoa(Integer idPessoa){
		return frameworkDao.findFrameworkByIdPessoa(idPessoa);
	}
	
	public Framework buscarPorId(Integer id) {
		return frameworkDao.pesquisarPorId(id, Framework.class);
	}
 
}
