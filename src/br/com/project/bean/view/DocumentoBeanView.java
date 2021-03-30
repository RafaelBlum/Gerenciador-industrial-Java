package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.DocumentoController;
import br.com.project.geral.controller.ProdutoController;
import br.com.project.model.classes.Documento;
import br.com.project.model.classes.Entidade;
import br.com.project.model.classes.Produto;

@Controller
@Scope(value="session")
@ManagedBean(name="documentoBeanView")
public class DocumentoBeanView extends BeanManagerViewAbstract{

	private static final long serialVersionUID = 1L;
	private int aux = 0;
	private String nomeAux = "";
	private boolean tipoAux = false;
	
	private String url = "/cadastro/cad_documento.jsf?faces-redirect=true";
	private String urlFind = "/cadastro/find_documento.jsf?faces-redirect=true";
	private String urlDetalhes = "/detalhes/detalhesDoc.jsf?faces-redirect=true";

	
	private Documento objetoSelecionado =  new Documento();
	private List<Documento> documentos = new ArrayList<Documento>();
	
	@Autowired
	private DocumentoController documentoController;

	//TESTE MAP-PRODUTO
	private List<Produto> produtos = new ArrayList<Produto>();
	private Produto produto =  new Produto();
	
	@Autowired
	private ProdutoController produtoController;

	public List<Produto> getProdutos() throws Exception {
		produtos = produtoController.findList(Produto.class);
		produto.getJsonProduto();
		return produtos;
	}
	
	public void handlerEvent() throws Exception{
		produtos = produtoController.findList(Produto.class);
		aux = objetoSelecionado.getMaterial();
		
		for (Produto produto : produtos) {
			if(produto.getMaterial() == aux){
				nomeAux = produto.getNome();
				tipoAux = produto.isTerceiro();
				this.objetoSelecionado.setNome(nomeAux);
				this.objetoSelecionado.setTerceiro(produto.isTerceiro());
			}
		}
	}

	public List<Documento> getDocumentos() throws Exception {
		documentos = documentoController.findList(getClassImplement());
		return documentos;
	}

	@Override
	public String save() throws Exception {
		this.objetoSelecionado.setDataEnvio(new Date());
		objetoSelecionado = documentoController.merge(objetoSelecionado);
		return url;
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		this.objetoSelecionado.setDataEnvio(new Date());
		objetoSelecionado = documentoController.merge(objetoSelecionado);
		documentos.add(objetoSelecionado);
		setarVariaveisNulas();
		sucesso();
	}
	
	@Override
	public void saveEdit() throws Exception {
		saveNotReturn();
	}
	
	@Override
	public String novo() throws Exception {
		setarVariaveisNulas();
		return url;
	}

	
	@Override
	public String editar() throws Exception {
		documentos.clear();
		handlerEvent();
		return url;
	}
	
	public String detalhesObjeto() throws Exception {		
		return urlDetalhes;
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Documento) documentoController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		documentoController.delete(objetoSelecionado);
		documentos.remove(objetoSelecionado);
		novo();
		sucesso();
	}
	
	
	public Documento getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Documento objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	@Override
	protected Class<Documento> getClassImplement() {
		return Documento.class;
	}

	@Override
	protected InterfaceCrud<Documento> getController() {
		return documentoController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		documentos.clear();
		nomeAux = "";
		objetoSelecionado = new Documento();
	}
	
	@Override
	public String redirecionarFindEntidade() throws Exception {
		setarVariaveisNulas();
		return urlFind;
	}
	
	@Override
	public void consultarEntidades() throws Exception {
		super.consultarEntidades();
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		return "";
	}

	public String getNomeAux() {
		return nomeAux;
	}

	public void setNomeAux(String nomeAux) {
		this.nomeAux = nomeAux;
	}

	public boolean isTipoAux() {
		return tipoAux;
	}

	public void setTipoAux(boolean tipoAux) {
		this.tipoAux = tipoAux;
	}
	
	
}
