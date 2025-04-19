package org.serratec.academia.especial;

public enum Plano {
    MENSAL_TOTAL("Plano Mensal", 190.0),
    TRIMESTRAL_TOTAL("Plano Trimestral", 520.0),
    SEMESTRAL_TOTAL("Plano Semestral", 950.0),
    ANUAL_TOTAL("Plano Anual", 1750.0);
	  
    private final String descricao;
    private final double valor;
    
    Plano(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public double getValor() {
        return valor;
    }
    
    @Override
    public String toString() {
        return descricao + " - R$ " + valor;
    }
}

