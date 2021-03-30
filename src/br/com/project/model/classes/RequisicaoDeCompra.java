package br.com.project.model.classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Audited
@Entity
@Table(name = "requisicaoDeCompra")
public class RequisicaoDeCompra implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "numero_rc", nullable = false, length = 5)
	private int numero = geraNumeroPedido();
	
	@Column(name = "descricao", nullable = false, length = 200)
	private String descricao;
	
	@Column(nullable = false, name="dataRequisicao")
	@Temporal(TemporalType.DATE)
	private Date dataRequisicao = new Date();

	
	@Column(name = "total", nullable = true, precision = 7, scale = 2)
	private BigDecimal total;
	
	
	@NotAudited
	@Basic
	@ManyToMany(cascade =  CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="requisicao_item",
				joinColumns={@JoinColumn(name="requisicao_id")},
				inverseJoinColumns={@JoinColumn(name="item_id")})
	private List<Item> itens = new ArrayList<Item>();
	
	@Basic
	@ManyToOne
	@JoinColumn(name = "fornecedor", nullable = false)
	@ForeignKey(name = "fornecedor_fk")
	private Fornecedor fornecedor =  new Fornecedor();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;
		
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int geraNumeroPedido() {
		int min = 1000;
		
		int max = 99999;
		
		Random random = new Random();
		
		int ranNum = random.nextInt((max - min) + 1) + min;

		return ranNum;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataRequisicao() {
		return dataRequisicao;
	}
	public void setDataRequisicao(Date dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
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
		RequisicaoDeCompra other = (RequisicaoDeCompra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RequisicaoDeCompra [id=" + id + ", numero=" + numero + ", descricao=" + descricao + ", dataRequisicao="
				+ dataRequisicao + ", total=" + total + ", itens=" + itens + ", fornecedor=" + fornecedor
				+ ", versionNum=" + versionNum + "]";
	}

	
}
