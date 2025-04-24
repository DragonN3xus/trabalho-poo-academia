package org.serratec.academia.main;

import org.serratec.academia.servicos.Login;

public class SistemaAcademia {
	public static void main(String[] args) {
		Login login = new Login();
		login.realizarLogin();
	}
}
