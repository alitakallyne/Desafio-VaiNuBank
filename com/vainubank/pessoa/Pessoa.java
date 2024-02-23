package com.vainubank.pessoa;

import java.util.ArrayList;
import java.util.List;

import com.vainubank.conta.Conta;

public class Pessoa {

	    private String nome;
	    private String cpf;
	    private String endereco;
	    private String telefone;
	    private String email;
	    private List<Conta> contas;
	    
		public Pessoa() {
			
		}
		
		public Pessoa(String nome, String cpf, String endereco, String telefone, String email) {
			super();
			this.nome = nome;
			this.cpf = cpf;
			this.endereco = endereco;
			this.telefone = telefone;
			this.email = email;
			this.contas = new ArrayList<>();
		}
		
		public String getNome() {
			return nome;
		}
		public String getCpf() {
			return cpf;
		}
		public String getEndereco() {
			return endereco;
		}
		public String getTelefone() {
			return telefone;
		}
		public String getEmail() {
			return email;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}
		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	    
		 public void adicionarConta(Conta conta) {
		        contas.add(conta);
		    }

	    public List<Conta> getContas() {
	        return contas;
	    }
    
	    
}

