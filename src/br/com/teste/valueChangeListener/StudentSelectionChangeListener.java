package br.com.teste.valueChangeListener;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class StudentSelectionChangeListener implements ValueChangeListener {

	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {
		StudentBean studentBean = (StudentBean)FacesContext.getCurrentInstance().
				getExternalContext().getRequestMap().get("studentBean");
		studentBean.setStudentName(event.getNewValue().toString());
	}
}
