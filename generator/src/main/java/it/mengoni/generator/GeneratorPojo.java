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

	public void genPojoIntfInner(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		String fileName =  "_"+tablename + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DTO_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf, Set<String> importSet) {
				importSet.add(PersistentObject.class.getName());
				buf.append("/* this file will be overwritten by code generator, apply edits oly if you agree to loose them*/\n\n");
				buf.append("public interface _").append(tablename).append(" extends PersistentObject {\n");
				List<TableColunm> fields = table.getColumns().getColunms();
				for (int i = 0; i < fields.size(); i++) {
					TableColunm c = fields.get(i);
					if (!c.isSelected()) continue;
					buf.append(c.getGetterDecl(importSet));
					buf.append(c.getSetterDecl(importSet));
				}
				Fks x = table.getForeignKeys();
				if (x!=null){
					List<Fk> fks = x.getChildren();
					for (int i = 0; i < fks.size(); i++) {
						Fk fk = fks.get(i);
						if (!fk.isSelected()) continue;
						if (!"1".endsWith(fk.getKeySeq())) continue;
						String s = fk.getFkcolumnName();
						if (s.toLowerCase().startsWith("id_"))
							s = s.substring(3);
						String pkTypeName = Helper.toCamel(s);
						String extPojoClass = Helper.toCamel(fk.getPktableName());
						buf.append("public ").append(extPojoClass).append(" getTo").append(pkTypeName).append("();\n");
						buf.append("public void setTo").append(pkTypeName).append("(").append(extPojoClass).append(" to").append(pkTypeName).append(");\n");
					}
				}
				TableReferences refs = table.getRefs();
				if (refs!=null){
					Iterator<TableReference> it = refs.getRefIterator();
					while (it.hasNext()) {
						TableReference ref = it.next();
						if (ref.isSelected()){
							Fk fk = ref.getFk();
							Table rTable = ref.getTarget();
							if (rTable!=null && fk!=null){
								importSet.add(List.class.getName());

								String s;
								if (fk.getFkcolumnName().equals(fk.getPkcolumnName())){
									s = fk.getFktableName();
								}else{
									s = fk.getFkcolumnName();
									if (s.toLowerCase().startsWith("id_"))
										s = s.substring(3);
									s = fk.getFktableName() + "_" + s;
								}
								String listMember = "list"+Helper.toCamel(s);
								String listEtter = "List"+Helper.toCamel(s);
								String extPojoClass = Helper.toCamel(rTable.getDbName());
								buf.append("public List<").append(extPojoClass).append("> get").append(listEtter).append("();\n");
								buf.append("public void set").append(listEtter).append("(List<").append(extPojoClass).append("> ").append(listMember).append(");\n");
							}
						}
					}
				}
				buf.append("}\n");
			}
		};
		gen.createFile(true);
	}

	public void genPojoIntf(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		String fileName =  tablename + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DTO_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf, Set<String> importSet) {
				buf.append("/* this file will be preserved by code generator, apply customization edits here */\n\n");
				buf.append("public interface ").append(tablename).append(" extends _").append(tablename).append(" {\n");
				buf.append("}\n");
			}
		};
		gen.createFile(false);
	}

	public void genPojoImpl(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		final String pojoImplName = "_"+tablename + IMPL_C;
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
				buf.append("/* this file will be overwritten by code generator, apply edits oly if you agree to loose them*/\n\n");
				buf.append("public abstract class ").append(pojoImplName).
				append(" extends AbstractPersistentObject implements ").
				append(tablename).append("  {\n");
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
					List<Fk> fks = x.getChildren();
					/* this empty instances are used by ZK binder in multiple dots bindings paths*/
					for (int i = 0; i < fks.size(); i++) {
						Fk fk = (Fk) fks.get(i);
						if (!fk.isSelected()) continue;
						if (!"1".endsWith(fk.getKeySeq())) continue;
						String s = fk.getFkcolumnName();
						if (s.toLowerCase().startsWith("id_"))
							s = s.substring(3);
						String pkTypeName = Helper.toCamel(s);
						String extPojoIntf = Helper.toCamel(fk.getPktableName());
						buf.append("to").append(pkTypeName).append("= new PoLazyProperty<").append(extPojoIntf).append(">();\n");
						String extPojoClass = extPojoIntf+IMPL_C;
						if (("_"+extPojoClass).equals(pojoImplName)) {
							buf.append("/* link to ").append(extPojoClass).append(" skipped, prevent stack overflow... */\n");
							continue;
						}
						importSet.add(concatPackage(basePackage,DAO_FACTORY));
						importSet.add(PoProperty.class.getName());
						importSet.add(PoLazyProperty.class.getName());
						importSet.add(concatPackage(basePackage,DTO_P,Helper.toCamel(fk.getPktableName())));
						//String fkPropParam = Helper.toCamel(fk.getFkcolumnName(), false);
						buf.append("to").append(pkTypeName).append(".setValue(new ").append(extPojoClass).append("());\n");
						buf.append("to").append(pkTypeName).append(".unResolve();\n");
					}
				}

				TableReferences refs = table.getRefs();
				if (refs!=null){
					Iterator<TableReference> it = refs.getRefIterator();
					while (it.hasNext()) {
						TableReference ref = it.next();
						if (ref.isSelected()){
							Fk fk = ref.getFk();
							Table rTable = ref.getTarget();
							if (rTable!=null && fk!=null){
								String extPojoClass = Helper.toCamel(rTable.getDbName());
								importSet.add(concatPackage(basePackage,DAO_FACTORY));
								importSet.add(List.class.getName());
								importSet.add(ConditionValue.class.getName());
								importSet.add(PoProperties.class.getName());
								importSet.add(PoLazyProperties.class.getName());
								importSet.add(concatPackage(basePackage,DTO_P,extPojoClass));
								String s;
								if (fk.getFkcolumnName().equals(fk.getPkcolumnName())){
									s = fk.getFktableName();
								}else{
									s = fk.getFkcolumnName();
									if (s.toLowerCase().startsWith("id_"))
										s = s.substring(3);
									s = fk.getFktableName() + "_" + s;
								}
								String listMember = "list"+Helper.toCamel(s);
								buf.append(listMember).append("= new PoLazyProperties<").append(extPojoClass).append(">();\n");
							}
						}
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
					List<Fk> fks = x.getChildren();
					for (int i = 0; i < fks.size(); i++) {
						Fk fk = (Fk) fks.get(i);
						if (!fk.isSelected()) continue;
						if (!"1".endsWith(fk.getKeySeq())) continue;
						String s = fk.getFkcolumnName();
						if (s.toLowerCase().startsWith("id_"))
							s = s.substring(3);
						String pkTypeName = Helper.toCamel(s);
						String extPojoClass = Helper.toCamel(fk.getPktableName());
						importSet.add(concatPackage(basePackage,DAO_FACTORY));
						importSet.add(PoProperty.class.getName());
						importSet.add(PoLazyProperty.class.getName());
						importSet.add(concatPackage(basePackage,DTO_P,Helper.toCamel(fk.getPktableName())));
						String fkPropParam = Helper.toCamel(fk.getFkcolumnName(), false);
						buf.append("protected transient PoProperty<").append(extPojoClass).append("> to").append(pkTypeName).append(";\n");
						buf.append("public ").append(extPojoClass).append(" getTo").append(pkTypeName).append("(){ \n");

						buf.append("if(").append(fkPropParam).append("==null) \n return this.to").append(pkTypeName).append(".getValue();\n");

						buf.append("return to").append(pkTypeName).append(".getValue(").append(DAO_FACTORY).append(".getInstance().get").
							append(extPojoClass).append(DAO_C).append("(), ").append(fkPropParam).append(");\n");
						buf.append("}\n");
						buf.append("public void setTo").append(pkTypeName).append("(").append(extPojoClass).append(" to").append(pkTypeName).append("){\n");
						buf.append("    this.to").append(pkTypeName).append(".setValue(to").append(pkTypeName).append(");\n");
						buf.append("  this.").append(fkPropParam).append(" = to").append(pkTypeName).append("==null?null:to").append(pkTypeName).append(".get").append(Helper.toCamel(fk.getPkcolumnName(), true)).append("();\n");

						buf.append("}\n");
					}
				}
				if (refs!=null){
					Iterator<TableReference> it = refs.getRefIterator();
					while (it.hasNext()) {
						TableReference ref = it.next();
						if (ref.isSelected()){
							Fk fk = ref.getFk();
							Table rTable = ref.getTarget();
							if (rTable!=null && fk!=null){
								String extPojoClass = Helper.toCamel(rTable.getDbName());
								String pkColumnName = Helper.toCamel(fk.getPkcolumnName(), false);
								importSet.add(concatPackage(basePackage,DTO_P,extPojoClass));
								String s;
								if (fk.getFkcolumnName().equals(fk.getPkcolumnName())){
									s = fk.getFktableName();
								}else{
									s = fk.getFkcolumnName();
									if (s.toLowerCase().startsWith("id_"))
										s = s.substring(3);
									s = fk.getFktableName() + "_" + s;
								}
								String listMember = "list"+Helper.toCamel(s);
								String listEtter = "List"+Helper.toCamel(s);

								buf.append("protected transient PoProperties<").append(extPojoClass).append("> ").append(listMember).append(";\n");
								buf.append("public List<").append(extPojoClass).append("> get").append(listEtter).append("(){ \n");
								buf.append("if(").append(pkColumnName).append("==null) \nreturn ").append(listMember).append(".getValue();\n");
								buf.append("return ").append(listMember).append(".getValue(").
									append(DAO_FACTORY).append(".getInstance().get").append(extPojoClass).append(DAO_C).append("(), new ConditionValue(\"").
								append(fk.getFkcolumnName()).append(" = ?\", ").append(pkColumnName).append("));\n");
								buf.append("}\n");
								buf.append("public void set").append(listEtter).append("(List<").append(extPojoClass).append("> ").append(listMember).append("){\n");
								buf.append("    this.").append(listMember).append(".setValue(").append(listMember).append(");\n");
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
		gen.createFile(true);
	}

	public void genPojoOuter(final Table table) throws IOException {
		final String tablename = Helper.toCamel(table.getJavaName());
		final String pojoImplName = tablename + IMPL_C;
		String fileName =  pojoImplName + JAVA_EXT;
		String filePackage = concatPackage(basePackage,DTO_P,IMPL_P);
		JavaFileGen gen = new JavaFileGen(rootOut, filePackage, fileName) {
			@Override
			protected void createClassCode(StringBuilder buf, Set<String> importSet) {
				buf.append("public class ").append(pojoImplName).append(" extends _").append(pojoImplName).append("  {\n");
				buf.append("	private static final long serialVersionUID = 1L;\n");
				//inizio costruttore
				buf.append("    public ").append(pojoImplName).append("(){\n");
				buf.append("super();\n");
				buf.append("}\n");
				//fine costruttore
				buf.append("}\n");
			}
		};
		gen.createFile(false);
	}

}