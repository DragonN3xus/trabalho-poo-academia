package org.serratec.academia.modelo;

import java.time.LocalDate;
import org.serratec.academia.especial.GerarRelatorio;

public class Avaliacao implements GerarRelatorio {
    private String aluno;
    private LocalDate data;
    private String personal;
    private String descricao;
    private double massa;
    private double altura;
    private double percentualGordura;
    private double metaMassa;
    private double metaPercentualGordura;

    public Avaliacao(String aluno, LocalDate data, String personal, String descricao, double massa, double altura,
            double percentualGordura, double metaMassa, double metaPercentualGordura) {
        super();
        this.aluno = aluno;
        this.data = data;
        this.personal = personal;
        this.descricao = descricao;
        this.massa = massa;
        this.altura = altura;
        this.percentualGordura = percentualGordura;
        this.metaMassa = metaMassa;
        this.metaPercentualGordura = metaPercentualGordura;
    }

    public String getAluno() {
        return aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public String getPersonal() {
        return personal;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMassa() {
        return massa;
    }

    public double getAltura() {
        return altura;
    }

    public double getPercentualGordura() {
        return percentualGordura;
    }

    public double getMetaMassa() {
        return metaMassa;
    }

    public double getMetaPercentualGordura() {
        return metaPercentualGordura;
    }

	@Override
	public String gerar() {
		return null;
	}

}

