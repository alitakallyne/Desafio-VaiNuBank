package com.vainubank.conta;

import javax.swing.JOptionPane;

import com.vainubank.exception.SaldoInsuficienteException;
import com.vainubank.pessoa.Pessoa;

public class ContaCorrente extends Conta {
	 private String pacoteEscolhido;
	 private double limiteCredito;
	 private double taxaManutencao; 

	public ContaCorrente(int numeroConta, String agenciaConta, Pessoa titular, double saldoInicial,String chavePix) {
		 super(numeroConta, agenciaConta, titular, saldoInicial, "Corrente",chavePix);
	     escolherPacote();
	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		// TODO Auto-generated method stub
		 if (valor > (getSaldo() + limiteCredito)) {
	            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque de R$" + getSaldo());
	        }
	        setSaldo(getSaldo() - valor);
	}
	
	private void escolherPacote() {
        String[] opcoesPacote = {"Básico", "Plus", "Premium", "Digital"};
        pacoteEscolhido = (String) JOptionPane.showInputDialog(
                null,
                "Escolha um pacote para sua conta corrente:\n\n" +
                "1. Básico:\n" +
                "   - Sem taxa de manutenção mensal.\n" +
                "   - Saques ilimitados no caixa eletrônico do próprio banco.\n" +
                "   - Extrato mensal online.\n\n" +
                "2. Plus:\n" +
                "   - Taxa de manutenção mensal com desconto.\n" +
                "   - Saques ilimitados em caixas eletrônicos de qualquer banco.\n" +
                "   - Extrato mensal apenas online.\n" +
                "   - Limite de crédito maior.\n\n" +
                "3. Premium:\n" +
                "   - Taxa de manutenção mensal com desconto.\n" +
                "   - Saques ilimitados em qualquer caixa eletrônico do Brasil.\n" +
                "   - Extrato mensal apenas online.\n" +
                "   - Limite de crédito mais alto.\n\n" +
                "4. Digital:\n" +
                "   - Taxa de manutenção mensal com desconto.\n" +
                "   - Saques ilimitados no caixa eletrônico do próprio banco.\n" +
                "   - Extrato mensal apenas online.\n" +
                "   - Limite de crédito mais baixo.\n" +
                "   - Transferências limitadas, com custo adicional após um certo número de transações.",
                "Escolha de Pacote",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesPacote,
                opcoesPacote[0]
        );

        if (pacoteEscolhido != null) {
            switch (pacoteEscolhido) {
                case "Básico":
                    limiteCredito = 1000.0;
                    taxaManutencao = 0.0;
                    break;
                case "Plus":
                    limiteCredito = 2000.0;
                    taxaManutencao = 5.0;
                    break;
                case "Premium":
                    limiteCredito = 5000.0;
                    taxaManutencao = 10.0;
                    break;
                case "Digital":
                    limiteCredito = 500.0;
                    taxaManutencao = 2.0;
                    break;
                default:
                    // Caso não seja escolhido nenhum pacote específico, pode ter uma lógica padrão ou lançar uma exceção, por exemplo.
                    break;
            }
        }
    }

}
  