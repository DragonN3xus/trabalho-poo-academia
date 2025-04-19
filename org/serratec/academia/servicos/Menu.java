package org.serratec.academia.servicos;

import org.serratec.academia.especial.Cargo;
import org.serratec.academia.modelo.Funcionario;
import org.serratec.academia.modelo.Personal;

public class Menu {
    public static void main(String[] args) {
        
        Personal p1 = null;
		Funcionario f1 = null;
		Funcionario f2 = null;
		
		try {
			p1 = new Personal("João", "12345678900", "olaaa", "treinador", "6548");
			f1 = new Funcionario("Maria", "12345678910", "lllll", Cargo.ATENDENTE);
			f2 = new Funcionario("José", "12345678900", "teste", Cargo.ZELADOR);
			
			System.out.println(p1);
			System.out.println(f1);
			System.out.println(f2);
		} catch (Exception IllegalArgumentException) {
			System.out.println("CPF ja cadastrado!");
		}
        


		
    }
}
