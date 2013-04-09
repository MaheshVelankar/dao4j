package it.mengoni.generator.gui.forms;

import it.mengoni.generator.CodeGenConfig;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class CodeGenOptionsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private CodeGenOptionsForm form;
	private CodeGenConfig codeGenConfig;
	private boolean ok = false;

	public CodeGenOptionsDialog(Frame parent, CodeGenConfig codeGenConfig, boolean modal) throws HeadlessException {
		super(parent, "Code Generation Options", modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		this.codeGenConfig = codeGenConfig;
		form = new CodeGenOptionsForm();
		getContentPane().add(form, BorderLayout.CENTER);
		setSize(500, 240);
		form.m_textRootOut.setText(codeGenConfig.getRootOut());
		form.m_textPackage.setText(codeGenConfig.getBasePackage());
		form.m_textCatalog.setText(codeGenConfig.getCatalogName());
		form.m_textSchema.setText(codeGenConfig.getSchemaName());
		form.m_btnCancel.addActionListener(closeListener);
		form.m_btnOk.addActionListener(saveListener);
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
			CodeGenOptionsDialog.this.dispose();
			ok=false;
		}
	};

	private ActionListener saveListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			codeGenConfig.setRootOut(form.m_textRootOut.getText());
			codeGenConfig.setBasePackage(form.m_textPackage.getText());
			codeGenConfig.setCatalogName(form.m_textCatalog.getText());
			codeGenConfig.setSchemaName(form.m_textSchema.getText());
			ok = true;
			CodeGenOptionsDialog.this.dispose();
		}
	};

}
