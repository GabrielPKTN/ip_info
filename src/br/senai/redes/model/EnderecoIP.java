package br.senai.redes.model;

import java.util.List;
import java.lang.Math;
import java.util.ArrayList;

public class EnderecoIP {
	private String IP;
	private int cidr;
	private int hosts;
	private int subRedes;
	private String classeIP;
	private String mascaraDecimal = "";
	private String mascaraBinario = "";
	private String[] octetos;

	private List<String> listaSubRedes = new ArrayList<>();

	public void setIP(String IP) {
		this.IP = IP;
	}

	// Separando octetos e CIDR do IP

	public void separaIP() {
		
		int cidr;

		String[] splitIP = IP.split("/");
		cidr = Integer.valueOf(splitIP[1]);
		if (cidr < 41 && cidr > -1) {
			this.cidr = cidr;
		}

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
		while (contador < cidr && contador < 32) {

			mascaraBinario += "1";
			octeto++;
			contador++;

			if (octeto == 8) {
				if (mascaraBinario.length() < 35) {
					mascaraBinario += ".";
				}
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

	public List<String> informaSubRedes() {

		listaSubRedes.clear();

		String ipSemOctetoMisto = retornaIpSemOctetoMisto();
		int salto = calculaSaltoSubRedes();
		int contadorSalto = 0;

		//

		for (int i = 0; i < subRedes; i++) {

			/*
			 * O StringBuilder é uma classe que permite criar e manipular cadeias de
			 * caracteres de forma eficiente, principalmente quando se trata de operações
			 * que envolvem múltiplas modificações.
			 *
			 * Em vez de criar uma nova string a cada alteração, o StringBuilder permite
			 * modificar a string original diretamente.
			 *
			 * Isso vai resolver o problema que eu estava tendo de criar listas com tamanhos
			 * enormes sem nescessidade.
			 *
			 */

			StringBuilder blocoSubRedes = new StringBuilder();

			// IP da sub rede:
			String ipSubRede = ipSemOctetoMisto + contadorSalto;

			// Declarando mútiplas variaveis ao mesmo tempo.
			String ipInicial, ipFinal, ipBroadcast;

			/*
			 * Mesma coisa que estava fazendo antes mas agora de forma mais legivel e limpa.
			 * 
			 */

			if (cidr == 31) {
				ipInicial = ipSemOctetoMisto + contadorSalto;
				contadorSalto += 1;
				ipFinal = ipSemOctetoMisto + contadorSalto;
				ipBroadcast = ipFinal;
			} else {
				ipInicial = ipSemOctetoMisto + (contadorSalto + 1);
				contadorSalto += salto;
				ipFinal = ipSemOctetoMisto + (contadorSalto - 2);
				ipBroadcast = ipSemOctetoMisto + (contadorSalto - 1);
			}

			blocoSubRedes.append("=========================================================================\n");
			blocoSubRedes.append("ID de sub-rede: ").append(i + 1).append("\n");
			blocoSubRedes.append("=========================================================================\n");
			blocoSubRedes.append("IP da sub-rede: ").append(ipSubRede).append("\n");
			blocoSubRedes.append("Range de IP's de sub-redes disponiveis para uso: (");
			blocoSubRedes.append(ipInicial).append(") ~ (").append(ipFinal).append(")\n");
			blocoSubRedes.append("IP de broadcast: ").append(ipBroadcast).append("\n");
			blocoSubRedes.append("=========================================================================\n");

			/*
			 * Adcionando todo bloco de texto acima dentro do array
			 * 
			 */

			listaSubRedes.add(blocoSubRedes.toString());
		}

		return listaSubRedes;

//		String ipSemOctetoMisto = retornaIpSemOctetoMisto();
//
//		int salto = calculaSaltoSubRedes();
//		int contadorSalto = 0;
//
//		int i = 0;
//		while (i < subRedes) {
//
//			this.listaSubRedes[i] = ("IP da sub-rede: " + ipSemOctetoMisto + contadorSalto + "#");
//
//			if (cidr == 31) {
//
//				this.listaSubRedes[i] += ("Range de IP's de sub-redes disponiveis para uso: (" + ipSemOctetoMisto
//						+ (contadorSalto) + ") ~ (");
//				contadorSalto += 1;
//				this.listaSubRedes[i] += (ipSemOctetoMisto + contadorSalto + ")#");
//
//				this.listaSubRedes[i] += ("IP de broadcast: " + ipSemOctetoMisto + (contadorSalto));
//
//			} else {
//
//				this.listaSubRedes[i] += ("Range de IP's de sub-redes disponiveis para uso: (" + ipSemOctetoMisto
//						+ (contadorSalto + 1) + ") ~ (");
//				contadorSalto += salto;
//				this.listaSubRedes[i] += (ipSemOctetoMisto + (contadorSalto - 2) + ")#");
//
//				this.listaSubRedes[i] += ("IP de broadcast: " + ipSemOctetoMisto + (contadorSalto - 1));
//			}
//
//			i++;

	}
	
	// Método para teste
	public void resultadosConsole() {
		System.out.println("IP: " + IP);
		System.out.println("Classe: " + classeIP);
		System.out.println("Máscara em binário: " + mascaraBinario);
		System.out.println("Máscara em decimal: " + mascaraDecimal);
		System.out.println("IP´s disponíveis por rede: " + hosts);
		System.out.println("Número de sub-redes: " + subRedes);
		System.out.println("IP sem octeto misto: " + retornaIpSemOctetoMisto());
		System.out.println("Salto de uma sub-rede para outra: " + calculaSaltoSubRedes());
		System.out.println("Octeto misto da mascara decimal: " + retornaOctetoMistoMascaraDecimal());

		if (cidr > 24 && cidr < 32) {

			informaSubRedes();

			/*
			 * "for-each" loop uma forma simplificada e mais legível de percorrer todos
			 * elementos de uma coleção/array, como List<String> listaSubRedes ou um array.
			 * 
			 * O que ele está fazendo?
			 * 
			 * Ele está percorrendo todos os elementos da lista listaSubRedes e a cada volta
			 * do laço ele pega um elemento da lista (uma string nesse caso), atribui este
			 * valor a variável subRede depois executa o corpo do for com essa variável.
			 * 
			 * De forma prática é a mesma coisa que:
			 * 
			 * for (int i = 0; i < listaSubRedes.size(); i++) { String subRede =
			 * listaSubRedes.get(i); System.out.println(subRede); }
			 * 
			 * Diferente dos arrays, nas listas ao invés de usarmos listaSubRedes.length
			 * usamos listaSubRedes.size() isso ocorre por conta do tipo de estrutura que
			 * estou usando, como estou utilizando uma lista ele vai aceitar apenas .size()
			 * a mesma coisa vale para array e o listaSubRedes.get(i).
			 * 
			 * Resumindo:
			 * 
			 * listaSubRedes.size() | não funciona em arrays listaSubRedes.get(i) | não
			 * funciona em arrays
			 * 
			 * listaSubRedes.length | não funciona em listas listaSubRedes[i] | não funciona
			 * em listas
			 * 
			 * 
			 * Como sei a diferença entre a Array e Lista?
			 * 
			 * Primeiro ponto são as características:
			 * 
			 * Arrays (String[], double[], int[], etc.) 1. Possuem um tamanho fixo, definido
			 * no momento da criação do mesmo.
			 * 
			 * 2. Acessa elementos com [i]
			 * 
			 * 3. A forma de adicionar elementos dentro do array:
			 * 
			 * String[] nomes = new String[3];
			 * 
			 * nomes[0] = "Ana"; nomes[1] = "Bruno"; nomes[2] = "Carlos";
			 * 
			 * System.out.println(nomes.length); == 3 System.out.println(nomes[1]); == Bruno
			 * 
			 * Listas (List<String>, ArrayList<Integer>, etc.) 1. Possuem um tamanho
			 * dinâmico, isso significa que elas podem crescer ou diminuir.
			 * 
			 * 2. Acessa elementos com get(i)
			 * 
			 * 3. A forma de adicionar elementos dentro da lista:
			 * 
			 * import java.util.List; import java.util.ArrayList;
			 * 
			 * List<String> nomes = new ArrayList<>();
			 * 
			 * nomes.add("Ana"); nomes.add("Bruno"); nomes.add("Carlos");
			 * 
			 * System.out.println(nomes.size()); == 3 System.out.println(nomes.get(1)); ==
			 * Bruno
			 * 
			 * 
			 */

			for (String subRede : listaSubRedes) {
				System.out.println(subRede);
			}

//			int i=0;
//			while (i < subRedes) {
//				
//				int contadorInformacoesSubRede=0;
//				String[] splitMensagemIpsSubRedes = listaSubRedes[i].split("#");
//				System.out.println("==========================================================");
//				System.out.println("ID de sub-rede: " + (i + 1));
//				System.out.println("==========================================================");
//				
//				
//				while (contadorInformacoesSubRede < splitMensagemIpsSubRedes.length) {
//					System.out.println(splitMensagemIpsSubRedes[contadorInformacoesSubRede]);
//					contadorInformacoesSubRede++;
//				}
//				System.out.println("========================================================== \n");
//				
//				i++;

		}

	}

	public List<String> resultados() {

		List<String> resultado = new ArrayList<>();

		resultado.add("IP: " + IP);
		resultado.add("Classe do IP: " + defineClasseIP());
		
		if (classeIP == "Classe D (Multicast)" || classeIP == "Classe E (Reservada)") {
	        resultado.add("Máscara em binário: IPs de classe D ou E não possuem máscara.");
	        resultado.add("Máscara em decimal: IPs de classe D ou E não possuem máscara.");
	        resultado.add("IPs disponíveis por sub-rede: Não aplicável.");
	        resultado.add("Número de sub-redes: Não possui.");
		} else {
			 resultado.add("Máscara em binário: " + converteBinario());
		     resultado.add("Máscara em decimal: " + converteDecimal());
		     resultado.add("IPs disponíveis por rede: " + calculaHosts());
		     resultado.add("Número de sub-redes: " + calculaSubRedes());
		     
		     
		     // Caso o CIDR for de 25 a 31
		     
		     if (cidr > 24 && cidr < 31) {
		    	 
		    	 /*
		    	  * Aqui estamos falando que a lista subRedesFormatadas
		    	  * recebe todo conteudo retornado de informaSubRedes
		    	  * */
		    	 
		    	 List<String> subRedesFormatadas = informaSubRedes();
		    	 resultado.addAll(subRedesFormatadas);
		     }
		     
		        

		}
		
		
		
		
		
//		String[] resultado = new String[6];
//		
//		if (cidr >= 25 && cidr < 32) {
//			
//			resultado = new String[6 + subRedes];
//
//		}
//
//		resultado[0] = "IP: " + IP;
//		resultado[1] = "Classe do IP: " + defineClasseIP();
//		if (classeIP == "Classe D (Multicast)" || classeIP == "Classe E (Reservada)") {
//
//			resultado[2] = "Máscara em binario: IP´s de classe D ou E não possuem máscara.";
//			resultado[3] = "Máscara em decimal: IP´s de classe D ou E não possuem máscara.";
//			resultado[4] = "IP's disponiveis por sub-rede: Não aplicável";
//			resultado[5] = "Numero de sub-redes: Não possui";
//
//		} else {
//
//			resultado[2] = "Mascara em binario: " + converteBinario();
//			resultado[3] = "Mascara em decimal: " + converteDecimal();
//			resultado[4] = "IP's disponiveis por sub-rede: " + calculaHosts();
//			resultado[5] = "Numero de sub-redes: " + calculaSubRedes();
//
//			if (cidr > 24 && cidr < 32) {
//
//				informaSubRedes();
//
//				int contadorSubRedes = 0;
//				int i = 6;
//
//				while (contadorSubRedes < subRedes) {
//					
//					int contadorInformacoesSubRede = 0;
//					String[] splitMensagemIpsSubRedes = listaSubRedes[contadorSubRedes].split("#");
//					
//					resultado[i] = "=========================================================================";
//					i++;
//
//					resultado[i] = "ID de sub-rede: " + (contadorSubRedes + 1);
//					i++;
//
//					resultado[i] = "=========================================================================";
//					i++;
//
//					while (contadorInformacoesSubRede < splitMensagemIpsSubRedes.length) {
//						resultado[i] = splitMensagemIpsSubRedes[contadorInformacoesSubRede];
//						i++;
//						contadorInformacoesSubRede++;
//					}
//
//					contadorSubRedes++;
//					i++;
//				}
//				resultado[i] = "=========================================================================";
//				i++;
//			}
//		}

		return resultado;
	}

}
