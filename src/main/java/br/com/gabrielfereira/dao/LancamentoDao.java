package br.com.gabrielfereira.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielfereira.dao.generico.DaoGenerico;
import br.com.gabrielfereira.entidade.Lancamento;

public class LancamentoDao extends DaoGenerico<Lancamento>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Lancamento> getLancamentosByUsuario(Integer idUsuario){
		String jpql = "SELECT l FROM Lancamento l join l.pessoa p where p.id = :idUsuario";
		TypedQuery<Lancamento> query = entityManager.createQuery(jpql,Lancamento.class);
		query.setParameter("idUsuario", idUsuario);
		List<Lancamento> resultados = query.getResultList();
		return resultados;
	}
	
	public List<Lancamento> getLancamentosByUsuarioLimit10(Integer idUsuario){
		String jpql = "SELECT l FROM Lancamento l join l.pessoa p where p.id = :idUsuario order by l.id desc";
		TypedQuery<Lancamento> query = entityManager.createQuery(jpql,Lancamento.class);
		query.setParameter("idUsuario", idUsuario);
		List<Lancamento> resultados = query.setMaxResults(10).getResultList();
		return resultados;
	}
	
	public List<Lancamento> getRelatorioLancamentos(Integer idUsuario, String numeroNota, Date dataInicial, Date dataFinal){
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		StringBuilder filtros = new StringBuilder();
		filtros.append("SELECT l FROM Lancamento l join l.pessoa p where p.id = :idUsuario");
		
		// Caso não foi inserido nenhum registro
		if((numeroNota == null || numeroNota.equals("")) && dataInicial == null && dataFinal == null) {
			lancamentos = getLancamentosFiltros(idUsuario,null,null,null,filtros.toString());
		
		// Caso foi so inserido o numero da nota
		} else if ((!numeroNota.isEmpty() || numeroNota != null) && dataInicial == null && dataFinal == null) {
			filtros.append(" and l.numeroNotaFiscal = :numeroNota");
			lancamentos = getLancamentosFiltros(idUsuario,numeroNota.trim(),null,null,filtros.toString());
			
		// Caso foi so inserido a data inicial
		} else if (dataInicial != null && dataFinal == null && (numeroNota == null || numeroNota.isEmpty() )) {
			filtros.append(" and l.dataInicial >= : dataInicial");
			lancamentos = getLancamentosFiltros(idUsuario,null,dataInicial,null,filtros.toString());
		}
		// Caso foi so inserido a data final
		else if(dataFinal != null && dataInicial == null && (numeroNota == null || numeroNota.isEmpty())){
			filtros.append(" and l.dataFinal <= : dataFinal");
			lancamentos = getLancamentosFiltros(idUsuario,null,null,dataFinal,filtros.toString());
		}
		// Caso foi so inserido numero e data inicial
		else if((numeroNota != null || !numeroNota.isEmpty()) && dataInicial != null && dataFinal == null) {
			filtros.append(" and l.dataInicial >= : dataInicial and l.numeroNotaFiscal = :numeroNota");
			lancamentos = getLancamentosFiltros(idUsuario, numeroNota.trim(), dataInicial, null, filtros.toString());
		}
		// Caso foi so inserido numero e data final 
		else if((numeroNota != null || !numeroNota.isEmpty()) && dataInicial == null && dataFinal != null) {
			filtros.append(" and l.dataFinal <= : dataFinal and l.numeroNotaFiscal = :numeroNota");
			lancamentos = getLancamentosFiltros(idUsuario, numeroNota.trim(),null, dataFinal, filtros.toString());
		}
		// Caso foi so inserido data inicial e data final 
		else if((numeroNota == null || numeroNota.isEmpty()) && dataInicial != null && dataFinal != null) {
			filtros.append(" and l.dataInicial >= : dataInicial and l.dataFinal <= :dataFinal");
			lancamentos = getLancamentosFiltros(idUsuario,null,dataInicial, dataFinal, filtros.toString());
		}
		// Caso foi so inserido data inicial, data final e numero
		else if((numeroNota != null || !numeroNota.isEmpty()) && dataInicial != null && dataFinal != null) {
			filtros.append(" and l.dataInicial >= : dataInicial and l.dataFinal <= :dataFinal and l.numeroNotaFiscal = :numeroNota");
			lancamentos = getLancamentosFiltros(idUsuario,numeroNota,dataInicial, dataFinal, filtros.toString());
		}
		
		
		return lancamentos;
	}
	
	public List<Lancamento> getLancamentosFiltros(Integer idUsuario,String numeroNota,Date dataInicial,Date dataFinal,String jpql){
		TypedQuery<Lancamento> query = entityManager.createQuery(jpql,Lancamento.class);
		query.setParameter("idUsuario", idUsuario);
		
		if(numeroNota != null) {
			query.setParameter("numeroNota", numeroNota);
		}
		
		if(dataInicial != null) {
			query.setParameter("dataInicial", dataInicial);
		}
		
		if(dataFinal != null) {
			query.setParameter("dataFinal", dataFinal);
		}
		
		List<Lancamento> resultados = query.getResultList();
		return resultados;
	}
}
