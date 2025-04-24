package org.serratec.academia.servicos.menu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Banco;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuRelatorio implements Menu {
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

    private void relatorioPlanos() {
		// TODO Auto-generated method stub
		
	}

	public void relatorioPessoas() {
        System.out.println("\n# ===== # Relatório de Alunos # ===== #");
        System.out.printf("%-30s %-15s %-15s %-15s %-30s%n", "Nome:", "CPF:", "Matrícula:", "Plano ID:", "Personal:");
        for (Aluno aluno : Banco.alunos) {
            System.out.printf("%-30s %-15s %-15s %-15d %-30s%n", aluno.getNome(), aluno.getCpf(),
                    aluno.getDataMatricula().format(DATE_FORMATTER), aluno.getIdPlano(), aluno.getPersonalContratado());
        }
        System.out.println("# ===== ===== ===== ===== ===== ===== #");

        System.out.println("\n# ===== # Relatório de Funcionários # ===== #");
        System.out.printf("%-30s %-15s %-15s%n", "Nome:", "CPF:", "Cargo:");
        for (Funcionario funcionario : Banco.funcionarios) {
            System.out.printf("%-30s %-15s %-15s%n", funcionario.getNome(), funcionario.getCpf(),
                    funcionario.getCargo());
        }
        System.out.println("# ===== ===== ===== ===== ===== ===== #");

        System.out.println("\n# ===== # Relatório de Personal Trainers # ===== #");
        System.out.printf("%-30s %-15s %-20s %-15s%n", "Nome:", "CPF:", "Especialidade:", "CREF:");
        for (Personal personal : Banco.personais) {
            System.out.printf("%-30s %-15s %-20s %-15s%n", personal.getNome(), personal.getCpf(),
                    personal.getEspecialidade(), personal.getCref());
        }
        System.out.println("# ===== ===== ===== ===== ===== ===== #");
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

        System.out.println("\n# ===== ===== ===== RELATÓRIO DE AVALIAÇÕES DE " + dataInicial.format(DATE_FORMATTER)
                + " ATÉ " + dataFim.format(DATE_FORMATTER) + " # ===== ===== ==== #");

        System.out.printf("%-30s %-15s %-30s %-40s%n", "Aluno", "Data", "Personal", "Descrição");
        System.out.println("# ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== #");
        LocalDate dataInicio = dataInicial;
        Banco.avaliacoes.stream()
            .filter(a -> a.getData().isAfter(dataInicio) && a.getData().isBefore(dataFim))
            .forEach(a -> System.out.printf(
                "%-30s %-15s %-30s %-40s%n",
                a.getAluno(),
                a.getData().format(DATE_FORMATTER),
                a.getPersonal(),
                a.getDescricao()
            ));
    }

    public void arquivosRelatorios() {
        System.out.println("Implementação da geração de arquivos de relatórios aqui.");
        
    }
}