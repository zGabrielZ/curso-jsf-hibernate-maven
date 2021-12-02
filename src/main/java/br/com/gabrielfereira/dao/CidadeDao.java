package br.com.gabrielfereira.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielfereira.dao.generico.DaoGenerico;
import br.com.gabrielfereira.entidade.Cidade;

public class CidadeDao extends DaoGenerico<Cidade>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Cidade> getCidadesPorIdEstado(Integer idEstado){
		String jpql = "SELECT c FROM Cidade c join c.estado e where e.id = :idEstado";
		TypedQuery<Cidade> query = entityManager.createQuery(jpql,Cidade.class);
		query.setParameter("idEstado", idEstado);
		List<Cidade> cidades = query.getResultList();
		return cidades;
	}
}
