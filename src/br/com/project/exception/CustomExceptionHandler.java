package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper{
	//AULA 107
	
	private ExceptionHandler wrapperd;
	
	final FacesContext faceContext = FacesContext.getCurrentInstance();
	
	final Map<String, Object> requestMap = faceContext.getExternalContext().getRequestMap();
	
	final NavigationHandler navigationHandler = faceContext.getApplication().getNavigationHandler();
	
	public CustomExceptionHandler(ExceptionHandler exceptionHandler){
		this.wrapperd = exceptionHandler;
	}
	
	//SOBRESCREVE O MÃ‰TODO EXCEPTIONHANDLER QUE RETORNA A PILHA DE EXCEÃ‡Ã•ES

	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}
	
	//SOBRESCREVE O METODO HANDER QUE É RESPONSAVEL POR MANIPULAR AS EXECÕES DO JSF	
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		while(iterator.hasNext()){
			ExceptionQueuedEvent evento = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) evento.getSource();
			
			//RECUPERAR A EXCEÇÃO DO CONTEXTO
			
			Throwable exeption = context.getException();
			
			//AQUI TRABALHA A EXCEÇÃO
			
			try{
				//TRABALHANDO EXCEÃ‡Ã•ES, COMO CHAVE ESTRANGEIRA
				requestMap.put("exceptionMessage", exeption.getMessage());
				
				if(exeption != null &&
						exeption.getMessage() != null
						&& exeption.getMessage().indexOf("ConstraintViolationException") != -1){
					
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_WARN, "Registro não pode ser removido por"
							+ " estar associado ou salvo por já existir.", ""));
					
				}else if(exeption != null &&
						exeption.getMessage() != null
						&& exeption.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_ERROR, "Registro foi atualizado ou excluido por outro usuário."
							+ " Consulte novamente.", ""));
					
				}else{
					//AVISA O USUÃ�RIO DO ERRO
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_FATAL, "O sistema se recuperou de um erro inesperado.",""));
					
					//TRANQUILIZA O USUÃ�RIO PARA QUE ELE CONTINUE USANDO O SISTEMA
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_INFO, "Você pode continuar a utilizar o sistema normalmente!",""));
					
					//MOSTRA AO USUÃ�RIO O ERRO
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_FATAL, "O erro foi causado por: \n" + exeption.getMessage(),""));
					
					//REQUESTCONTEXT DO PRIMEFACES
					//ESSE ALERT APE AS Ã‰ EXIBIDO SE A PÃ�GINA NÃƒO REDIRECIONAR
					RequestContext.getCurrentInstance().execute("alert('O sistema se recuperou de um erro inesperado!')");
					
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "ERRO", "O sistema se recuperou de um erro inesoerado..."));
					
					navigationHandler.handleNavigation(faceContext, null, 
							"/error/error.jsf?faces-redirect=true&expired=true");
				}
				
				//POR ULTIMO RENDERIZA A PÃ�GINA DE ERRO E EXIBE AS MENSAGENS
				faceContext.renderResponse();
				
				
			}catch (Exception error) {
				Messages.addGlobalError("Ocorreu um erro ao tentar realizar uma ação!!");
				error.printStackTrace();	
				
			}finally{
				SessionFactory sf = HibernateUtil.getSessionFactory();
				
				if(sf.getCurrentSession().getTransaction().isActive()){
					sf.getCurrentSession().getTransaction().rollback();
				}
				
				//IMPRIME O ERRO NO CONSOLE
				exeption.printStackTrace();
				
				iterator.remove();
				
			}			
		}
		
		getWrapped().handle();		
		
	}

}
