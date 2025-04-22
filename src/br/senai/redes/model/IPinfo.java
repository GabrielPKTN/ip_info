package br.senai.redes.model;


import java.lang.Math;

public class IPinfo {
	private String IP;
	private int cidr;
	private int hosts;
	private String classeIP;
	private String mascaraPadrao;
	private String mascaraBinario;
	private String[] octetos;
	
	public void setIP(String IP) {
		this.IP = IP;
	}
	
	public void setCIDR(int cidr) {
		this.cidr = cidr;
	}
	
	public void calculaHosts() {
		int hosts = 32 - cidr;
		this.hosts = (int) Math.pow(2, hosts)- 2;
	}
	
	public void splitIP() {
		
		String[] splitIP = IP.split("/");
		cidr = Integer.valueOf(splitIP[1]);
		octetos = splitIP[0].split("\\.");
		
	}
	
	public void infoIP() {
		int primeiroOcteto = Integer.valueOf(octetos[0]);
		if (primeiroOcteto > 0 && primeiroOcteto < 172);
	}
	
	
//	public void infoIP() {
//		if (cidr == 8) {
//			classeIP = "Classe A";
//			mascaraPadrao = "255.0.0.0";
//			mascaraBinario = "11111111.00000000.00000000.00000000";
//		} else if (cidr == 16) {
//			classeIP = "Classe B";
//			mascaraPadrao = "255.255.0.0";
//			mascaraBinario = "11111111.11111111.00000000.00000000";
//		} else if (cidr == 24) {
//			classeIP = "Classe C";
//			mascaraPadrao = "255.255.255.0";
//			mascaraBinario = "11111111.11111111.11111111.00000000";
//		}
//	}

	
	public void mostraResultado() {
		System.out.println("Informações do endereço de ip");
		System.out.println("Classe do IP: " + classeIP);
		System.out.println("Classe padrão em decimal: " + mascaraPadrao);
		System.out.println("Classe padrão em binário: " + mascaraBinario);
		System.out.println("Hosts: " + hosts + " por rede");
	}
}
