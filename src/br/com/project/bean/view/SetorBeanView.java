package br.com.project.bean.view;

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
import br.com.project.geral.controller.SetorController;
import br.com.project.model.classes.Item;
import br.com.project.model.classes.Procedimento;
import br.com.project.model.classes.Setor;

@Controller
@Scope(value="session")
@ManagedBean(name="setorBeanView")
public class SetorBeanView extends BeanManagerViewAbstract {

	private static final long serialVersionUID = 1L;
	
	private String url = "/cadastro/setores/cad_setor.jsf?faces-redirect=true";
	
	private String urlFind = "/cadastro/find_setor.jsf?faces-redirect=true";
	
	private String urlDetalhes = "/detalhes/detalhesSetor.jsf?faces-redirect=true";
	
	private String urlVoltar = "/cadastro/setores/cad_setor.jsf?faces-redirect=true";
	
	private Setor objetoSelecionado =  new Setor();	
	
	private List<Setor> setores = new ArrayList<Setor>();
	private List<Procedimento> lista = new ArrayList<Procedimento>();
	
	
	@Autowired
	private SetorController setorController;
	
	public String detalhesObjeto() throws Exception {		
		return urlDetalhes;
	}
	
	public String voltar() throws Exception {
		return url;
	}
	
	public String detalhesObjetoTeste() throws Exception {		
		return urlDetalhes;
	}
	
	@Override
	public String save() throws Exception {
		objetoSelecionado = setorController.merge(objetoSelecionado);		
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		setores.clear();
		objetoSelecionado = setorController.merge(objetoSelecionado);
		setores.add(objetoSelecionado);
		objetoSelecionado = new Setor();
		sucesso();
		voltar();
	}
	
	@Override
	public String novo() throws Exception {
		setarVariaveisNulas();
		return url;
	}
	
	@Override
	public String editar() throws Exception {	
		setores.clear();
		return url;
	}
	
	@Override
	public void saveEdit() throws Exception {
		saveNotReturn();
	}
	
	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Setor) setorController.getSession().get(getClassImplement(), objetoSelecionado.getId());
		setorController.delete(objetoSelecionado);
		setores.remove(objetoSelecionado);
		novo();
		sucesso();
	}
	
	@Override
	public StreamedContent getArquivoReport() throws Exception {
		return super.getArquivoReport();
	}
	
	public void adicionar(ActionEvent evento) throws Exception{
		Procedimento procedimento = (Procedimento) evento.getComponent().getAttributes().get("procedimentoAdicionado");
		
		lista = objetoSelecionado.getProcedimentos();
		
		for (int i = 0; i < objetoSelecionado.getProcedimentos().size(); i++) {

			if(objetoSelecionado.getProcedimentos().get(i).getIdentificador().equals(procedimento.getIdentificador())){
				Messages.addGlobalInfo("Procedimento já esta adicionado!");
				return;
				
			}
		}
		
		lista.add(procedimento);			
		objetoSelecionado.setProcedimentos(lista);
		Messages.addGlobalInfo("Procedimento adicionado com sucesso!");
	}
	
	public void excluir(ActionEvent evento) throws Exception{
		Procedimento procedimento = (Procedimento) evento.getComponent().getAttributes().get("itemExclui");
		
		lista = objetoSelecionado.getProcedimentos();
		lista.remove(procedimento);
		
		objetoSelecionado.setProcedimentos(lista);
		
		Messagens.msgSeverityInfo("Procedimento excluido com sucesso!!");
	}
		
	public Setor getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Setor objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public List<Setor> getSetores() throws Exception {
		setores = setorController.findList(getClassImplement());
		return setores;
	}
	
	@Override
	protected Class<Setor> getClassImplement() {
		return Setor.class;
	}

	@Override
	protected InterfaceCrud<Setor> getController() {
		return setorController;
	}
	
	@Override
	public void setarVariaveisNulas() throws Exception {
		setores.clear();
		objetoSelecionado = new Setor();
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
