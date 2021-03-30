package br.com.project.bean.view.grafico;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class ChartsView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private PieChartModel grafico = new PieChartModel();
	
	public PieChartModel getPieGrafico(){
		int random1 = (int) (Math.random() * 1000);
		int random2 = (int) (Math.random() * 1000);
		
		grafico.getData().put("Teste 1", random1);
		grafico.getData().put("Teste 2", random2);
		
		return grafico;
	}
	

	public PieChartModel getGrafico() {
		return grafico;
	}


}
