package org.serratec.academia.modelo;

public class Personal extends Pessoa {
    private String especialidade;
    private String cref;

    public Personal(String nome, String cpf, String senha, String especialidade, String cref) {
        super(nome, cpf, senha);
        this.especialidade = especialidade;
        setCref(cref);
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getCref() {
        return cref;
    }
    
    public void setCref(String cref) {
        if (cref != null && cref.matches("\\d{6}[GP][A-Z]{2}")) {
            this.cref = cref;
        } else {
            throw new IllegalArgumentException("CREF inválido! Formato: 6 dígitos + G ou P + UF");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n- Especialidade: " + especialidade + "\n- CREF: " + cref;
    }
}
