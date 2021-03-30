package br.com.project.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.project.model.classes.Item;

@FacesConverter(forClass = Item.class)
public class ItemConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
		// RECEBE CÓDIGO DO OBJETO
		if (codigo != null && !codigo.isEmpty()){
			
			//CONSULTA NO BANCO
			return (Item) HibernateUtil.getCurrentSession().get(Item.class, new Long(codigo));
		}
		return codigo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		// RECEBE OBJETO E RETORNA O CÓDIGO - TIPO STRING
		if (objeto != null){
			Item c = (Item) objeto;
			return c.getId() != null && c.getId() > 0 ? c.getId().toString() : null;
		}

		//RETORNA NULL CASO O OBJETO SEJA NULL
		return null;
	}	
}
