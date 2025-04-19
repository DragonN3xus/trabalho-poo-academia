package org.serratec.academia.modelo;
import org.serratec.academia.especial.Cargo;

public class Funcionario extends Pessoa {
    private Cargo cargo;

    public Funcionario(String nome, String cpf, String senha, Cargo cargo) {
        super(nome, cpf, senha);
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return super.toString() + "\n- Cargo: " + cargo;
    }
    
    

}
