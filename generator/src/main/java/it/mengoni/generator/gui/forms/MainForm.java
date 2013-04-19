package it.mengoni.generator.gui.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainForm extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton m_btnLoad = new JButton();
	JButton m_btnSave = new JButton();
	JButton m_btnConnection = new JButton();
	JButton m_btnCodeOptions = new JButton();
	JButton m_btnReadMetaData = new JButton();
	JButton m_btnGenerateCode = new JButton();
	JButton m_btnSaveSchema = new JButton();
	JButton m_btnLoadSchema = new JButton();
	JPanel m_gridView = new JPanel();

	/**
	 * Default constructor
	 */
	public MainForm() {
		initializePanel();
	}

	/**
	 * Main method for panel
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.setLocation(100, 100);
		frame.getContentPane().add(new MainForm());
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
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
		// Not yet implemented...
		// I18NUtils.applyComponentOrientation(this, orientation);
		super.applyComponentOrientation(orientation);
	}

	public JPanel createPanel() {
		JPanel jpanel1 = new JPanel();
		FormLayout formlayout1 = new FormLayout(
				"FILL:4DLU:NONE,FILL:DEFAULT:GROW(1.0),FILL:4DLU:NONE",
				"CENTER:2DLU:NONE,CENTER:DEFAULT:NONE,CENTER:2DLU:NONE,CENTER:DEFAULT:GROW(1.0),CENTER:2DLU:NONE,CENTER:DEFAULT:NONE");
		CellConstraints cc = new CellConstraints();
		jpanel1.setLayout(formlayout1);

		jpanel1.add(createPanel1(), cc.xy(2, 2));
		jpanel1.add(creategridView(), new CellConstraints(2, 4, 1, 1,
				CellConstraints.FILL, CellConstraints.FILL));
		addFillComponents(jpanel1, new int[] { 1, 2, 3 }, new int[] { 1, 2, 3,
				4, 5, 6 });
		return jpanel1;
	}

	public JPanel createPanel1() {
		JPanel jpanel1 = new JPanel();
		FormLayout formlayout1 = new FormLayout(
				"FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE",
				"CENTER:DEFAULT:NONE");
		CellConstraints cc = new CellConstraints();
		jpanel1.setLayout(formlayout1);

		m_btnLoad.setActionCommand("load");
		m_btnLoad.setName("btnLoad");
		m_btnLoad.setText("load");
		jpanel1.add(m_btnLoad, cc.xy(1, 1));

		m_btnSave.setActionCommand("save");
		m_btnSave.setName("btnSave");
		m_btnSave.setText("save");
		jpanel1.add(m_btnSave, cc.xy(3, 1));

		m_btnConnection.setActionCommand("Connection");
		m_btnConnection.setName("btnConnection");
		m_btnConnection.setText("Connection");
		jpanel1.add(m_btnConnection, cc.xy(5, 1));

		m_btnCodeOptions.setActionCommand("Code Generation");
		m_btnCodeOptions.setName("btnCodeOptions");
		m_btnCodeOptions.setText("Code Options");
		jpanel1.add(m_btnCodeOptions, cc.xy(7, 1));

		m_btnReadMetaData.setActionCommand("read from DB");
		m_btnReadMetaData.setName("btnReadMetaData");
		m_btnReadMetaData.setText("read from DB");
		jpanel1.add(m_btnReadMetaData, cc.xy(9, 1));

		m_btnGenerateCode.setActionCommand("Code Generation");
		m_btnGenerateCode.setName("btnGenerateCode");
		m_btnGenerateCode.setText("Generate");
		jpanel1.add(m_btnGenerateCode, cc.xy(11, 1));

		m_btnSaveSchema.setActionCommand("Save schema");
		m_btnSaveSchema.setName("btnSaveSchema");
		m_btnSaveSchema.setText("Save schema");
		jpanel1.add(m_btnSaveSchema, cc.xy(13, 1));

		m_btnLoadSchema.setActionCommand("Load Schema");
		m_btnLoadSchema.setName("btnSaveSchema");
		m_btnLoadSchema.setText("Load Schema");
		jpanel1.add(m_btnLoadSchema, cc.xy(15, 1));

		addFillComponents(jpanel1, new int[] { 2, 4, 6, 8, 10, 12, 14 },
				new int[0]);
		return jpanel1;
	}

	public JPanel creategridView() {
		m_gridView.setName("gridView");
		FormLayout formlayout1 = new FormLayout("FILL:DEFAULT:NONE",
				"CENTER:DEFAULT:NONE");
		m_gridView.setLayout(formlayout1);

		addFillComponents(m_gridView, new int[] { 1 }, new int[] { 1 });
		return m_gridView;
	}

	/**
	 * Initializer
	 */
	protected void initializePanel() {
		setLayout(new BorderLayout());
		add(createPanel(), BorderLayout.CENTER);
	}

}
