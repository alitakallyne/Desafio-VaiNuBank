package com.vainubank.conta;

import java.time.LocalDate;

import com.vainubank.exception.SaldoInsuficienteException;
import com.vainubank.pessoa.Pessoa;

public abstract class Conta {

	private int numero;
    private String agencia;
    private Pessoa titular;
    private double saldo;
    private String tipoConta;
    private LocalDate dataCriacao;
    private boolean isAtivo;
    private String chavePix;
    
    public Conta() {    }
    

    
    public Conta(int numero, String agencia, Pessoa titular, double saldo, String tipoConta, String chavePix) {
		super();
		this.numero = numero;
		this.agencia = agencia;
		this.titular = titular;
		this.saldo = saldo;
		this.tipoConta = tipoConta;
		this.dataCriacao = LocalDate.now();
		this.isAtivo  = true;
		this.chavePix = chavePix;
		
		// Adiciona a conta à lista de contas da pessoa titular
        titular.adicionarConta(this);
	}



	public int getNumero() {
		return numero;
	}

	public String getAgencia() {
		return agencia;
	}

	public Pessoa getTitular() {
		return titular;
	}

	public double getSaldo() {
		return saldo;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public void setTitular(Pessoa titular) {
		this.titular = titular;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}
	
	
	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}



	public abstract void sacar(double valor) throws SaldoInsuficienteException;

    public void depositar(double valor) {
        this.saldo += valor;
    }

    public void transferir(Conta destino, double valor) throws SaldoInsuficienteException {
        this.sacar(valor);
        destino.depositar(valor);
    }
}
