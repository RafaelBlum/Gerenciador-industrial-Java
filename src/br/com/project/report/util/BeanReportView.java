package br.com.project.report.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import br.com.project.Util.all.BeanViewAbstract;

@Component
public abstract class BeanReportView extends BeanViewAbstract{

	/**
	 * CLASSE CRIADA PARA FACILITAR A UTILIZAÇÃO DE PASSAGEM DOS PARAMETROS REALIZADOS NA CLASSE "REPORTUTIL"
	 * ATRIBUTOS PROTEGIDOS POR QUE, APENAS QUE EXTENDER A CLASSE BEANREPORTVIEW VAI PODER TER ACESSO.
	 */
	private static final long serialVersionUID = 1L;
	
	//RETORNAR ARQUIVO PRONTA PARA WEB
	protected StreamedContent arquivoReport;
	
	protected int tipoRelatorio;
	
	//LISTA DE DADOS - POR EX: BAIRRO, DOCUMENTOS, CIDADES, ETC...
	protected List<?> listaDataBeanCollectionReport;
	
	HashMap<Object, Object> parametrosRelatorio;
	
	//NOME PADRÃO DO RELATORIO, CASO VENHA A NÃO DEFINIR
	protected String nomeRelatorioJasper = "default";
	
	protected String nomeRelatorioSaida = "default";
	
	//INJEÇÃO DE DEPENDENCIAS
	@Resource
	private ReportUtil reportUtil;
	
	@SuppressWarnings("rawtypes")
	public BeanReportView(){
		parametrosRelatorio = new HashMap<Object, Object>();
		listaDataBeanCollectionReport = new ArrayList();
	}
	
	public ReportUtil getReportUtil() {
		return reportUtil;
	}
	
	public void setReportUtil(ReportUtil reportUtil) {
		this.reportUtil = reportUtil;
	}
	
	//METODO IMPORTANTE, POR FACILITAR E MONTAR
	public StreamedContent getArquivoReport() throws Exception{
		return getReportUtil().geraRelatorio(getListaDataBeanCollectionReport(), 
											getParametrosRelatorio(), getNomeRelatorioJasper(), 
											getNomeRelatorioSaida(), getTipoRelatorio());
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public List<?> getListaDataBeanCollectionReport() {
		return listaDataBeanCollectionReport;
	}

	public void setListaDataBeanCollectionReport(List<?> listaDataBeanCollectionReport) {
		this.listaDataBeanCollectionReport = listaDataBeanCollectionReport;
	}

	public HashMap<Object, Object> getParametrosRelatorio() {
		return parametrosRelatorio;
	}

	public void setParametrosRelatorio(HashMap<Object, Object> parametrosRelatorio) {
		this.parametrosRelatorio = parametrosRelatorio;
	}

	public String getNomeRelatorioJasper() {
		return nomeRelatorioJasper;
	}

	public void setNomeRelatorioJasper(String nomeRelatorioJasper) {
		if(nomeRelatorioJasper == null || nomeRelatorioJasper.isEmpty()){
			nomeRelatorioJasper = "default";
		}
		
		this.nomeRelatorioJasper = nomeRelatorioJasper;
	}

	public String getNomeRelatorioSaida() {
		return nomeRelatorioSaida;
	}

	public void setNomeRelatorioSaida(String nomeRelatorioSaida) {
		if(nomeRelatorioSaida == null || nomeRelatorioSaida.isEmpty()){
			nomeRelatorioSaida = "default";
		}
		
		this.nomeRelatorioSaida = nomeRelatorioSaida;
	}

}
