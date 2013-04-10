package it.mengoni.generator;

public interface GeneratorConst {

	public enum DatabaseProductType {firebird, postgresql, mysql, oracle, unknow}

	public static final String JAVA_EXT = ".java";

	public static final String DAO_FACTORY = "DaoFactory";
	public static final String DTO_P = "dto";
	public static final String IMPL_P = "impl";
	public static final String DAO_P = "dao";
	public static final String TEST_P = "test";

	public static final String DTO_C = "Dto";
	public static final String IMPL_C = "Impl";
	public static final String DAO_C = "Dao";

}
