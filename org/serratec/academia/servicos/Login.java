package org.serratec.academia.servicos;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Banco;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;
import org.serratec.academia.servicos.menu.MenuAluno;
import org.serratec.academia.servicos.menu.MenuFuncionario;
import org.serratec.academia.servicos.menu.MenuPersonal;

public class Login {
	String cpf,senha;
	Scanner sc = new Scanner(System.in);
	Boolean acessoConcedido = false;
	Path path = Paths.get(".\\src\\dados_academia.csv");
	Pessoa usuarioLogado = null;

	public void realizarLogin() {
		Banco.cadastrar(path);
		int tent = 0;


		// Loop do tipo "Enquanto" privando o usuario de seguir enquanto não prover um CPF e Senha adequados: 
		while (!acessoConcedido){
			tent++;
			System.out.println(tent + "° Tentativa");
			System.out.print("Informe seu CPF: ");
			cpf = sc.nextLine();
			System.out.print("Informe sua Senha: ");
			senha = sc.nextLine();

			for (int i = 0; i < Banco.alunos.size(); i++) {
				if(Banco.alunos.get(i).getCpf().equals(cpf) && Banco.alunos.get(i).getSenha().equals(senha)) {
					acessoConcedido = true;
					usuarioLogado = Banco.alunos.get(i);
					break;
				}
			}

			for (int i = 0; i < Banco.funcionarios.size(); i++) {
				if(Banco.funcionarios.get(i).getCpf().equals(cpf) && Banco.funcionarios.get(i).getSenha().equals(senha)) {
					acessoConcedido = true;
					usuarioLogado = Banco.funcionarios.get(i);
					break;
				}
			}

			for (int i = 0; i < Banco.personais.size(); i++) {
				if(Banco.personais.get(i).getCpf().equals(cpf) && Banco.personais.get(i).getSenha().equals(senha)) {
					acessoConcedido = true;
					usuarioLogado = Banco.personais.get(i);
					break;
				}
			}
			if (!acessoConcedido) {
				System.out.println("Login invalido, tente novamente.");
			}
		}
		if (usuarioLogado instanceof Aluno) {
			MenuAluno menuAluno = new MenuAluno();
			menuAluno.exibirMenu(usuarioLogado);
		} else if (usuarioLogado instanceof Funcionario) {
			MenuFuncionario menuFuncionario = new MenuFuncionario();
			menuFuncionario.exibirMenu(usuarioLogado);
		} else if (usuarioLogado instanceof Personal) {
			MenuPersonal menuPersonal = new MenuPersonal();
			menuPersonal.exibirMenu(usuarioLogado);
		}
	}
}

