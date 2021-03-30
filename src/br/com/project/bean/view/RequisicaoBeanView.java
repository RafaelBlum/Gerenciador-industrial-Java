package br.com.project.bean.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.Util.all.Messagens;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.RequisicaoController;
import br.com.project.model.classes.Item;
import br.com.project.model.classes.Procedimento;
import br.com.project.model.classes.RequisicaoDeCompra;

@Controller
@Scope(value="session")
@ManagedBean(name="requisicaoBeanView")
public class RequisicaoBeanView extends BeanManagerViewAbstract {

	private static final long serialVersionUID = 1L;
	
	private String url = "/cadastro/compras/cad_requisicao.jsf?faces-redirect=true";
	
	private String urlFind = "/cadastro/find_requisicao.jsf?faces-redirect=true";
	
	private String urlDetalhes = "/cadastro/compras/listar_requisicoes.jsf?faces-redirect=true";
	
	private RequisicaoDeCompra objetoSelecionado =  new RequisicaoDeCompra();
	
	private List<RequisicaoDeCompra> requisicoes = new ArrayList<RequisicaoDeCompra>();
	
	private List<Item> itens = new ArrayList<Item>();
	
	@Autowired
	private RequisicaoController requisicaoController;
	
	public String voltar() throws Exception {
		novo();
		return urlDetalhes;
	}
	
	public String getMostraNumRC(){
		String rc= "";
		if(objetoSelecionado.getId() == null){
			return "-------";
		}
		rc = String.valueOf(objetoSelecionado.getNumero());
		return rc;
	}
	
	private BigDecimal ajustaValor(List<Item> list){
		
		BigDecimal aux =  new BigDecimal(0);
		
		for (int i = 0; i < itens.size(); i++) {
			aux = aux.add(itens.get(i).getValor());
		}
		return aux;
	}
	
	public void adicionar(ActionEvent evento) throws Exception{
		Item item = (Item) evento.getComponent().getAttributes().get("itemAdicionado");

		itens = objetoSelecionado.getItens();
		itens.add(item);
		
		objetoSelecionado.setItens(itens);
		objetoSelecionado.setTotal(ajustaValor(itens));

		Messages.addGlobalInfo("Procedimento adicionado com sucesso!");
	}

	public void excluir(ActionEvent evento) throws Exception{
			Item item = (Item) evento.getComponent().getAttributes().get("itemExclui");
			
			itens = objetoSelecionado.getItens();
			itens.remove(item);
			
			objetoSelecionado.setItens(itens);
			objetoSelecionado.setTotal(ajustaValor(itens));
			
			Messagens.msgSeverityInfo("Item excluido com sucesso!!");
	}
	
	@Override
	public String save() throws Exception {
		objetoSelecionado = requisicaoController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		requisicoes.clear();
		
		objetoSelecionado = requisicaoController.merge(objetoSelecionado);
		requisicoes.add(objetoSelecionado);		
		objetoSelecionado = new RequisicaoDeCompra();
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
		requisicoes.clear();
		return url;
	}


	public RequisicaoDeCompra getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(RequisicaoDeCompra objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public List<RequisicaoDeCompra> getRequisicoesDeCompra() throws Exception {
		requisicoes = requisicaoController.findList(getClassImplement());
		return requisicoes;
	}
	
	@Override
	protected Class<RequisicaoDeCompra> getClassImplement() {
		return RequisicaoDeCompra.class;
	}

	@Override
	protected InterfaceCrud<RequisicaoDeCompra> getController() {
		return requisicaoController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		requisicoes.clear();
		objetoSelecionado = new RequisicaoDeCompra();
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
