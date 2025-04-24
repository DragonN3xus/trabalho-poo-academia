package org.serratec.academia.modelo;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private String senha;

    public Pessoa(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "- Nome: " + nome + "\n- CPF: " + cpf + "\n- Senha: " + senha;
    }
}


