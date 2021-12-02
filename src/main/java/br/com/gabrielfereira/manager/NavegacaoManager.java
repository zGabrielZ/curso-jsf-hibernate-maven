package br.com.gabrielfereira.manager;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class NavegacaoManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void relatorioLancamento() {
		ExternalContext externalContext = getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/relatorio/RelatorioLancamento.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void relatorioUsuario() {
		ExternalContext externalContext = getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/relatorio/RelatorioUsuario.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cadastroPessoa() {
		ExternalContext externalContext = getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/pessoa/Pessoa.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void consultarPessoa() {
		ExternalContext externalContext = getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/pessoa/ConsultaPessoa.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cadastroLancamento() {
		ExternalContext externalContext = getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/lancamentos/Lancamento.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
