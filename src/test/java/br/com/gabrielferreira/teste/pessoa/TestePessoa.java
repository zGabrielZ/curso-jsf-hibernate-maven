package br.com.gabrielferreira.teste.pessoa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.gabrielfereira.dao.CidadeDao;
import br.com.gabrielfereira.dao.EstadoDao;
import br.com.gabrielfereira.dao.FrameworkDao;
import br.com.gabrielfereira.dao.LinguagemDao;
import br.com.gabrielfereira.dao.NivelProgramadorDao;
import br.com.gabrielfereira.dao.PerfilDao;
import br.com.gabrielfereira.dao.PessoaDao;
import br.com.gabrielfereira.entidade.Cidade;
import br.com.gabrielfereira.entidade.Endereco;
import br.com.gabrielfereira.entidade.Estado;
import br.com.gabrielfereira.entidade.Framework;
import br.com.gabrielfereira.entidade.Linguagem;
import br.com.gabrielfereira.entidade.NivelProgramador;
import br.com.gabrielfereira.entidade.Perfil;
import br.com.gabrielfereira.entidade.Pessoa;
import br.com.gabrielfereira.entidade.enums.Sexo;

public class TestePessoa {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private PessoaDao pessoaDao = new PessoaDao();
	private EstadoDao estadoDao = new EstadoDao();
	private CidadeDao cidadeDao = new CidadeDao();
	private PerfilDao perfilDao = new PerfilDao();
	private NivelProgramadorDao nivelDao = new NivelProgramadorDao();
	private LinguagemDao linguagemDao = new LinguagemDao();
	private FrameworkDao frameDao = new FrameworkDao();
	
	@Test
	public void inserir() throws ParseException {
		
		Estado estado = estadoDao.pesquisarPorId(26, Estado.class);
		Cidade cidade = cidadeDao.pesquisarPorId(9111, Cidade.class);
		estado.getCidades().add(cidade);
		cidade.setEstado(estado);
		
		Perfil perfil = perfilDao.pesquisarPorId(1,Perfil.class);
		NivelProgramador nivelProgramador = nivelDao.pesquisarPorId(3, NivelProgramador.class);
		
		List<Linguagem> linguagems = new ArrayList<Linguagem>();
		Linguagem linguagem = linguagemDao.pesquisarPorId(1, Linguagem.class);
		Linguagem linguagem2 = linguagemDao.pesquisarPorId(3, Linguagem.class);
		linguagems.addAll(Arrays.asList(linguagem,linguagem2));
		
		List<Framework> frameworks = new ArrayList<Framework>();
		Framework framework = frameDao.pesquisarPorId(1, Framework.class);
		Framework framework2 = frameDao.pesquisarPorId(2, Framework.class);
		Framework framework3 = frameDao.pesquisarPorId(3, Framework.class);
		frameworks.addAll(Arrays.asList(framework,framework2,framework3));
		
		Endereco endereco = new Endereco();
		endereco.setCep("03921-060");
		endereco.setBairro("Jardim Iva");
		endereco.setCidade(cidade);
		endereco.setComplemento("");
		endereco.setDdd("11");
		endereco.setIbge("3550308");
		endereco.setLocalidade("São Paulo");
		endereco.setLogradouro("Rua Coronel Petrarca de Mesquita");
		endereco.setSiafi("7107");
		endereco.setUf("SP");
		
		Pessoa pessoa = new Pessoa();
		pessoa.setDataNascimento(sdf.parse("26/12/1997"));
		pessoa.setNome("Gabriel");
		pessoa.setSobrenome("Ferreira");
		pessoa.setNomeCompleto(pessoa.getNome() + " " + pessoa.getSobrenome());
		pessoa.setLogin("gferreira1");
		pessoa.setSenha("123");
		pessoa.setPerfil(perfil);
		pessoa.setNivelProgramador(nivelProgramador);
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setEndereco(endereco);
		pessoa.setAtivo(true);
		
		pessoa.getLinguagens().addAll(linguagems);
		pessoa.getFrameworks().addAll(frameworks);
		
		pessoaDao.inserir(pessoa);
		System.out.println(pessoa);
	}
	
	@Test
	public void atualizar() throws ParseException {
		Pessoa pessoa = pessoaDao.pesquisarPorId(4, Pessoa.class);
		pessoa.setDataNascimento(sdf.parse("05/12/1999"));
		pessoa.setNome("Matheus");
		pessoa.setSobrenome("da Silva");
		pessoa.setNomeCompleto(pessoa.getNome() + " " + pessoa.getSobrenome());
		pessoa = pessoaDao.atualizar(pessoa);
		System.out.println(pessoa);
	}
	
	@Test
	public void buscarPorId() {
		Pessoa pessoa = pessoaDao.pesquisarPorId(1,Pessoa.class);
		System.out.println(pessoa);
	}
	
	@Test
	public void deletar() {
		pessoaDao.removerPorId(3,Pessoa.class);
	}
	
	@Test
	public void listagem() {
		List<Pessoa> pessoas = pessoaDao.listagem(Pessoa.class);
		for(Pessoa p : pessoas) {
			System.out.println(p);
		}
	}
}
