package org.serratec.academia.servicos.menu;

import java.util.Scanner;

import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Avaliacao;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class MenuAluno implements Menu {
    private Scanner sc = new Scanner(System.in);
    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Avaliacao> avaliacoes = new ArrayList<>();

    @Override
    public void exibirMenu(Pessoa pessoa) {
        pessoas.add(pessoa);
        Aluno aluno = (Aluno) pessoa;
        
        int opcao;
        do {
            System.out.println("\n# ===== # Menu de Alunos # ===== #");
            System.out.println("1. Visualizar dados pessoais e plano contratado");
            System.out.println("2. Contratar personal trainer");
            System.out.println("3. Visualizar avaliações físicas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            
            switch (opcao) {
                case 1:
                    visualizarDadosPessoais(aluno);
                    break;
                case 2:
                    contratarPersonal(aluno);
                    break;
                case 3:
                    visualizarAvaliacoes(aluno);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    private void visualizarDadosPessoais(Aluno aluno) {
        System.out.println("\n==== Dados Pessoais ====");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCpf());
        System.out.println("Data de Matrícula: " + aluno.getDataMatricula());
        System.out.println("\n==== Plano Contratado ====");
        System.out.println("Plano: " + aluno.getPlano().getDescricao());
        System.out.println("Valor: R$ " + aluno.getPlano().getValor());
        
        if (aluno.getPersonalContratado() != null) {
            System.out.println("\n==== Personal Contratado ====");
            System.out.println("Nome: " + aluno.getPersonalContratado().getNome());
            System.out.println("Especialidade: " + aluno.getPersonalContratado().getEspecialidade());
            System.out.println("CREF: " + aluno.getPersonalContratado().getCREF());
        } else {
            System.out.println("\nVocê ainda não contratou um personal trainer.");
        }
    }
    
    private void contratarPersonal(Aluno aluno) {
        if (aluno.getPersonalContratado() != null) {
            System.out.println("Você já possui um personal trainer contratado: " + 
                               aluno.getPersonalContratado().getNome());
            System.out.print("Deseja trocar? (S/N): ");
            sc.nextLine();
            String resposta = sc.nextLine().toUpperCase();
            
            if (!resposta.equals("S")) {
                return;
            }
        }
        
        List<Personal> personais = new ArrayList<>();
        System.out.println("\n==== Personais Disponíveis ====");
        int i = 1;
        
        for (Pessoa p : pessoas) {
            if (p instanceof Personal) {
                Personal personal = (Personal) p;
                personais.add(personal);
                System.out.println(i + ". " + personal.getNome() + " - Especialidade: " + 
                                   personal.getEspecialidade());
                i++;
            }
        }
        
        if (personais.isEmpty()) {
            System.out.println("Não há personais cadastrados no sistema.");
            return;
        }
        
        System.out.print("Escolha o número do personal desejado (0 para cancelar): ");
        int escolha = sc.nextInt();
        
        if (escolha > 0 && escolha <= personais.size()) {
            aluno.setPersonalContratado(personais.get(escolha - 1));
            System.out.println("Personal " + personais.get(escolha - 1).getNome() + 
                               " contratado com sucesso!");
        } else if (escolha != 0) {
            System.out.println("Opção inválida!");
        }
    }
    
    private void visualizarAvaliacoes(Aluno aluno) {
        boolean temAvaliacao = false;
        
        System.out.println("\n==== Suas Avaliações Físicas ====");
        
        for (Avaliacao avaliacao : avaliacoes) {
            if (avaliacao.getAluno().equals(aluno)) {
                temAvaliacao = true;
                System.out.println("\nData: " + avaliacao.getData());
                System.out.println("Personal: " + avaliacao.getPersonal().getNome());
                System.out.println("Descrição: " + avaliacao.getDescricao());
            }
        }
        
        if (!temAvaliacao) {
            System.out.println("Você ainda não possui avaliações registradas.");
        }
    }
}
