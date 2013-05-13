package it.mengoni.generator;

import it.mengoni.persistence.exception.SystemError;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hunsicker.jalopy.Jalopy;

public abstract class JavaFileGen extends AbstractFileGenerator {

	private static final Logger logger = LoggerFactory.getLogger(JavaFileGen.class);
	private final Set<String> importSet = new HashSet<String>();

	public JavaFileGen(String rootOut, String filePackage, String fileName) {
		super(rootOut, filePackage, fileName);
	}

	void createFile(boolean overwrite) {
		StringBuilder code = new StringBuilder();
		createClassCode(code, importSet);
		StringBuilder buf = new StringBuilder();

		buf.append("package ");
		buf.append(filePackage);
		buf.append(";\n");
		for (String imp : importSet)
			buf.append("import ").append(imp).append(";\n");
		buf.append(code);
		String fileName = concatPath(rootOut , filePackage.replaceAll("\\.", "/"), _fileName);
		File x = new File(fileName);
		if (!overwrite && x.exists()){
			logger.info("skipped file:"+filePackage+"." +_fileName);
			return;
		}
		String dir = getFileDirectory(fileName, "/");
		forceDir(dir, "/");
		try {
			FileUtils.writeStringToFile(new File(fileName), buf.toString());
		} catch (IOException e) {
			logger.error("Error in writing:" + fileName, e);
			throw new SystemError("Error in writing:" + fileName, e);
		}
		try {
			Jalopy jalopy = new Jalopy();
			jalopy.setInput(new File(fileName));
			jalopy.setOutput(new File(fileName));
			jalopy.setForce(true);
			jalopy.format();
		} catch (Exception e) {
			logger.error("Error in format:" + fileName, e);
		}
		logger.info("generated file:"+ filePackage+"." +_fileName);
	}

	protected abstract void createClassCode(StringBuilder code,
			Set<String> importSet2);

	/**
	 * Forza la costruzione di un'alberatura di directory indicata
	 *
	 * @param dir
	 *            l'alberatura di directory da costruire, se vuota (o null) non
	 *            viene fatto nulla
	 */

	private void forceDir(String dir, String separator) {
		String[] dirs = dir.split(separator);
		String path = null;
		if (dirs != null) {
			for (int n = 0; n < dirs.length; n++) {
				path = (path != null) ? path + separator + dirs[n] : dirs[n];
				File file = new File(path);
				if (!file.exists()) {
					file.mkdir();
				}
			}
		}
	}

	private String getFileDirectory(String filePath, String sep) {
		int separatorIndex = filePath.lastIndexOf(sep);
		if (separatorIndex == -1)
			return filePath;
		return filePath.substring(0, separatorIndex);
	}

}