package org.serratec.academia.modelo;

public class Personal extends Pessoa {
    private String especialidade;
    private String cref;

    public Personal(String nome, String cpf, String senha, String especialidade, String cref) {
        super(nome, cpf, senha);
        this.especialidade = especialidade;
        this.cref = cref;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getCref() {
        return cref;
    }

    @Override
    public String toString() {
        return super.toString() + "\nEspecialidade: " + especialidade + "\nCREF: " + cref;
    }

    
}