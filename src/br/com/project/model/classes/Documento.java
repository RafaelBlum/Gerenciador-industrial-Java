package br.com.project.model.classes;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import br.com.project.annotation.IdentificaCampoPesquisa;

@Audited
@Entity
@Table(name = "documento")
public class Documento implements Serializable{

	private static final long serialVersionUID = 1L;

	@IdentificaCampoPesquisa(descricaoCampo = "Codigo", campoConsulta = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "material", length = 5, nullable = false)
	private int material;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Nome", campoConsulta = "nome",  principal = 1)
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "lote", length = 5, nullable = false)
	private int lote;
	
	@Column(name = "op", unique = true, length = 7, nullable = false)
	private int op;
	
	@Column(name = "rendimento", nullable = false)
	private int rendimento;
	
	@Column(nullable = false, name="dataTermino")
	@Temporal(TemporalType.DATE)
	private Date dataTermino;
	
	@Column(nullable = false, name="dataLancamento")
	@Temporal(TemporalType.DATE)
	private Date dataLancamento;
	
	@Column(nullable = false, name="dataEnvio")
	@Temporal(TemporalType.DATE)
	private Date dataEnvio;
	
	@Column(name = "erro", length = 5)
	private int erro;
	
	@Column(name = "descricao", length = 100)
	private String descricao;
	
	@Column(name = "terceiro")
	private boolean terceiro;
	
	public String getTipoOrdem(){
		String tipo_ordem = "Produção Interna";
		
		if(this.isTerceiro() == false){
			return tipo_ordem = "Produção terceiro";
		}
		
		return tipo_ordem;
	}
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

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

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}

	public int getLote() {
		return lote;
	}

	public void setLote(int lote) {
		this.lote = lote;
	}

	public int getRendimento() {
		return rendimento;
	}

	public void setRendimento(int rendimento) {
		this.rendimento = rendimento;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public int getErro() {
		return erro;
	}

	public void setErro(int erro) {
		this.erro = erro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isTerceiro() {
		return terceiro;
	}

	public void setTerceiro(boolean terceiro) {
		this.terceiro = terceiro;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
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
		Documento other = (Documento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Documento [id=" + id + ", nome=" + nome + ", lote=" + lote + ", op=" + op + ", material=" + material
				+ ", rendimento=" + rendimento + ", dataTermino=" + dataTermino + ", dataLancamento=" + dataLancamento
				+ ", dataEnvio=" + dataEnvio + ", erro=" + erro + ", descricao=" + descricao + ", terceiro=" + terceiro
				+ ", versionNum=" + versionNum + "]";
	}

}
