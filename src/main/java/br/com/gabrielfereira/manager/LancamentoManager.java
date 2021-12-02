package br.com.gabrielfereira.manager;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.Lancamento;
import br.com.gabrielfereira.entidade.Pessoa;
import br.com.gabrielfereira.service.LancamentoService;
import br.com.gabrielfereira.service.PessoaService;
import br.com.gabrielfereira.util.JSFUtil;

@Named
@ViewScoped
public class LancamentoManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private LancamentoService lancamentoService;
	
	private Lancamento lancamento;
	
	private List<Lancamento> lancamentos;
	
	private Lancamento lancamentoSelecionado;
	
	@PostConstruct
	public void iniciar() {
		lancamento = new Lancamento();
		lancamentos = lancamentoService.listagemLimit10(JSFUtil.getRecuperarPessoaLogada().getId());
	}
	
	public void novo() {
		lancamento = new Lancamento();
	}
	
	public void cadastro() {
		if(lancamento.getId() == null) {
			inserir();
		} else {
			atualizar();
		}
		
		iniciar();
	}
	
	public void inserir() {
		
		Pessoa pessoa = JSFUtil.getRecuperarPessoaLogada();
		
		lancamento.setPessoa(pessoa);
		lancamentoService.inserir(lancamento);
		
		pessoa.getLancamentos().add(lancamento);
		pessoaService.atualizar(pessoa);
		
		JSFUtil.enviarMensagemSucesso("Inserido com sucesso !", "frmLancamento:msg");
		lancamento = new Lancamento();
	}
	
	public void atualizar() {
		lancamentoService.atualizar(lancamento);
		JSFUtil.enviarMensagemSucesso("Atualizado com sucesso !", "frmLancamento:msg");
		lancamento = new Lancamento();
	}
	
	public String deletar() {
		lancamentoService.remover(lancamentoSelecionado);
		JSFUtil.enviarMensagemSucesso("Removido com sucesso !", "frmLancamento:msg");
		iniciar();
		return "";
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Lancamento getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}

	public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}

}
