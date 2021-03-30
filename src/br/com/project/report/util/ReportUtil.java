package br.com.project.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String UNDERLINE = "_";
	private static final String FOLDER_RELATORIOS = "/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String EXTENSION_ODS = "ods";
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_HTML = "html";
	private static final String EXTENSION_PDF = "pdf";
	private String SEPARATOR = File.separator;
	private static final int RELATORIO_PDF = 1;
	private static final int RELATORIO_EXCEL = 2;
	private static final int RELATORIO_HTML = 3;
	private static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;
	private static final String PONTO = ".";
	private StreamedContent arquivoRetorno = null;
	private String caminhoArquivoRelatorio = null;
	private JRExporter tipoArquivoExportado = null;
	private String caminhoSubreport_dir = "";
	private String extensaoArquivoExportado = "";
	private File arquivoGerado = null;

	/*
	 * GERAÇÃO DO RELATORIO:: CRIA A LISTA DE COLLECTIONDATASOURCE DE BEANS QUE
	 * CARREGA OS DADOS PARA O RELATÓRIO NÃO VAMOS GERAR O RELATORIO EXECUTANDO
	 * SQL POR SER DE BAIXA PERFORMANCE, ASSIM PASSAMOS UMA LISTA PRONTA PARA O
	 * RELATORIO.
	 * 
	 * 2º FORNECE O CAMINHO FISICO ATÉ A PASTA QUE CONTÉM OS RELATÓRIOS
	 * COMPILADOS .jasper.
	 * 
	 * 3º MONTA O CAMINHO COMPLETO ATÉ A PASTA DE RELATORIO EX:
	 * C:/aplicacao/relatorios/rel_bairro.jasper
	 * 
	 * 4º IMAGEM NO RELATORIO. CAMINHO PARA IMAGEM
	 * 
	 * 5º PRECISAMOS ESPECIFICAR O CAMINHO DO RELATORIO NOVAMENTE, PORQUE A
	 * CONDIÇÃO IF LINHA 69 SE POR DENTRO GERA UM CAMINHO OU SE POR FORA GERA
	 * OUTRO CAMINHO DIFERENTE, ENTÃO INDEPENDENTE DOS CAMINHOS, PRECISAMOS DO
	 * CAMINHO CORRETO PARA OS DOIS.
	 * 
	 * 6º FAZ CARREGAMENTO DO RELATORIO
	 * 
	 * 7º SETA PARAMETRO SUBREPORT_DIR COMO CAMINHO FISICO PARA SUB-REPORTS
	 * 
	 */

	public StreamedContent geraRelatorio(List<?> listDataBeanCollectionReport, 
										HashMap parametrosRelatorio, String nomeRelatorioJaper, 
										String nomeRelatorioSaida, int TipoRelatorio) throws Exception{
		
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listDataBeanCollectionReport);
		
		//2
		FacesContext context = FacesContext.getCurrentInstance();
		context.responseComplete();
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
		
		//
		String caminhoRelatorio = scontext.getRealPath(FOLDER_RELATORIOS);
		
		//3
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJaper + PONTO + "jasper");
		
		//CONDIÇÃO CRIADA PARA QUE A APLICAÇÃO NÃO SE PERCA
		if(caminhoRelatorio == null || caminhoRelatorio != null && caminhoRelatorio.isEmpty() || !file.exists()){
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}
		
		//4
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);
		
		//5
		String caminhoArquivoJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJaper + PONTO + "jasper";
		
		//6
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);
		
		caminhoSubreport_dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_dir);
		
		//CARREGA O ARQUIVO COMPILADO PARA MEMORIA
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrBeanCollectionDataSource);
		
		switch (TipoRelatorio) {
		case RELATORIO_PDF:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
			
		case RELATORIO_EXCEL:
			tipoArquivoExportado = new JRXlsExporter();
			extensaoArquivoExportado = EXTENSION_XLS;
			break;
			
		case RELATORIO_HTML:
			tipoArquivoExportado = new JRHtmlExporter();
			extensaoArquivoExportado = EXTENSION_HTML;
			break;
			
		case RELATORIO_PLANILHA_OPEN_OFFICE:
			tipoArquivoExportado = new JROdtExporter();
			extensaoArquivoExportado = EXTENSION_ODS;
			break;

		default:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
		}
		
		nomeRelatorioSaida += UNDERLINE + DateUtils.getDateAtualReportName();
		
		//CAMINHO RELATORIO EXPORTADO
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + PONTO + extensaoArquivoExportado;
		
		//CRIAR NOVO FILE EXPORTADO
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		//PREPARAR A IMPRESSÃO
		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		//NOME ARQUIVO A SER EXPORTADO
		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);

		//EXECUTA EXPORTAÇÃO
		tipoArquivoExportado.exportReport();
		
		//APOS FEITO DOWNLOADS: REMOÇÃO DO ARQUIVO NO SERVIDO, PARA QUE NÃO FIQUE CONSUMINDO RECURSOS NO SERVIDOR.
		arquivoGerado.deleteOnExit();
				
		//CRIAR O INPUTSTREAM PARA SER USADO PELO PRIMEFACES
		InputStream  conteudoDoRelatorio = new FileInputStream(arquivoGerado);
			
		//FAZ RETORNO PARA APLICAÇÃO
		arquivoRetorno = new DefaultStreamedContent(conteudoDoRelatorio, 
				"application/"+extensaoArquivoExportado, nomeRelatorioSaida + PONTO + extensaoArquivoExportado);
		
		return arquivoRetorno;
		
	}

}
