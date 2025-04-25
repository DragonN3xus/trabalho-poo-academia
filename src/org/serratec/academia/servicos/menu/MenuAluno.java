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
        System.out.println("Olá " + aluno.getNome() + "!");
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
        System.out.println("\n# ===== # Dados Pessoais # ===== #");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCpf());
        System.out.println("Data de Matrícula: " + aluno.getDataMatricula());

        System.out.println("\n# ===== # Plano Contratado # ===== #");
        Plano plano = null;
        for (Plano pl : Banco.planos) {
            if (pl.getId() == aluno.getIdPlano()) {
                plano = pl;
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
            System.out.println("\n# ===== # Personal Contratado # ===== #");
            for (Personal per : Banco.personais) {
                if (per.getNome().equalsIgnoreCase(aluno.getPersonalContratado())) {
                    System.out.println("Nome: " + per.getNome());
                    System.out.println("Especialidade: " + per.getEspecialidade());
                    System.out.println("CREF: " + per.getCref());
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

		System.out.println("\nPersonais disponíveis:");
		System.out.println("# ===== ===== ===== ===== ===== ===== #");
		System.out.printf("%-30s %-30s%n", "Nome:", "Especialidade:");

		for (Personal per : Banco.personais) {
			System.out.printf("%-30s %-30s%n", per.getNome(), per.getEspecialidade());
		}

		String escolhido = null;

		do {
			System.out.print("\nInforme o nome do Personal desejado: ");
			String nomePersonal = sc.nextLine();

			for (Personal per : Banco.personais) {
				if (per.getNome().equalsIgnoreCase(nomePersonal)) {
					escolhido = per.getNome();
					aluno.setPersonalContratado(per.getNome());
					System.out.println("Personal " + per.getNome() + " contratado com sucesso!");
					break;
				}
			}

			if (escolhido == null) {
				System.out.println("Personal não encontrado. Tente novamente.");
			}
		} while (escolhido == null);
    }

    
    private void visualizarAvaliacoes(Aluno aluno) {
        boolean temAvaliacao = false;
        
        System.out.println("\n# ===== # Suas Avaliações Físicas # ===== #");
        
        for (Avaliacao av : Banco.avaliacoes) {
            if (av.getAluno().equals(aluno.getNome())) {
                temAvaliacao = true;
                System.out.println("\nData: " + av.getData());
                System.out.println("Personal: " + av.getPersonal());
                System.out.println("Descrição: " + av.getDescricao());
            }
        }
        
        if (!temAvaliacao) {
            System.out.println("Você ainda não possui avaliações registradas.");
        }
    }
}