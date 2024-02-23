package com.vainubank.conta;

import com.vainubank.exception.SaldoInsuficienteException;
import com.vainubank.pessoa.Pessoa;

public class ContaPoupanca extends Conta {
	private int diaAniversario;
	private static final double TAXA_RENDIMENTO = 0.005; // 0.5% ao ano

	
	public ContaPoupanca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContaPoupanca(int numeroConta, String agenciaConta, Pessoa titular, double saldoInicial,String chavePix, int diaAniversario) {
		super(numeroConta, agenciaConta, titular, saldoInicial, "Poupança",chavePix);
		this.diaAniversario = diaAniversario;
		
	}


	@Override
	public void sacar(double valor) throws SaldoInsuficienteException{
		double saldoDisponivel = getSaldo() + calcularRendimento();
		if (valor > saldoDisponivel) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque de R$" + valor);
        }
        // Aplica juros de 2%
        setSaldo(getSaldo() - (valor * 0.02));
	}
	
	 public void aplicarRendimento() {
	        double rendimento = calcularRendimento();
	        setSaldo(getSaldo() + rendimento);
	    }

	
	 private double calcularRendimento() {
		    double taxaMensal = TAXA_RENDIMENTO / 12.0;
	        return getSaldo() * taxaMensal;
	 }
}
