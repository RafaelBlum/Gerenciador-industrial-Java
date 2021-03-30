package br.com.project.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.project.model.classes.Fornecedor;

@FacesConverter(forClass = Fornecedor.class)
public class FornecedorConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
		// RECEBE CODIGO DO OBJETO
		if (codigo != null && !codigo.isEmpty()){
			
			//CONSULTA NO BANCO
			return (Fornecedor) HibernateUtil.getCurrentSession().get(Fornecedor.class, new Long(codigo));
		}
		return codigo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		// RECEBE OBJETO E RETORNA O CODIGO - TIPO STRING
		if (objeto != null){
			Fornecedor f = (Fornecedor) objeto;
			return f.getId() != null && f.getId() > 0 ? f.getId().toString() : null;
		}

		//RETORNA NULL CASO O OBJETO SEJA NULL
		return null;
	}	
}
