package br.com.gabrielfereira.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "LANCAMENTO")
public class Lancamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "LANCAMENTO_SEQ", sequenceName = "SEQ_LANCAMENTO", allocationSize = 1, initialValue = 1 )
	@GeneratedValue(generator = "LANCAMENTO_SEQ",strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@NotBlank(message = "É necessário inserir o número da nota fiscal")
	@Column(name = "numero_nota_fiscal")
	private String numeroNotaFiscal;
	
	@NotBlank(message = "É necessário inserir a empresa origem")
	@Column(name = "empresa_origem")
	private String empresaOrigem;
	
	@NotBlank(message = "É necessário inserir a empresa destino")
	@Column(name = "empresa_destino")
	private String empresaDestino;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_PESSOA")
	private Pessoa pessoa;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_inicial")
	private Date dataInicial;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_final")
	private Date dataFinal;
	
	public Lancamento() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getEmpresaOrigem() {
		return empresaOrigem;
	}

	public void setEmpresaOrigem(String empresaOrigem) {
		this.empresaOrigem = empresaOrigem;
	}

	public String getEmpresaDestino() {
		return empresaDestino;
	}

	public void setEmpresaDestino(String empresaDestino) {
		this.empresaDestino = empresaDestino;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
		Lancamento other = (Lancamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", numeroNotaFiscal=" + numeroNotaFiscal + ", empresaOrigem=" + empresaOrigem
				+ ", empresaDestino=" + empresaDestino + ", pessoa=" + pessoa + "]";
	}
	
	
	

}
