package br.com.gabrielfereira.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.gabrielfereira.entidade.Pessoa;

public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		HttpSession session = httpServletRequest.getSession();
		Pessoa pessoaLogado = (Pessoa) session.getAttribute("pessoaLogada");
		String url = httpServletRequest.getRequestURL().toString();
		
		if(pessoaLogado == null && !url.contains("/Login.xhtml")) {
			httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/Login.xhtml");
		} else {
			chain.doFilter(httpServletRequest, httpServletResponse);
		}
		
		
	}

}
