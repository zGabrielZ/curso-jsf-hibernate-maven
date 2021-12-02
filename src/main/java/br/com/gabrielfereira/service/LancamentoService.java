package br.com.gabrielfereira.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.gabrielfereira.dao.LancamentoDao;
import br.com.gabrielfereira.entidade.Lancamento;

public class LancamentoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentoDao lancamentoDao;
	
	public void inserir(Lancamento lancamento) {
		lancamentoDao.inserir(lancamento);
	}
	
	public Lancamento atualizar(Lancamento lancamento) {
		return lancamentoDao.atualizar(lancamento);
	}
	
	public Lancamento pesquisarPorId(Lancamento lancamento) {
		return lancamentoDao.pesquisarPorId(lancamento.getId(), Lancamento.class);
	}
	
	public void remover(Lancamento lancamento) {
		lancamentoDao.removerPorId(lancamento.getId(), Lancamento.class);
	}
	
	public List<Lancamento> listagem(Integer idUsuario){
		return lancamentoDao.getLancamentosByUsuario(idUsuario);
	}
	
	public List<Lancamento> listagemLimit10(Integer idUsuario){
		return lancamentoDao.getLancamentosByUsuarioLimit10(idUsuario);
	}
	
	public List<Lancamento> getRelatorioLancamento(Integer idUsuario, String numeroNota, Date dataInicial, Date dataFinal){
		return lancamentoDao.getRelatorioLancamentos(idUsuario, numeroNota, dataInicial, dataFinal);
	}
 
}
