package org.serratec.academia.modelo;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.especial.Modalidade;
import org.serratec.academia.especial.Periodo;
import org.serratec.academia.especial.Plano;

public class Banco {
	public static ArrayList<Funcionario> funcionarios = new ArrayList<>();
	public static ArrayList<Aluno> alunos = new ArrayList<>();
	public static ArrayList<Personal> personais = new ArrayList<>();
	public static ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
	public static ArrayList<Plano> planos = new ArrayList<>();
	
	public static void cadastrar(Path arquivo) {
		try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
			String linha;
			
			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(",");
				
				String tipo = campos[0].trim().toLowerCase();
				// PLANO
				if(tipo.equals("plano")) {
					int id = Integer.parseInt(campos[1].trim());
					Periodo periodo = Periodo.valueOf(campos[2].trim().toUpperCase());
					List<Modalidade> modalidades = new ArrayList<>();
					String[] mod = campos[3].trim().split(";");
					for (String modalidade : mod) {
						modalidades.add(Modalidade.valueOf(modalidade.trim().toUpperCase()));
					}
					Double valor = Double.parseDouble(campos[4].trim());
					Plano plano = new Plano(id, periodo, modalidades, valor);
					planos.add(plano);
				} 
				// AVALIACAO
				if(tipo.equals("avaliacao")) {
					String aluno = campos[1].trim();
					LocalDate data = LocalDate.parse(campos[2].trim());
					String personal = campos[3].trim();
					String descricao = campos[4].trim();
					Avaliacao avaliacao = new Avaliacao(aluno, data, personal, descricao);
					avaliacoes.add(avaliacao);
				}
				// PESSOA
				String nome = campos[1].trim();
				String cpf = campos[2].trim();
				String senha = campos[3].trim();
				
				// FUNCIONARIO
				if(tipo.equals("funcionario")) {
					Cargo cargo = Cargo.valueOf(campos[4].trim().toUpperCase());
					Funcionario funcionario = new Funcionario(nome, cpf, senha, cargo);
					funcionarios.add(funcionario);
					
				// ALUNO
				} else if(tipo.equals("aluno")) {
					LocalDate dataMatricula = LocalDate.parse(campos[4].trim());
					int idPlano = Integer.parseInt(campos[5].trim());
					String personalContratado = campos[6].trim();
					Aluno aluno = new Aluno(nome, cpf, senha, dataMatricula, idPlano, personalContratado);
					alunos.add(aluno);
				
				// PERSONAL
				} else if(tipo.equals("personal")) {
					String especialidade = campos[4].trim();
					String cref = campos[5].trim();
					Personal personal = new Personal(nome, cpf, senha, especialidade, cref);
					personais.add(personal);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
	    System.out.println("Banco de dados carregado com sucesso!");
	}
}

