package br.com.gabrielfereira.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielfereira.dao.generico.DaoGenerico;
import br.com.gabrielfereira.entidade.Linguagem;

public class LinguagemDao extends DaoGenerico<Linguagem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Linguagem> findLinguagemByIdPessoa(Integer id){
		String jpql = "SELECT l FROM Pessoa p join p.linguagens l where p.id = :id";
		TypedQuery<Linguagem> query = entityManager.createQuery(jpql, Linguagem.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
}
