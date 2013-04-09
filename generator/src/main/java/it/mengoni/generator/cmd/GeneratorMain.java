package it.mengoni.generator.cmd;

import it.mengoni.generator.Generator;
import it.mengoni.generator.Helper;
import it.mengoni.generator.Project;
import it.mengoni.generator.SchemaReader;
import it.mengoni.jdbc.model.Root;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GeneratorMain {

	private static final String configLocation = "/config/context.xml";

	public static void main(String[] args) {
		try {
			ApplicationContext context = new  FileSystemXmlApplicationContext(configLocation);
			Project project = (Project) context.getBean("project");
			Helper.setConfig(project.getCodeGenConfig());
			ComboPooledDataSource ds = new ComboPooledDataSource();
			ds.setJdbcUrl(project.getJdbcConfig().getJdbcUrl());
			ds.setDriverClass(project.getJdbcConfig().getDriverClass());
			ds.setPassword(project.getJdbcConfig().getPassword());
			ds.setUser(project.getJdbcConfig().getUser());
			SchemaReader sr = new SchemaReader(project.getCodeGenConfig());
			Root root = sr.readSchema(ds.getConnection());
			Generator gm = new Generator(project.getCodeGenConfig(), project.getJdbcConfig());
			gm.generateCode(root.getSchema("default","default"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}