package br.com.project.model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.project.annotation.IdentificaCampoPesquisa;

@Audited
@Entity
@Table(name = "procedimento")
public class Procedimento implements Serializable{

	/**
	 * PROCEDIMENTO OPERACINAL DO SETOR - DOCUMENTAÇÃO
	 */
	
	private static final long serialVersionUID = 1L;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Codigo", campoConsulta = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@IdentificaCampoPesquisa(descricaoCampo = "identificador", campoConsulta = "identificador", principal = 1)
	@Column(name = "identificador", nullable = false, length = 50, unique = true)
	private String identificador;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;
	
	@Column(name = "versao", nullable = false, length = 4)
	private int versao = 0;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHomologacao;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Setor", campoConsulta = "setor.nome")
	@NotAudited
	@Basic
	@ManyToMany(mappedBy="procedimentos", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Setor> setores = new ArrayList<Setor>();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}

	public Date getDataHomologacao() {
		return dataHomologacao;
	}

	public void setDataHomologacao(Date dataHomologacao) {
		this.dataHomologacao = dataHomologacao;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
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
		Procedimento other = (Procedimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Procedimento [id=" + id + ", identificador=" + identificador + ", descricao=" + descricao + ", versao="
				+ versao + ", dataHomologacao=" + dataHomologacao + ", setores=" + setores + ", versionNum="
				+ versionNum + "]";
	}

}
