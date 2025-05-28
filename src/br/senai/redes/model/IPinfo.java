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

	private String[] listaSubRedes = new String[312];

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

		// variaveis que passar o valor para os parametros
		int binario = 128;
		int octetoDecimal = 0;

		// contadores para loop

		int i = 0;
		int indexContador = 0;

		// enquanto "i" for menor que a quantidade de octetos o loop não acaba

		while (i < 4) {

			// split do primeiro octeto para pegar um index de cada vez

			String[] octeto = splitBinario[i].split("");

			// enquanto o contador de index for menor que o tamanho do
			// primeiro octeto, então o loop continua

			while (indexContador < octeto.length) {

				// se o conteudo do index atual for igual a 1 então o
				// parametro octeto decimal recebe o valor do primeiro
				// bit em decimal [128, 64, 32, 16, 8, 4, 2, 1]

				if (octeto[indexContador].equals("1")) {

					octetoDecimal += binario;
					binario = binario / 2;
					indexContador++;

				} else {

					binario = binario / 2;
					indexContador++;

				}
			}

			i++;
			binario = 128;
			indexContador = 0;

			// conversão para String
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
				if (bits <= 0) {
					bits = bits * -1;
				}
				subRedes = (int) Math.pow(2, bits);
			}

		}

		return subRedes;
	}

	public String retornaIpSemOctetoMisto() {

		String ipComTresOctetos = "";

		int i;
		for (i = 0; i <= 2; i++) {
			ipComTresOctetos += octetos[i] + ".";

		}
		return ipComTresOctetos;
	}

	public int retornaOctetoMistoMascaraDecimal() {

		int octetoMistoMascaraDecimal;

		String[] octetosMascaraDecimal = mascaraDecimal.split("\\.");
		octetoMistoMascaraDecimal = Integer.valueOf(octetosMascaraDecimal[3]);

		return octetoMistoMascaraDecimal;
	}

	public int calculaSaltoSubRedes() {

		int salto;
		int octetoMisto = retornaOctetoMistoMascaraDecimal();

		salto = 256 - octetoMisto;

		return salto;
	}

	public String[] informaSubRedes() {

		String ipSemOctetoMisto = retornaIpSemOctetoMisto();

//		String[] listaSubRedes = new String[subRedes];

		int salto = calculaSaltoSubRedes();
		int contadorSalto = 0;

		int i = 0;
		while (i < subRedes) {

			this.listaSubRedes[i] = ("IP da sub-rede: " + ipSemOctetoMisto + contadorSalto + "#");

			if (cidr == 31) {

				this.listaSubRedes[i] += ("Range de IP's de sub-redes disponiveis para uso: (" + ipSemOctetoMisto
						+ (contadorSalto) + ") ~ (");
				contadorSalto += 1;
				this.listaSubRedes[i] += (ipSemOctetoMisto + contadorSalto + ")#");

				this.listaSubRedes[i] += ("IP de broadcast: " + ipSemOctetoMisto + (contadorSalto));

			} else {

				this.listaSubRedes[i] += ("Range de IP's de sub-redes disponiveis para uso: (" + ipSemOctetoMisto
						+ (contadorSalto + 1) + ") ~ (");
				contadorSalto += salto;
				this.listaSubRedes[i] += (ipSemOctetoMisto + (contadorSalto - 2) + ")#");

				this.listaSubRedes[i] += ("IP de broadcast: " + ipSemOctetoMisto + (contadorSalto - 1));
			}

			i++;

		}
		return listaSubRedes;
	}

//	public void resultadosConsole() {
//		System.out.println("IP: " + IP);
//		System.out.println("Classe: " + classeIP);
//		System.out.println("Máscara em binário: " + mascaraBinario);
//		System.out.println("Máscara em decimal: " + mascaraDecimal);
//		System.out.println("IP´s disponíveis por rede: " + hosts);
//		System.out.println("Número de sub-redes: " + subRedes);
//		System.out.println("IP sem octeto misto: " + retornaIpSemOctetoMisto());
//		System.out.println("Salto de uma sub-rede para outra: " + calculaSaltoSubRedes());
//		System.out.println("Octeto misto da mascara decimal: " + retornaOctetoMistoMascaraDecimal());
//		
//		if (cidr > 24 || cidr < 32) {
//			
//			informaSubRedes();
//			
//			int i=0;
//			while (i < subRedes) {
//				
//				int contadorInformacoesSubRede=0;
//				String[] splitMensagemIpsSubRedes = listaSubRedes[i].split("#");
//				System.out.println("==========================================================");
//				System.out.println("ID de sub-rede: " + (i + 1));
//				System.out.println("==========================================================");
//				while (contadorInformacoesSubRede < splitMensagemIpsSubRedes.length) {
//					System.out.println(splitMensagemIpsSubRedes[contadorInformacoesSubRede]);
//					contadorInformacoesSubRede++;
//				}
//				System.out.println("========================================================== \n");
//				
//				i++;
//			}
//			
//		}
//		
//	}

	public String[] resultados() {

		String[] resultado = new String[6];

		if (cidr >= 25 && cidr < 31) {

			resultado = new String[500];

		}

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

			if (cidr > 24 && cidr < 32) {

				informaSubRedes();

				int contadorSubRedes = 0;
				int i = 6;

				while (contadorSubRedes < subRedes) {
//					
					int contadorInformacoesSubRede = 0;
					String[] splitMensagemIpsSubRedes = listaSubRedes[contadorSubRedes].split("#");
//					
					resultado[i] = "=========================================================================";
					i++;

					resultado[i] = "ID de sub-rede: " + (contadorSubRedes + 1);
					i++;

					resultado[i] = "=========================================================================";
					i++;

					while (contadorInformacoesSubRede < splitMensagemIpsSubRedes.length) {
						resultado[i] = splitMensagemIpsSubRedes[contadorInformacoesSubRede];
						i++;
						contadorInformacoesSubRede++;
					}

					contadorSubRedes++;
					i++;
				}
				resultado[i] = "=========================================================================";
				i++;
			}
		}

		return resultado;
	}

}
