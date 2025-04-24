package org.serratec.academia.servicos.menu;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.especial.Modalidade;
import org.serratec.academia.especial.Periodo;
import org.serratec.academia.especial.Plano;
import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Banco;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuFuncionario implements Menu {
	Scanner sc = new Scanner(System.in);
	private static List<Pessoa> pessoas = new ArrayList<>();

	@Override
	public void exibirMenu(Pessoa pessoa) {
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

		for (Aluno aluno : Banco.alunos) {
	        int idPlano = aluno.getidPlano();

	        for (Plano plano : Banco.planos) {
	            if (plano.getId() == idPlano) {
	                valorTotal += plano.getValor();
	                break; // plano encontrado, pode sair do loop
	            }
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
		System.out.print("Informe a CREF do Personal: ");
		String cref = sc.nextLine();
		Banco.personais.add(new Personal(nome, cpf, senha, espec, cref));
		System.out.println("Personal " + nome + " cadastrado(a) com sucesso!");
	}

	private void cadastrarPlano() {
		System.out.println("Escolha a periodicidade do Plano:");
		System.out.println("1. Mensal\n2. Trimestral\n3. Semestral\n4. Anual");                
		int opcaoPeriodo = sc.nextInt();
		Periodo periodo = null;

		do {
			switch (opcaoPeriodo) {
			case 1: periodo = Periodo.MENSAL; break;
			case 2: periodo = Periodo.TRIMESTRAL; break;
			case 3: periodo = Periodo.SEMESTRAL; break;
			case 4: periodo = Periodo.ANUAL; break;
			default: 
				System.out.println("Opção inválida!");
			} 
		} while (opcaoPeriodo < 1 || opcaoPeriodo > 4);

		System.out.print("Escolha uma Modalidade do plano: ");
		System.out.println("1. Musculação\n2. Funcional\n3. Pilates\n4. Jiu-Jitsu\n5. Boxe");
		int opcaoModalidade = sc.nextInt();
		List<Modalidade> modalidades = new ArrayList<>();
		String resposta;
		do {
			Modalidade modalidade = null;
			do {
				switch (opcaoModalidade) {
				case 1:
					modalidade = Modalidade.MUSCULACAO;
					break;
				case 2:
					modalidade = Modalidade.FUNCIONAL;
					break;
				case 3:
					modalidade = Modalidade.PILATES;
					break;
				case 4:
					modalidade = Modalidade.JIUJITSU;
					break;
				case 5:
					modalidade = Modalidade.BOXE;
					break;
				default:
					System.out.println("Opção inválida!");
				}
		        if (modalidade != null) {
		            modalidades.add(modalidade);
		        }
			} while (opcaoModalidade < 1 || opcaoModalidade > 5);
			System.out.print("Deseja adicionar mais uma modalidade? (S/N): ");
			sc.nextLine();
			resposta = sc.nextLine().toUpperCase();
		} while (resposta != "S");
		
		System.out.print("Informe o valor do plano: ");
		double valor = sc.nextDouble();
		Plano plano = new Plano(periodo, modalidades, valor);
		Banco.planos.add(plano);
		System.out.println("Plano cadastrado com sucesso! Id do plano: " + plano.getId());
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

		String personalEncontrado = null;

		do {
			System.out.print("Informe o nome do Personal desejado: ");
			String nomePersonal = sc.nextLine();

			for (Pessoa p : pessoas) {
				if (p instanceof Personal && p.getNome().equalsIgnoreCase(nomePersonal)) {
					personalEncontrado = p.getNome();
					break;
				}
			}

			if (personalEncontrado == null) {
				System.out.println("Personal não encontrado. Tente novamente.");
			}
		} while (personalEncontrado == null);
		
		System.out.println("Escolha o ID do plano desejado:");
		for (Plano plano : Banco.planos) {
		    System.out.println("ID: " + plano.getId() + " - Modalidades: " + plano.getModalidades() + " - Valor: R$" + plano.getValor());
		}
		int idPlanoEscolhido = sc.nextInt();
		sc.nextLine();
		
		Banco.alunos.add(new Aluno(nome, cpf, senha, dataMatricula, idPlanoEscolhido, personalEncontrado));
		System.out.println("Aluno " + nome + " cadastrado(a) com sucesso!");
	}

	private void emitirRelatorio() {
		System.err.println("TBD");
	}

	private static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
			System.out.println("CPF inválido! Um CPF não pode ser nulo ou vazio.");
			return false;
		}
        
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
