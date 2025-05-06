package br.senai.redes.model;


import java.lang.Math;

public class IPinfo {
	private String IP;
	private int cidr;
	private int hosts;
	private int subRedes;
	private String classeIP;
	private String mascaraDecimal = "";
	private String mascaraBinario = "";
	private String[] octetos;
	
	public void setIP(String IP) {
		this.IP = IP;
	}
	
	// Separando octetos e CIDR do IP
	
	public void splitIP() {
			
		String[] splitIP = IP.split("/");
		cidr = Integer.valueOf(splitIP[1]);
		octetos = splitIP[0].split("\\.");
			
	}
	
	// Define a classe de ip de acordo com o primeiro octeto
	
	public String classeIP() {
		
		int primeiroOcteto = Integer.valueOf(octetos[0]);

		if (primeiroOcteto > 1 && primeiroOcteto < 127) {
			classeIP = "Classe A";
			
		} else if (primeiroOcteto > 127 && primeiroOcteto < 192) {
			classeIP = "Classe B";
			
		} else if (primeiroOcteto > 192 && primeiroOcteto < 223) {
			classeIP = "Classe C";
		}
		return classeIP;
	}
	
	// Define a mascara em padrão binario
	
	public String binario() {
		int contador = 0;
		int octeto = 0;
		while (contador < cidr) {
			
			mascaraBinario += "1";
			octeto++;
			contador++;
			
			if (octeto == 8) {
				mascaraBinario += ".";
				octeto = 0;
			}
		
		if (contador == cidr) {
			while (mascaraBinario.length() < 35) {
				mascaraBinario += "0";
				octeto++;
				
				if (octeto == 8 && mascaraBinario.length() < 35) {
					mascaraBinario += ".";
					octeto = 0;
				}
			}
		}
		}
		return mascaraBinario;
	}
	
	// Transformando mascara em decimal
	
	public String decimal() {
		
		String[] splitBinario = mascaraBinario.split("\\.");
		
		//variaveis que passar o valor para os parametros
		int binario = 128;
		int octetoDecimal = 0;
		
		//contadores para loop
		
		int i = 0;
		int indexContador = 0;
		
		//enquanto "i" for menor que a quantidade de octetos o loop não acaba
		
		while (i < 4) {
			
			//split do primeiro octeto para pegar um index de cada vez
			
			String[] octeto = splitBinario[i].split("");
			
			//enquanto o contador de index for menor que o tamanho do
			//primeiro octeto, então o loop continua
			
			while (indexContador < octeto.length) {
				
				//se o conteudo do index atual for igual a 1 então o
				//parametro octeto decimal recebe o valor do primeiro
				//bit em decimal [128, 64, 32, 16, 8, 4, 2, 1]
				
				if (octeto[indexContador].equals("1")) {
					
					octetoDecimal += binario;
					binario = binario/2;
					indexContador++;
				
				} else {
					
					binario = binario/2;
					indexContador++;
				
				}
			}
			
			i++;
			binario = 128;
			indexContador = 0;
			
			//conversão para String
			if (i == 4) {
				mascaraDecimal += String.valueOf(octetoDecimal);
				octetoDecimal = 0;
			} else {
				mascaraDecimal += String.valueOf(octetoDecimal) + ".";
				octetoDecimal = 0;
			}
			
		}
		return mascaraDecimal;
	}
	
	// Calculando ips disponiveis na rede
	
	public int hosts() {
		
		hosts = 32 - cidr;
		this.hosts = (int) Math.pow(2, hosts)- 2;
		return this.hosts;
			
	}
	
	public int subRedes() {
		String binarioSemPonto = "";
		String[] splitBinario = mascaraBinario.split("\\.");
		for (int i = 0; i < splitBinario.length; i++) {
			binarioSemPonto += splitBinario[i];
		}
		int bits = 0;
		String[] splitBinarioSemPonto = binarioSemPonto.split("");
		for (int i = 0; i < splitBinarioSemPonto.length; i++) {
			if (splitBinarioSemPonto[i].equals("1")) {
				bits++;
				
			}
			
		}
		
		int n = 0;
				
		if (classeIP.equals("Classe A")) {
			n = bits - 8;
			subRedes = (int) Math.pow(2, n);
		} else if (classeIP.equals("Classe B")) {
			n = bits - 16;
			subRedes = (int) Math.pow(2, n);
		} else if (classeIP.equals("Classe C")) {
			n = bits - 24;
			subRedes = (int) Math.pow(2, n);
		}
		
		return subRedes;
	}
	
	public String[] resultados() {
		
		String[] resultado = new String[6];
		
		resultado[0] = "IP: " + IP;
		resultado[1] = "Classe do IP: " + classeIP();
		resultado[2] = "Mascara em binario: " + binario();
		resultado[3] = "Mascara em decimal: " + decimal();
		resultado[4] = "IP's disponiveis por rede: " + hosts();
		resultado[5] = "Numero de sub-redes: " + subRedes();
		
		return resultado;
		
//		System.out.println("Retorno de dados do IP");
//		System.out.println("IP: " + IP);
//		System.out.println("Classe do IP: " + classeIP());
//		System.out.println("Mascara em binario: " + binario());
//		System.out.println("Mascara em decimal: " + decimal());
//		System.out.println("IP's disponiveis por rede: " + hosts());
	}
	
}
	 