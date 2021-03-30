package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.bouncycastle.asn1.isismtt.x509.Admissions;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.omnifaces.util.Messages;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.Util.all.Messagens;
import br.com.project.acessos.Permissao;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.carregamento.lazy.CarregamentoLazyListForObject;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.model.classes.Entidade;

@Controller
@Scope(value="session")
@ManagedBean(name = "funcionarioBeanView")
public class FuncionarioBeanView extends BeanManagerViewAbstract{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntidadeController entidadeController;
	
	@Autowired
	private ContextBean contextoBean;
	
	private Entidade objetoSelecionado = new Entidade();
	private DualListModel<?> listMenu;
	
	private CarregamentoLazyListForObject<Entidade> list = new CarregamentoLazyListForObject<Entidade>();
	private List<Entidade> lista = new ArrayList<Entidade>();
	
	private String url = "/cadastro/usuarios/usuarios.jsf?faces-redirect=true";
	private String urlDetalhes = "/cadastro/usuarios/usuarioSelecionado.jsf?faces-redirect=true";
	
	//LISTANDO PERMISSÕES PARA O USUÁRIO - PICKLIST ******************
	private List<String> listaEnum = new ArrayList<>();
	private List<String> myListEnum = new ArrayList<>();
	private List<String> auxA = new ArrayList<>();
    
    
    public DualListModel<?> getlistaDual(){
    	objetoSelecionado = getObjetoSelecionado();
    	
    	if(auxA.isEmpty()){
    		listMenu = new DualListModel<>(auxA = listaEnumPermissoes(), objetoSelecionado.getAcessos());
    	}else{
    		auxA.clear();
    		listMenu = new DualListModel<>(auxA = listaEnumPermissoes(), objetoSelecionado.getAcessos());
    	}
    	return listMenu;
    }
    
    public void setlistaDual(DualListModel<?> listaDual) {
		this.listMenu = listaDual;
	}
   
    public List<String> getMinhasPermissoesDeUsuarioLogado(){
    	try{
    		List<String> aux = new ArrayList<>();

    		for (String permissao : objetoSelecionado.getAcessos()) {
	    		aux.add(permissao);
			}
	    	return aux;
    	
    	}catch (Exception e) {
			return myListEnum = new ArrayList<>();
		}   
    }
	
    public List<String> listaEnumPermissoes(){
    	objetoSelecionado = getObjetoSelecionado();
    	boolean teste = true;
    	
    	for (Permissao string : Arrays.asList(Permissao.values())) {
    		String aux = string.name();
    		
       		for (int i = 0; i < objetoSelecionado.getAcessos().size(); i++) {

    			if(objetoSelecionado.getAcessos().get(i).equals(aux)){
					teste = false;
					break;
				}else{
					teste = true;
				}
			}
    		
    		if(teste){
    			listaEnum.add(aux);
    		}
		}
    	
    	return listaEnum;
    }
	
    public void handleTransfer(TransferEvent event){
		
		for (Object item : event.getItems()) {
			if(event.isAdd()){

				objetoSelecionado.getAcessos().add(item.toString());
			}
			
			if(event.isRemove()){
				objetoSelecionado.getAcessos().remove(item.toString());
			}	
		}
	}
	
	@Override
	protected Class<Entidade> getClassImplement() {
		return Entidade.class;
	}
	
	@Override
	public String save() throws Exception {
		objetoSelecionado = entidadeController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		if(!objetoSelecionado.getAcessos().contains("USER")){
			objetoSelecionado.getAcessos().add("USER");
		}
		objetoSelecionado = entidadeController.merge(objetoSelecionado);
		lista.add(objetoSelecionado);
		novo();
		
		Messagens.msgSeverityInfo("Atualizado com sucesso!!!");
	}
	
	@Override
	public void excluir() throws Exception {
			objetoSelecionado = (Entidade) entidadeController.getSession().get(getClassImplement(), objetoSelecionado.getEnt_codigo());
			entidadeController.delete(objetoSelecionado);
			list.remove(objetoSelecionado);
			objetoSelecionado = new Entidade();
			sucesso();
	}
	

	
	@Override
	public String editar() throws Exception {
		list.clear();
		return url;
	}
	
	public String detalhesObjeto() throws Exception {		
		return urlDetalhes;
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
	public void setarVariaveisNulas() throws Exception {
		lista.clear();
		objetoSelecionado = new Entidade();
	}

	@Override
	protected InterfaceCrud<Entidade> getController() {
		return entidadeController;
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		return "";
	}

	public Entidade getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Entidade objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public List<Entidade> getLista() throws Exception{
		lista = entidadeController.findList(getClassImplement());
		List<Entidade> aux = new ArrayList<>();
				
		for (Entidade entidade : lista) {
			if(!entidade.getTipoEntidade().equals("ADMINISTRADOR")){
				aux.add(entidade);
			}
		}
		return aux;
	}

	public CarregamentoLazyListForObject<Entidade> getList() {
		return list;
	}

	public void setList(CarregamentoLazyListForObject<Entidade> listas) {
		list = listas;
	}
	
	@Override
	public void consultarEntidades() throws Exception {
		objetoSelecionado = new Entidade();
		list.clear();
		list.setNartive(true);
		list.setClassImpl(getClassImplement());
		list.setTotalRegistroConsulta(super.totalRegistroConsulta(), super.getSqlLazyQueryNative());
	}
	

	public DualListModel<?> getListMenu() {
		return listMenu;
	}

	public void setListMenu(DualListModel<?> listMenu) {
		this.listMenu = listMenu;
	}

	public List<String> getListaEnum(){
		return listaEnum;
	}

	public void setListaEnum(List<String> listaEnum) {
		this.listaEnum = listaEnum;
	}

	public List<String> getMyListEnum() {
		return myListEnum;
	}

	public void setMyListEnum(List<String> myListEnum) {
		this.myListEnum = myListEnum;
	}

	
}
