package br.com.teste.valueChangeListener;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
@ManagedBean(name = "studentBean", eager = true)
@RequestScoped
public class StudentBean {
	private String studentName ="Ram";
	private Map<String, String> studentMap = new LinkedHashMap<String, String>();
	{
		studentMap.put("student-1", "Ram");
		studentMap.put("student-2", "Shyam");
		studentMap.put("student-3", "Mohan");
	}
	public Map<String, String> getStudents(){
		return studentMap;
	}
	public void selectionChanged(ValueChangeEvent e){
		studentName = e.getNewValue().toString();
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}