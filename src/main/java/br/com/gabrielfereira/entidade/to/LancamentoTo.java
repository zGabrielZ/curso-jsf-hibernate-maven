package br.com.gabrielfereira.entidade.to;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import br.com.gabrielfereira.entidade.Lancamento;

public class LancamentoTo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nota;
	private String empresaOrigem;
	private String empresaDestino;
	private String dataInicial;
	private String dataFinal;
	
	public LancamentoTo(Lancamento lancamento) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		id = lancamento.getId();
		nota = lancamento.getNumeroNotaFiscal();
		empresaOrigem = lancamento.getEmpresaOrigem();
		empresaDestino = lancamento.getEmpresaDestino();
		dataInicial = sdf.format(lancamento.getDataInicial());
		dataFinal = sdf.format(lancamento.getDataFinal());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getEmpresaOrigem() {
		return empresaOrigem;
	}
	public void setEmpresaOrigem(String empresaOrigem) {
		this.empresaOrigem = empresaOrigem;
	}
	public String getEmpresaDestino() {
		return empresaDestino;
	}
	public void setEmpresaDestino(String empresaDestino) {
		this.empresaDestino = empresaDestino;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
	

}
