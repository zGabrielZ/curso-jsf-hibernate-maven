package br.com.gabrielfereira.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;

import br.com.gabrielfereira.dao.PessoaDao;
import br.com.gabrielfereira.entidade.Endereco;
import br.com.gabrielfereira.entidade.Pessoa;
import br.com.gabrielfereira.exception.RegraDeNegocioException;

public class PessoaService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaDao pessoaDao;
	
	public void inserir(Pessoa pessoa) {
		pessoa.setNomeCompleto(pessoa.getNome() + " " + pessoa.getSobrenome());
		verificarNomeSalvo(listagem(), pessoa);
		verificarLoginSalvo(listagem(), pessoa);
		pessoaDao.inserir(pessoa);
	}
	
	public Pessoa atualizar(Pessoa pessoa) {
		pessoa.setNomeCompleto(pessoa.getNome() + " " + pessoa.getSobrenome());
		verificarNomeSalvoAtualizar(listagem(), pessoa);
		verificarLoginSalvoAtualizar(listagem(), pessoa);
		return pessoaDao.atualizar(pessoa);
	}
	
	public Pessoa pesquisarPorId(Pessoa pessoa) {
		return pessoaDao.pesquisarPorId(pessoa.getId(), Pessoa.class);
	}
	
	public void remover(Pessoa pessoa) {
		pessoaDao.removerPorId(pessoa.getId(), Pessoa.class);
	}
	
	public List<Pessoa> listagem(){
		return pessoaDao.listagem(Pessoa.class);
	}
	
	public List<Pessoa> listagemLimit10(){
		return pessoaDao.getPessoasLimit10();
	}
	
	public List<Pessoa> getRelatorioPessoas(String nome, Date dataInicial, Date dataFinal){
		return pessoaDao.getRelatorioPessoa(nome, dataInicial, dataFinal);
	}
	
	public Pessoa logarPessoa(String login, String senha) {
		return pessoaDao.logarPessoa(login, senha);
	}
	
	public Endereco pesquisarCep(String cep) throws IOException {
		String urlWebService = "https://viacep.com.br/ws/"+cep+"/json/"; 
		URL url = new URL(urlWebService);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		
		if(conexao.getResponseCode() != 200) {
			throw new RegraDeNegocioException("Não é possível seguir em frente com esse cep.");
		}
		
		InputStream is = conexao.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String linha = null;
		while((linha = bufferedReader.readLine()) != null) {
			sb.append(linha);
		}
		
		if(sb.toString().contains("erro")) {
			throw new RegraDeNegocioException("Não é possível seguir em frente com esse cep, pois não é válido.");
		}
		
		Gson gson = new Gson();
		Endereco endereco = gson.fromJson(sb.toString(), Endereco.class);
		return endereco;
	}
	
	private void verificarNomeSalvoAtualizar(List<Pessoa> pessoas, Pessoa pessoa) {
		for(Pessoa p : pessoas) {
			if(!p.getId().equals(pessoa.getId())) {
				if(p.getNomeCompleto().equals(pessoa.getNomeCompleto())) {
					throw new RegraDeNegocioException("Não é possível atualizar, pois já existe pessoa com este nome completo !");
				}
			}
		}
	}
	
	private void verificarNomeSalvo(List<Pessoa> pessoas, Pessoa pessoa) {
		for(Pessoa p : pessoas) {
			if(p.getNomeCompleto().equals(pessoa.getNomeCompleto())) {
				throw new RegraDeNegocioException("Não é possível inserir, pois já existe pessoa com este nome completo !");
			}
		}
	}
	
	private void verificarLoginSalvo(List<Pessoa> pessoas, Pessoa pessoa) {
		for(Pessoa p : pessoas) {
			if(p.getLogin().equals(pessoa.getLogin())) {
				throw new RegraDeNegocioException("Não é possível inserir, pois já existe este login !");
			}
		}
	}
	
	private void verificarLoginSalvoAtualizar(List<Pessoa> pessoas, Pessoa pessoa) {
		for(Pessoa p : pessoas) {
			if(!p.getId().equals(pessoa.getId())) {
				if(p.getLogin().equals(pessoa.getLogin())) {
					throw new RegraDeNegocioException("Não é possível atualizar, pois já existe este login !");
				}
			}
		}
	}
 
}
