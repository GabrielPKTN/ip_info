package br.senai.redes.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import br.senai.redes.model.EnderecoIP;

public class MostrarResultados {
	
	private JLabel labelIP;
	private JTextField textIP;
	
	private JButton buttonIP;
	private JButton buttonLimparIP;
	
	private JList<String> listInfoIP;
	private JScrollPane scrollListaIP;
	
	private JLabel labelMensagemErro;
	
	private String nomeSistema;
	
	public void criaTela(String nomeSistema) {
		
		this.nomeSistema = nomeSistema;
		JFrame tela = new JFrame();
		
		tela.setSize(600, 400);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setResizable(false);
		tela.setLayout(null);
		tela.setTitle(this.nomeSistema);
		
		Container container = tela.getContentPane();
		
		labelIP = new JLabel();
		labelIP.setText("Insira o IP desejado para pegar informações: ");
		labelIP.setBounds(95, 30, 300, 15); //Ocupa 350 da width, na altura 100 até 110.
		
		textIP = new JTextField();
		textIP.setBounds(95, 50, 400, 30);
		
		buttonIP = new JButton();
		buttonIP.setBounds(95, 85, 195, 30);
		buttonIP.setText("Calcular");
		
		buttonLimparIP = new JButton();
		buttonLimparIP.setBounds(300, 85, 195, 30);
		buttonLimparIP.setText("Limpar");
		
		listInfoIP = new JList<String>();
//		listInfoIP.setBounds(95, 120, 400, 200);
		
		scrollListaIP = new JScrollPane(listInfoIP);
		scrollListaIP.setBounds(10, 120, 565, 200);
		
		
		Font estiloMensagemErro = new Font(null, Font.BOLD, 32);
		
		labelMensagemErro = new JLabel();
		labelMensagemErro.setText("IP inválido.");
		labelMensagemErro.setFont(estiloMensagemErro);
		labelMensagemErro.setForeground(Color.RED);
		labelMensagemErro.setVisible(false);
		labelMensagemErro.setBounds(210, 140, 400, 100);
		
		
		container.add(labelIP);
		container.add(textIP);
		container.add(buttonIP);
		container.add(buttonLimparIP);
		container.add(scrollListaIP);
		container.add(labelMensagemErro);
		
		buttonIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					labelMensagemErro.setVisible(false);
					listInfoIP.setVisible(true);
					scrollListaIP.setVisible(true);
					
					String ipTextField = textIP.getText();
					
					EnderecoIP ip = new EnderecoIP();
					ip.setIP(ipTextField);
					ip.separaIP();					
					
//					String[] IPinfoResult = ip.resultados();
//					listInfoIP.setListData(IPinfoResult);
					
					/*
					 * Bora la, para não se perder..
					 * 
					 * List<String> IPinfoResult = ip.resultados();
					 * 
					 * Como alterei o tipo de retorno na classe pricipal
					 * no método resultados(), aqui não vai ser diferente
					 * então aqui tenho uma lista que recebe o retorno de
					 * resultados, que é uma lista (list<String>).
					 * 
					 * Basicamente estou "Copiando" a lista resultados do
					 * método resultados() da classe IPinfo.
					 * 
					 * DefaultListModel<String> model = new DefaultListModel<>();
					 * 
					 * Primeiro o que é DefaultListModel?
					 *  
					 * É uma implementação padrão de ListModel usada com JList
					 * no Swing.
					 * 
					 * O que consigo fazer com isso?
					 * 
					 * Controlar os dados exibidos pelo JList de forma flexível,
					 * ou seja:
					 * 
					 * Adicionar itens dinamicamente | addElement()
					 * Remover itens                 | removeElementAt()
					 * Atualizar elementos           | setElementAt()
					 * Consultar conteúdo            | getElementAt(), getSize(), etc.
					 * 
					 * Por que devo utilizar DefaultListModel?
					 * 
					 * Por padrão, o JList usa um ListModel para saber quais itens 
					 * exibir. Se você quiser modificar esses itens em tempo de execução 
					 * (por exemplo, após um clique no botão),  precisa usar um modelo 
					 * que permita edição — como o DefaultListModel.
					 * 
					 * */
					
					List<String> IPinfoResult = ip.resultados();
					
					DefaultListModel<String> model = new DefaultListModel<>();
					
					// bloco recebe String com informações das sub redes
					for (String bloco : IPinfoResult) {
						// quebra cada string em várias linhas que se separam pelos \n
						String[] linhas = bloco.split("\n");
						
						/*
						 * Dentro desse for-each sabemos que linhas recebeu todas as
						 * linhas separadas pelos \n, agora pegamos cada linha dessa 
						 * e adicionamos ao model (JList) uma linha de cada vez.
						 */
						for (String linha : linhas) {
							model.addElement(linha);
						}
					}
					
					// Aplicamos o modelo no JList
					listInfoIP.setModel(model);
					
					
				} catch (ArrayIndexOutOfBoundsException exception) {
					
					String[] nullResultado = new String[0];
					listInfoIP.setListData(nullResultado);
					
					textIP.setText(null);
					
					listInfoIP.setVisible(false);
					scrollListaIP.setVisible(false);
					labelMensagemErro.setVisible(true);
					
				} catch (Exception e2) {
					
					// O printStackTrace vai printar todo tipo de código que for diferente
					// do primeiro erro do catch.
					e2.printStackTrace();
					
					String[] nullResultado = new String[0];
					listInfoIP.setListData(nullResultado);
					
					textIP.setText(null);
					
					listInfoIP.setVisible(false);
					scrollListaIP.setVisible(false);
					labelMensagemErro.setVisible(true);
				}
			}
		});
		
		buttonLimparIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelMensagemErro.setVisible(false);
				listInfoIP.setVisible(true);
				scrollListaIP.setVisible(true);
				
				String[] nullResultado = new String[0];
				listInfoIP.setListData(nullResultado);
				
				textIP.setText(null);
				
				textIP.requestFocus();
				
			}
		});
		
		tela.setVisible(true);
		
	}

}