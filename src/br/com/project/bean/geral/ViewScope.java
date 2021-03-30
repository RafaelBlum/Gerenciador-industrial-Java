package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

public class ViewScope implements Scope, Serializable {

	/**
	 * AULA 96 - FALA SOBRE O SCOPE DO PROJETO COM SPRING E JSF
	 * 1Âº CLASSE PARA REGISTRAR SCOPE DE VIEW DO SPRING
	 */

	// PRECISAMOS UMA INSTANCIA DE VARIAVEL PARA O REMOVE
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

	private static final long serialVersionUID = 1L;

	// getViewRoot(): RETORNA O COMPONENTE RAIZ QUE ESTA ASSOCIADO A ESTA SOLICITAÇÃO(REQUEST)
	// getViewMap(): RETORNA UM MAP QUE ATUA COMO A INTERFACE PARA O ARMAZENAMENTO
	// DE DADOS
	private Map<String, Object> getViewMap() {
		return FacesContext.getCurrentInstance() != null ? FacesContext.getCurrentInstance().getViewRoot().getViewMap()
				: new HashMap<String, Object>();

	}

	// METODO RESPONSAVEL POR RETORNAR O OBJETO DE SCOPE
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = getViewMap().get(name);
		if (instance == null) {
			instance = objectFactory.getObject();
			getViewMap().put(name, instance);
		}
		return instance;
	}

	@Override
	public String getConversationId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);

		return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}

	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if (callbacks != null) {
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
	}

	@Override
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);
		if (instance != null) {
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if (callBacks != null) {
				callBacks.remove(name);
			}
		}
		return instance;
	}

	@Override
	public Object resolveContextualObject(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		return facesRequestAttributes.resolveReference(name);
	}

}
