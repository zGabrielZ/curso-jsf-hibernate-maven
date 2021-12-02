package br.com.gabrielfereira.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.br.TituloEleitoral;

import br.com.gabrielfereira.entidade.enums.Sexo;


@Entity
@Table(name = "PESSOA")
public class Pessoa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "PESSOA_SEQ", sequenceName = "SEQ_PESSOA", allocationSize = 1, initialValue = 1 )
	@GeneratedValue(generator = "PESSOA_SEQ",strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@NotBlank(message = "É necessário inserir o nome")
	@Size.List({
		@Size(min = 5, message = "Nome : Mínimo de 5"),
		@Size(max = 120, message = "Nome : Máximo de 120")
	})
	private String nome;
	
	@NotBlank(message = "É necessário inserir o sobrenome")
	@Size.List({
		@Size(min = 5, message = "Sobrenome : Mínimo de 5"),
		@Size(max = 120, message = "Sobrenome : Máximo de 120")
	})
	private String sobrenome;
	
	@NotNull(message = "É necessário inserir a data de nascimento")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Column(name = "nome_completo")
	private String nomeCompleto;
	
	@NotNull(message = "É necessário inserir o sexo da pessoa")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	private Boolean ativo;
	
	private String login;
	
	private String senha;
	
	@Lob
	@Column(name = "caminho_imagem")
	private String caminhoImagem;
	
	// EXTENSAO -> JPG,PNG,JPEG
	private String extensao;
	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE)
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name = "ID_PERFIL")
	private Perfil perfil;
	
	@OneToOne
	@JoinColumn(name = "ID_NIVEL_PROGRAMADOR")
	private NivelProgramador nivelProgramador;
	
	@ManyToMany
	@JoinTable(name = "PESSOA_LINGUAGEM", joinColumns = @JoinColumn(name = "ID_PESSOA"),
		inverseJoinColumns = @JoinColumn(name = "ID_LINGUAGEM"))
	private List<Linguagem> linguagens = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "PESSOA_FRAMEWORK", joinColumns = @JoinColumn(name = "ID_PESSOA "),
		inverseJoinColumns = @JoinColumn(name = "ID_FRAMEWORK"))
	private List<Framework> frameworks = new ArrayList<>();
	
	@NotBlank(message = "É necessário informar cpf")
	@CPF(message = "CPF inválido")
	@Column(name = "cpf")
	private String cpf;
	
	@NotBlank(message = "É necessário informar título eleitoral")
	@TituloEleitoral(message = "Título Eleitoral inválido")
	@Column(name = "titulo_eleitoral")
	private String tituloEleitoral;
	
	public Pessoa() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	public List<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public NivelProgramador getNivelProgramador() {
		return nivelProgramador;
	}

	public void setNivelProgramador(NivelProgramador nivelProgramador) {
		this.nivelProgramador = nivelProgramador;
	}

	public List<Linguagem> getLinguagens() {
		return linguagens;
	}

	public void setLinguagens(List<Linguagem> linguagens) {
		this.linguagens = linguagens;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTituloEleitoral() {
		return tituloEleitoral;
	}

	public void setTituloEleitoral(String tituloEleitoral) {
		this.tituloEleitoral = tituloEleitoral;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", dataNascimento=" + dataNascimento
				+ ", nomeCompleto=" + nomeCompleto + ", sexo=" + sexo + ", ativo=" + ativo + ", login=" + login
				+ ", senha=" + senha + ", caminhoImagem=" + caminhoImagem + ", extensao=" + extensao + ", lancamentos="
				+ lancamentos + ", endereco=" + endereco + ", perfil=" + perfil + ", nivelProgramador="
				+ nivelProgramador + ", linguagens=" + linguagens + ", frameworks=" + frameworks + ", cpf=" + cpf
				+ ", tituloEleitoral=" + tituloEleitoral + "]";
	}

	
	
	
	
	
}
