package br.com.project.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.utils.UtilFramework;
import br.com.project.listener.ContextLoaderListenerGerIndustrialUtils;
import br.com.project.model.classes.Entidade;

@WebFilter(filterName =  "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable{

	/**
	 * RESPONSAVEL POR INTERCEPTAR TODAS TRANSAÃ‡Ã•ES, COMMIT E ROLLBACK
	 * QUALQUER OPERAÃ‡ÃƒO OU CLIQUE (SALVAR, EDITAR), PASSAR POR ESTE FILTRO.
	 */
	
	private static final long serialVersionUID = 1L;
	
	private static SessionFactory sf;
	
	//EXECUTA APENAS UMA VEZ
	//EXECUTA QUANDO A APLICAÃ‡ÃƒO ESTA SENDO INICIADA
	@Override
	protected void initFilterBean() throws ServletException{
		sf = HibernateUtil.getSessionFactory();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
															throws ServletException, IOException {
		
		//JDBC DO SPRING (COMMIT / ROLLBACK)
		BasicDataSource springDataSource = (BasicDataSource) ContextLoaderListenerGerIndustrialUtils.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try{
			request.setCharacterEncoding("UTF-8"); //DEFINE CODIFICAÃ‡ÃƒO
			
			//CAPTURA USUÃ�RIO QUE FAZ A OPERAÃ‡ÃƒO
			HttpServletRequest request2 = (HttpServletRequest) request;
			
			HttpSession sessao = request2.getSession();
			Entidade userLogadoSessao = (Entidade) sessao.getAttribute("userLogadoSessao");
			
			if(userLogadoSessao != null){
				UtilFramework.getThreadLocal().set(userLogadoSessao.getEnt_codigo());
			}
			
			sf.getCurrentSession().beginTransaction();
			
			//ANTES DE EXECUTAR A AÃ‡ÃƒO - (REQUEST)
			chain.doFilter(request, response); //EXECUTA NOSSA AÃ‡ÃƒO NO SERVIDOR
			//APÃ“S DE ECECUTAR AÃ‡ÃƒO (RESPOSTA)
			
			transactionManager.commit(status);
			
			if(sf.getCurrentSession().getTransaction().isActive()){
				sf.getCurrentSession().flush();
				sf.getCurrentSession().getTransaction().commit();
			}
			
			if(sf.getCurrentSession().isOpen()){
				sf.getCurrentSession().close();
			}
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
		}catch(Exception e){			
			transactionManager.rollback(status);
			e.printStackTrace();
			
			if(sf.getCurrentSession().getTransaction().isActive()){
				sf.getCurrentSession().getTransaction().rollback();
			}
			
			if(sf.getCurrentSession().isOpen()){
				sf.getCurrentSession().close();
			}
			
			
		}finally {
			if(sf.getCurrentSession().isOpen()){
				if(sf.getCurrentSession().beginTransaction().isActive()){
					sf.getCurrentSession().flush();
					sf.getCurrentSession().clear();
				}				
				if(sf.getCurrentSession().isOpen()){
					sf.getCurrentSession().close();
				}
			}
		}
		
		
	}

}
