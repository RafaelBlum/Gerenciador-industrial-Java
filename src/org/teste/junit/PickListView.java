package org.teste.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.mapping.AuxiliaryDatabaseObject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.context.Theme;

import br.com.project.acessos.Permissao;
import br.com.project.bean.view.ContextBean;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.model.classes.Entidade;

@ManagedBean
public class PickListView {
	
	private Entidade objetoSelecionado = new Entidade();
     
    private List<String> minhalista = new ArrayList<>();
    
    private DualListModel<String> lista_userPermissao ;
    
    private List<String> tipos = new ArrayList<>();
    
    @Autowired
	private ContextBean contextoBean;
	
	@Autowired
	private EntidadeController entidadeController;
    
     
    @PostConstruct
    public void init() throws Exception {
    	
    	for (String permissao : contextoBean.getEntidadeLogada().getAcessos()) {
    		minhalista.add(permissao);
    		
		}
    	    	
    	for (Permissao string : Arrays.asList(Permissao.values())) {
    		String aux = string.toString();
			tipos.add(aux);
		}
    	        
    	lista_userPermissao = new DualListModel<>(tipos, minhalista);
    	     
    }
    
    public DualListModel<String> getLista_userPermissao() {
		return lista_userPermissao;
	}

	public void setLista_userPermissao(DualListModel<String> lista_userPermissao) {
		this.lista_userPermissao = lista_userPermissao;
	}

	public DualListModel<String> getLista() throws Exception{
    	
    	for (String permissao : contextoBean.getEntidadeLogada().getAcessos()) {
    		minhalista.add(permissao);
		}
    	    	
    	for (Permissao string : Arrays.asList(Permissao.values())) {
    		String aux = string.toString();
			tipos.add(aux);
		}
    	        
    	return lista_userPermissao = new DualListModel<>(tipos, minhalista);
    	    	
    }
    
    public void teste(){
    	System.out.println("**************TESTE*************");
    }

	public Entidade getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Entidade objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}
 
    
}
