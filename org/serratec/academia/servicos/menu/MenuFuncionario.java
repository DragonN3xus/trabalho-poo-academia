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

public class MenuFuncionario implements Menu {
	Scanner sc = new Scanner(System.in);
	private static List<Pessoa> pessoas = new ArrayList<>();

	@Override
	public void exibirMenu(Pessoa pessoa) {
		pessoas.add(pessoa);
		Funcionario funcionario = (Funcionario) pessoa;

		int opcao;
		do {
			System.out.println("\n# ===== # Menu de Funcionarios # ===== #");
			System.out.println("1. Emitir Relatorios\n2. Ver Valor total a receber no Mês");
			if (funcionario.getCargo() == Cargo.GERENTE) {
				System.out.println(
						"3. Cadastrar novo Aluno" + "\n4. Cadastrar novo Plano" + "\n5. Cadastrar novo Personal");
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
				if (funcionario.getCargo() == Cargo.GERENTE) {
					cadastrarAluno();
				} else {
					System.out.println("Acesso negado. Apenas gerentes podem acessar esta função.");
				}
				break;
			case 4:
				if (funcionario.getCargo() == Cargo.GERENTE) {
					cadastrarPlano();
				} else {
					System.out.println("Acesso negado. Apenas gerentes podem acessar esta função.");
				}
				break;
			case 5:
				if (funcionario.getCargo() == Cargo.GERENTE) {
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

		for (Pessoa p : pessoas) {
			if (p instanceof Aluno) {
				Aluno aluno = (Aluno) p;
				valorTotal += aluno.getPlano().getValor();
			}
		}
		System.out.println("O Total para se receber nesse mês é: R$" + deci.format(valorTotal));
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
	    
	    String cref;
	    boolean crefValido;
	    
	    do {
	        System.out.print("Informe a CREF do Personal (6 dígitos + G ou P + UF): ");
	        cref = sc.nextLine();
	        crefValido = cref.matches("\\d{6}[GP][A-Z]{2}");
	        
	        if (!crefValido) {
	            System.out.println("Por favor, informe um CREF válido no formato: 6 dígitos + G ou P + UF");
	        }
	    } while (!crefValido);
	    
	    pessoas.add(new Personal(nome, cpf, senha, espec, cref));
	    System.out.println("Personal " + nome + " cadastrado(a) com sucesso!");
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

		List<Personal> personaisDisponiveis = new ArrayList<>();
		System.out.println("\nPersonais disponíveis:");
		System.out.println("0. Nenhum personal");

		int index = 1;
		for (Pessoa p : pessoas) {
			if (p instanceof Personal) {
				Personal personal = (Personal) p;
				personaisDisponiveis.add(personal);
				System.out.println(
						index + ". " + personal.getNome() + " - Especialidade: " + personal.getEspecialidade());
				index++;
			}
		}

		Personal personalEscolhido = null;
		int escolha;

		do {
			System.out.print("\nEscolha um personal pelo número (0 para nenhum): ");
			escolha = sc.nextInt();
			sc.nextLine();

			if (escolha == 0) {

				break;
			} else if (escolha > 0 && escolha <= personaisDisponiveis.size()) {
				personalEscolhido = personaisDisponiveis.get(escolha - 1);
				break;
			} else {
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (true);

		pessoas.add(new Aluno(nome, cpf, senha, dataMatricula, Plano.ANUAL_TOTAL, personalEscolhido));

		if (personalEscolhido != null) {
			System.out.println(
					"Aluno " + nome + " cadastrado(a) com sucesso com o personal " + personalEscolhido.getNome() + "!");
		} else {
			System.out.println("Aluno " + nome + " cadastrado(a) com sucesso sem personal!");
		}
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
