package com.vainubank.servico;

import com.vainubank.pessoa.Pessoa;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import com.vainubank.conta.Conta;
import com.vainubank.conta.ContaCorrente;
import com.vainubank.conta.ContaPoupanca;
import com.vainubank.exception.SaldoInsuficienteException;
import com.vainubank.exception.ValorInvalidoException;

public class ContaService {
	
	 public void cadastrarConta(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas, String cpfTitular) {
	       
		 try {
	            Pessoa titular = mapaPessoas.get(cpfTitular);
	            if (titular == null) {
	                cadastrarNovaPessoa(mapaPessoas, cpfTitular);
	                titular = mapaPessoas.get(cpfTitular);
	            }

	            String tipoConta = JOptionPane.showInputDialog("Informe o tipo da conta (Corrente ou Poupança):");
	            int numeroContaValido = obterNumeroInteiroPositivoValido("Informe o número da conta:");
	    	    String agenciaConta = JOptionPane.showInputDialog("Informe a agência da conta:");
	    	    double saldoInicial = obterValorDoubleValido("Informe o saldo inicial da conta:");
	           
	    	    
	            String chavePix = criarChavePix();
	            
	            System.out.println("teste: "+chavePix);

	            Conta novaConta;
	            if ("Corrente".equalsIgnoreCase(tipoConta)) {
	                novaConta = new ContaCorrente(numeroContaValido, agenciaConta, titular, saldoInicial,chavePix);
	            } else if ("Poupança".equalsIgnoreCase(tipoConta)) {
	            	 String diaAniversario = JOptionPane.showInputDialog("Informe o dia do aniversário:");
	                novaConta = new ContaPoupanca(numeroContaValido, agenciaConta, titular, saldoInicial,chavePix,Integer.parseInt(diaAniversario));
	            } else {
	                JOptionPane.showMessageDialog(null, "Tipo de conta inválido.");
	                return;
	            }

	            listaContas.add(novaConta);
	            JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!");
	        } catch (ValorInvalidoException e) {
	            JOptionPane.showMessageDialog(null, e.getMessage());
	        }
	    }

	
	 private void cadastrarNovaPessoa(Map<String, Pessoa> mapaPessoas, String cpfTitular) {
	        String nomeTitular = JOptionPane.showInputDialog("Informe o nome do titular:");
	        String enderecoTitular = JOptionPane.showInputDialog("Informe o endereço do titular:");
	        String telefoneTitular = JOptionPane.showInputDialog("Informe o telefone do titular:");
	        String emailTitular = JOptionPane.showInputDialog("Informe o email do titular:");

	        Pessoa novaPessoa = new Pessoa(nomeTitular, cpfTitular, enderecoTitular, telefoneTitular, emailTitular);
	        mapaPessoas.put(cpfTitular, novaPessoa);
	 }
	 private double obterValorDoubleValido(String mensagem) throws ValorInvalidoException {
	        while (true) {
	            try {
	                return Double.parseDouble(JOptionPane.showInputDialog(mensagem));
	            } catch (NumberFormatException e) {
	                throw new ValorInvalidoException("Valor inválido. Certifique-se de fornecer um número válido.");
	            }
	        }
	    }
	 private int obterNumeroInteiroPositivoValido(String mensagem) throws ValorInvalidoException {
	        while (true) {
	            try {
	                int numero = Integer.parseInt(JOptionPane.showInputDialog(mensagem));
	                if (numero > 0) {
	                    return numero;
	                } else {
	                    throw new ValorInvalidoException("Número deve ser um inteiro positivo.");
	                }
	            } catch (NumberFormatException e) {
	                throw new ValorInvalidoException("Valor inválido. Certifique-se de fornecer um número válido.");
	            }
	        }
	    }
	 
	 
	    private String criarChavePix() {
	        String[] opcoesChave = {"CPF", "Aleatória"};

	        int escolha = JOptionPane.showOptionDialog(
	                null,
	                "Escolha o tipo de chave PIX:",
	                "Criar Chave PIX",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opcoesChave,
	                opcoesChave[0]
	        );

	        switch (escolha) {
	            case 0:
	               
	                String cpfChave = JOptionPane.showInputDialog("Digite o CPF para a chave PIX:");
	                return "CPF:" + cpfChave; 

	            case 1:
	               
	                return gerarChavePixAleatoria();

	            default:
	               
	                return "SemChavePIX";
	        }
	    }
	    
	
	    private String gerarChavePixAleatoria() {
	        int comprimentoChave = 10;
	        
	        StringBuilder chavePix = new StringBuilder();

	      
	        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	        Random random = new Random();

	        for (int i = 0; i < comprimentoChave; i++) {
	            int indice = random.nextInt(caracteresPermitidos.length());
	            char caractere = caracteresPermitidos.charAt(indice);
	            chavePix.append(caractere);
	        }

	        return "Aleatoria:" + chavePix.toString();
	    }


	    		
		private String obterChavePIX(String mensagem) {
		    String[] opcoes = {"Aleatória", "CPF"};
		    int escolha = JOptionPane.showOptionDialog(null, mensagem, "Escolha o tipo de chave PIX",
		            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

		    if (escolha == 0) {
		        
		        return gerarChavePixAleatoria();
		    } else if (escolha == 1) {
		       
		        return JOptionPane.showInputDialog("Informe o CPF como chave PIX:");
		    } else {
		      
		        return null;
		    }
		}

	 

	 public void visualizarTodasContas(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
	     if (listaContas.isEmpty()) {
	         JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada.");
	         return;
	     }

	     
	     StringBuilder mensagem = new StringBuilder("Contas Cadastradas:\n");
	     for (Conta conta : listaContas) {
	    	 if (conta.isAtivo()) {
	             Pessoa titular = conta.getTitular();
	             
	             mensagem.append(String.format("Titular: %s (%s), Tipo de Conta: %s, Número da Conta: %d, Saldo: %.2f\n",
	                     titular.getNome(), titular.getCpf(), conta.getTipoConta(), conta.getNumero(), conta.getSaldo()));
	         }
	     }

	     JOptionPane.showMessageDialog(null, mensagem.toString());
	 }
	 
	 
	 
	 public void visualizarTodasContasPorTitular(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
		    if (listaContas.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada.");
		        return;
		    }

		    String cpfTitular = JOptionPane.showInputDialog("Informe o CPF do titular:");
		    Pessoa titular = mapaPessoas.get(cpfTitular);

		    if (titular == null) {
		        JOptionPane.showMessageDialog(null, "Titular não encontrado.");
		        return;
		    }

		    List<Conta> contasAtivas = obterContasAtivasPorTitular(listaContas, titular);

		    if (contasAtivas.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Este titular não possui contas ativas.");
		        return;
		    }

		    StringBuilder mensagem = new StringBuilder(String.format("Contas Ativas para %s (%s):\n", titular.getNome(), cpfTitular));
		    for (Conta conta : contasAtivas) {
		        mensagem.append(conta.toString()).append("\n");
		    }

		    JOptionPane.showMessageDialog(null, mensagem.toString());
		}

	 

	 public void inativarConta(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
	     if (listaContas.isEmpty()) {
	         JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada.");
	         return;
	     }

	     String cpfTitular = JOptionPane.showInputDialog("Informe o CPF do titular:");
	     Pessoa titular = mapaPessoas.get(cpfTitular);

	     if (titular == null) {
	         JOptionPane.showMessageDialog(null, "Titular não encontrado.");
	         return;
	     }

	     List<Conta> contasAtivas = obterContasAtivasPorTitular(listaContas, titular);

	     if (contasAtivas.isEmpty()) {
	         JOptionPane.showMessageDialog(null, "Este titular não possui contas ativas.");
	         return;
	     }

	     StringBuilder mensagem = new StringBuilder(String.format("Contas Ativas para %s (%s):\n", titular.getNome(), cpfTitular));
	     for (Conta conta : contasAtivas) {
	         mensagem.append(conta.toString()).append("\n");
	     }

	     int numeroContaInativar = obterNumeroContaParaInativar(mensagem.toString());

	     for (Conta conta : contasAtivas) {
	         if (conta.getNumero() == numeroContaInativar) {
	             conta.setAtivo(false);
	             JOptionPane.showMessageDialog(null, "Conta inativada com sucesso!");
	             return;
	         }
	     }

	     JOptionPane.showMessageDialog(null, "Número de conta inválido. Nenhuma conta inativada.");
	 }

	 private List<Conta> obterContasAtivasPorTitular(List<Conta> listaContas, Pessoa titular) {
	     List<Conta> contasAtivas = new ArrayList<>();
	     for (Conta conta : listaContas) {
	         if (conta.getTitular().equals(titular) && conta.isAtivo()) {
	             contasAtivas.add(conta);
	         }
	     }
	     return contasAtivas;
	 }

	 private int obterNumeroContaParaInativar(String mensagem) {
	     while (true) {
	         try {
	             return Integer.parseInt(JOptionPane.showInputDialog(mensagem + "Informe o número da conta que deseja inativar:"));
	         } catch (NumberFormatException e) {
	             JOptionPane.showMessageDialog(null, "Número de conta inválido. Tente novamente.");
	         }
	     }
	 }

	 
	 public void realizarTransferencia(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
		    String opcaoTransferencia = JOptionPane.showInputDialog(
		            "Selecione o tipo de transferência:\n" +
		            "1 - Transferência por Número de Conta\n" +
		            "2 - Transferência PIX\n" +
		            "0 - Voltar"
		    );

		    switch (opcaoTransferencia) {
		        case "1":
		            transferenciaPorNumeroConta(listaContas, mapaPessoas);
		            break;
		        case "2":
		            transferenciaPIX(listaContas, mapaPessoas);
		            break;
		        case "0":
		            return; 
		        default:
		            JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
		    }
		}

	 private void transferenciaPorNumeroConta(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
		    
		    Conta contaOrigem = obterConta("Informe o número da conta de origem:", listaContas);

		    if (contaOrigem == null || !contaOrigem.isAtivo()) {
		        JOptionPane.showMessageDialog(null, "Conta de origem não encontrada ou inativa.");
		        return;
		    }

		    
		    Conta contaDestino = obterConta("Informe o número da conta de destino:", listaContas);

		    if (contaDestino == null || !contaDestino.isAtivo()) {
		        JOptionPane.showMessageDialog(null, "Conta de destino não encontrada ou inativa.");
		        return;
		    }

		   
		    String valorTransferenciaStr = JOptionPane.showInputDialog("Informe o valor da transferência:");

		    double valorTransferencia = Double.parseDouble(valorTransferenciaStr);

		   
		    try {
		        contaOrigem.sacar(valorTransferencia);
		    } catch (SaldoInsuficienteException e) {
		        JOptionPane.showMessageDialog(null, e.getMessage());
		        return;
		    }

		    
		    contaDestino.depositar(valorTransferencia);

		    // Passo 6: Registrar a transação (se necessário)
		    // registrarTransacao(contaOrigem, contaDestino, valorTransferencia);

		    JOptionPane.showMessageDialog(null, "Transferência por Número de Conta realizada com sucesso!");
		}

	 private void transferenciaPIX(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
		 
		   
		   Conta contaOrigem = obterConta("Informe o número da conta de origem:", listaContas);

		    if (contaOrigem == null || !contaOrigem.isAtivo() || contaOrigem.getChavePix() == null) {
		        JOptionPane.showMessageDialog(null, "Conta de origem não encontrada, inativa ou sem chave PIX cadastrada.");
		        return;
		    }
    
		    		   
		    Conta contaDestino = obterContaPorChavePIX(listaContas);
		    if (contaDestino == null || !contaDestino.isAtivo() || contaDestino.getChavePix()== null) {
		        JOptionPane.showMessageDialog(null, "Conta de destino não encontrada, inativa ou sem chave PIX cadastrada.");
		        return;
		    }

		  
		    String valorTransferenciaStr = JOptionPane.showInputDialog("Informe o valor da transferência:");

		    double valorTransferencia = Double.parseDouble(valorTransferenciaStr);
		    
		    
		    try {
		        contaOrigem.sacar(valorTransferencia);
		    } catch (SaldoInsuficienteException e) {
		        JOptionPane.showMessageDialog(null, e.getMessage());
		        return;
		    }

		    
		    contaDestino.depositar(valorTransferencia);

		    
		 //  registrarTransacao(contaOrigem, contaDestino, valorTransferencia);

		    JOptionPane.showMessageDialog(null, "Transferência PIX realizada com sucesso!");
		}
	 
	 private Conta obterConta(String mensagem, List<Conta> listaContas) {
		    try {
		        int numeroConta = Integer.parseInt(JOptionPane.showInputDialog(mensagem));

		        for (Conta conta : listaContas) {
		            if (conta.getNumero() == numeroConta && conta.isAtivo()) {
		                return conta;
		            }
		        }
		        return null;
		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Número de conta inválido. Tente novamente.");
		        return null;
		    }
		}

	 private Conta obterContaPorChavePIX(List<Conta> listaContas) {
		    String chaveInserida = JOptionPane.showInputDialog("Informe a chave PIX da conta:");

		    for (Conta conta : listaContas) {
		        if (conta.getChavePix() != null && conta.getChavePix().equals(chaveInserida) && conta.isAtivo()) {
		            return conta;
		        }
		    }

		    return null;
		}
	 
	 public void visualizarChavePix(List<Conta> listaContas) {
		 int numeroConta = Integer.parseInt(JOptionPane.showInputDialog("Informe o número da conta para visualizar a Chave PIX:"));
		 for (Conta conta : listaContas) {
	            if (conta.getNumero() == numeroConta && conta.isAtivo() && conta.getChavePix() != null) {
	            	   JOptionPane.showMessageDialog(null, "Chave PIX da conta " + conta.getNumero() + ": " + conta.getChavePix());
	                return;
	            }
	        }
		 
		  JOptionPane.showMessageDialog(null, "Conta não encontrada ou inativa. Nenhuma Chave PIX visualizada.");
	    
	 }
	 
	 
	  public void buscarContaPorNumero(List<Conta> listaContas, Map<String, Pessoa> mapaPessoas) {
	        Conta conta = obterConta("Informe o número da conta para buscar:", listaContas);

	        if (conta != null) {
	            Pessoa titular = mapaPessoas.get(conta.getTitular().getCpf());

	            if (titular != null) {
	                String mensagem = String.format("Informações da Conta:\n" +
	                        "Número da Conta: %d\n" +
	                        "Tipo de Conta: %s\n" +
	                        "Saldo: %.2f\n" +
	                        "Titular: %s\n" +
	                        "CPF do Titular: %s\n" +
	                        "Endereço do Titular: %s\n" +
	                        "Telefone do Titular: %s\n" +
	                        "Email do Titular: %s",
	                        conta.getNumero(), conta.getTipoConta(), conta.getSaldo(),
	                        titular.getNome(), titular.getCpf(), titular.getEndereco(),
	                        titular.getTelefone(), titular.getEmail());

	                JOptionPane.showMessageDialog(null, mensagem);
	            } else {
	                JOptionPane.showMessageDialog(null, "Titular não encontrado.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Conta não encontrada ou inativa.");
	        }
	    }

	  
	  public void editarDadosPessoa(Map<String, Pessoa> mapaPessoas) {
	        String cpfTitular = JOptionPane.showInputDialog("Informe o CPF do titular para editar dados:");

	        Pessoa titular = mapaPessoas.get(cpfTitular);
	        if (titular == null) {
	            JOptionPane.showMessageDialog(null, "Titular não encontrado.");
	            return;
	        }

	        String opcaoEdicao = JOptionPane.showInputDialog(
	                "Escolha o dado que deseja editar:\n" +
	                        "1 - Nome\n" +
	                        "2 - Endereço\n" +
	                        "3 - Telefone\n" +
	                        "4 - Email\n" +
	                        "0 - Voltar"
	        );

	        switch (opcaoEdicao) {
	            case "1":
	                String novoNome = JOptionPane.showInputDialog("Digite o novo nome:");
	                titular.setNome(novoNome);
	                JOptionPane.showMessageDialog(null, "Nome atualizado com sucesso!");
	                break;

	            case "2":
	                String novoEndereco = JOptionPane.showInputDialog("Digite o novo endereço:");
	                titular.setEndereco(novoEndereco);
	                JOptionPane.showMessageDialog(null, "Endereço atualizado com sucesso!");
	                break;

	            case "3":
	                String novoTelefone = JOptionPane.showInputDialog("Digite o novo telefone:");
	                titular.setTelefone(novoTelefone);
	                JOptionPane.showMessageDialog(null, "Telefone atualizado com sucesso!");
	                break;

	            case "4":
	                String novoEmail = JOptionPane.showInputDialog("Digite o novo email:");
	                titular.setEmail(novoEmail);
	                JOptionPane.showMessageDialog(null, "Email atualizado com sucesso!");
	                break;

	            case "0":
	                return;

	            default:
	                JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
	        }
	    }
	  
	  public void depositarEmConta(List<Conta> listaContas) throws ValorInvalidoException {
	        Conta conta = obterConta("Informe o número da conta para depósito:", listaContas);
	        if (conta == null) {
	            JOptionPane.showMessageDialog(null, "Conta não encontrada.");
	            return;
	        }

	        double valorDeposito = obterValorDoubleValido("Informe o valor do depósito:");
	        conta.depositar(valorDeposito);

	        JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!");
	    }
	  
	  public void sacarDeConta(List<Conta> listaContas) throws ValorInvalidoException {
	        Conta conta = obterConta("Informe o número da conta para saque:", listaContas);
	        if (conta == null) {
	            JOptionPane.showMessageDialog(null, "Conta não encontrada.");
	            return;
	        }

	        double valorSaque = obterValorDoubleValido("Informe o valor do saque:");

	        try {
	            conta.sacar(valorSaque);
	            JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!");
	        } catch (SaldoInsuficienteException e) {
	            JOptionPane.showMessageDialog(null, e.getMessage());
	        }
	    }

		
}
