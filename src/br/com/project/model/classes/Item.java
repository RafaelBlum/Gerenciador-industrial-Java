package br.com.project.model.classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.project.annotation.IdentificaCampoPesquisa;

@Audited
@Entity
@Table(name = "item")
public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "valor_un", nullable = true, precision = 7, scale = 2)
	private BigDecimal valor;
	
	@Transient
	private int quatidade;
	
	@NotAudited
	@Basic
	@ManyToMany(mappedBy="itens", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<RequisicaoDeCompra> requisicoes = new ArrayList<RequisicaoDeCompra>();
	
	
	@IdentificaCampoPesquisa(descricaoCampo = "Conta Razao", campoConsulta = "conta.descricao")
	@Basic
	@ManyToOne
	@JoinColumn(name = "contaRazao", nullable = false)
	@ForeignKey(name = "contaRazao_fk")
	private ContaRazao conta =  new ContaRazao();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;
	
	public List<RequisicaoDeCompra> getRequisicoes() {
		return requisicoes;
	}
	public void setRequisicoes(List<RequisicaoDeCompra> requisicoes) {
		this.requisicoes = requisicoes;
	}
	public ContaRazao getConta() {
		return conta;
	}
	public void setConta(ContaRazao conta) {
		this.conta = conta;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public int getQuatidade() {
		return quatidade;
	}
	public void setQuatidade(int quatidade) {
		this.quatidade = quatidade;
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", nome=" + nome + ", valor=" + valor + ", quatidade=" + quatidade + ", requisicoes="
				+ requisicoes + ", conta=" + conta + ", versionNum=" + versionNum + "]";
	}

}
