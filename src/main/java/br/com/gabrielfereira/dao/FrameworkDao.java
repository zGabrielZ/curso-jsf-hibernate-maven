package br.com.gabrielfereira.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielfereira.dao.generico.DaoGenerico;
import br.com.gabrielfereira.entidade.Framework;

public class FrameworkDao extends DaoGenerico<Framework>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Framework> findFrameworkByIdPessoa(Integer id){
		String jpql = "SELECT f FROM Pessoa p join p.frameworks f where p.id = :id";
		TypedQuery<Framework> query = entityManager.createQuery(jpql, Framework.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
}
