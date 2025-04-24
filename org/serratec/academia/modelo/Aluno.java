package org.serratec.academia.modelo;

import java.time.LocalDate;

public class Aluno extends Pessoa {
    private LocalDate dataMatricula;
    private int idPlano;
    private String personalContratado;
    
    
    public Aluno(String nome, String cpf, String senha, LocalDate dataMatricula, int idPlano, String personalContratado) {
		super(nome, cpf, senha);
		this.dataMatricula = dataMatricula;
		this.idPlano = idPlano;
		this.personalContratado = personalContratado;
	}
    
	public int getIdPlano() {
		return idPlano;
	}
	public void setid_Plano(int idPlano) {
		this.idPlano = idPlano;
	}
	public String getPersonalContratado() {
		return personalContratado;
	}
	public void setPersonalContratado(String personalContratado) {
		this.personalContratado = personalContratado;
	}
	public LocalDate getDataMatricula() {
		return dataMatricula;
	}
}


