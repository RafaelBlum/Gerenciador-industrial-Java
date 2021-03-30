package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.ProdutoController;
import br.com.project.model.classes.Produto;

@Controller
@Scope(value="session")
@ManagedBean(name="produtoBeanView")
public class ProdutoBeanView extends BeanManagerViewAbstract {

	private static final long serialVersionUID = 1L;
	
	private String url = "/cadastro/cad_produto.jsf?faces-redirect=true";
	private String urlFind = "/cadastro/busca/find_produto.jsf?faces-redirect=true";
	
	private Produto objetoSelecionado =  new Produto();
	
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@Autowired
	private ProdutoController produtoController;

	
	public List<SelectItem> getProduto() throws Exception{		
		return produtoController.getListProdutoController();
	}
	
	@Override
	public String save() throws Exception {
		objetoSelecionado = produtoController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		produtos.clear();
		objetoSelecionado = produtoController.merge(objetoSelecionado);
		produtos.add(objetoSelecionado);
		objetoSelecionado = new Produto();
		sucesso();
	}
	
	@Override
	public StreamedContent getArquivoReport() throws Exception {
		return super.getArquivoReport();
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
		produtos.clear();
		return url;
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Produto) produtoController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		produtoController.delete(objetoSelecionado);
		produtos.remove(objetoSelecionado);
		novo();
		sucesso();
	}

	public Produto getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Produto objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public List<Produto> getProdutos() throws Exception {
		produtos = produtoController.findList(getClassImplement());
		return produtos;
	}
	
	@Override
	protected Class<Produto> getClassImplement() {
		return Produto.class;
	}

	@Override
	protected InterfaceCrud<Produto> getController() {
		return produtoController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		produtos.clear();
		objetoSelecionado = new Produto();
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
	
}
