package br.com.project.listener;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@ApplicationScoped
public class ContextLoaderListenerGerIndustrialUtils extends ContextLoaderListener implements Serializable{

	private static final long serialVersionUID = 1L;
	/*AULA 109
	 * ESTA CLASSE VAI PROVER TODAS INFORMAÇÕES DO AMBIENTE DE EXECUÇÃO DO SPRING.
	 * UNICA PARA TODOs SISTEMA
	 * */
	
	//METODO PASSA TODOSl CONTEXTO
	private static WebApplicationContext getWac(){
		return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}
	
	//METODOs PARA RETORNAR O ID OU CLASSE - (objeto do proprio spring)
	public static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}
	
	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}

}
