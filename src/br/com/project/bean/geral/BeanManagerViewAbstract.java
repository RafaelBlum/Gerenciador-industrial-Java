package br.com.project.bean.geral;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.stereotype.Component;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.annotation.IdentificaCampoPesquisa;
import br.com.project.enums.CondicaoPesquisa;
import br.com.project.report.util.BeanReportView;
import br.com.project.report.util.UtilitariaRegex;

@Component
public abstract class BeanManagerViewAbstract extends BeanReportView{
	
	private static final long serialVersionUID = 1L;
	
	protected abstract Class<?> getClassImplement();
	
	protected abstract InterfaceCrud<?> getController();
	
	public ObjetoCampoConsulta campoPesquisaSelecionado;
	
	public List<SelectItem> listaCampoPesquisa;
	
	public List<SelectItem> listaCondicaoPesquisa;
	
	public abstract String condicaoAndParaPesquisa() throws Exception;
	
	public CondicaoPesquisa condicaoPesquisaSelecionado;
	
	public String valorPesquisa;
	
	
	protected String getSqlLazyQuery() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select entity from ");
		sql.append(getQueryConsulta());
		sql.append(" order by entity.");
		sql.append(campoPesquisaSelecionado.getCampoBanco());
		System.out.println("Metodo SqlLazyQuery: " + sql.toString());
		return sql.toString();
	}
	
	protected String getSqlLazyQueryNative() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from ");
		sql.append(getQueryConsultaNative());
		sql.append(" order by entity.");
		sql.append(campoPesquisaSelecionado.getCampoBanco());
		return sql.toString();
	}
	
	protected int totalRegistroConsulta() throws Exception {
		 String sql = (" select count(1) from " + getQueryConsulta()).toLowerCase();
		 System.out.println("Metodo totalRegistroConsulta: " + sql.toString());
		 return getController().getJdbcTemplate().queryForInt(sql);
	}
	
	private StringBuilder getQueryConsultaNative() throws Exception {
		valorPesquisa = new  UtilitariaRegex().retiraAcentos(valorPesquisa);
		StringBuilder sql = new StringBuilder();
		sql.append(getClassImplement().getSimpleName());
		sql.append(" as entity where ");

		sql.append(" retira_acentos(upper(cast(entity.");
		sql.append(campoPesquisaSelecionado.getCampoBanco());
		sql.append(" as character(100)  ))) ");

		if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.IGUAL_A.name())) {
			sql.append(" = retira_acentos(upper('");
			sql.append(valorPesquisa);
			sql.append("'))");
		} else if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.CONTEM.name())) {
			sql.append(" like retira_acentos(upper('%");
			sql.append(valorPesquisa);
			sql.append("%'))");
		} else if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.INICIA_COM.name())) {
			sql.append(" like retira_acentos(upper('");
			sql.append(valorPesquisa);
			sql.append("%'))");
		} else if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.TERMINA_COM.name())) {
			sql.append(" like retira_acentos(upper('%");
			sql.append(valorPesquisa);
			sql.append("'))");
		}
		sql.append(" ");
		sql.append(condicaoAndParaPesquisa());
		return sql;
	}

	private StringBuilder getQueryConsulta() throws Exception {
		valorPesquisa = new  UtilitariaRegex().retiraAcentos(valorPesquisa);
		StringBuilder sql = new StringBuilder();
		sql.append(getClassImplement().getSimpleName());
		sql.append(" entity where ");

		sql.append(" retira_acentos(upper(cast(entity.");
		sql.append(campoPesquisaSelecionado.getCampoBanco());
		sql.append(" as character(100) ))) ");

		if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.IGUAL_A.name())) {
			sql.append(" = retira_acentos(upper('");
			sql.append(valorPesquisa);
			sql.append("'))");
		} else if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.CONTEM.name())) {
			sql.append(" like retira_acentos(upper('%");
			sql.append(valorPesquisa);
			sql.append("%'))");
		} else if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.INICIA_COM.name())) {
			sql.append(" like retira_acentos(upper('");
			sql.append(valorPesquisa);
			sql.append("%'))");
		} else if (condicaoPesquisaSelecionado.name().equals(
				CondicaoPesquisa.TERMINA_COM.name())) {
			sql.append(" like retira_acentos(upper('%");
			sql.append(valorPesquisa);
			sql.append("'))");
		}
		sql.append(" ");
		sql.append(condicaoAndParaPesquisa());
		System.out.println("Metodo QueryConsulta: " + sql.toString());
		return sql;
	}
	
	
	public List<SelectItem> getListaCampoPesquisa() {
		listaCampoPesquisa = new ArrayList<SelectItem>();
		List<ObjetoCampoConsulta> listTemp = new ArrayList<ObjetoCampoConsulta>();
		
		for (Field field : getClassImplement().getDeclaredFields()) {
			
			if (field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
				String descricaoCampo = field.getAnnotation(
						IdentificaCampoPesquisa.class).descricaoCampo();
				String descricaoCampoConsulta = field.getAnnotation(
						IdentificaCampoPesquisa.class).campoConsulta();
				int isPrincipal = field.getAnnotation(
						IdentificaCampoPesquisa.class).principal();

				ObjetoCampoConsulta objetoCampoConsulta = new ObjetoCampoConsulta();
				objetoCampoConsulta.setDescricao(descricaoCampo);
				objetoCampoConsulta.setCampoBanco(descricaoCampoConsulta);
				objetoCampoConsulta.setTipoClass(field.getType().getCanonicalName());
				objetoCampoConsulta.setPrincipal(isPrincipal);

				listTemp.add(objetoCampoConsulta);

			}
		}

		ordernarReverse(listTemp);

		for (ObjetoCampoConsulta objetoCampoConsulta : listTemp) {
			listaCampoPesquisa.add(new SelectItem(objetoCampoConsulta));
		}

		return listaCampoPesquisa;
	}	
	
	private void ordernarReverse(List<ObjetoCampoConsulta> listTemp){
		Collections.sort(listTemp, new Comparator<ObjetoCampoConsulta>() {

			@Override
			public int compare(ObjetoCampoConsulta obj_1, ObjetoCampoConsulta obj_2) {

				return obj_1.isPrincipal().compareTo(obj_2.isPrincipal());
			}
		});
	}
	
	
	public ObjetoCampoConsulta getCampoPesquisaSelecionado() {
		return campoPesquisaSelecionado;
	}

	public void setCampoPesquisaSelecionado(
			ObjetoCampoConsulta campoPesquisaSelecionado) {
		if (campoPesquisaSelecionado != null) {
			for (Field field : getClassImplement().getDeclaredFields()) {
				if (field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
					if (campoPesquisaSelecionado.getCampoBanco().equalsIgnoreCase(field.getName())) {
						String descricaoCampo = field.getAnnotation(IdentificaCampoPesquisa.class).descricaoCampo();
						campoPesquisaSelecionado.setDescricao(descricaoCampo);
						campoPesquisaSelecionado.setTipoClass(field.getType().getCanonicalName());
						campoPesquisaSelecionado.setPrincipal(field.getAnnotation(IdentificaCampoPesquisa.class).principal());
						break;
					}

				}
			}
		}
		this.campoPesquisaSelecionado = campoPesquisaSelecionado;
	}
	
	
	public List<SelectItem> getListaCondicaoPesquisa() {
		listaCondicaoPesquisa = new ArrayList<SelectItem>();
		for (CondicaoPesquisa enumCp : CondicaoPesquisa.values()) {
			listaCondicaoPesquisa
					.add(new SelectItem(enumCp, enumCp.toString()));
		}
		return listaCondicaoPesquisa; 
	}
	
	
	public void setCondicaoPesquisaSelecionado(CondicaoPesquisa condicaoPesquisaSelecionado) {
		this.condicaoPesquisaSelecionado = condicaoPesquisaSelecionado;
	}
	
	public CondicaoPesquisa getCondicaoPesquisaSelecionado() {
		return condicaoPesquisaSelecionado;
	}
	
	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;
	}
	
	public String getValorPesquisa() {
		return valorPesquisa != null ? new  UtilitariaRegex().retiraAcentos(valorPesquisa): "";
	}


}
