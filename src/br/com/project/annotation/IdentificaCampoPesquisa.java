package br.com.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public abstract @interface IdentificaCampoPesquisa {
	
	String descricaoCampo(); //DESCRIÇÃO DO CAMPO PARA TELA
	String campoConsulta();  // CAMPO DO BANCO
	int principal() default 10000; // POSIÇÃO QUE IRÁ APARECER NO COMBO	
	
}
