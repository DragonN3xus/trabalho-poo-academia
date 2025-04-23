package org.serratec.academia.especial;

import java.util.List;

public class Plano {
    private Periodo periodo;
    private List<Modalidade> modalidades;
    private double valor;

    public Plano(Periodo periodo, List<Modalidade> modalidades, double valor) {
        super();
        this.periodo = periodo;
        this.modalidades = modalidades;
        this.valor = valor;
    }

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Plano: ").append(periodo);
		builder.append("\nModalidades: ");

		if (modalidades != null && !modalidades.isEmpty()) {
			for (int i = 0; i < modalidades.size(); i++) {
				builder.append(modalidades.get(i));
				if (i < modalidades.size() - 1) {
					builder.append(", ");
				}
			}
		} else {
			builder.append("Nenhuma");
		}

		builder.append("\nValor: ").append(valor);
		return builder.toString();
	}
}

