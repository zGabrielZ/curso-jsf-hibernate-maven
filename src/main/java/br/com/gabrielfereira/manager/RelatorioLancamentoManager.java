package br.com.gabrielfereira.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielfereira.entidade.Lancamento;
import br.com.gabrielfereira.entidade.to.LancamentoTo;
import br.com.gabrielfereira.service.LancamentoService;
import br.com.gabrielfereira.service.RelatorioService;
import br.com.gabrielfereira.util.JSFUtil;

@Named(value = "relatorioLancamentoManager")
@ViewScoped
public class RelatorioLancamentoManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentoService lancamentoService;
	
	@Inject
	private RelatorioService relatorioService;
	
	private Date dataInicial;
	private Date dataFinal;
	private String numeroNotaFiscal;
	
	private List<Lancamento> lancamentos;

	public void consultarLancamento() {
		lancamentos = lancamentoService.getRelatorioLancamento(JSFUtil.getRecuperarPessoaLogada().getId(), 
					numeroNotaFiscal, dataInicial, dataFinal);
		if(lancamentos.isEmpty()) {
			JSFUtil.enviarMensagemAtencao("Nenhum registro encontrado !", "frmLancamento:msg");
		}
	}
	
	public void gerarRelatorioLancamento() {
		if(!lancamentos.isEmpty()) {
			List<LancamentoTo> lancamentoTos = new ArrayList<LancamentoTo>();
			for(Lancamento lancamento : lancamentos) {
				LancamentoTo lancamentoTo = new LancamentoTo(lancamento);
				lancamentoTos.add(lancamentoTo);
			}
			
			relatorioService.gerarRelatorioLancamento(lancamentoTos,null);
		}
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	
	
}
