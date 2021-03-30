package br.com.project.acessos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Permissao {
	
	ADMIN("ADMIN", "Administrador"),
	USER("USER", "Usuário Padrão"),	
	
	CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - acessar"),
	
	PRODUTO_ACESSAR("PRODUTO_ACESSAR", "Produto - Acessar"),
	PRODUTO_NOVO("PRODUTO_NOVO", "Produto - Novo"),
	PRODUTO_EDITAR("PRODUTO_EDITAR", "Produto - Editar"),
	PRODUTO_EXCLUIR("PRODUTO_EXCLUIR", "Produto - Excluir"),
	
	SETOR_ACESSAR("SETOR_ACESSAR", "Setor - Acessar"),
	SETOR_NOVO("SETOR_NOVO", "Setor - Novo"),
	SETOR_EDITAR("SETOR_EDITAR", "Setor - Editar"),
	SETOR_EXCLUIR("SETOR_EXCLUIR", "Setor - Excluir"),
	
	PROCEDIMENTO_ACESSAR("PROCEDIMENTO_ACESSAR", "Procedimento - Acessar"),
	PROCEDIMENTO_NOVO("PROCEDIMENTO_NOVO", "Procedimento - Novo"),
	PROCEDIMENTO_EDITAR("PROCEDIMENTO_EDITAR", "Procedimento - Editar"),
	PROCEDIMENTO_EXCLUIR("PROCEDIMENTO_EXCLUIR", "Procedimento - Excluir"),
	
	META_ACESSAR("META_ACESSAR", "Meta - Acessar"),
	META_NOVO("META_NOVO", "Meta - Novo"),
	META_EDITAR("META_EDITAR", "Meta - Editar"),
	META_EXCLUIR("META_EXCLUIR", "Meta - Excluir"),
	META_ADD_PROD("META_ADD_PROD", "Adicina produto Meta - Adiciona Produto"),
	
	COMPRA_ACESSAR("COMPRA_ACESSAR", "Procedimento - Acessar"),
	COMPRA_NOVO("COMPRA_NOVO", "Procedimento - Novo"),
	COMPRA_EDITAR("COMPRA_EDITAR", "Procedimento - Editar"),
	COMPRA_EXCLUIR("COMPRA_EXCLUIR", "Procedimento - Excluir"),
	
	FORNECEDOR_ACESSAR("FORNECEDOR_ACESSAR", "Fornecedor - Acessar"),
	FORNECEDOR_NOVO("FORNECEDOR_NOVO", "Fornecedor - Novo"),
	FORNECEDOR_EDITAR("FORNECEDOR_EDITAR", "Fornecedor - Editar"),
	FORNECEDOR_EXCLUIR("FORNECEDOR_EXCLUIR", "Fornecedor - Excluir"),
	
	USUARIO_ACESSAR("USUARIO_ACESSAR", "Usuário - Acessar"),
	USUARIO_NOVO("USUARIO_NOVO", "Usuário - Novo"),
	USUARIO_EDITAR("USUARIO_EDITAR", "Usuário - Editar"),
	USUARIO_EXCLUIR("USUARIO_EXCLUIR", "Usuário - Excluir"),
	
	ORCAMENTO_ACESSAR("ORCAMENTO_ACESSAR", "Orçamento - Acessar"),
	ORCAMENTO_NOVO("ORCAMENTO_NOVO", "Orçamento - Novo"),
	ORCAMENTO_EDITAR("ORCAMENTO_EDITAR", "Orçamento - Editar"),
	ORCAMENTO_EXCLUIR("ORCAMENTO_EXCLUIR", "Orçamento - Excluir"),
	
	CONTA_ACESSAR("CONTA_ACESSAR", "Conta RazÃ£o - Acessar"),
	CONTA_NOVO("CONTA_NOVO", "Conta Razão - Novo"),
	CONTA_EDITAR("CONTA_EDITAR", "Conta Razão - Editar"),
	CONTA_EXCLUIR("CONTA_EXCLUIR", "Conta Razão - Excluir"),

	GERA_PDF("GERA_PDF", "Gerar PDF - Acessar"),
	GERA_GRAFICO("GERA_GRAFICO", "Gera Gráfico - Acessar"),
	
	MENSAGEM_ACESSAR("MENSAGEM_ACESSAR", "Mensagem recebida - Acessar");

	private String valor = "";
	private String descricao = "";
	
	private Permissao(String name, String descricao){
		this.valor = name;
		this.descricao = descricao;
	}
	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return getValor();
	}
	
	//DEIXAR AS PERMISSÃ•ES EM ORDEM ALFABÃ‰TIA*******************
	public static List<Permissao> getListPermissao(){
		List<Permissao> permissoes = new ArrayList<Permissao>();
		for(Permissao permissao: Permissao.values()){
			permissoes.add(permissao);
		}
		
		Collections.sort(permissoes, new Comparator<Permissao>(){
			@Override
			public int compare(Permissao o1, Permissao o2) {
				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}			
		});
		
		return permissoes;
	}
	
}
