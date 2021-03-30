package br.com.project.bean.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.ContaRazaoController;
import br.com.project.model.classes.ContaRazao;

@Controller
@Scope(value="session")
@ManagedBean(name="contaRazaoBeanView")
public class ContaRazaoBeanView extends BeanManagerViewAbstract {
	
	private static final long serialVersionUID = 1L;	
	
	@Autowired
	private ContaRazaoController contaRazaoController;
	
	private String url = "/cadastro/orcamento/cad_conta.jsf?faces-redirect=true";
	private String urlFind = "/cadastro/orcamento/find_conta.jsf?faces-redirect=true";
	private ContaRazao objetoSelecionado = new ContaRazao();
	private List<ContaRazao> contas = new ArrayList<ContaRazao>();
	
	
	public List<SelectItem> getContasRazao() throws Exception{		
		return contaRazaoController.getListContasController();
	}
	
	@Override
	public String save() throws Exception {
		objetoSelecionado = contaRazaoController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		contas.clear();
		if(objetoSelecionado.getValorGeral() == null){
			BigDecimal aux =  new BigDecimal(0);
			objetoSelecionado.setValorGeral(aux);
		}
		objetoSelecionado = contaRazaoController.merge(objetoSelecionado);
		contas.add(objetoSelecionado);
		objetoSelecionado = new ContaRazao();
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
		contas.clear();
		return url;
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (ContaRazao) contaRazaoController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		contaRazaoController.delete(objetoSelecionado);
		contas.remove(objetoSelecionado);
		novo();
		sucesso();
	}

	@Override
	protected Class<ContaRazao> getClassImplement() {
		return ContaRazao.class;
	}

	@Override
	protected InterfaceCrud<ContaRazao> getController() {
		return contaRazaoController;
	}

	public ContaRazao getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(ContaRazao objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}
	
	public List<ContaRazao> getContas() throws Exception {
		contas = contaRazaoController.findList(getClassImplement());
		return contas;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		contas.clear();
		objetoSelecionado = new ContaRazao();
		getContasRazao();
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
		// TODO Auto-generated method stub
		return "";
	}

}
