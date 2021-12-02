package br.com.gabrielfereira.entidade.to;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import br.com.gabrielfereira.entidade.Pessoa;

public class PessoaTo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String sobrenome;
	private String sexo;
	private String dataNascimento;
	
	public PessoaTo(Pessoa pessoa) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		id = pessoa.getId();
		nome = pessoa.getNome();
		sobrenome = pessoa.getSobrenome();
		sexo = pessoa.getSexo().getCodigo().equals(1) ? "Masculino" : "Feminino";
		dataNascimento = sdf.format(pessoa.getDataNascimento());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	

}
