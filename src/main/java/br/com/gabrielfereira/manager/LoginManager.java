package br.com.gabrielfereira.manager;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


import br.com.gabrielfereira.entidade.Pessoa;
import br.com.gabrielfereira.service.PessoaService;
import br.com.gabrielfereira.util.JSFUtil;

@Named
@SessionScoped
public class LoginManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaService pessoaService;
	
	private Pessoa pessoa;
	
	@PostConstruct
	public void iniciar() {
		pessoa = new Pessoa();
	}
	
	public String logar() {
		Pessoa pessoaLogada = pessoaService.logarPessoa(pessoa.getLogin(), pessoa.getSenha());
		if(pessoaLogada != null) {
			HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			this.pessoa = pessoaLogada;
			httpSession.setAttribute("pessoaLogada", pessoaLogada);
			return "/pessoa/Pessoa.xhtml?faces-redirect=true";
		}
		JSFUtil.enviarMensagemErro("Login ou senha inválida !", "frmLoginPessoa:msg");
		return null;
	}
	
	public String logout() {
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);
		httpSession.removeAttribute("pessoaLogada");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/Login.xhtml?faces-redirect=true";
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
