package it.mengoni.generator.gui.forms;

import it.mengoni.generator.CodeGenConfig;
import it.mengoni.generator.Generator;
import it.mengoni.generator.Helper;
import it.mengoni.generator.Project;
import it.mengoni.generator.SchemaReader;
import it.mengoni.generator.gui.DbNode;
import it.mengoni.jdbc.model.Catalog;
import it.mengoni.jdbc.model.Root;
import it.mengoni.jdbc.model.Schema;
import it.mengoni.persistence.dao.Dao.DatabaseProductType;
import it.mengoni.persistence.exception.SystemError;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MainFrame extends JFrame {

	private final static Logger logger = LoggerFactory.getLogger(MainFrame.class);

	private static final long serialVersionUID = 1L;
	private MainForm mainForm;
	private Project _project;
	private DbTree schemaTree;
	private JTable tableNameSubst;
	private DataSource dataSource;
	private Root dbRoot;

	private void createDataSource() {
		try {
			ComboPooledDataSource ds = new ComboPooledDataSource();
			ds.setJdbcUrl(_project.getJdbcConfig().getJdbcUrl());
			ds.setDriverClass(_project.getJdbcConfig().getDriverClass());
			ds.setPassword(_project.getJdbcConfig().getPassword());
			ds.setUser(_project.getJdbcConfig().getUser());
			dataSource = ds;
		} catch (PropertyVetoException e) {
			throw new SystemError(e);
		}
	}

	/*
	 * t's very easy, JTable have scroolRectToVisible method to :)
	 * If you want, you can try something like this to make scrollpane
	 * go to to the bottom if new record added :
	jTable1.getSelectionModel().setSelectionInterval(i, i);
	jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(i, 0, true)));

	 */

	public MainFrame() throws HeadlessException, SQLException, IOException, PropertyVetoException {
		super("Dao Gen");
		_project = new Project();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1000, 800);
		setLocation(100, 100);
		mainForm = new MainForm();
		schemaTree = new DbTree();
		tableNameSubst = new JTable();
		JScrollPane scrollPaneL = new JScrollPane(schemaTree);
		JScrollPane scrollPaneR = new JScrollPane(tableNameSubst);
		scrollPaneL.setMinimumSize(new Dimension(150, 150));
		scrollPaneR.setMinimumSize(new Dimension(150, 150));
		scrollPaneL.setPreferredSize(new Dimension(250, 200));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneL, scrollPaneR);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(600);
		mainForm.m_gridView.setLayout(new BorderLayout());
		mainForm.m_gridView.add(splitPane, BorderLayout.CENTER);

		dbRoot = new Root();
		DbNode rootNode = new DbNode(dbRoot);
		TreeModel newModel = new DefaultTreeModel(rootNode, true);
		schemaTree.setModel(newModel);
		List<RowBean> data = new ArrayList<RowBean>();
		TableModel dataModel = new NameSubstTableModel(data);
		tableNameSubst.setModel(dataModel);
		TableCellEditor anEditor = new NameSubstTableCellEditor();
		tableNameSubst.setCellEditor(anEditor);

		DbItemCellRenderer renderer = new DbItemCellRenderer();
		schemaTree.setCellRenderer(renderer);

		DbItemCellEditor editor = new DbItemCellEditor();
		schemaTree.setCellEditor(editor);
		schemaTree.setEditable(true);

		getContentPane().add(mainForm);
		mainForm.m_btnLoad.setAction(new OpenFileAction(this));
		mainForm.m_btnSave.setAction(new SaveFileAction(this));

		mainForm.m_btnConnection.setAction(new SetConnectionAction(this));
		mainForm.m_btnCodeOptions.setAction(new SetCodeGenerationAction(this));
		mainForm.m_btnGenerateCode.setAction(new RunCodeGenerationAction(this));
		mainForm.m_btnReadMetaData.setAction(new ReadMetadataAction(this));
		mainForm.m_btnSaveSchema.setAction(new SaveSchemaAction(this));
		mainForm.m_btnLoadSchema.setAction(new OpenSchemaAction(this));

		setVisible(true);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent evt) {
				if (JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(MainFrame.this, "Quit?", "Dao Gen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)){
					MainFrame.this.dispose();
					System.exit(0);
				}
			}
		});
	}


	private Root _readMetadata()  {
		try {
			SchemaReader sr = new SchemaReader(_project.getCodeGenConfig());
			Helper.setConfig(_project.getCodeGenConfig());
			return sr.readSchema(getConnection());
		} catch (SQLException e) {
			logger.error("Error" ,e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			logger.error("Error" ,e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private Connection getConnection() {
		if (dataSource==null)
			createDataSource();
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new SystemError(e);
		}
	}

	private void refreshModel(Root dbRoot)  {
		this.dbRoot = dbRoot;
		_project.setModel(dbRoot);
		DbNode rootNode = new DbNode(dbRoot);
		TreeModel newModel = new DefaultTreeModel(rootNode, true);
		schemaTree.setModel(newModel);
		List<RowBean> data = new ArrayList<RowBean>();
		if (_project.getCodeGenConfig().getNameSubstMap()!=null)
			for (String key:_project.getCodeGenConfig().getNameSubstMap().keySet()){
				data.add(new RowBean(key, _project.getCodeGenConfig().getNameSubstMap().get(key)));
			}
		TableModel dataModel = new NameSubstTableModel(data);
		tableNameSubst.setModel(dataModel);
		TableCellEditor anEditor = new NameSubstTableCellEditor();
		tableNameSubst.setCellEditor(anEditor);
	}



	public class SetConnectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFrame frame;

		SetConnectionAction(JFrame frame) {
			super("Connection...");
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			JdbcOptionsDialog x = new JdbcOptionsDialog(frame, _project.getJdbcConfig(), true);
			if (x.isOk()){
				createDataSource();
			}
		}
	};

	public class SetCodeGenerationAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFrame frame;

		SetCodeGenerationAction(JFrame frame) {
			super("Code Options...");
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			@SuppressWarnings("unused")
			CodeGenOptionsDialog x = new CodeGenOptionsDialog(frame, _project.getCodeGenConfig(), true);
			//			if (x.isOk()){
			//				refreshModel(_readMetadata());
			//			}
		}
	};

	public class ReadMetadataAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFrame frame;

		ReadMetadataAction(JFrame frame) {
			super("read from DB");
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			try {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				refreshModel(_readMetadata());
			} finally {
				frame.setCursor(Cursor.getDefaultCursor());
			}
		}
	};

	public class RunCodeGenerationAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFrame frame;

		RunCodeGenerationAction(JFrame frame) {
			super("Generate Code");
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			CodeGenConfig cf = _project.getCodeGenConfig();
			Schema schema = dbRoot.getSchema(cf.getCatalogName(),cf.getSchemaName());
			if (schema == null){
				String message = String.format("Catalog:%s, Schema:%s not found", cf.getCatalogName(),cf.getSchemaName());
				JOptionPane.showMessageDialog(MainFrame.this, message, "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Generator gm = new Generator(cf, _project.getJdbcConfig());
				gm.generateCode(dbRoot.getSchema(cf.getCatalogName(),cf.getSchemaName()));
			} catch (IOException e) {
				throw new SystemError(e);
			} finally {
				frame.setCursor(Cursor.getDefaultCursor());
			}
		}
	};


	public FileFilter filter = new FileFilter() {

		@Override
		public String getDescription() {
			return "xml file";
		}

		@Override
		public boolean accept(File f) {
			return f.isFile() && f.getPath().endsWith(".xml");
		}
	};

	public class OpenFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFrame frame;
		JFileChooser chooser;

		OpenFileAction(JFrame frame) {
			super("Open...");
			this.chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			chooser.showOpenDialog(frame);
			File file = chooser.getSelectedFile();
			if (file!=null){
				try {
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					_project = Project.load(file);
					createDataSource();
					refreshModel(_readMetadata());
				} finally {
					frame.setCursor(Cursor.getDefaultCursor());
				}
			}
		}
	};

	public class SaveFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFileChooser chooser;
		JFrame frame;

		SaveFileAction(JFrame frame) {
			super("Save...");
			this.chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			chooser.showSaveDialog(frame);
			File file = chooser.getSelectedFile();
			if (file!=null){
				Project.save(_project, file);
			}
		}
	};

	public class SaveSchemaAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFileChooser chooser;
		JFrame frame;

		SaveSchemaAction(JFrame frame) {
			super("Save Schema");
			this.chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			chooser.showSaveDialog(frame);
			File file = chooser.getSelectedFile();
			if (file!=null){
				CodeGenConfig conf = _project.getCodeGenConfig();
				if (conf!=null && conf.getCatalogName()!=null && conf.getSchemaName()!=null){
					Schema schema = _project.getModel().getSchema(conf.getCatalogName(), conf.getSchemaName());
					if (schema!=null)
						Project.saveSchema(schema, file);
				}else
					Project.saveDbRoot(_project.getModel(), file);
			}
		}
	};

	public class OpenSchemaAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		JFrame frame;
		JFileChooser chooser;

		OpenSchemaAction(JFrame frame) {
			super("Open Schema");
			this.chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent evt) {
			chooser.showOpenDialog(frame);
			File file = chooser.getSelectedFile();
			if (file!=null){
				try {
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					Schema schema = Project.loadSchema(file);
					dbRoot = new Root();
					Catalog catalog = new Catalog(DatabaseProductType.unknow, dbRoot);
					dbRoot.addCatalog(catalog);
					catalog.addSchema(schema);
					_project.setModel(dbRoot);
					DbNode rootNode = new DbNode(dbRoot);
					TreeModel newModel = new DefaultTreeModel(rootNode, true);
					schemaTree.setModel(newModel);
				} finally {
					frame.setCursor(Cursor.getDefaultCursor());
				}
			}
		}
	};

}