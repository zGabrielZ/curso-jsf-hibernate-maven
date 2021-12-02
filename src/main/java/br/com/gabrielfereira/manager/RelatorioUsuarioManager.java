package br.com.gabrielfereira.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.Pessoa;
import br.com.gabrielfereira.entidade.to.PessoaTo;
import br.com.gabrielfereira.service.PessoaService;
import br.com.gabrielfereira.service.RelatorioService;
import br.com.gabrielfereira.util.JSFUtil;

@Named(value = "relatorioUsuarioManager")
@ViewScoped
public class RelatorioUsuarioManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private RelatorioService relatorioService;
	
	private String nome;
	private Date dataInicioNascimento;
	private Date dataFimNascimento;
	
	private List<Pessoa> pessoas;

	public void consultarPessoa() {
		pessoas = pessoaService.getRelatorioPessoas(nome, dataInicioNascimento, dataFimNascimento);
		if(pessoas.isEmpty()) {
			JSFUtil.enviarMensagemAtencao("Nenhum registro encontrado !", "frmPessoa:msg");
		}
	}
	
	public void gerarRelatorioPessoa() {
		if(!pessoas.isEmpty()) {
			List<PessoaTo> pessoaTos = new ArrayList<PessoaTo>();
			for(Pessoa pessoa : pessoas) {
				PessoaTo pessoaTo = new PessoaTo(pessoa);
				pessoaTos.add(pessoaTo);
			}
			
			relatorioService.gerarRelatorioLancamento(null,pessoaTos);
		}
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicioNascimento() {
		return dataInicioNascimento;
	}

	public void setDataInicioNascimento(Date dataInicioNascimento) {
		this.dataInicioNascimento = dataInicioNascimento;
	}

	public Date getDataFimNascimento() {
		return dataFimNascimento;
	}

	public void setDataFimNascimento(Date dataFimNascimento) {
		this.dataFimNascimento = dataFimNascimento;
	}

	
	
}
