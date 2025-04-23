package org.serratec.academia.modelo;

import java.time.LocalDate;

public class Avaliacao {
    private Aluno aluno;
    private LocalDate data;
    private Personal personal;
    private String descricao;

    public Avaliacao(Aluno aluno, LocalDate data, Personal personal, String descricao) {
        super();
        this.aluno = aluno;
        this.data = data;
        this.personal = personal;
        this.descricao = descricao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public Personal getPersonal() {
        return personal;
    }

    public String getDescricao() {
        return descricao;
    }

}

