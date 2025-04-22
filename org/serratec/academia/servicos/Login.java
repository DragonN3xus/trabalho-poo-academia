package org.serratec.academia.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;
import org.serratec.academia.servicos.menu.MenuFuncionario;

public class Login {
	String cpf,senha;
	Scanner sc = new Scanner(System.in);
	Boolean acessoConcedido = false;
	
	Pessoa admin = new Funcionario("admin", "admin", "1234", Cargo.GERENTE);
	private static List<Pessoa> pessoas = new ArrayList<>();

	
	public void realizarLogin() {
		pessoas.add(admin);
		int tent = 0;
		
		// Loop do tipo "Enquanto" privando o usuario de seguir enquanto não prover um CPF e Senha adequados: 
		while (!acessoConcedido){
			tent++;
			System.out.println(tent + "° Tentativa");
			System.out.print("Informe seu CPF: ");
			cpf = sc.nextLine();
			System.out.print("Informe sua Senha: ");
			senha = sc.nextLine();

			for (int i = 0; i < pessoas.size(); i++) {

				if(pessoas.get(i).getCpf().equals(cpf) && pessoas.get(i).getSenha().equals(senha)) {
					acessoConcedido = true;
					System.out.println("Login realizado com sucesso!");
					Pessoa usuarioLogado = pessoas.get(i);
					
					// TROQUEM AQUI PELOS MENUS PARA CADA TIPO DE PESSOA
					if (usuarioLogado instanceof Funcionario) {
					    MenuFuncionario menuFuncionario = new MenuFuncionario();
						menuFuncionario.exibirMenu(usuarioLogado);
					} else if (usuarioLogado instanceof Aluno) {
						MenuFuncionario menuFuncionario = new MenuFuncionario();
						menuFuncionario.exibirMenu(usuarioLogado);
					} else if (usuarioLogado instanceof Personal) {
						MenuFuncionario menuFuncionario = new MenuFuncionario();
						menuFuncionario.exibirMenu(usuarioLogado);
					}
					break;
				}
			System.out.println("Acesso não autorizado, CPF ou Senha incorretos");
			}

		}
		
	}

}

}
