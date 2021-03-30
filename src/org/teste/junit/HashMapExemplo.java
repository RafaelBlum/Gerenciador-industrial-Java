package org.teste.junit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapExemplo {
	
	public static void main(String[] args){
		Map veiculos = new HashMap();
		
		veiculos.put("BMW", 10);
		veiculos.put("MOTO", 3);
		veiculos.put("FORD", 8);
		
		System.out.println("Total veiculo: " + veiculos.size());
		
		for (String string : args) {
			
		}
		

		
	}

}
