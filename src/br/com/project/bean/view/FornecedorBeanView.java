package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import org.omnifaces.util.Messages;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.carregamento.lazy.CarregamentoLazyListForObject;
import br.com.project.geral.controller.FornecedorController;
import br.com.project.model.classes.Fornecedor;

@Controller
@Scope("session")
@ManagedBean(name="fornecedorBeanView")
public class FornecedorBeanView extends BeanManagerViewAbstract {

	private static final long serialVersionUID = 1L;
	
	private String url = "/cadastro/compras/cad_fornecedor.jsf?faces-redirect=true";
	private String urlFind = "/cadastro/busca/find_fornecedor.jsf?faces-redirect=true";
	
	private Fornecedor objetoSelecionado =  new Fornecedor();
	
	private CarregamentoLazyListForObject<Fornecedor> list = new CarregamentoLazyListForObject<Fornecedor>();
	
	private List<Fornecedor> listaGeral = new ArrayList<Fornecedor>();
	
	@Autowired
	private FornecedorController fornecedorController;
	
	
	public FornecedorController getFornecedorController() {
		return fornecedorController;
	}

	public void setFornecedorController(FornecedorController fornecedorController) {
		this.fornecedorController = fornecedorController;
	}

	public List<SelectItem> getFornecedoresConverter() throws Exception{		
		return fornecedorController.getListFornecedorController();
	}
	
	@Override
	public String save() throws Exception {
		objetoSelecionado = fornecedorController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		list.getList().clear();
		objetoSelecionado = fornecedorController.merge(objetoSelecionado);
		list.add(objetoSelecionado);
		objetoSelecionado = new Fornecedor();
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
		list.getList().clear();
		return url;
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Fornecedor) fornecedorController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		fornecedorController.delete(objetoSelecionado);
		list.remove(objetoSelecionado);
		novo();
		sucesso();
	}

	public Fornecedor getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Fornecedor objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public CarregamentoLazyListForObject<Fornecedor> getList() throws Exception {
		return list;
	}
	
	public List<Fornecedor> getListGeral() throws Exception{
		listaGeral = fornecedorController.findList(getClassImplement());
		return listaGeral;
	}

	@Override
	protected Class<Fornecedor> getClassImplement() {
		return Fornecedor.class;
	}

	@Override
	protected InterfaceCrud<Fornecedor> getController() {
		return fornecedorController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		list.getList().clear();
		objetoSelecionado = new Fornecedor();
	}
	
	@Override
	public String redirecionarFindEntidade() throws Exception {
		setarVariaveisNulas();
		return urlFind;
	}
	
	@Override
	public void consultarEntidades() throws Exception {
		objetoSelecionado = new Fornecedor();
		list.clear();
		list.setNartive(true);
		list.setClassImpl(getClassImplement());
		list.setTotalRegistroConsulta(super.totalRegistroConsulta(), super.getSqlLazyQueryNative());
	}

	@Override
	public String condicaoAndParaPesquisa(){
		return "";
	}

	public List<Fornecedor> getListaGeral() {
		return listaGeral;
	}

	public void setListaGeral(List<Fornecedor> listaGeral) {
		this.listaGeral = listaGeral;
	}

	public void setList(CarregamentoLazyListForObject<Fornecedor> list) {
		this.list = list;
	}
}
