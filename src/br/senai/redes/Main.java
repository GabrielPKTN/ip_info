package br.senai.redes;

import br.senai.redes.gui.MostrarResultados;
import br.senai.redes.model.IPinfo;


public class Main {

	public static void main(String[] args) {

//		MostrarResultados tela = new MostrarResultados();
//		tela.criaTela("IP Info");
		
		IPinfo ip = new IPinfo();
		
		ip.setIP("192.168.1.0/27");
		ip.separaIP();
		
		
		
	}
	
}
