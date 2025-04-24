package org.serratec.academia.servicos.menu;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	Path path = Paths.get(".\\src\\dados_academia.csv");
	Pessoa p1 = null;

	@Override
	public void exibirMenu(Pessoa pessoa) {
		Funcionario funcionario = (Funcionario) pessoa;
		p1 = pessoa;

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

	private void emitirRelatorio() {
		MenuRelatorio menuRelatorio = new MenuRelatorio();
		menuRelatorio.exibirMenu(p1);
		
	}
	
	private void gerarValorTotal() {
		double valorTotal = 0;
		DecimalFormat deci = new DecimalFormat("0.00");

		for (Aluno aluno : Banco.alunos) {
	        int idPlano = aluno.getIdPlano();
	        
	        for (Plano plano : Banco.planos) {
	            if (plano.getId() == idPlano) {
	                valorTotal += plano.getValor();
	                break;
	            }
	        }
	    }
		
		System.out.println("O Total para se receber nesse mês é: R$" + deci.format(valorTotal));
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

		System.out.println("\nPersonais disponíveis:");
		System.out.println("# ===== ===== ===== ===== ===== ===== #");
		System.out.printf("%-30s %-30s%n", "Nome:", "Especialidade:");

		for (Personal p : Banco.personais) {
			System.out.printf("%-30s %-30s%n", p.getNome(), p.getEspecialidade());
		}

		String personalEncontrado = null;

		do {
			System.out.print("\nInforme o nome do Personal desejado: ");
			String nomePersonal = sc.nextLine();

			for (Personal p : Banco.personais) {
				if (p.getNome().equalsIgnoreCase(nomePersonal)) {
					personalEncontrado = p.getNome();
					break;
				}
			}

			if (personalEncontrado == null) {
				System.out.println("Personal não encontrado. Tente novamente.");
			}
		} while (personalEncontrado == null);

		System.out.println("\nEscolha o ID do plano desejado:");
		System.out.println("# ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== #");
		System.out.printf("%-5s %-15s %-40s %-10s%n", "ID:", "Periodicidade:", "Modalidades:", "Valor:");

		for (Plano plano : Banco.planos) {
			String modalidadesStr = plano.getModalidades().stream().map(Enum::name).collect(Collectors.joining(", "));

			System.out.printf("%-5d %-15s %-40s R$%-10.2f%n", plano.getId(), plano.getPeriodo().name(), modalidadesStr,
					plano.getValor());
		}

		int idPlanoEscolhido = sc.nextInt();
		sc.nextLine();

		Banco.alunos.add(new Aluno(nome, cpf, senha, dataMatricula, idPlanoEscolhido, personalEncontrado));
		System.out.println("Aluno " + nome + " cadastrado(a) com sucesso!");

		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

			String dataFormatada = dataMatricula.toString();
			writer.write("aluno," + nome + "," + cpf + "," + senha + "," + dataFormatada + "," + idPlanoEscolhido + ","
					+ personalEncontrado + "\n");
			System.out.println("Aluno adicionado ao arquivo CSV com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro ao adicionar aluno ao arquivo CSV: " + e.getMessage());
			e.printStackTrace();
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

        Banco.personais.add(new Personal(nome, cpf, senha, espec, cref));
        System.out.println("Personal " + nome + " cadastrado(a) com sucesso!");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

            writer.write("personal," + nome + "," + cpf + "," + senha + "," + espec + "," + cref + "\n");
            System.out.println("Personal adicionado ao arquivo CSV com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao adicionar personal ao arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

	private void cadastrarPlano() {
		System.out.println("Escolha a periodicidade do Plano:");
		System.out.println("1. Mensal\n2. Trimestral\n3. Semestral\n4. Anual");
		int opcaoPeriodo = sc.nextInt();
		Periodo periodo = null;

		do {
			switch (opcaoPeriodo) {
			case 1:
				periodo = Periodo.MENSAL;
				break;
			case 2:
				periodo = Periodo.TRIMESTRAL;
				break;
			case 3:
				periodo = Periodo.SEMESTRAL;
				break;
			case 4:
				periodo = Periodo.ANUAL;
				break;
			default:
				System.out.println("Opção inválida!");
			}
		} while (opcaoPeriodo < 1 || opcaoPeriodo > 4);

		List<Modalidade> modalidades = new ArrayList<>();
		String resposta = "S";

		do {
			System.out.println("Escolha uma Modalidade do plano (Max: 4)");
			System.out.println("1. Musculação\n2. Funcional\n3. Pilates\n4. Jiu-Jitsu\n5. Boxe");

			int opcaoModalidade = sc.nextInt();
			sc.nextLine();

			Modalidade modalidade = null;

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
				continue;
			}

			if (modalidades.contains(modalidade)) {
				System.out.println("Modalidade já selecionada. Escolha outra.");
				continue;
			}

			modalidades.add(modalidade);

			if (modalidades.size() < 4) {
				System.out.print("Deseja adicionar mais uma modalidade? (S/N): ");
				resposta = sc.nextLine().toUpperCase();
			} else {
				System.out.println("Máximo de 4 modalidades atingido.");
				break;
			}

		} while (!resposta.equals("N"));
		System.out.print("Informe o valor do plano: ");
		double valor = sc.nextDouble();

		int id = Banco.planos.stream().mapToInt(Plano::getId).max().orElse(0) + 1;

		Plano plano = new Plano(id, periodo, modalidades, valor);
		Banco.planos.add(plano);
		System.out.println("Plano cadastrado com sucesso!");

		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

			String modalidadesStr = modalidades.stream().map(m -> m.name().toLowerCase())
					.collect(Collectors.joining(";"));

			writer.write("plano," + id + "," + periodo.name().toLowerCase() + "," + modalidadesStr + "," + valor + "\n");
			System.out.println("Plano adicionado ao arquivo CSV com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro ao adicionar plano ao arquivo CSV: " + e.getMessage());
			e.printStackTrace();
		}
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

	    boolean cpfExistente = Stream.of(Banco.alunos, Banco.funcionarios, Banco.personais)
	        .flatMap(Collection::stream)
	        .anyMatch(pessoa -> pessoa.getCpf().equals(cpf));

	    if (cpfExistente) {
	        System.out.println("CPF já cadastrado no sistema!");
	        return false;
	    }

	    return true;
	}
}
