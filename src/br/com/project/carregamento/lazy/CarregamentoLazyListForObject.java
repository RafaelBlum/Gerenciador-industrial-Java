package br.com.project.carregamento.lazy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.framework.controller.crud.Controller;
import br.com.project.listener.ContextLoaderListenerGerIndustrialUtils;
/**
 * Classe que implementa o carregamento preguiçoso (Carregamento por demanda) para os dataTable do primefaces das telas
 * Assim os carregamentos das tabelas quando tiver muitos registros serão sempre rapidos e sem lentidão
 * @author alex
 *
 * @param <T>
 */
public class CarregamentoLazyListForObject<T> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	private List<T> list = new ArrayList<T>();

	private int totalRegistroConsulta = 0;
	
	private boolean isNartive = false;
	
	private Class<?> classImpl ;

	private String query = null;

	private Controller controller = (Controller) ContextLoaderListenerGerIndustrialUtils.getBean(Controller.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

		try {
			if (query != null && !query.isEmpty() && !isNartive)
				list = (List<T>) controller.findByQueryDinamica(query, first, pageSize);
			else if (query != null && !query.isEmpty() && isNartive) {
				list = (List<T>) controller.findByQueryDinamicaNative(query, first, pageSize);
				List<String> ids = new ArrayList<String>();
				for (Object id : list) {
					Object[] objetoBanco = (Object[]) id;
					ids.add(""+objetoBanco[0]);
				}
				
				String filelIDClass = "";
				for (Field field : classImpl.getDeclaredFields()) {
					if (field.isAnnotationPresent(Id.class)) {
						filelIDClass = field.getName();
						break;
					}
				}
				
				list = (List<T>) controller.findByQueryDinamica2("from " + classImpl.getSimpleName() + " where " + filelIDClass +" in(:" + filelIDClass +")",
				                 filelIDClass, ids, first, pageSize);
			}
			

			if (totalRegistroConsulta == 0) {
				setRowCount(list.size());
			} else {
				setRowCount(totalRegistroConsulta);
			}
			setPageSize(pageSize);
		} catch (Exception e) {
		}

		return (List<T>) list;
	}

	public void setTotalRegistroConsulta(int totalRegistroConsulta, String queryDeBuscaDeConsulta) {
		this.query = queryDeBuscaDeConsulta;
		System.out.println("TotalRegistroConsulta: " + query.toString());
		this.totalRegistroConsulta = totalRegistroConsulta;
	}

	public List<T> getList() {
		return list;
	}

	public void remove(T objetoSelecionado) {
		this.list.remove(objetoSelecionado);
	}

	public void clear() {
		this.query = null;
		this.totalRegistroConsulta = 0;
		this.list.clear();
	}

	public void add(T objetoSelecionado) {
		this.list.add(objetoSelecionado);
	}

	public void addAll(List<T> collections) {
		this.list.addAll(collections);
	}
	
	public Object getRowKey(T object) {
		return object;
	};
	
	public void setNartive(boolean isNartive) {
		this.isNartive = isNartive;
	}
	
	public void setClassImpl(Class<?> classImpl) {
		this.classImpl = classImpl;
	}

}