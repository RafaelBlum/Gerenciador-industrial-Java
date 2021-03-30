package org.teste.junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.project.geral.controller.ProdutoController;
import br.com.project.model.classes.Produto;

@ManagedBean(name = "prodUserTest", eager = true)
@SessionScoped
public class ProdUserTest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProdutoController produtoController;
	
	private static Map<String,String> countryMap;
	
	private Produto obj =  new Produto();
	
	private List<Produto> produtos = new ArrayList<Produto>();
	
	private String selectedCountry = "United Kingdom"; //default value
	
	public Map<String, String> getProdutos() throws Exception {
		produtos = produtoController.findList(Produto.class);
		
		countryMap = new LinkedHashMap<String,String>();
		
		for (int i = 0; i < produtos.size(); i++) {
			String n1 = Integer.toString(produtos.get(i).getMaterial());
			String n2 = produtos.get(i).getNome();
			
			countryMap.put(n1, n2);
		}
		
		return countryMap;
	}
	
	static {
		
	}
	public void localeChanged(ValueChangeEvent e) {
		//assign new value to country
		selectedCountry = e.getNewValue().toString();
	}
	public Map<String, String> getCountries() throws Exception {
		getProdutos();
		return countryMap;
	}
	public String getSelectedCountry() {
		return selectedCountry;
	}
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	
}
