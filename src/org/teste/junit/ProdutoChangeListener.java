package org.teste.junit;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import br.com.project.model.classes.Produto;

public class ProdutoChangeListener implements ValueChangeListener {
	@Override
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		
		//
		Produto p = (Produto) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("p");
		p.setNome(event.getNewValue().toString());
	}
}
