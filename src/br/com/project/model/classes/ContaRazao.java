package br.com.project.model.classes;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "contaRazao")
public class ContaRazao implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Codigo", campoConsulta = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "conta", unique = true, length = 5, nullable = false)
	private int conta;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Nome", campoConsulta = "descricao", principal = 1)
	@Column(name = "descricao", nullable = true, length = 30)
	private String descricao;
	
	@Column(name = "valorGeral", nullable = true, precision = 7, scale = 2)
	private BigDecimal valorGeral;
	
	@NotAudited
	@OneToMany(mappedBy = "conta", orphanRemoval = false)
	@Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private List<Item> itens = new ArrayList<Item>();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorGeral() {
		return valorGeral;
	}

	public void setValorGeral(BigDecimal valorGeral) {
		this.valorGeral = valorGeral;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
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
		ContaRazao other = (ContaRazao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContaRazao [id=" + id + ", conta=" + conta + ", descricao=" + descricao + ", valorGeral=" + valorGeral
				+ ", itens=" + itens + ", versionNum=" + versionNum + "]";
	}

}
