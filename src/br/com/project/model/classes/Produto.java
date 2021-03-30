package br.com.project.model.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.envers.Audited;
import org.primefaces.json.JSONObject;

import br.com.project.annotation.IdentificaCampoPesquisa;

@Audited
@Entity
@Table(name = "produto")
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "material", unique = true, length = 5, nullable = false)
	private int material;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Nome", campoConsulta = "nome",  principal = 1)
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "terceiro")
	private boolean terceiro;
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isTerceiro() {
		return terceiro;
	}
	
	public String tipoProduto(Produto p){
		if(p.terceiro == true){
			return "Tercerizado";
		}
		
		return "Interno";
	}

	public void setTerceiro(boolean terceiro) {
		this.terceiro = terceiro;
	}
	
	public JSONObject getJsonProduto() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("material", material);
		map.put("nome", nome);
		map.put("terceiro", terceiro);
		return new JSONObject(map);
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", material=" + material + ", nome=" + nome + ", terceiro=" + terceiro
				+ ", versionNum=" + versionNum + "]";
	}
	
}
