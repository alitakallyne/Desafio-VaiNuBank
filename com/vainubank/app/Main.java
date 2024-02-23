package com.vainubank.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.vainubank.conta.Conta;
import com.vainubank.conta.ContaCorrente;
import com.vainubank.exception.ValorInvalidoException;
import com.vainubank.pessoa.*;
import com.vainubank.servico.ContaService;

public class Main {

	public static void main(String[] args) throws ValorInvalidoException {
		Map<String, Pessoa> mapaPessoas = new HashMap<>();
		List<Conta> listaContas = new ArrayList<>();
		ContaService contaService = new ContaService();
		
		exibirSaudacao();
		
	      while (true) {
	            String opcao = JOptionPane.showInputDialog(
	                    "Selecione uma opção:\n" +
	                    "1 - Cadastrar Conta\n" +
	                    "2 - Excluir Conta\n" +
	                    "3 - Editar Conta\n" +
	                    "4 - Realizar Saque\n" +
	                    "5 - Realizar Depósito\n" +
	                    "6 - Realizar Transferência\n" +
	                    "7 - Visualizar Todas as Contas\n" +
	                    "8 - Visualizar Contas por Titular\n" +
	                    "9 - Buscar Conta por Número\n" +
	                    "0 - Sair"
	            );

	            switch (opcao) {
	                case "1":
	                	 cadastrarConta(contaService, listaContas, mapaPessoas);
	                  
	                    break;
	                case "2":
	                	contaService.inativarConta(listaContas, mapaPessoas);
	                   
	                    break;
	                case "3":
	                	contaService.editarDadosPessoa(mapaPessoas);
	                    
	                    break;
	                case "4": 
	                	contaService.sacarDeConta(listaContas);
	                    
	                    break;
	                case "5":
	                	contaService.depositarEmConta(listaContas);
	                   
	                    break;
	                case "6":
	                	contaService.realizarTransferencia(listaContas, mapaPessoas);
	                    
	                    break;
	                case "7":
	                	  contaService.visualizarTodasContas(listaContas, mapaPessoas);
	                    
	                    break;
	                case "8":
	                     contaService.visualizarTodasContasPorTitular(listaContas, mapaPessoas);
	                    break;
	                case "9":
	                	contaService.buscarContaPorNumero(listaContas, mapaPessoas);
	                    
	                    break;
	                case "0":
	                    JOptionPane.showMessageDialog(null, "Saindo do programa. Até mais!");
	                    System.exit(0);
	                default:
	                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
	            }
	        }
		
	}
	
	public static void exibirSaudacao() {
        String mensagemSaudacao = "Bem-vindo ao Vai Nu Bank!\n" +
                "O banco onde sua vida financeira decola!\n" +
                "----------------------------------------";
        JOptionPane.showMessageDialog(null, mensagemSaudacao);
    }
	private static void cadastrarConta(ContaService contaService, List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
	    String cpfTitular = JOptionPane.showInputDialog("Informe o CPF do titular:");
	
	    contaService.cadastrarConta(listaContas, mapaPessoas, cpfTitular);
	}

}
