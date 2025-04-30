package br.senai.redes.gui;

import java.awt.Container;

import javax.swing.*;

public class MostrarResultados {
	
	private JLabel labelIP;
	
	private JTextField textIP;
	
	private JList listInfoIP;
	
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
		labelIP.setBounds(150, 60, 300, 15); //Ocupa 350 da width, na altura 100 até 110.
		
		textIP = new JTextField();
		textIP.setBounds(140, 80, 300, 30);
		
		listInfoIP = new JList();
		listInfoIP.setBounds(140, 120, 300, 200);
		
		
		container.add(labelIP);
		container.add(textIP);
		container.add(listInfoIP);
		
		tela.setVisible(true);
		
	}

}
