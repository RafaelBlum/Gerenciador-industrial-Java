package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.ProcedimentoController;
import br.com.project.geral.controller.SetorController;
import br.com.project.model.classes.Procedimento;
import br.com.project.model.classes.Setor;

@Controller
@Scope(value="session")
@ManagedBean(name="procedimentoBeanView")
public class ProcedimentoBeanView extends BeanManagerViewAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url = "/cadastro/setores/cad_procedimento.jsf?faces-redirect=true";
	private String urlFind = "/cadastro/find_procedimento.jsf?faces-redirect=true";
	
	private Procedimento objetoSelecionado =  new Procedimento();
	
	private List<Procedimento> procedimentos = new ArrayList<Procedimento>();
	
	@Autowired
	private ProcedimentoController procedimentoController;
	
	@Autowired
	private SetorController setorController;
	private List<Setor> setores = new ArrayList<Setor>();
	
	@Override
	public String save() throws Exception {
		this.objetoSelecionado.setDataHomologacao(new Date());
		this.objetoSelecionado.setVersao(getAtualizaVersao());
		objetoSelecionado = procedimentoController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		procedimentos.clear();
		this.objetoSelecionado.setDataHomologacao(new Date());
		
		objetoSelecionado = procedimentoController.merge(objetoSelecionado);
		procedimentos.add(objetoSelecionado);
		objetoSelecionado = new Procedimento();
		sucesso();
	}
	
	public int getAtualizaVersao(){
		int versao = 0;
		versao = this.objetoSelecionado.getVersao();
		versao += 1;		
		return versao;
	}
	
	@Override
	public void saveEdit() throws Exception {
		this.objetoSelecionado.setVersao(getAtualizaVersao());
		saveNotReturn();
	}
	
	@Override
	public String novo() throws Exception {
		setarVariaveisNulas();
		return url;
	}
	
	@Override
	public String editar() throws Exception {
		procedimentos.clear();
		return url;
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Procedimento) procedimentoController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		
		setores = setorController.findList(Setor.class);
		
		for (Setor setor : setores) {
			setor.getProcedimentos().remove(objetoSelecionado);
			
		}
		
		objetoSelecionado.getSetores().clear();
		
		
		//REMOVE DA LISTA DE PROCEDIMENTOS
		procedimentos.remove(objetoSelecionado);
		
		//REMOVENDO DO BANCO
		procedimentoController.delete(objetoSelecionado);
		
		novo();
		sucesso();
	}

	public Procedimento getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Procedimento objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public List<Procedimento> getProcedimentos() throws Exception {
		procedimentos = procedimentoController.findList(getClassImplement());
		return procedimentos;
	}
	
	@Override
	protected Class<Procedimento> getClassImplement() {
		return Procedimento.class;
	}

	@Override
	protected InterfaceCrud<Procedimento> getController() {
		return procedimentoController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		procedimentos.clear();
		objetoSelecionado = new Procedimento();
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
