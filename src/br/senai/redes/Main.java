package br.senai.redes;

import br.senai.redes.model.IPinfo;

public class Main {

	public static void main(String[] args) {

		IPinfo ip1 = new IPinfo();
		
		ip1.setIP("168.198.0.0/24");
		ip1.splitIP();
		ip1.classeIP();
		ip1.binario();
		ip1.decimal();
		ip1.calculaHosts();
		ip1.resultados();
		
	}
	
}
