package org.serratec.academia.servicos.menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.serratec.academia.especial.GerarRelatorios;
import org.serratec.academia.especial.Modalidade;
import org.serratec.academia.especial.Plano;
import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Banco;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuRelatorio implements Menu, GerarRelatorios {
	Scanner sc = new Scanner(System.in);
	Path path = Paths.get(".\\src\\dados_academia.csv");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void exibirMenu(Pessoa pessoa) {
		int opcao = 0;

		do {
			System.out.println("# ===== # Menu de Relatorios # ===== #");
			System.out.println("1. Relatório de Planos");
			System.out.println("2. Relatório de Pessoas (alunos, funcionários e personal trainers)");
			System.out.println("3. Relatório de Avaliações Físicas por Periodo");
			System.out.println("4. Gerar arquivo dos Relatórios");
			System.out.println("5. Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = sc.nextInt();
			sc.nextLine();
			switch (opcao) {
			case 1:
				relatorioPlanos();
				break;
			case 2:
				relatorioPessoas();
				break;
			case 3:
				relatorioAvaliacao();
				break;
			case 4:
				arquivosRelatorios();
				break;
			case 5:
				System.out.println("Voltando ao menu principal...");
				break;
			default:
				System.out.println("Opção inválida. Digite uma opção válida.");
			}

		} while (opcao != 5);

	}
	
	@Override
	public void relatorioPlanos() {
		System.out.println("\n# ===== # Relatório de Planos # ===== #");
		System.out.printf("%-5s %-15s %-40s %-10s%n", "ID:", "Periodicidade:", "Modalidades:", "Valor:");
		System.out.println("# ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== #");

		DecimalFormat df = new DecimalFormat("0.00");

		for (Plano plano : Banco.planos) {
			String modalidadesStr = plano.getModalidades().stream().map(Enum::name).collect(Collectors.joining(", "));

			System.out.printf("%-5d %-15s %-40s R$%-10s%n", plano.getId(), plano.getPeriodo().name(), modalidadesStr,
					df.format(plano.getValor()));
		}
		System.out.println("# ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== #");
	}

	public void relatorioPessoas() {
		System.out.println("\n# ===== # Relatório de Alunos # ===== #");
		System.out.printf("%-30s %-15s %-15s %-15s %-30s%n", "Nome:", "CPF:", "Matrícula:", "Plano ID:", "Personal:");
		for (Aluno aluno : Banco.alunos) {
			System.out.printf("%-30s %-15s %-15s %-15d %-30s%n", aluno.getNome(), aluno.getCpf(),
					aluno.getDataMatricula().format(DATE_FORMATTER), aluno.getIdPlano(), aluno.getPersonalContratado());
		}

		System.out.println("\n# ===== # Relatório de Funcionários # ===== #");
		System.out.printf("%-30s %-15s %-15s%n", "Nome:", "CPF:", "Cargo:");
		for (Funcionario funcionario : Banco.funcionarios) {
			System.out.printf("%-30s %-15s %-15s%n", funcionario.getNome(), funcionario.getCpf(),
					funcionario.getCargo());
		}

		System.out.println("\n# ===== # Relatório de Personal Trainers # ===== #");
		System.out.printf("%-30s %-15s %-20s %-15s%n", "Nome:", "CPF:", "Especialidade:", "CREF:");
		for (Personal personal : Banco.personais) {
			System.out.printf("%-30s %-15s %-20s %-15s%n", personal.getNome(), personal.getCpf(),
					personal.getEspecialidade(), personal.getCref());
		}
	}

	public void relatorioAvaliacao() {
		System.out.print("Digite a data de início (formato: AAAA-MM-DD) ou pressione Enter para hoje: ");
		String entrada = sc.nextLine().trim();
		LocalDate dataInicial;

		if (entrada.isEmpty()) {
			dataInicial = LocalDate.now();
		} else {
			try {
				dataInicial = LocalDate.parse(entrada);
			} catch (Exception e) {
				System.out.println("Data inválida! Usando a data atual.");
				dataInicial = LocalDate.now();
			}
		}

		System.out.print("Digite o período (mensal, trimestral, semestral, anual): ");
		String periodoStr = sc.nextLine().trim().toLowerCase();

		LocalDate dataFim;
		switch (periodoStr) {
		case "mensal":
			dataFim = dataInicial.plusMonths(1);
			break;
		case "trimestral":
			dataFim = dataInicial.plusMonths(3);
			break;
		case "semestral":
			dataFim = dataInicial.plusMonths(6);
			break;
		case "anual":
			dataFim = dataInicial.plusYears(1);
			break;
		default:
			System.out.println("Período inválido!");
			return;
		}

		System.out.println("\n# ===== ===== ===== # RELATÓRIO DE AVALIAÇÕES DE " + dataInicial.format(DATE_FORMATTER)
				+ " ATÉ " + dataFim.format(DATE_FORMATTER) + " # ===== ===== ==== #");

		System.out.printf("%-30s %-15s %-30s %-40s%n", "Aluno", "Data", "Personal", "Descrição");
		LocalDate dataInicio = dataInicial;
		Banco.avaliacoes.stream().filter(a -> a.getData().isAfter(dataInicio) && a.getData().isBefore(dataFim))
				.forEach(a -> System.out.printf("%-30s %-15s %-30s %-40s%n", a.getAluno(),
						a.getData().format(DATE_FORMATTER), a.getPersonal(), a.getDescricao()));
	}
	
	public void arquivosRelatorios() {
	    System.out.println("\n# ===== # Geração de Arquivos de Relatórios # ===== #");
	    System.out.println("1. Planos\n2. Pessoas\n3. Avaliações\n4. Todos\n5. Voltar");
	    System.out.print("Escolha uma opção: ");
	    
	    int opcao = sc.nextInt();
	    sc.nextLine();

	    criarPastaRelatorios();
	    String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

	    try {
	        switch (opcao) {
	            case 1 -> gerarArquivoPlanos(data);
	            case 2 -> gerarArquivoPessoas(data);
	            case 3 -> gerarArquivoAvaliacoes(data);
	            case 4 -> {
	                gerarArquivoPlanos(data);
	                gerarArquivoPessoas(data);
	                gerarArquivoAvaliacoes(data);
	                System.out.println("Todos os relatórios foram gerados com sucesso!");
	            }
	            case 5 -> System.out.println("Voltando...");
	            default -> System.out.println("Opção inválida. Digite uma opção válida.");
	        }
	    } catch (IOException e) {
	        System.out.println("Erro ao gerar relatório: " + e.getMessage());
	    }
	}

	private void criarPastaRelatorios() {
	    File pasta = new File("relatorios");
	    if (!pasta.exists()) pasta.mkdir();
	}

	private void gerarArquivoPlanos(String data) throws IOException {
	    String nomeArquivo = "relatorios/relatorio_planos_" + data + ".txt";
	    try (PrintWriter gravador = new PrintWriter(new FileWriter(nomeArquivo))) {
	        gravador.println("# ===== # RELATÓRIO DE PLANOS # ===== #");
	        gravador.println("Data de geração: " + data + "\n");

	        for (Plano plano : Banco.planos) {
	            gravador.printf("ID: %d%nPeriodicidade: %s%n", plano.getId(), plano.getPeriodo());

	            String modalidades = plano.getModalidades().stream()
	                .map(Modalidade::toString)
	                .collect(Collectors.joining(", "));
	            gravador.println("Modalidades: " + modalidades);
	            gravador.printf("Valor: R$ %.2f%n", plano.getValor());
	        }
	        System.out.println("Relatório de planos gerado com sucesso: " + nomeArquivo);
	    }
	}

	private void gerarArquivoPessoas(String data) throws IOException {
	    String nomeArquivo = "relatorios/relatorio_pessoas_" + data + ".txt";
	    try (PrintWriter gravador = new PrintWriter(new FileWriter(nomeArquivo))) {
	        gravador.println("# ===== # RELATÓRIO DE PESSOAS # ===== #");
	        gravador.println("Data de geração: " + data + "\n");

	        escreverPessoas("ALUNOS", Banco.alunos, gravador);
	        escreverPessoas("FUNCIONÁRIOS", Banco.funcionarios, gravador);
	        escreverPessoas("PERSONAIS", Banco.personais, gravador);

	        System.out.println("Relatório de pessoas gerado com sucesso: " + nomeArquivo);
	    }
	}

	private void escreverPessoas(String titulo, List<? extends Pessoa> pessoas, PrintWriter gravador) {
	    gravador.printf("\n--- %s ---%n", titulo);
	    for (Pessoa p : pessoas) {
	        gravador.printf("Nome: %s%nCPF: %s%n", p.getNome(), p.getCpf());

	        if (p instanceof Aluno a) {
	            gravador.printf("Data de Matrícula: %s%n", a.getDataMatricula().format(DATE_FORMATTER));
	            gravador.printf("ID do Plano: %d%nPersonal: %s%n", a.getIdPlano(), a.getPersonalContratado());
	        } else if (p instanceof Funcionario f) {
	            gravador.printf("Cargo: %s%n", f.getCargo());
	        } else if (p instanceof Personal per) {
	            gravador.printf("Especialidade: %s%nCREF: %s%n", per.getEspecialidade(), per.getCref());
	        }

	        gravador.println("# ===== ===== ===== ===== ===== ===== ===== #");
	    }
	}

	private void gerarArquivoAvaliacoes(String data) throws IOException {
	    LocalDate dataInicial = obterDataInicial();
	    LocalDate dataFim = obterDataFinal(dataInicial);
	    if (dataFim == null) return;

	    String nomeArquivo = "relatorios/relatorio_avaliacoes_" + data + ".txt";
	    try (PrintWriter gravador = new PrintWriter(new FileWriter(nomeArquivo))) {
	        gravador.println(" # ===== # RELATÓRIO DE AVALIAÇÕES # ===== #");
	        gravador.println("Data de geração: " + data);
	        gravador.printf("Período: de %s até %s%n%n",
	                dataInicial.format(DATE_FORMATTER),
	                dataFim.format(DATE_FORMATTER));

	        boolean encontrou = false;
	        for (var av : Banco.avaliacoes) {
	            if (!av.getData().isBefore(dataInicial) && av.getData().isBefore(dataFim)) {
	                gravador.printf("Aluno: %s%nData: %s%nPersonal: %s%nDescrição: %s%n",
	                        av.getAluno(),
	                        av.getData().format(DATE_FORMATTER),
	                        av.getPersonal(),
	                        av.getDescricao());
	                gravador.println("# ===== ===== ===== ===== ===== ===== ===== #");
	                encontrou = true;
	            }
	        }

	        if (!encontrou) {
	            gravador.println("Nenhuma avaliação encontrada no período especificado.");
	        }

	        System.out.println("Relatório de avaliações gerado com sucesso: " + nomeArquivo);
	    }
	}

	private LocalDate obterDataInicial() {
	    System.out.print("Digite a data de início (AAAA-MM-DD) ou Enter para hoje: ");
	    String entrada = sc.nextLine().trim();
	    try {
	        return entrada.isEmpty() ? LocalDate.now() : LocalDate.parse(entrada);
	    } catch (Exception e) {
	        System.out.println("Data inválida! Usando a data atual.");
	        return LocalDate.now();
	    }
	}

	private LocalDate obterDataFinal(LocalDate dataInicial) {
	    System.out.print("Digite o período ( Mensal, Trimestral, Semestral ou Anual ): ");
	    return switch (sc.nextLine().trim().toLowerCase()) {
	        case "mensal" -> dataInicial.plusMonths(1);
	        case "trimestral" -> dataInicial.plusMonths(3);
	        case "semestral" -> dataInicial.plusMonths(6);
	        case "anual" -> dataInicial.plusYears(1);
	        default -> {
	            System.out.println("Período inválido!");
	            yield null;
	        }
	    };
	}

}