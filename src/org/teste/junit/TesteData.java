package org.teste.junit;

import java.util.Calendar;

import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.Test;

import br.com.project.report.util.DateUtils;

public class TesteData {
	
	@Test
	public void testeData(){
		try{
			System.out.println(DateUtils.formatDateSQLSimples(Calendar.getInstance().getTime()));

		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Erro");
		}		
	}
	

	
}
