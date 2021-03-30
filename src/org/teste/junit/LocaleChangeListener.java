package org.teste.junit;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class LocaleChangeListener implements ValueChangeListener {
	@Override
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		
		//access country bean directly
		UserData userData = (UserData) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("userData");
		userData.setSelectedCountry(event.getNewValue().toString());
	}
}
