package it.mengoni.generator;

import it.mengoni.jdbc.model.Fk;
import it.mengoni.jdbc.model.Fks;
import it.mengoni.jdbc.model.Table;
import it.mengoni.jdbc.model.TableColunm;
import it.mengoni.jdbc.model.TableReference;
import it.mengoni.jdbc.model.TableReferences;
import it.mengoni.persistence.dao.ConditionValue;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PersistentObject;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dto.PoLazyProperty;
import it.mengoni.persistence.dto.PoProperties;
import it.mengoni.persistence.dto.PoProperty;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.javatuples.Tuple;

public class GeneratorPojo extends AbstractGenerator implements GeneratorConst{

	private String rootOut;
	private String basePackage;

	public GeneratorPojo(String rootOut, String basePackage) {
		super();
		this.rootOut = rootOut;
		this.basePackage = basePackage;
	}

	public void genPojo(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		String fileName =  tablename + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DTO_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf, Set<String> importSet) {
				importSet.add(PersistentObject.class.getName());
				buf.append("public interface ").append(tablename).append(" extends PersistentObject {\n");

				List<TableColunm> fields = table.getColumns().getColunms();
				for (int i = 0; i < fields.size(); i++) {
					TableColunm c = fields.get(i);
					if (!c.isSelected()) continue;
					buf.append(c.getGetterDecl(importSet));
					buf.append(c.getSetterDecl(importSet));
				}
				Fks x = table.getForeignKeys();
				if (x!=null){
					Set<String> done = new HashSet<String>();
					List<Fk> fks = x.getChildren();
					for (int i = 0; i < fks.size(); i++) {
						Fk fk = fks.get(i);
						if (!fk.isSelected()) continue;
						if (!"1".endsWith(fk.getKeySeq())) continue;
						String pkTypeName = Helper.toCamel(fk.getPktableName());
						if (done.contains(pkTypeName)) continue;
						done.add(pkTypeName);
						buf.append("public ").append(pkTypeName).append(" getTo").append(pkTypeName).append("();\n");
						buf.append("public void setTo").append(pkTypeName).append("(").append(pkTypeName).append(" to").append(pkTypeName).append(");\n");
					}
				}
				TableReferences refs = table.getRefs();
				if (refs!=null){
					Iterator<TableReference> it = refs.getRefIterator();
					Set<String> done = new HashSet<String>();
					while (it.hasNext()) {
						TableReference ref = it.next();
						if (ref.isSelected()){
							Fk fk = ref.getFk();
							Table rTable = ref.getTarget();
							if (rTable!=null && fk!=null){
								importSet.add(List.class.getName());
								String fkTypeName = Helper.toCamel(fk.getFktableName());
								if (done.contains(fkTypeName)) continue;
								done.add(fkTypeName);
								buf.append("public List<").append(fkTypeName).append("> getList").append(fkTypeName).append("();\n");
								buf.append("public void setList").append(fkTypeName).append("(List<").append(fkTypeName).append("> to").append(fkTypeName).append(");\n");
							}
						}
					}
				}
				buf.append("}\n");
			}
		};
		gen.createFile();
	}

	public void genPojoImpl(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		final String pojoImplName = tablename + IMPL_C;
		String fileName =  pojoImplName + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DTO_P,IMPL_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf, Set<String> importSet) {
				List<TableColunm> fields = table.getColumns().getColunms();
				importSet.add(AbstractPersistentObject.class.getName());
				importSet.add(Tuple.class.getName());
				importSet.add(concatPackage(basePackage,DTO_P,tablename));
				KeyData kd = new KeyData(fields);
				importSet.addAll(kd.importSet);
				importSet.add(kd.tupleClass.getName());
				buf.append("public class ").append(pojoImplName).append(" extends AbstractPersistentObject implements ").append(tablename).append("  {\n");
				buf.append("	private static final long serialVersionUID = 1L;\n");
				for (int i = 0; i < fields.size(); i++) {
					TableColunm c = fields.get(i);
					if (!c.isSelected()) continue;
					buf.append(c.getFieldDecl(importSet));
				}
				//inizio costruttore
				buf.append("    public ").append(pojoImplName).append("(){\n");
				Fks x = table.getForeignKeys();
				if (x!=null){
					Set<String> done = new HashSet<String>();
					List<Fk> fks = x.getChildren();
					for (int i = 0; i < fks.size(); i++) {
						Fk fk = (Fk) fks.get(i);
						if (!fk.isSelected()) continue;
						if (!"1".endsWith(fk.getKeySeq())) continue;
						String pkTypeName = Helper.toCamel(fk.getPktableName());
						if (done.contains(pkTypeName)) continue;
						done.add(pkTypeName);
						importSet.add(concatPackage(basePackage,DAO_FACTORY));
						importSet.add(PoProperty.class.getName());
						importSet.add(PoLazyProperty.class.getName());
						importSet.add(concatPackage(basePackage,DTO_P,pkTypeName));
						//String fkPropParam = Helper.toCamel(fk.getFkcolumnName(), false);
						buf.append("to").append(pkTypeName).append(".setValue(new ").append(pkTypeName).append(IMPL_C).append("());\n");
						buf.append("to").append(pkTypeName).append(".unResolve();\n");
					}
				}
				buf.append("}\n");
				//fine costruttore

				TableColunm displayField = null;
				for (int i = 0; i < fields.size(); i++) {
					TableColunm c = fields.get(i);
					if (!c.isSelected()) continue;
					buf.append(c.getGetterBody(importSet));
					buf.append(c.getSetterBody(importSet));
					if (displayField==null && c.getJavaClass().equals("String"))
						displayField = c;
				}

				buf.append("    @Override\n    public String getDisplayLabel() {\n");
				if (displayField ==null)
					buf.append("         return null;\n");
				else
					buf.append("         return ").append(displayField.getPropertyName()).append(";\n");
				buf.append("    }\n");

				x = table.getForeignKeys();
				if (x!=null){
					Set<String> done = new HashSet<String>();
					List<Fk> fks = x.getChildren();
					for (int i = 0; i < fks.size(); i++) {
						Fk fk = (Fk) fks.get(i);
						if (!fk.isSelected()) continue;
						if (!"1".endsWith(fk.getKeySeq())) continue;
						String pkTypeName = Helper.toCamel(fk.getPktableName());
						if (done.contains(pkTypeName)) continue;
						done.add(pkTypeName);
						importSet.add(concatPackage(basePackage,DAO_FACTORY));
						importSet.add(PoProperty.class.getName());
						importSet.add(PoLazyProperty.class.getName());
						importSet.add(concatPackage(basePackage,DTO_P,pkTypeName));
						String fkPropParam = Helper.toCamel(fk.getFkcolumnName(), false);
						buf.append("private transient PoProperty<").append(pkTypeName).append("> to").append(pkTypeName).
						append("= new PoLazyProperty<").append(pkTypeName).append(">();\n");
						buf.append("public ").append(pkTypeName).append(" getTo").append(pkTypeName).append("(){ \n");

						buf.append("if(").append(fkPropParam).append("==null) \n return this.to").append(pkTypeName).append(".getValue();\n");

						buf.append("return to").append(pkTypeName).append(".getValue(").append(DAO_FACTORY).append(".getInstance().get").
							append(pkTypeName).append(DAO_C).append("(), ").append(fkPropParam).append(");\n");
						buf.append("}\n");
						buf.append("public void setTo").append(pkTypeName).append("(").append(pkTypeName).append(" to").append(pkTypeName).append("){\n");
						buf.append("    this.to").append(pkTypeName).append(".setValue(to").append(pkTypeName).append(");\n");
						buf.append("  this.").append(fkPropParam).append(" = this.to").append(pkTypeName).append("==null?null:to").append(pkTypeName).append(".get").append(Helper.toCamel(fk.getFkcolumnName(), true)).append("();\n");

						buf.append("}\n");
					}
				}
				TableReferences refs = table.getRefs();
				if (refs!=null){
					Set<String> done = new HashSet<String>();
					Iterator<TableReference> it = refs.getRefIterator();
					while (it.hasNext()) {
						TableReference ref = it.next();
						if (ref.isSelected()){
							Fk fk = ref.getFk();
							Table rTable = ref.getTarget();
							if (rTable!=null && fk!=null){
								String fkTypeName = Helper.toCamel(fk.getFktableName());
								String fkColumnName = fk.getFkcolumnName();
								String pkColumnName = Helper.toCamel(fk.getPkcolumnName(), false);
								if (done.contains(fkTypeName)) continue;
								done.add(fkTypeName);
								importSet.add(concatPackage(basePackage,DAO_FACTORY));
								importSet.add(List.class.getName());
								importSet.add(ConditionValue.class.getName());
								importSet.add(PoProperties.class.getName());
								importSet.add(PoLazyProperties.class.getName());
								importSet.add(concatPackage(basePackage,DTO_P,fkTypeName));

								buf.append("private transient PoProperties<").append(fkTypeName).append("> list").append(fkTypeName).
								append("= new PoLazyProperties<").append(fkTypeName).append(">();\n");

								buf.append("public List<").append(fkTypeName).append("> getList").append(fkTypeName).append("(){ \n");

								buf.append("if(").append(pkColumnName).append("==null) \nreturn null;");

								buf.append("return list").append(fkTypeName).append(".getValue(").
									append(DAO_FACTORY).append(".getInstance().get").append(fkTypeName).append(DAO_C).append("(), new ConditionValue(\"").
								append(fkColumnName).append(" = ?\", ").append(pkColumnName).append("));\n");
								buf.append("}\n");

								buf.append("public void setList").append(fkTypeName).append("(List<").append(fkTypeName).append("> list").append(fkTypeName).append("){\n");
								buf.append("    this.list").append(fkTypeName).append(".setValue(list").append(fkTypeName).append(");\n");
								buf.append("}\n");
							}
						}
					}
				}
				buf.append("   @Override\n    protected Tuple newKey() {\n");
				buf.append("    return new ").append(kd.tupleType).append(kd.ka.toString()).append("(").append(kd.kf).append(");\n");
				buf.append("}\n");
				buf.append("}\n");
			}
		};
		gen.createFile();
	}
}