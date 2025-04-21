package org.serratec.academia.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.modelo.Aluno;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;
import org.serratec.academia.modelo.Pessoa;

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
					if (usuarioLogado instanceof Funcionario) {
						System.out.println("Você é um Funcionario");
					} else if (usuarioLogado instanceof Aluno) {
						System.out.println("Você é um Aluno");
					} else if (usuarioLogado instanceof Personal) {
						System.out.println("Você é um Personal");
					}
					break;
				}
			System.out.println("Acesso não autorizado, CPF ou Senha incorretos");
			}

		}
		
	}

}
