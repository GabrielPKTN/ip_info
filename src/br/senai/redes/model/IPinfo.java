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
	
	public void separaIP() {
			
		String[] splitIP = IP.split("/");
		cidr = Integer.valueOf(splitIP[1]);
		octetos = splitIP[0].split("\\.");
		if (octetos.length != 4) {
			octetos[0] = "Formato de IP invalido.";
		}
			
	}
	
	// Define a classe de ip de acordo com o primeiro octeto
	
	public String defineClasseIP() {
		
		int primeiroOcteto = Integer.valueOf(octetos[0]);

		if (primeiroOcteto > 0 && primeiroOcteto <= 127) {
			classeIP = "Classe A";
			
		} else if (primeiroOcteto > 127 && primeiroOcteto <= 191) {
			classeIP = "Classe B";
			
		} else if (primeiroOcteto > 191 && primeiroOcteto <= 223) {
			classeIP = "Classe C";
			
		} else if (primeiroOcteto > 223 && primeiroOcteto <= 239) {
			classeIP = "Classe D (Multicast)";
			
		} else if (primeiroOcteto > 239 && primeiroOcteto <= 255) {
			classeIP = "Classe E (Reservada)";
			
		}
		
		return classeIP;
	}
	
	// Define a mascara em padrão binario
	
	public String converteBinario() {
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
	
	public String converteDecimal() {
		
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
	
	public int calculaHosts() {
		
		hosts = 32 - cidr;
		this.hosts = (int) Math.pow(2, hosts);
		return this.hosts;
			
	}
	
	public int calculaSubRedes() {
		
		int bits = 0;
				
		if (classeIP.equals("Classe A")) {
			if (cidr == 8) {
				subRedes = 0;
			} else {
				bits = 8 - cidr;
				subRedes = (int) Math.pow(2, bits);
			}
		} else if (classeIP.equals("Classe B")) {
			if (cidr == 16) {
				subRedes = 0;
			} else {
				bits = 16 - cidr;
				subRedes = (int) Math.pow(2, bits);
			}
		} else if (classeIP.equals("Classe C")) {
			if (cidr == 24) {
				subRedes = 0;
			} else {
				bits = 24 - cidr;
				subRedes = (int) Math.pow(2, bits);
			}
			
		}
		
		return subRedes;
	}
	
	public String[] resultados() {
		String[] resultado = new String[6];
		
		resultado[0] = "IP: " + IP;
		resultado[1] = "Classe do IP: " + defineClasseIP();
		if (classeIP == "Classe D (Multicast)" || classeIP == "Classe E (Reservada)") {
			
			resultado[2] = "Mascara em binario: IP´s de classe D ou E não possuem máscara.";
			resultado[3] = "Mascara em decimal: IP´s de classe D ou E não possuem máscara.";
			resultado[4] = "IP's disponiveis por rede: Não aplicável";
			resultado[5] = "Numero de sub-redes: Não possui";
			
			
		} else {
			
			resultado[2] = "Mascara em binario: " + converteBinario();
			resultado[3] = "Mascara em decimal: " + converteDecimal();
			resultado[4] = "IP's disponiveis por rede: " + calculaHosts();
			resultado[5] = "Numero de sub-redes: " + calculaSubRedes();
			
		}
		
			
		return resultado;
	}
	
}
	 