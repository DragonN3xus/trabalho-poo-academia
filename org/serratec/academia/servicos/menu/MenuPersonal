package org.serratec.academia.servicos.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Avaliacao;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuPersonal implements Menu {
    private Scanner sc = new Scanner(System.in);
    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Avaliacao> avaliacoes = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void exibirMenu(Pessoa pessoa) {
        pessoas.add(pessoa);
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
        
        System.out.println("\n==== Seus Alunos ====");
        
        for (Pessoa p : pessoas) {
            if (p instanceof Aluno) {
                Aluno aluno = (Aluno) p;
                if (aluno.getPersonalContratado() != null && 
                    aluno.getPersonalContratado().equals(personal)) {
                    temAlunos = true;
                    System.out.println("Nome: " + aluno.getNome());
                    System.out.println("CPF: " + aluno.getCpf());
                    System.out.println("Data de Matrícula: " + aluno.getDataMatricula());
                    System.out.println("Plano: " + aluno.getPlano().getDescricao());
                    System.out.println("--------------------");
                }
            }
        }
        
        if (!temAlunos) {
            System.out.println("Você ainda não possui alunos cadastrados.");
        }
    }
    
    private void registrarAvaliacao(Personal personal) {
        List<Aluno> alunosPersonal = new ArrayList<>();
        
        for (Pessoa p : pessoas) {
            if (p instanceof Aluno) {
                Aluno aluno = (Aluno) p;
                if (aluno.getPersonalContratado() != null && 
                    aluno.getPersonalContratado().equals(personal)) {
                    alunosPersonal.add(aluno);
                }
            }
        }
        
        if (alunosPersonal.isEmpty()) {
            System.out.println("Você não possui alunos para realizar avaliações.");
            return;
        }
        
        System.out.println("\n==== Selecione um aluno para avaliação ====");
        for (int i = 0; i < alunosPersonal.size(); i++) {
            System.out.println((i + 1) + ". " + alunosPersonal.get(i).getNome());
        }
        
        System.out.print("Escolha o número do aluno (0 para cancelar): ");
        int escolha = sc.nextInt();
        
        if (escolha <= 0 || escolha > alunosPersonal.size()) {
            if (escolha != 0) {
                System.out.println("Opção inválida!");
            }
            return;
        }
        
        Aluno alunoSelecionado = alunosPersonal.get(escolha - 1);
        
        LocalDate dataAvaliacao = LocalDate.now();
        boolean dataValida = false;
        
        sc.nextLine(); 
        
        while (!dataValida) {
            System.out.print("Data da avaliação (formato dd/MM/yyyy, ou ENTER para hoje): ");
            String dataInput = sc.nextLine().trim();
            
            if (dataInput.isEmpty()) {
                dataAvaliacao = LocalDate.now();
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
        
        Avaliacao avaliacao = new Avaliacao(alunoSelecionado, dataAvaliacao, personal, descricao);
        avaliacoes.add(avaliacao);
        
        System.out.println("Avaliação registrada com sucesso para " + alunoSelecionado.getNome() + "!");
    }
    
    private void visualizarAvaliacoes(Personal personal) {
        boolean temAvaliacao = false;
        
        System.out.println("\n==== Avaliações Realizadas ====");
        
        for (Avaliacao avaliacao : avaliacoes) {
            if (avaliacao.getPersonal().equals(personal)) {
                temAvaliacao = true;
                System.out.println("\nAluno: " + avaliacao.getAluno().getNome());
                System.out.println("Data: " + avaliacao.getData());
                System.out.println("Descrição: " + avaliacao.getDescricao());
                System.out.println("--------------------");
            }
        }
        
        if (!temAvaliacao) {
            System.out.println("Você ainda não possui avaliações registradas.");
        }
    }
}
