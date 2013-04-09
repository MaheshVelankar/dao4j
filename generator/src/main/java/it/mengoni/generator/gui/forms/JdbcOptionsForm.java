package it.mengoni.generator.gui.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class JdbcOptionsForm extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JPanel m_New_Form = new JPanel();
	JLabel m_Test_Comp = new JLabel();
	JLabel m_Test_Comp1 = new JLabel();
	JLabel m_Test_Comp2 = new JLabel();
	JLabel m_Test_Comp3 = new JLabel();
	JTextField m_textDriverClass = new JTextField();
	JTextField m_textJdbcUrl = new JTextField();
	JTextField m_textUser = new JTextField();
	JTextField m_textPassword = new JTextField();
	JButton m_btnOk = new JButton();
	JButton m_btnCancel = new JButton();
	JButton m_btnTest = new JButton();

	/**
	 * Default constructor
	 */
	public JdbcOptionsForm() {
		initializePanel();
	}

	/**
	 * Adds fill components to empty cells in the first row and first column of
	 * the grid. This ensures that the grid spacing will be the same as shown in
	 * the designer.
	 *
	 * @param cols
	 *            an array of column indices in the first row where fill
	 *            components should be added.
	 * @param rows
	 *            an array of row indices in the first column where fill
	 *            components should be added.
	 */
	void addFillComponents(Container panel, int[] cols, int[] rows) {
		Dimension filler = new Dimension(10, 10);

		boolean filled_cell_11 = false;
		CellConstraints cc = new CellConstraints();
		if (cols.length > 0 && rows.length > 0) {
			if (cols[0] == 1 && rows[0] == 1) {
				/** add a rigid area */
				panel.add(Box.createRigidArea(filler), cc.xy(1, 1));
				filled_cell_11 = true;
			}
		}

		for (int index = 0; index < cols.length; index++) {
			if (cols[index] == 1 && filled_cell_11) {
				continue;
			}
			panel.add(Box.createRigidArea(filler), cc.xy(cols[index], 1));
		}

		for (int index = 0; index < rows.length; index++) {
			if (rows[index] == 1 && filled_cell_11) {
				continue;
			}
			panel.add(Box.createRigidArea(filler), cc.xy(1, rows[index]));
		}

	}

	/**
	 * Helper method to load an image file from the CLASSPATH
	 *
	 * @param imageName
	 *            the package and name of the file to load relative to the
	 *            CLASSPATH
	 * @return an ImageIcon instance with the specified image file
	 * @throws IllegalArgumentException
	 *             if the image resource cannot be loaded.
	 */
	public ImageIcon loadImage(String imageName) {
		try {
			ClassLoader classloader = getClass().getClassLoader();
			java.net.URL url = classloader.getResource(imageName);
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				return icon;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Unable to load image: " + imageName);
	}

	/**
	 * Method for recalculating the component orientation for right-to-left
	 * Locales.
	 *
	 * @param orientation
	 *            the component orientation to be applied
	 */
	public void applyComponentOrientation(ComponentOrientation orientation) {
		super.applyComponentOrientation(orientation);
	}

	public JPanel createNew_Form() {
		m_New_Form.setName("New Form");
		TitledBorder titledborder1 = new TitledBorder(null, "Connessione Jdbc", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(49, 106, 196));
		m_New_Form.setBorder(titledborder1);
		FormLayout formlayout1 = new FormLayout("FILL:4DLU:NONE,FILL:PREF:NONE,FILL:4DLU:NONE,FILL:PREF:GROW(1.0),FILL:4DLU:NONE", "CENTER:PREF:NONE,CENTER:PREF:NONE,CENTER:2DLU:NONE,CENTER:PREF:NONE,CENTER:2DLU:NONE,CENTER:PREF:NONE,CENTER:2DLU:NONE,CENTER:PREF:NONE,CENTER:4DLU:NONE,CENTER:PREF:NONE,CENTER:5DLU:NONE");
		CellConstraints cc = new CellConstraints();
		m_New_Form.setLayout(formlayout1);

		m_Test_Comp.setName("Test Comp");
		m_Test_Comp.setText("Driver Class:");
		m_New_Form.add(m_Test_Comp, cc.xy(2, 2));

		m_Test_Comp1.setName("Test Comp");
		m_Test_Comp1.setText("Jdbc Url:");
		m_New_Form.add(m_Test_Comp1, cc.xy(2, 4));

		m_Test_Comp2.setName("Test Comp");
		m_Test_Comp2.setText("User:");
		m_New_Form.add(m_Test_Comp2, cc.xy(2, 6));

		m_Test_Comp3.setName("Test Comp");
		m_Test_Comp3.setText("Password:");
		m_New_Form.add(m_Test_Comp3, cc.xy(2, 8));

		m_textDriverClass.setName("textDriverClass");
		m_New_Form.add(m_textDriverClass, cc.xy(4, 2));

		m_textJdbcUrl.setName("textJdbcUrl");
		m_textJdbcUrl.setToolTipText("");
		m_New_Form.add(m_textJdbcUrl, cc.xy(4, 4));

		m_textUser.setName("textUser");
		m_New_Form.add(m_textUser, cc.xy(4, 6));

		m_textPassword.setName("textPassword");
		m_New_Form.add(m_textPassword, cc.xy(4, 8));

		m_New_Form.add(createPanel(), cc.xy(4, 10));
		addFillComponents(m_New_Form, new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 });
		return m_New_Form;
	}

	public JPanel createPanel() {
		JPanel jpanel1 = new JPanel();
		FormLayout formlayout1 = new FormLayout("FILL:DEFAULT:GROW(1.0),FILL:DEFAULT:NONE,FILL:DEFAULT:GROW(1.0),FILL:DEFAULT:NONE,FILL:DEFAULT:GROW(1.0),FILL:DEFAULT:NONE,FILL:DEFAULT:GROW(1.0)", "CENTER:DEFAULT:NONE");
		CellConstraints cc = new CellConstraints();
		jpanel1.setLayout(formlayout1);

		m_btnOk.setActionCommand("Ok");
		m_btnOk.setName("btnOk");
		m_btnOk.setText("Ok");
		jpanel1.add(m_btnOk, cc.xy(2, 1));

		m_btnCancel.setActionCommand("Cancel");
		m_btnCancel.setName("btnCancel");
		m_btnCancel.setText("Cancel");
		jpanel1.add(m_btnCancel, cc.xy(4, 1));

		m_btnTest.setActionCommand("Test");
		m_btnTest.setName("btnTest");
		m_btnTest.setText("Test");
		jpanel1.add(m_btnTest, cc.xy(6, 1));

		addFillComponents(jpanel1, new int[] { 1, 3, 5, 7 }, new int[] { 1 });
		return jpanel1;
	}

	/**
	 * Initializer
	 */
	protected void initializePanel() {
		setLayout(new BorderLayout());
		add(createNew_Form(), BorderLayout.CENTER);
	}

}
