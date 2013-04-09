package it.mengoni.generator.gui;

import it.mengoni.generator.gui.forms.MainFrame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Gui {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			System.out.println("Error setting Nimbus LAF: " + e);
		}
		try {
			new MainFrame();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}