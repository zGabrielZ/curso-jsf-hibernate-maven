package br.com.gabrielfereira.entidade.enums;

public enum Sexo {

	MASCULINO(1,"Masculino"),
	FEMININO(2,"Feminino");
	
	private Integer codigo;
	private String descricao;
	
	Sexo(Integer codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
