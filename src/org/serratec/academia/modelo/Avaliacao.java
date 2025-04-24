package org.serratec.academia.modelo;

import java.time.LocalDate;

public class Avaliacao {
    private String aluno;
    private LocalDate data;
    private String personal;
    private String descricao;

    public Avaliacao(String aluno, LocalDate data, String personal, String descricao) {
        super();
        this.aluno = aluno;
        this.data = data;
        this.personal = personal;
        this.descricao = descricao;
    }

    public String getAluno() {
        return aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public String getString() {
        return personal;
    }

    public String getDescricao() {
        return descricao;
    }

	public String getPersonal() {
		return personal;
	}

	@Override
	public String toString() {
		return "Aluno: " + aluno + "\nData :" + data + "\nPersonal: " + personal + "\nDescrição: " + descricao;
	}

}

