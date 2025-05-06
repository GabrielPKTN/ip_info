package br.senai.redes.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import br.senai.redes.model.IPinfo;

public class MostrarResultados {
	
	private JLabel labelIP;
	private JTextField textIP;
	
	private JButton buttonIP;
	private JButton buttonLimparIP;
	
	private JList<String> listInfoIP;
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
		listInfoIP.setBounds(95, 120, 400, 200);
		
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
		container.add(listInfoIP);
		container.add(labelMensagemErro);
		
		buttonIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					labelMensagemErro.setVisible(false);
					listInfoIP.setVisible(true);
					
					String ipTextField = textIP.getText();
					
					IPinfo ip = new IPinfo();
					
					ip.setIP(ipTextField);
					ip.splitIP();
					
					String[] IPinfoResult = ip.resultados();
					listInfoIP.setListData(IPinfoResult);
					
					
				} catch (ArrayIndexOutOfBoundsException exception) {
					
					String[] nullResultado = new String[0];
					listInfoIP.setListData(nullResultado);
					
					textIP.setText(null);
					
					listInfoIP.setVisible(false);
					labelMensagemErro.setVisible(true);
					
				}
			}
		});
		
		buttonLimparIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelMensagemErro.setVisible(false);
				listInfoIP.setVisible(true);
				
				String[] nullResultado = new String[0];
				listInfoIP.setListData(nullResultado);
				
				textIP.setText(null);
				
				textIP.requestFocus();
				
			}
		});
		
		tela.setVisible(true);
		
	}

}