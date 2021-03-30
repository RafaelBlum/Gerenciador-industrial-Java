package br.com.project.geral.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.model.SelectItem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.ContaRazao;
import br.com.repository.interfaces.RepositoryContaRazao;
import br.com.srv.interfaces.SrvContaRazao;

@Controller
public class ContaRazaoController extends ImplemantacaoCRUD<ContaRazao> implements InterfaceCrud<ContaRazao>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SrvContaRazao srvContaRazao;
	
	@Resource
	private RepositoryContaRazao repositoryContaRazao;
	
	public List<SelectItem> getListContasController() throws Exception {
		List<SelectItem> list = new ArrayList<SelectItem>();

		List<ContaRazao> contas = super.findByQueryDinamica(" from ContaRazao");

		for (ContaRazao conta : contas) {
			list.add(new SelectItem(conta, conta.getDescricao()));
		}
		return list;
	}
	
	@RequestMapping("**/gerarGraficoInicial")
	public @ResponseBody String gerarGraficoInicial(@RequestParam(value = "dias") int dias){
		
		List<Map<String, Object>> totalOrdensDias = getTotalOrdensDias(dias);
		
		//PARA NUNCA SER ZERO, COLOCAMOS + 1
		int totalLinhas = totalOrdensDias.size() + 1;
		
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
			dados[cont] = "[\"" + "Tipo" +  "\"," + "\"" + "Quantidade " +  "\"," +  "\"" + "Media R$" +  "\"]";
			cont ++;
			for (Map<String, Object> map : totalOrdensDias) {
				dados[cont] = "[\"" +  map.get("situacao") +  "\"," + "\"" +  map.get("quantidade") +  "\"," +  "\"" +  map.get("media_valor") +  "\"]";
				cont++;
			}
		}
		
		return Arrays.toString(dados);
	}

	private List<Map<String, Object>> getTotalOrdensDias(int dias) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("(select count(1) as quantidade, contaRazao as situacao, truncate(avg(coalesce(valor_un, 0.00)), "+dias+") as media_valor ");
		sql.append("from item ");
		sql.append("group by contaRazao) order by media_valor ");
		
		return super.getSimpleJdbcTemplate().queryForList(sql.toString());
	}
	


}
