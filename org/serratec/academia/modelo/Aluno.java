package org.serratec.academia.modelo;

import java.time.LocalDate;
import org.serratec.academia.especial.Plano;

public class Aluno extends Pessoa {
    private LocalDate dataMatricula;
    private Plano plano;
    private Personal personalContratado;
    
    
    public Aluno(String nome, String cpf, String senha, LocalDate dataMatricula, Plano plano, Personal personalContratado) {
		super(nome, cpf, senha);
		this.dataMatricula = dataMatricula;
		this.plano = plano;
		this.personalContratado = personalContratado;
	}
    
	public Plano getPlano() {
		return plano;
	}
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	public Personal getPersonalContratado() {
		return personalContratado;
	}
	public void setPersonalContratado(Personal personalContratado) {
		this.personalContratado = personalContratado;
	}
	public LocalDate getDataMatricula() {
		return dataMatricula;
	}
}


