package br.com.project.bean.view;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagerViewAbstract;
import br.com.project.geral.controller.SessionController;
import br.com.srv.interfaces.SrvLogin;

@Controller
@Scope(value="request")
@ManagedBean(name="loginBeanView")
public class LoginBeanView extends BeanManagerViewAbstract{
	/*Usamos o @Controller do spring
	 *Scope(value="request") = Vamos dar o scope do managerBean (request, requisição), por exemplo: Quando ação de deslogar, a intancia do managerBean
	 * vai durar o tempo da requisição;
	 * @ManagedBean = Temos que dar uma nome ao ManagerBean para ligar com a Página (sempre nome da classe loginBeanView)
	 * */

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	@Resource
	private SessionController sessionController;
	
	@Resource
	private SrvLogin srvLogin;
	
	@RequestMapping(value="**/invalidar_sessao", method = RequestMethod.POST)
	public void invalidarSessionMetodo(HttpServletRequest httpServletRequest) throws Exception{
		String userLogadoSessao = null;
		
		if(httpServletRequest.getUserPrincipal() != null){
			userLogadoSessao = httpServletRequest.getUserPrincipal().getName();
		}
		
		if(userLogadoSessao == null || (userLogadoSessao != null && userLogadoSessao.trim().isEmpty())){
			userLogadoSessao = httpServletRequest.getRemoteUser();
		}
		
		if(userLogadoSessao != null && userLogadoSessao.isEmpty()){
			sessionController.invalidateSession(userLogadoSessao);
		}
	}
	
	
	public void invalidar(ActionEvent actionEvent) throws Exception{
		//Trabalhamos com atributo de resposta, setando ele, para o contexto da página.
		//Ex: addCallBackParam: Parametro de retorno do ajax
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;
		
		//SE AUTENTICO, ELE VALIDA, SE NÃO invalidos
		if(srvLogin.autentico(getUsername(), getPassword())){
			sessionController.invalidateSession(getUsername());
			loggedIn = true;
		}else{
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acesso negado!", "Login ou senha estão incorretos.");
		}
		
		if(message != null){
			FacesContext.getCurrentInstance().addMessage("msg", message);
		}
		
		context.addCallbackParam("loggedIn", loggedIn);
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	protected Class<?> getClassImplement() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected InterfaceCrud<?> getController() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
