package br.com.project.geral.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.RequisicaoDeCompra;
import br.com.repository.interfaces.RepositoryRequisicao;
import br.com.srv.interfaces.SrvRequisicao;

@Controller
public class RequisicaoController extends ImplemantacaoCRUD<RequisicaoDeCompra> 
implements InterfaceCrud<RequisicaoDeCompra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SrvRequisicao srvRequisicao;
	
	@Resource
	private RepositoryRequisicao repositoryRequisicao;
	
	@RequestMapping("**/gerarGraficoMes")
	public @ResponseBody String gerarGraficoMes(@RequestParam(value = "dias") int dias){
		
		List<Map<String, Object>> totalComprasMes = getTotalComprasMes(dias);
		
		//PARA NUNCA SER ZERO, COLOCAMOS + 1
		int totalLinhas = totalComprasMes.size() + 1;
		
		boolean semDados = false;
		
		if (totalLinhas <= 1){
			totalLinhas++;
			semDados = true;  
		}
		
		String[] dados = new String[totalLinhas];
		
		int cont = 0;
		
		if (semDados){
			dados[cont ++] = "[\"" +  "Sem Dados" +  "\"," + "\"" + 0  +  "\"," +  "\"" + 0 +  "\"]";
		}else{
			dados[cont] = "[\"" + "mes" +  "\"," + "\"" + "total_gasto " +  "\"]";
			cont ++;
			for (Map<String, Object> map : totalComprasMes) {
				dados[cont] = "[\"" +  map.get("mes") +  "\"," + "\"" +  map.get("total_gasto") +  "\"]";
				cont++;
			}
		}
		
		return Arrays.toString(dados);
	}
	
	private List<Map<String, Object>> getTotalComprasMes(int dias) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SET @totalagregado := "+dias+"; ");
		sql.append("SELECT "); 
		sql.append("resultados_mes.mes as mes, ");
		sql.append("resultados_mes.total_gasto as total_gasto ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT DISTINCT MONTH(dataRequisicao) as mes, SUM(total) as total_gasto ");
		sql.append("FROM requisicaodecompra ");
		sql.append("GROUP BY mes ");
		sql.append("ORDER BY mes ASC ");  
		sql.append(") as resultados_mes ");
		
		return super.getSimpleJdbcTemplate().queryForList(sql.toString());
	}

}
