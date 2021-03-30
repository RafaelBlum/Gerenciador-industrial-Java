package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.ItemController;
import br.com.project.model.classes.Item;

@Controller
@Scope(value="session")
@ManagedBean(name="itemBeanView")
public class ItemBeanView extends BeanManagerViewAbstract {

	private static final long serialVersionUID = 1L;
	
	private String url = "/cadastro/compras/cad_item.jsf?faces-redirect=true";
	private String urlFind = "/cadastro/find_item.jsf?faces-redirect=true";
	private Item objetoSelecionado =  new Item();
	
	private List<Item> itens = new ArrayList<Item>(); 
	
	@Autowired
	private ItemController itemController;

	
	@Override
	public String save() throws Exception {
		objetoSelecionado = itemController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		itens.clear();
		
		objetoSelecionado = itemController.merge(objetoSelecionado);
		
		itens.add(objetoSelecionado);
		objetoSelecionado = new Item();
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

	public List<Item> getItens() throws Exception {
		itens = itemController.findList(getClassImplement());
		return itens;
	}	
	
	@Override
	public String editar() throws Exception {	
		itens.clear();
		return url;
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Item) itemController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		itemController.delete(objetoSelecionado);
		itens.remove(objetoSelecionado);
		novo();
		sucesso();
	}


	public Item getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Item objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	
	@Override
	protected Class<Item> getClassImplement() {
		return Item.class;
	}

	@Override
	protected InterfaceCrud<Item> getController() {
		return itemController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		itens.clear();
		objetoSelecionado = new Item();
	}
	
	@Override
	public String redirecionarFindEntidade() throws Exception {
		setarVariaveisNulas();
		return urlFind;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	@Override
	public void consultarEntidades() throws Exception {
		super.consultarEntidades();
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
