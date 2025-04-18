package br.senai.redes;

import br.senai.redes.model.IPinfo;

public class Main {

	public static void main(String[] args) {

		IPinfo ip1 = new IPinfo();
		
		ip1.setCIDR(16);
		ip1.calculaHosts();
		ip1.infoIP();
		ip1.mostraResultado();
		
	}

}
