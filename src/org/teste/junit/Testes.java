package org.teste.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.project.acessos.Permissao;

public class Testes {
	
	public static void main(String[] args) {
		Permissao enumsList;
		List<String> teste = new ArrayList<>();
		
		
		for (Permissao string : Arrays.asList(Permissao.values())) {
    		String aux = string.name();
    		teste.add(aux);
    		System.out.println(aux);
		}
		
		System.out.println(teste.size() + " - Tamanho");
	}

}
