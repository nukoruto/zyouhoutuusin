package gui;

/**************************************************
 *	Main.java
 *	------------------------------
 *
 *	Version: 1.00
 *	last change: Feb 2005
 *
 *	written by Akira Nakano
 *	email: nakano@kurume-nct.ac.jp
 *	copyrighted (c) by Nakano Lab.
 *
 **************************************************/

//�����̃p�b�P�[�W�̗��p
//import javax.swing.*;
import javax.swing.JFrame;


//import java.applet.*;
import java.applet.Applet;

//import java.awt.*;
import java.awt.BorderLayout;

public class Main extends Applet{
	protected static GUIPanel guiPanel;

	/**
	 * ���C�����\�b�h
	 */
	public static void main(String[] args){
		JFrame frame = new JFrame("SampleAppletcation");
		frame.setSize(500, 300);
		frame.setVisible(true);
	
		guiPanel = new GUIPanel();
		frame.getContentPane().add(guiPanel);

		//�p�l���̍Ĕz�u
		guiPanel.revalidate();
	
		System.out.println("\n/_/_/_/_/_/_/_/_/_/_/_/_/");
		System.out.println("GUI(Ver 1.00)");
		System.out.println("\n/_/_/_/_/_/_/_/_/_/_/_/_/");
	}

	/**
	 * �A�v���b�g�p�̏���
	 */
	public void init(){
		guiPanel = new GUIPanel();
		this.setLayout(new BorderLayout());
		this.add(guiPanel, BorderLayout.CENTER);

		System.out.println("Applet initialize.");
	}
}
