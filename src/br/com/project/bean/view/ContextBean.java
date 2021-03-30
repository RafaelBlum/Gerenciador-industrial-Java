package br.com.project.bean.view;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.project.geral.controller.EntidadeController;
import br.com.project.geral.controller.SessionController;
import br.com.project.model.classes.Entidade;

@Scope(value = "session")
@Component(value="contextBean")
public class ContextBean implements Serializable{

	/**
	 * @Component - AULA 146
	 * ContexBean -> EntidadeController -> SrvEntidade
	 */
	private static final long serialVersionUID = 1L;
	private static final String USER_LOGADO_SESSAO = "userLogadoSessao";
	
	@Autowired
	private EntidadeController entidadeController;
	
	@Autowired
	private SessionController sessionController;
	
	//RETORNA TODAS INFORMAÇÕES DO USUÁRIO QUE ESTA SENDO CONTROLADO PELO SPRING SECURITY
	public Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	//VAMOS RECUPERAR O OBJETO ENTIDADE QUE ESTA SENDO CONTROLADO NA APLICAÇÃO
	//NA CLASSE FilterOpenSessionView temos o userlogadosessao 
	public Entidade getEntidadeLogada() throws Exception{
		Entidade entidade = (Entidade) getExternalContext().getSessionMap().get(USER_LOGADO_SESSAO);
		
		if(entidade == null || (entidade != null && 
				!entidade.getEnt_login().equals(getUserPrincipal()))){
			
			
			/*PRECISAMOS DA ENTIDADECONTROLLER E UM METODO - EXPLICAÇÃO AULA 147 AO 31:00
			 * RECUPERA TODOS OBJETO, SETAR E FAZER AS VERIFICAÇÕES, ATUALIZAR NO BANCO, CONSULTAR E
			 * ADICIONAR NA SESSÃO "OU LOGOU COM OUTRO USUARIO OU FEZ TROCA".
			 * */
			if(getAuthentication().isAuthenticated()){
				entidadeController.updateUltimoAcessoUser(getAuthentication().getName());
				entidade = entidadeController.findUserLogado(getAuthentication().getName());				
				getExternalContext().getSessionMap().put(USER_LOGADO_SESSAO, entidade);				
				sessionController.addSession(entidade.getEnt_login(), (HttpSession) getExternalContext().getSession(true));
			}
			
		}
		
		System.out.println(entidade.getEnt_ultimoacesso());
		System.out.println(USER_LOGADO_SESSAO);
		
		return entidade;
	}
	
	private String getUserPrincipal() {
		return getExternalContext().getUserPrincipal().getName();
	}

	//PRECISAMOS PEGAR O ExternalContext
	//PODEMOS TER TODAS INFORMAÇÕES DO CONTEXTO JSF "APLICAÇÃO"
	public ExternalContext getExternalContext(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext;
	}
	
	public boolean possuiAcesso(String... acessos){
		
		for(String acesso : acessos){
			for(GrantedAuthority authority: getAuthentication().getAuthorities()){
				if(authority.getAuthority().trim().equals(acesso.trim())){
					return true;
				}
			}
		}		
		return false;
	}

}
