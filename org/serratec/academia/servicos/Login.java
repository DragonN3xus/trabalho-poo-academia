package org.serratec.academia.servicos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.especial.Modalidade;
import org.serratec.academia.especial.Periodo;
import org.serratec.academia.especial.Plano;
import org.serratec.academia.modelo.Aluno;
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
	LocalDate data = LocalDate.parse("2025-12-04");
	private static List<Pessoa> pessoas = new ArrayList<>();
	
	Plano anual = new Plano(Periodo.ANUAL, List.of(Modalidade.MUSCULACAO, Modalidade.BOXE), 4000);
	Funcionario admin = new Funcionario("admin", "12345678910", "1234", Cargo.GERENTE);
	Personal personal = new Personal("Claudio", "32345678910", "1234", "bag", "321");
	Pessoa aluno = new Aluno("Bernardo", "22345678910", "555",  data, anual, personal);
			


	
	public void realizarLogin() {
		pessoas.add(admin);
		pessoas.add(personal);
		pessoas.add(aluno);
		
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
						MenuAluno menuAluno = new MenuAluno();
						menuAluno.exibirMenu(usuarioLogado);
					} else if (usuarioLogado instanceof Personal) {
						MenuPersonal menuPersonal = new MenuPersonal();
						menuPersonal.exibirMenu(usuarioLogado); 
					} else {
						System.out.println("Acesso não autorizado, CPF ou Senha incorretos");
					}
					break;
				}
			}

		}

	}

}
