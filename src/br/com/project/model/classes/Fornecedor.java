package br.com.project.model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.project.annotation.IdentificaCampoPesquisa;

@Audited
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable{

	private static final long serialVersionUID = 1L;

	@IdentificaCampoPesquisa(descricaoCampo = "Código", campoConsulta = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Nome", campoConsulta = "nome", principal = 1)
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "cnpj", length = 12)
	private Integer cnpj;
	
	@NotAudited
	@OneToMany(mappedBy = "fornecedor", orphanRemoval = false)
	@Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private List<RequisicaoDeCompra> requisicoes = new ArrayList<RequisicaoDeCompra>();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCnpj() {
		return cnpj;
	}

	public void setCnpj(Integer cnpj) {
		this.cnpj = cnpj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	public List<RequisicaoDeCompra> getRequisicoes() {
		return requisicoes;
	}

	public void setRequisicoes(List<RequisicaoDeCompra> requisicoes) {
		this.requisicoes = requisicoes;
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
		Fornecedor other = (Fornecedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fornecedor [id=" + id + ", nome=" + nome + ", cnpj=" + cnpj + ", requisicoes=" + requisicoes
				+ ", versionNum=" + versionNum + "]";
	}

	
}
