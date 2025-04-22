package org.serratec.academia.servicos.menu;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.especial.Plano;
import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuFuncionario extends Menu {
	Scanner sc = new Scanner(System.in);
	private static List<Pessoa> pessoas = new ArrayList<>();

	@Override
	public void exibirMenu(Pessoa userLogado) {
		pessoas.add(userLogado);
		Funcionario funcionario = (Funcionario) userLogado;
		
		int opcao;
		do {
			System.out.println("\n# ===== # Menu de Funcionarios # ===== #");
			System.out.println("1. Emitir Relatorios\n2. Ver Valor total a receber no Mês");
			if(funcionario.getCargo() == Cargo.GERENTE) {
				System.out.println("3. Cadastrar novo Aluno"
						+ "\n4. Cadastrar novo Plano"
						+ "\n5. Cadastrar novo Personal");
			}
			System.out.println("0. Sair");
			System.out.print("Escolha uma opção: ");
			opcao = sc.nextInt();
			switch (opcao) {
			case 1: 
				emitirRelatorio();
				break;
			case 2: 
				gerarValorTotal();
				break;
			case 3: 
				if(funcionario.getCargo() == Cargo.GERENTE) {
					cadastrarAluno();
				} else {
					System.out.println("Acesso negado. Apenas gerentes podem acessar esta função.");
				}
				break;
			case 4: 
				if(funcionario.getCargo() == Cargo.GERENTE) {
					cadastrarPlano();
				} else {
					System.out.println("Acesso negado. Apenas gerentes podem acessar esta função.");
				}
				break;
			case 5: 
				if(funcionario.getCargo() == Cargo.GERENTE) {
					cadastrarPersonal();
				} else {
					System.out.println("Acesso negado. Apenas gerentes podem acessar esta função.");
				}
				break;
			case 0: 
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida! Tente novamente.");
			}
		} while (opcao != 0);
	}

	private void gerarValorTotal() {
		double valorTotal = 0;
        DecimalFormat deci = new DecimalFormat("0.00");
        
		for (Pessoa pessoa : pessoas) {
			if (pessoa instanceof Aluno) {
				Aluno aluno = (Aluno) pessoa;
				valorTotal += aluno.getPlano().getValor();
			}
		System.out.println("O Total para se receber nesse mês é: R$" + deci.format(valorTotal));
		}
	}
	
	private void cadastrarPersonal() {
		System.out.print("Informe o nome do Personal: ");		    
		sc.nextLine();
		String nome = sc.nextLine();

		String cpf;
		boolean cpfValido;

		do {
			System.out.print("Informe o CPF (Apenas Números): ");
			cpf = sc.nextLine();
			cpf = cpf.replaceAll("[^0-9]", "");
			cpfValido = validarCPF(cpf);

			if (!cpfValido) {
				System.out.println("Por favor, informe um CPF válido.");
			}
		} while (!cpfValido);
		
		System.out.print("Informe a senha do Personal: ");
		String senha = sc.nextLine();
		System.out.print("Informe a especialidade do Personal: ");
		String espec = sc.nextLine();
		System.out.print("Informe a CREF do Personal: ");
		String cref = sc.nextLine();
		pessoas.add(new Personal(nome, cpf, senha, espec, cref));
		System.out.println("Participante " + nome + " cadastrado(a) com sucesso!");

	}

	private void cadastrarPlano() {
		System.err.println("TBD");

	}

	private void cadastrarAluno() {
		System.out.print("Informe o nome do Aluno: ");
		sc.nextLine();
		String nome = sc.nextLine();

		String cpf;
		boolean cpfValido;
		LocalDate dataMatricula = LocalDate.now();

		do {
			System.out.print("Informe o CPF (Apenas Números): ");
			cpf = sc.nextLine();
			cpf = cpf.replaceAll("[^0-9]", "");
			cpfValido = validarCPF(cpf);

			if (!cpfValido) {
				System.out.println("Por favor, informe um CPF válido.");
			}
		} while (!cpfValido);
		
		System.out.print("Informe a senha do Aluno: ");
		String senha = sc.nextLine();
		
		Personal personalEncontrado = null;

		do {
		    System.out.print("Informe o nome do Personal desejado: ");
		    String nomePersonal = sc.nextLine();

		    for (Pessoa p : pessoas) {
		        if (p instanceof Personal && p.getNome().equalsIgnoreCase(nomePersonal)) {
		            personalEncontrado = (Personal) p;
		            break;
		        }
		    }

		    if (personalEncontrado == null) {
		        System.out.println("Personal não encontrado. Tente novamente.");
		    }
		} while (personalEncontrado == null);
		pessoas.add(new Aluno(nome, cpf, senha, dataMatricula, Plano.ANUAL_TOTAL, personalEncontrado));

	}

	private void emitirRelatorio() {
		System.err.println("TBD");

	}


	private static boolean validarCPF(String cpf) {
		if (cpf.length() != 11) {
			System.out.println("CPF inválido! Um CPF deve conter 11 dígitos.");
			return false;
		}

		for (Pessoa pessoa : pessoas) {
			if (pessoa.getCpf().equals(cpf)) {
				System.out.println("CPF já cadastrado no sistema!");
				return false;
			}
		}
		return true;
	}
}