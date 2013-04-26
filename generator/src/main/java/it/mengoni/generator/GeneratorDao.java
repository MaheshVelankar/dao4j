package it.mengoni.generator;

import it.mengoni.jdbc.model.DbItem;
import it.mengoni.jdbc.model.ModelFactory;
import it.mengoni.jdbc.model.Pk;
import it.mengoni.jdbc.model.PkColumn;
import it.mengoni.jdbc.model.Schema;
import it.mengoni.jdbc.model.Table;
import it.mengoni.jdbc.model.TableColunm;
import it.mengoni.jdbc.model.TableType;
import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Dao;
import it.mengoni.persistence.dao.Dao.DatabaseProductType;
import it.mengoni.persistence.dao.DateField;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.IntegerField;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.KeyGenerator;
import it.mengoni.persistence.dao.PkFieldImpl;
import it.mengoni.persistence.dao.PkIntegerField;
import it.mengoni.persistence.dao.PkShortField;
import it.mengoni.persistence.dao.PkStringField;
import it.mengoni.persistence.dao.PostgresqlSequenceReader;
import it.mengoni.persistence.dao.ShortField;
import it.mengoni.persistence.dao.StringField;
import it.mengoni.persistence.dao.TimeField;
import it.mengoni.persistence.dao.TimestampField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.javatuples.Tuple;

public class GeneratorDao extends AbstractGenerator implements GeneratorConst{

	private String rootOut;
	private String basePackage;


	public GeneratorDao(String rootOut, String basePackage) {
		super();
		this.rootOut = rootOut;
		this.basePackage = basePackage;
	}


	public void genDaoImpl(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
//		final String daoName = tablename + DAO_C;
		final String daoImplName = tablename + DAO_C + IMPL_C ;
		String fileName = daoImplName + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DAO_P,IMPL_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf, Set<String> importSet) {
				List<TableColunm> fields = table.getColumns().getColunms();

				importSet.add(java.sql.Types.class.getName());

				importSet.add(AbstractRelationDao.class.getName());
				importSet.add(it.mengoni.persistence.dao.Field.class.getName());
				importSet.add(concatPackage(basePackage,DTO_P,tablename));
				importSet.add(concatPackage(basePackage,DTO_P,IMPL_P,tablename+IMPL_C));

				importSet.add(ArrayList.class.getName());
				importSet.add(List.class.getName());
				importSet.add(Tuple.class.getName());
				importSet.add(JdbcHelper.class.getName());
				importSet.add(CharsetConverter.class.getName());

				KeyData kd = new KeyData(fields);
				importSet.addAll(kd.importSet);

				String tupleType = kd.tupleClass.getSimpleName();
				importSet.add(kd.tupleClass.getName());
				String daoName = tablename + DAO_C;
				importSet.add(concatPackage(basePackage,DAO_P,daoName));

				String s = String.format("public class %s extends AbstractRelationDao<%s> implements %s {\n", daoImplName, tablename, daoName);
				buf.append(s);
				buf.append("private static final List<Field<").append(tablename).append(", ?>> fields = new ArrayList<Field<").append(tablename).append(", ?>>();\n");
				buf.append("static {");

				for (int i = 0; i < fields.size(); i++) {
					TableColunm c = fields.get(i);
					if (!c.isSelected()) continue;
					String fieldClassName =  c.getJavaClass();
					String cn = Helper.toCamel(c.getJavaName());
					buf.append("fields.add(new ");
					getFieldImplClass(c,buf, importSet);
					buf.append(") {\n");
					buf.append("@Override\npublic void setValue(").append(fieldClassName).append(" value, ").append(tablename).append(" bean) " +
							" { bean.set").append(cn).append("(value); }\n");

					buf.append("@Override\npublic ").append(fieldClassName).append(" getValue(").append(tablename).append(" bean) { return bean.get").append(cn).append("(); } \n");
					buf.append("});\n");
				}
				buf.append("}\n");
				buf.append("public ").append(daoImplName).append("(JdbcHelper jdbcHelper, CharsetConverter charsetConverter)" +
						" {super(jdbcHelper, charsetConverter, \"");
				buf.append(ModelFactory.quoteSqlReserved(table.getJavaName(), table.getDatabaseProductType()));
				buf.append("\", fields);");
				if (isOneFieldPkWithSequence(table)){
					if (table.getDatabaseProductType()==DatabaseProductType.postgresql){
/*
       setKeyGenerator(new KeyGenerator<Richiesta>() {

        	PostgresqlSequenceReader gen = new PostgresqlSequenceReader("id_richiesta");

			@Override
			public Tuple newKey(Richiesta bean, String[] keyNames) throws SystemError,LogicError {
				Integer id = gen.readSequence(RichiestaDaoImpl.this.jdbcHelper).intValue();
				bean.setIdRichiesta(id);
				return RichiestaDaoImpl.this.newKey(id);
			}
		});
  */
						importSet.add(KeyGenerator.class.getName());
						importSet.add(PostgresqlSequenceReader.class.getName());
						buf.append("setKeyGenerator(new KeyGenerator<").append(tablename).append(">() {\n");
						String pkFieldName = getPkFieldName(table);
						buf.append("PostgresqlSequenceReader gen = new PostgresqlSequenceReader(\"").append(pkFieldName).append("\");\n");
						buf.append("	@Override\n");
						buf.append("	public Tuple newKey(").append(tablename).append(" bean, String[] keyNames) {\n");
						buf.append("		Integer id = gen.readSequence(").append(daoImplName).append(".this.jdbcHelper).intValue();\n");
						buf.append("		bean.set").append(Helper.toCamel(pkFieldName)).append("(id);\n");
						buf.append("		return ").append(daoImplName).append(".this.newKey(id);\n");
						buf.append("	}\n");
						buf.append("});\n");

					}else if (table.getDatabaseProductType()==DatabaseProductType.firebird){

					}

				}
				buf.append("}\n");

				buf.append("    @Override\n    public ").append(tablename).append(" newIstance() { return new ").append(tablename).append(IMPL_C).append("();	}\n");

				buf.append("protected Tuple newKey(").append(kd.kp).append(") {\n");
				buf.append("    return new ").append(tupleType).append(kd.ka.toString()).append("(").append(kd.kf).append(");\n");
				buf.append("}\n");

				buf.append("public ").append(tablename).append(" getByPrimaryKey(").append(kd.kp).append(") {\n");
				buf.append("    return get(new ").append(tupleType).append(kd.ka.toString()).append("(").append(kd.kf).append("));\n");
				buf.append("}\n");

				buf.append("}\n");

			}

			private void getFieldImplClass(TableColunm c, StringBuilder buf, Set<String> importSet) {
				String fieldClassName = c.getJavaClass();
				String propertyName = Helper.toCamel(c.getJavaName(), false);
				if (c.isPk() && "Short".equals(fieldClassName)){
					importSet.add(PkShortField.class.getName());
					buf.append(PkShortField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if (c.isPk() && "String".equals(fieldClassName)){
					importSet.add(PkStringField.class.getName());
					buf.append(PkStringField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if (c.isPk() && "Integer".equals(fieldClassName)){
					importSet.add(PkIntegerField.class.getName());
					buf.append(PkIntegerField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if (c.isPk()){
					importSet.add(PkFieldImpl.class.getName());
					buf.append(PkFieldImpl.class.getSimpleName());
					buf.append("<").append(tablename).append(", ").append(fieldClassName);
				}else if ("String".equals(fieldClassName)){
					importSet.add(StringField.class.getName());
					buf.append(StringField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if ("Integer".equals(fieldClassName)){
					importSet.add(IntegerField.class.getName());
					buf.append(IntegerField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if ("Short".equals(fieldClassName)){
					importSet.add(ShortField.class.getName());
					buf.append(ShortField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if ("java.util.Date".equals(fieldClassName)){
					importSet.add(DateField.class.getName());
					buf.append(DateField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if ("java.sql.Timestamp".equals(fieldClassName)){
					importSet.add(TimestampField.class.getName());
					buf.append(TimestampField.class.getSimpleName());
					buf.append("<").append(tablename);
				}else if ("java.sql.Time".equals(fieldClassName)){
					importSet.add(TimeField.class.getName());
					buf.append(TimeField.class.getSimpleName());
					buf.append("<").append(tablename);
				} else {
					importSet.add(FieldImpl.class.getName());
					buf.append(FieldImpl.class.getSimpleName());
					buf.append("<").append(tablename).append(", ").append(fieldClassName);
				}
				buf.append(">(\"").append(ModelFactory.quoteSqlReserved(c.getDbName(), c.getParent().getParent().getDatabaseProductType())).append("\", ");
				buf.append("\"").append(propertyName).append("\", ");
				if (!c.isPk())
					buf.append(c.isNullable()).append(", ");
				buf.append(c.getLenght()).append(", ").
				append(Helper.getTypeSqlString(c.getSqlType()));
			}
		};
		gen.createFile();

	}


	protected String getPkFieldName(Table table) {
		return table.getConstraints().getPk().getColumns().get(0).getDbName();
	}


	protected boolean isOneFieldPkWithSequence(Table table) {
		if (table.getConstraints()==null)
			return false;
		if (table.getConstraints().getPk()==null)
			return false;
		Pk pk = table.getConstraints().getPk();
		if (pk.getChildCount()==1){
			PkColumn pkf = pk.getColumns().get(0);
			Schema schema = table.getParent().getParent();
			TableType sequence = schema.find("SEQUENCE");
			Iterator<DbItem> it = sequence.getChildIterator();
			while(it.hasNext()){
				DbItem obj = it.next();
				if (obj.getDbName().equals(pkf.getDbName()))
					return true;
			}

		}
		return false;
	}


	public void genDao(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		final String daoName = tablename + DAO_C;
		String fileName = daoName + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DAO_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf,
					Set<String> importSet) {
				List<TableColunm> fields = table.getColumns().getColunms();
				importSet.add(Dao.class.getName());
				importSet.add(concatPackage(basePackage,DTO_P,tablename));
				buf.append("public interface ").append(daoName).append(" extends Dao<").append(tablename).append("> {\n");
				KeyData kd = new KeyData(fields);
				importSet.addAll(kd.importSet);
				buf.append("public ").append(tablename).append(" getByPrimaryKey(").append(kd.kp).append(");\n");
				buf.append("}\n");
			}
		};
		gen.createFile();
	}

}
