package it.mengoni.generator.gui.forms;

import it.mengoni.generator.JdbcConfig;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class JdbcOptionsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JdbcOptionsForm form;
	private JdbcConfig jdbcConfig;
	private boolean ok = false;

	public JdbcOptionsDialog(Frame parent, JdbcConfig jdbcConfig, boolean modal) throws HeadlessException {
		super(parent, "Jdbc Connection", modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		this.jdbcConfig = jdbcConfig;
		form = new JdbcOptionsForm();
		getContentPane().add(form, BorderLayout.CENTER);
		setSize(500, 240);
		form.m_textDriverClass.setText(jdbcConfig.getDriverClass());
		form.m_textJdbcUrl.setText(jdbcConfig.getJdbcUrl());
		form.m_textPassword.setText(jdbcConfig.getPassword());
		form.m_textUser.setText(jdbcConfig.getUser());
		form.m_btnCancel.addActionListener(closeListener);
		form.m_btnOk.addActionListener(saveListener);
		form.m_btnTest.addActionListener(testListener);
		setVisible(true);
	}

	public boolean isOk() {
		return ok;
	}

	@SuppressWarnings("serial")
	protected JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
				ok=false;
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		return rootPane;
	}

	private ActionListener closeListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JdbcOptionsDialog.this.dispose();
			ok=false;
		}
	};

	private ActionListener saveListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			jdbcConfig.setDriverClass(form.m_textDriverClass.getText());
			jdbcConfig.setJdbcUrl(form.m_textJdbcUrl.getText());
			jdbcConfig.setPassword(form.m_textPassword.getText());
			jdbcConfig.setUser(form.m_textUser.getText());
			ok = true;
			JdbcOptionsDialog.this.dispose();
		}
	};

	private ActionListener testListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				Class.forName(form.m_textDriverClass.getText());
			}catch( Exception e ) {
				JOptionPane.showMessageDialog(JdbcOptionsDialog.this
						, "Classe non trovata: "+form.m_textDriverClass.getText(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try{
				Connection con = DriverManager.getConnection(form.m_textJdbcUrl.getText(), form.m_textUser.getText(), form.m_textPassword.getText());
				@SuppressWarnings("unused")
				java.sql.Statement stmt = con.createStatement();
				JOptionPane.showMessageDialog(JdbcOptionsDialog.this,"Test eseguito con successo", "Dao Gen", JOptionPane.INFORMATION_MESSAGE);
			}
			catch( Exception e ) {
				JOptionPane.showMessageDialog(JdbcOptionsDialog.this
						, getMessage(e), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		private String getMessage(Exception e) {
			return e.getMessage()==null?e.getClass().getName():e.getMessage();
		}
	};

}
