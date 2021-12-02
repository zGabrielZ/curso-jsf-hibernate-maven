package br.com.gabrielfereira.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielfereira.dao.PessoaDao;
import br.com.gabrielfereira.entidade.Pessoa;

@WebServlet("/PessoaImagem")
public class PessoaImagem extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaDao pessoaDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPessoa = request.getParameter("idPessoa");
		if(idPessoa == null || idPessoa.equals("")) {
			return;
		}
		
		Pessoa pesquisarPessoa = new Pessoa();
		pesquisarPessoa.setId(Integer.parseInt(idPessoa));
		
		Pessoa pessoa = pessoaDao.pesquisarPorId(Integer.parseInt(idPessoa),Pessoa.class);
		
		if(pessoa.getCaminhoImagem() != null) {
			File arquivo = new File(pessoa.getCaminhoImagem());
			FileInputStream fileInputStream = new FileInputStream(arquivo);
			byte[] arrayImagem = new byte[(int) arquivo.length()];
			fileInputStream.read(arrayImagem);
			fileInputStream.close();
			
			response.getOutputStream().write(arrayImagem);
			response.getOutputStream().flush();
		}
	}

}