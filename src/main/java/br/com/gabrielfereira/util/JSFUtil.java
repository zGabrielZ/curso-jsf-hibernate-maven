package br.com.gabrielfereira.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.gabrielfereira.entidade.Pessoa;

public class JSFUtil {

	public static Pessoa getRecuperarPessoaLogada() {
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);
		Pessoa pessoa = (Pessoa) httpSession.getAttribute("pessoaLogada");
		return pessoa;
	}
	
	public static void enviarMensagemSucesso(String msg, String formulario) {
		FacesContext.getCurrentInstance().addMessage(formulario, new FacesMessage(FacesMessage.SEVERITY_INFO,
				msg, null));
	}
	
	public static void enviarMensagemErro(String msg, String formulario) {
		FacesContext.getCurrentInstance().addMessage(formulario, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, null));
	}
	
	public static void enviarMensagemAtencao(String msg, String formulario) {
		FacesContext.getCurrentInstance().addMessage(formulario, new FacesMessage(FacesMessage.SEVERITY_WARN,
				msg, null));
	}
}
