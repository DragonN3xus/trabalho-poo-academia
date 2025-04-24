package org.serratec.academia.servicos.menu;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Avaliacao;
import org.serratec.academia.modelo.Banco;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuPersonal implements Menu {
    private Scanner sc = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Path path = Paths.get(".\\src\\dados_academia.csv");

    @Override
    public void exibirMenu(Pessoa pessoa) {
    	Personal personal = (Personal) pessoa;
        int opcao;
        do {
            System.out.println("\n# ===== # Menu de Personais # ===== #");
            System.out.println("1. Visualizar alunos");
            System.out.println("2. Registrar avaliações físicas dos alunos");
            System.out.println("3. Visualizar lista de avaliações realizadas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            
            switch (opcao) {
                case 1:
                    visualizarAlunos(personal);
                    break;
                case 2:
                    registrarAvaliacao(personal);
                    break;
                case 3:
                    visualizarAvaliacoes(personal);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private void visualizarAlunos(Personal personal) {
        boolean temAlunos = false;
        
        System.out.println("\n# ==== # Seus Alunos # ==== #");
        
        for (Aluno a : Banco.alunos) {
                if (a.getPersonalContratado() != null && 
                    a.getPersonalContratado().equals(personal.getNome())) {
                    temAlunos = true;
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("CPF: " + a.getCpf());
                    System.out.println("Data de Matrícula: " + a.getDataMatricula());
                    System.out.println("# ===== ===== ===== ===== #");
                }
            }
        
        if (!temAlunos) {
            System.out.println("Você ainda não possui alunos cadastrados.");
        }
    }
    
    private void registrarAvaliacao(Personal personal) {
        int count = 0;
        Map<Integer, Aluno> mapaAlunos = new HashMap<>();

        System.out.println("\n==== Selecione um aluno para avaliação ====");
        
        for (Aluno aluno : Banco.alunos) {
            if (aluno.getPersonalContratado().equalsIgnoreCase(personal.getNome())) {
                count++;
                System.out.println(count + ". " + aluno.getNome());
                mapaAlunos.put(count, aluno);
            }
        }

        if (count == 0) {
            System.out.println("Você não possui alunos para realizar avaliações.");
            return;
        }

        System.out.print("Escolha o número do aluno (0 para cancelar): ");
        int escolha = sc.nextInt();
        sc.nextLine();

        if (escolha == 0) return;

        Aluno alunoSelecionado = mapaAlunos.get(escolha);
       
        
        if (alunoSelecionado == null) {
            System.out.println("Opção inválida!");
            return;
        }
        
        String nomeAluno = alunoSelecionado.getNome();
        LocalDate dataAvaliacao = LocalDate.now();
        boolean dataValida = false;
        
        while (!dataValida) {
            System.out.print("Data da avaliação (formato dd/MM/yyyy, ou ENTER para hoje): ");
            String dataInput = sc.nextLine().trim();

            if (dataInput.isEmpty()) {
                dataValida = true;
            } else {
                try {
                    dataAvaliacao = LocalDate.parse(dataInput, formatter);
                    dataValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
                }
            }
        }

        System.out.print("Descrição da avaliação: ");
        String descricao = sc.nextLine();

        Avaliacao avaliacao = new Avaliacao(nomeAluno, dataAvaliacao, personal.getNome(), descricao);
        Banco.avaliacoes.add(avaliacao);

        System.out.println("Avaliação registrada com sucesso para " + alunoSelecionado.getNome() + "!");
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

			writer.write("avaliacao," + nomeAluno + "," + dataAvaliacao + "," + personal.getNome() + "," + descricao + "\n");
			System.out.println("Adicionando avaliação ao arquivo CSV...");
		} catch (IOException e) {
			System.err.println("Erro ao adicionar avaliação ao arquivo CSV: " + e.getMessage());
			e.printStackTrace();
		}
	}

    
    private void visualizarAvaliacoes(Personal personal) {
        boolean temAvaliacao = false;
        
        System.out.println("\n# ===== # Avaliações Realizadas # ===== #");
        
        for (Avaliacao av : Banco.avaliacoes) {
            if (av.getPersonal().equals(personal.getNome())) {
                temAvaliacao = true;
                System.out.println("\nAluno: " + av.getAluno());
                System.out.println("Data: " + av.getData());
                System.out.println("Descrição: " + av.getDescricao());
                System.out.println("# ===== ===== ===== ===== #");
            }
        }
        
        if (!temAvaliacao) {
            System.out.println("Você ainda não possui avaliações registradas.");
        }
    }
}
