package org.serratec.academia.servicos.menu;

import java.util.List;
import java.util.Scanner;

import org.serratec.academia.especial.Plano;
import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Avaliacao;
import org.serratec.academia.modelo.Banco;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

public class MenuAluno implements Menu {
    private Scanner sc = new Scanner(System.in);

    @Override
    public void exibirMenu(Pessoa pessoa) {
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
        Plano plano = null;
        for (Plano p : Banco.planos) {
            if (p.getId() == aluno.getIdPlano()) {
                plano = p;
                break;
            }
        }

        if (plano != null) {
            System.out.println("Plano: " + plano.getModalidades());
            System.out.println("Valor: R$ " + plano.getValor());
        } else {
            System.out.println("Nenhum plano contratado.");
        }

        if (aluno.getPersonalContratado() != null && !aluno.getPersonalContratado().isEmpty()) {
            System.out.println("\n==== Personal Contratado ====");
            for (Personal p : Banco.personais) {
                if (p.getNome().equalsIgnoreCase(aluno.getPersonalContratado())) {
                    System.out.println("Nome: " + p.getNome());
                    System.out.println("Especialidade: " + p.getEspecialidade());
                    System.out.println("CREF: " + p.getCref());
                    return;
                }
            }
            System.out.println("Personal não encontrado.");
        } else {
            System.out.println("\nVocê ainda não contratou um personal trainer.");
        }
    }
    
    private void contratarPersonal(Aluno aluno) {
        if (aluno.getPersonalContratado() != null && !aluno.getPersonalContratado().isEmpty()) {
            System.out.println("Você já possui um personal trainer contratado: " + aluno.getPersonalContratado());
            System.out.print("Deseja trocar? (S/N): ");
            sc.nextLine();
            String resposta = sc.nextLine().toUpperCase();

            if (!resposta.equals("S")) {
                return;
            }
        }

        List<Personal> personais = Banco.personais;
        if (personais.isEmpty()) {
            System.out.println("Não há personais cadastrados no sistema.");
            return;
        }

        System.out.println("\n==== Personais Disponíveis ====");
        for (int i = 0; i < personais.size(); i++) {
            Personal p = personais.get(i);
            System.out.printf("%d. %s - Especialidade: %s\n", i + 1, p.getNome(), p.getEspecialidade());
        }

        System.out.print("Escolha o número do personal desejado (0 para cancelar): ");
        int escolha = sc.nextInt();
        sc.nextLine();

        if (escolha > 0 && escolha <= personais.size()) {
            Personal escolhido = personais.get(escolha - 1);
            aluno.setPersonalContratado(escolhido.getNome());
            System.out.println("Personal " + escolhido.getNome() + " contratado com sucesso!");
        } else if (escolha != 0) {
            System.out.println("Opção inválida!");
        }
    }
    
    private void visualizarAvaliacoes(Aluno aluno) {
        boolean temAvaliacao = false;
        
        System.out.println("\n==== Suas Avaliações Físicas ====");
        
        for (Avaliacao avaliacao : Banco.avaliacoes) {
            if (avaliacao.getAluno().equals(aluno.getNome())) {
                temAvaliacao = true;
                System.out.println("\nData: " + avaliacao.getData());
                System.out.println("Personal: " + avaliacao.getPersonal());
                System.out.println("Descrição: " + avaliacao.getDescricao());
            }
        }
        
        if (!temAvaliacao) {
            System.out.println("Você ainda não possui avaliações registradas.");
        }
    }
}