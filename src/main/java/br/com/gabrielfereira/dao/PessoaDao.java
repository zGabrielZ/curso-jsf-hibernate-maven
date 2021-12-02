package br.com.gabrielfereira.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielfereira.dao.generico.DaoGenerico;
import br.com.gabrielfereira.entidade.Pessoa;

public class PessoaDao extends DaoGenerico<Pessoa>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public Pessoa logarPessoa(String login, String senha) {
		String jpql = "SELECT p FROM Pessoa p where p.login = :login and p.senha = :senha";
		TypedQuery<Pessoa> query = entityManager.createQuery(jpql, Pessoa.class);
		query.setParameter("login", login);
		query.setParameter("senha", senha);
		
		Pessoa pessoa = verificarNulo(query);
		return pessoa;
	}
	
	public List<Pessoa> getPessoasLimit10(){
		String jpql = "SELECT p from Pessoa p order by p.id desc";
		TypedQuery<Pessoa> query = entityManager.createQuery(jpql,Pessoa.class);
		List<Pessoa> resultados = query.setMaxResults(10).getResultList();
		return resultados;
	}
	
	public List<Pessoa> getRelatorioPessoa(String nome, Date dataInicial, Date dataFinal) {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		StringBuilder filtros = new StringBuilder();
		filtros.append("SELECT p FROM Pessoa p");

		// Caso não foi inserido nenhum registro
		if ((nome == null || nome.isEmpty()) && dataInicial == null && dataFinal == null) {
			pessoas = getPessoasFiltros(null, null, null, filtros.toString());

		// Caso foi so inserido o nome
		} else if ((!nome.isEmpty() || nome != null) && dataInicial == null && dataFinal == null) {
			filtros.append(" where p.nome like :nome");
			pessoas = getPessoasFiltros(nome.trim(), null, null, filtros.toString());

		// Caso foi so inserido a data inicial nascimento
		} else if (dataInicial != null && dataFinal == null && (nome == null || nome.isEmpty())) {
			filtros.append(" where p.dataNascimento >= : dataInicial");
			pessoas = getPessoasFiltros(null, dataInicial, null, filtros.toString());
		}
		// Caso foi so inserido a data final nascimento
		else if (dataFinal != null && dataInicial == null && (nome == null || nome.isEmpty())) {
			filtros.append(" where p.dataNascimento <= : dataFinal");
			pessoas = getPessoasFiltros(null, null, dataFinal, filtros.toString());
		}
		// Caso foi so inserido nome e data inicial nascimento
		else if ((nome != null || !nome.isEmpty()) && dataInicial != null && dataFinal == null) {
			filtros.append(" where p.dataNascimento >= : dataInicial and p.nome like :nome");
			pessoas = getPessoasFiltros(nome.trim(), dataInicial, null, filtros.toString());
		}
		// Caso foi so inserido nome e data final nascimento
		else if ((nome != null || !nome.isEmpty()) && dataInicial == null && dataFinal != null) {
			filtros.append(" where p.dataNascimento <= : dataFinal and p.nome like :nome");
			pessoas = getPessoasFiltros(nome.trim(), null, dataFinal, filtros.toString());
		}
		// Caso foi so inserido data inicial e data final
		else if ((nome == null || nome.isEmpty()) && dataInicial != null && dataFinal != null) {
			filtros.append(" where p.dataNascimento >= : dataInicial and p.dataNascimento <= :dataFinal");
			pessoas = getPessoasFiltros(null, dataInicial, dataFinal, filtros.toString());
		}
		// Caso foi so inserido data inicial, data final e nome
		else if ((nome != null || !nome.isEmpty()) && dataInicial != null && dataFinal != null) {
			filtros.append(
					" where p.dataNascimento >= : dataInicial and p.dataNascimento <= :dataFinal and p.nome like :nome");
			pessoas = getPessoasFiltros(nome, dataInicial, dataFinal, filtros.toString());
		}

		return pessoas;
	}
	
	public List<Pessoa> getPessoasFiltros(String nome,Date dataInicial,Date dataFinal,String jpql){
		TypedQuery<Pessoa> query = entityManager.createQuery(jpql,Pessoa.class);
		
		if(nome != null) {
			query.setParameter("nome", nome + "%");
		}
		
		if(dataInicial != null) {
			query.setParameter("dataInicial", dataInicial);
		}
		
		if(dataFinal != null) {
			query.setParameter("dataFinal", dataFinal);
		}
		
		List<Pessoa> resultados = query.getResultList();
		return resultados;
	}
}
