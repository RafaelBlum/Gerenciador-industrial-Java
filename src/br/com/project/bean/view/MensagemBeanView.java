package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.Util.all.Messagens;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.geral.controller.MensagemController;
import br.com.project.model.classes.Entidade;
import br.com.project.model.classes.Mensagem;

@Controller
@Scope(value="session")
@ManagedBean(name="mensagemBeanView")
public class MensagemBeanView extends BeanManagerViewAbstract{

	private Mensagem objetoSelecionado = new Mensagem();
	
	@Autowired
	private ContextBean contextBean;
	
	@Autowired
	private EntidadeController entidadeController;
	
	@Autowired
	private MensagemController mensagemController;
	
	private static final long serialVersionUID = 1L;
	
	private List<Mensagem> listaMsg = new ArrayList<Mensagem>();
	private List<Mensagem> minhasMsgs = new ArrayList<Mensagem>();
	
	
	public List<Mensagem> getListaMensagensUser() throws Exception{
		minhasMsgs.clear();
		
		listaMsg = mensagemController.findList(getClassImplement());

		for (int i = 0; i < listaMsg.size(); i++) {
			if(listaMsg.get(i).getUsr_destino().getEnt_codigo().equals(contextBean.getEntidadeLogada().getEnt_codigo())){
				minhasMsgs.add(listaMsg.get(i));
			}
		}
		return minhasMsgs;
	}
	
	public String novo() throws Exception{
		objetoSelecionado = new Mensagem();
		objetoSelecionado.setUsr_origem(contextBean.getEntidadeLogada());
		return "";
	}
	
	@Override
	public void saveNotReturn() throws Exception {
		
		if(objetoSelecionado.getUsr_destino().getEnt_codigo().equals(objetoSelecionado.getUsr_origem().getEnt_codigo())){
			addMsg("Origem não pode ser igual ao destino!");
			return;
		}
		String msString  = objetoSelecionado.getMen_mensagem();
		objetoSelecionado.setMen_mensagem(msString + "\nAtensiosamente,\n" + objetoSelecionado.getUsr_origem().getEnt_nomefantasia());
		
		
		mensagemController.merge(objetoSelecionado);
		listaMsg.add(objetoSelecionado);
		novo();
		addMsg("Mensagem enviada com sucesso!!!");
	}

	@Override
	protected Class<Mensagem> getClassImplement() {
		return Mensagem.class;
	}

	@Override
	protected InterfaceCrud<Mensagem> getController() {
		return mensagemController;
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Mensagem getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Mensagem objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	/*MAPEAMENTO COM SPRING MVC*/
	@RequestMapping(value = "**/buscarUsuarioDestinoMsg")
	public void buscarUsuarioDestinoMsg(@RequestParam(value="codEntidade") Long codEntidade, 
													HttpServletResponse httpServletResponse) throws Exception {
		Entidade entidade = entidadeController.findByPorId(Entidade.class, codEntidade);
		
		if(entidade != null){
			objetoSelecionado.setUsr_destino(entidade);
			httpServletResponse.getWriter().write(entidade.getJson().toString());
		}
		 	 
	}

	public List<Mensagem> getListaMsg() throws Exception {
		listaMsg = mensagemController.findList(getClassImplement());
		return listaMsg;
	}

	public void setListaMsg(List<Mensagem> listaMsg) {
		this.listaMsg = listaMsg;
	}
	
}
