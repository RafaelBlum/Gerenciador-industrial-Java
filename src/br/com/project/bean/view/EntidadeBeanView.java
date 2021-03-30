package br.com.project.bean.view;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.bean.geral.EntidadeAtualizaSenhaBean;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.model.classes.Entidade;

@Controller
@Scope(value = "session")
@ManagedBean(name = "entidadeBeanView")
public class EntidadeBeanView extends BeanManagerViewAbstract{
	

	private static final long serialVersionUID = 1L;
		
	@Autowired
	private EntidadeController entidadeController;
	
	@Autowired
	private ContextBean contextBean;
	
	private EntidadeAtualizaSenhaBean entidadeAtualizaSenhaBean = new EntidadeAtualizaSenhaBean();

	public String getUsuarioLogadoSecurity(){				
		return contextBean.getAuthentication().getName();		
	}
	
	public Date getUltimoAcesso() throws Exception {
		return contextBean.getEntidadeLogada().getEnt_ultimoacesso();
	}

	@Override
	protected Class<Entidade> getClassImplement() {
		return Entidade.class;
	}

	@Override
	protected InterfaceCrud<Entidade> getController() {
		return entidadeController;
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		// TODO Auto-generated method stub
		return "";
	}

	public EntidadeAtualizaSenhaBean getEntidadeAtualizaSenhaBean() {
		return entidadeAtualizaSenhaBean;
	}

	public void setEntidadeAtualizaSenhaBean(EntidadeAtualizaSenhaBean entidadeAtualizaSenhaBean) {
		this.entidadeAtualizaSenhaBean = entidadeAtualizaSenhaBean;
	}
	
	//AULA 198
	public void updateSenha() throws Exception{
		Entidade entidadeLogada = contextBean.getEntidadeLogada();
		
		
		
		if (!entidadeAtualizaSenhaBean.getSenhaAtual().equals(
				entidadeLogada.getEnt_senha())) {
			addMsg("A senha atual não é válida");
			return;
			
		} else if (entidadeAtualizaSenhaBean.getSenhaAtual().equals(
				entidadeAtualizaSenhaBean.getNovaSenha())) {
			addMsg("A senha atual não pode ser igual a nova senha.");
			return;
			
		} else if (!entidadeAtualizaSenhaBean.getNovaSenha().equals(
				entidadeAtualizaSenhaBean.getConfirmaSenha())) {
			addMsg("A nova senha e a confimação não conferem.");
			return;
			
		} else {
			
			entidadeLogada.setEnt_senha(entidadeAtualizaSenhaBean.getNovaSenha());

			entidadeController.merge(entidadeLogada);
			
			//MODIFICADO O METODO DA ENTIDADECONTROLER DE SAVEorUPDATE para MERGE**** 08/07/18

			entidadeLogada = entidadeController.findByPorId(Entidade.class, entidadeLogada.getEnt_codigo());
			
			if (entidadeLogada.getEnt_senha().equals(
					entidadeAtualizaSenhaBean.getNovaSenha())) {
					sucesso();
			} else {
					addMsg("A nova senha não pode ser atualizada. ****");
					error();
			}
		}
		
		entidadeAtualizaSenhaBean = new EntidadeAtualizaSenhaBean();
	}
	
}
