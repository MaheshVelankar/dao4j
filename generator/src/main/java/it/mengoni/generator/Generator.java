package it.mengoni.generator;

import it.mengoni.jdbc.model.Constraints;
import it.mengoni.jdbc.model.Pk;
import it.mengoni.jdbc.model.Schema;
import it.mengoni.jdbc.model.Table;
import it.mengoni.jdbc.model.TableType;
import it.mengoni.persistence.dao.AbstractDaoFactory;
import it.mengoni.persistence.exception.SystemError;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Generator extends AbstractGenerator implements GeneratorConst{

	private final CodeGenConfig config;
	private final JdbcConfig jdbcConfig;
	private final GenDaoFactory genDaoFactory;

	public Generator(CodeGenConfig config, JdbcConfig jdbcConfig) {
		super();
		this.config = config;
		this.jdbcConfig = jdbcConfig;
		genDaoFactory = new GenDaoFactory();
	}

	public void generateCode(Schema schema) throws IOException {
		TableType tableType = schema.find("TABLE");
		if (tableType==null)
			throw new SystemError("TABLE non trovata");
		for (Table item : tableType.getTables()) {
			Table c = item;
			if (!c.isSelected())
				continue;
			generateForTable(c);
		}
		genDaoFactory.createFile(true);
		new GenAbstractBaseT().createFile(true);
		String xml = FileUtils.readFileToString(new File("src/main/resources/applicationContext.xml"));
		xml = xml.replaceAll("%DaoFactoryCLASS%", concatPackage(config.getBasePackage(), "DaoFactory")).replaceAll("%configuration.properties%", config.getBasePackage()+"-configuration.properties");
		FileUtils.writeStringToFile(new File(concatPath(config.getRootTestOut()
				, config.getBasePackage()+"-applicationContext.xml")), xml);
		StringBuilder buf = new StringBuilder();

		buf.append("datasource_driver=").append(jdbcConfig.getDriverClass()).append("\n");
		buf.append("datasource_url=").append(jdbcConfig.getJdbcUrl()).append("\n");
		buf.append("datasource_username=").append(jdbcConfig.getUser()).append("\n");
		buf.append("datasource_password=").append(jdbcConfig.getPassword()).append("\n");
		FileUtils.writeStringToFile(new File(concatPath(config.getRootTestOut()
				, config.getBasePackage()+"-configuration.properties")), buf.toString());

		GenAllTests gat = new GenAllTests();
		gat.tablenames = genDaoFactory.tablenames;
		gat.createFile(true);

	}

	private void generateForTable(Table table) throws IOException {
		Constraints constraints = table.getConstraints();
		Pk pk = constraints.getPk();
		if (pk==null)
			throw new SystemError("Pk non trovata per " + table);
		int c = pk.getChildCount();
		if (c > 10)
			System.err.println("tabella:'" + table.getDbName() + "' con " + c + " campi in chiave");
		if (c > 0)
			generateDao(table);
		else
			System.err.println("tabella:'" + table.getDbName() + "' senza pk");
	}

	private class GenDaoFactory extends JavaFileGen {

		private List<String> tablenames = new ArrayList<String>();

		public GenDaoFactory() {
			super(config.getRootOut(), config.getBasePackage(), "DaoFactory.java");
		}

		public void addTable(String tableName) {
			tablenames.add(tableName);
		}

		@Override
		protected void createClassCode(StringBuilder code, Set<String> importSet) {
			code.append("public class DaoFactory extends AbstractDaoFactory {\n");
			code.append("    private static DaoFactory instance;\n");

			code.append("    public static DaoFactory getInstance()  {\n");
			code.append("        return instance;\n     }\n\n");

			code.append("    public void init() {\n");
			code.append("        DaoFactory.instance = this;\n     }\n\n");

			for (String tablename : tablenames) {
				String implClass = tablename + DAO_C + IMPL_C;
				String daoClass = tablename + DAO_C;
				importSet.add(concatPackage(config.getBasePackage(),DAO_P, IMPL_P, implClass));
				importSet.add(concatPackage(config.getBasePackage(),DAO_P, daoClass));
				importSet.add(AbstractDaoFactory.class.getName());
				code.append("    public ").append(tablename).append(DAO_C).append(" get").append(daoClass).append("()").append("    {\n");
				code.append("        return new ").append(implClass).append("(getHelper(), getCharsetConverter());\n").append("    }\n\n");
			}
			code.append("}\n");
		}
	}

	private void generateDao(Table table) throws IOException {
		String tablename = Helper.toCamel(table.getJavaName());
		genDaoFactory.addTable(tablename);
		GeneratorDao gd = new GeneratorDao(config.getRootOut(), config.getBasePackage());
		gd.genDaoImplInner(table);
		gd.genDaoImplOuter(table);
		gd.genDaoInterfaceInner(table);
		gd.genDaoInterfaceOuter(table);
		GeneratorPojo gp = new GeneratorPojo(config.getRootOut(), config.getBasePackage());
		gp.genPojoIntfInner(table);
		gp.genPojoIntf(table);
		gp.genPojoImpl(table);
		gp.genPojoOuter(table);
		GenTest gt = new GenTest(Helper.toCamel(table.getJavaName()));
		gt.createFile(false);
	}

	private class GenAbstractBaseT extends JavaFileGen {

		public GenAbstractBaseT() {
			super(config.getRootTestOut(), Generator.this.concatPackage(config.getBasePackage(),TEST_P), "AbstractBaseT.java");
		}

		@Override
		protected void createClassCode(StringBuilder code, Set<String> importSet) {
			importSet.add("org.springframework.context.support.AbstractApplicationContext");
			importSet.add("org.springframework.context.support.ClassPathXmlApplicationContext");
			importSet.add(concatPackage(config.getBasePackage(),"DaoFactory"));

			code.append("public abstract class AbstractBaseT {\n");

			code.append("    private static ClassPathXmlApplicationContext applicationContext;\n");
			code.append("    private static DaoFactory daoFactory;\n");

			code.append("    @SuppressWarnings(\"unchecked\")\n");
			code.append("    protected static <T> T getBean(String id){\n");
			code.append("        return (T)getApplicationContext().getBean(id);\n");
			code.append("    }\n");

			code.append("    private static AbstractApplicationContext getApplicationContext() {\n");
			code.append("    	if (applicationContext==null)\n");
			code.append("    		applicationContext = new ClassPathXmlApplicationContext(\"");
			code.append(config.getBasePackage());
			code.append("-applicationContext.xml\");\n");
			code.append("    	return applicationContext;\n");
			code.append("    }\n");

			code.append("    public static DaoFactory getDaoFactory() {\n");
			code.append("        if (daoFactory==null)\n");
			code.append("            daoFactory = getBean(\"daoFactory\");\n");
			code.append("        return daoFactory;\n");
			code.append("    }\n");
			code.append("}\n");
		}
	}

	private class GenTest extends JavaFileGen {

		private String tablename;

		public GenTest(String tablename) {
			super(config.getRootTestOut(), Generator.this.concatPackage(config.getBasePackage(),TEST_P), "Test"+tablename+"Dao.java");
			this.tablename = tablename;
		}

		@Override
		protected void createClassCode(StringBuilder code, Set<String> importSet) {
			importSet.add("java.util.List");
			importSet.add("org.junit.Test");
			importSet.add(concatPackage(config.getBasePackage(),DAO_P,tablename+DAO_C));
			importSet.add(concatPackage(config.getBasePackage(),DTO_P,tablename));
			code.append("public class Test").append(tablename).append("Dao extends AbstractBaseT {  \n");
			code.append("\n");
			code.append("	@Test\n");
			code.append("	public void Test1() throws Exception {     \n");
			code.append("		").append(tablename).append("Dao dao = getDaoFactory().get").append(tablename).append("Dao();\n");
			code.append("		List<").append(tablename).append("> list = dao.getAll(1,10);\n");
			code.append("		for (").append(tablename).append(" bean : list)\n");
			code.append("			System.out.println(bean);\n");
			code.append("	}\n");
			code.append("\n");
			code.append("}\n");
		}
	}

	private class GenAllTests extends JavaFileGen {

		private List<String> tablenames = new ArrayList<String>();

		public GenAllTests() {
			super(config.getRootTestOut(), Generator.this.concatPackage(config.getBasePackage(),TEST_P), "AllTests.java");
		}

		@Override
		protected void createClassCode(StringBuilder code, Set<String> importSet) {
			importSet.add("org.junit.runner.RunWith");
			importSet.add("org.junit.runners.Suite");
			importSet.add("org.junit.runners.Suite.SuiteClasses");
			code.append("@RunWith(Suite.class)\n");
			code.append("@SuiteClasses({");
			for (int n=0; n<tablenames.size(); n++) {
				if (n>0)
					code.append(", ");
				code.append("Test").append(tablenames.get(n)).append("Dao.class");

			}
			code.append("})");
			code.append("public class AllTests {  \n");
			code.append("\n");
			code.append("}\n");
		}
	}

}
