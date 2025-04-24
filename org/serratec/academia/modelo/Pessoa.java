package org.serratec.academia.modelo;

import java.util.HashSet;
import java.util.Set;

public abstract class Pessoa {
    private static Set<String> cpfsCadastrados = new HashSet<>();

    private String nome;
    private String cpf;
    private String senha;

    public Pessoa(String nome, String cpf, String senha) {
        validarCpf(cpf);

        if (cpfsCadastrados.contains(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }

        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;

        cpfsCadastrados.add(cpf);
    }

    private void validarCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo.");
        }

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos, sem letras ou símbolos.");
        }
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


