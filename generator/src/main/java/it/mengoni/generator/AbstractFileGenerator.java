package it.mengoni.generator;

public abstract class AbstractFileGenerator extends AbstractGenerator {

	protected final String rootOut;
	protected final String filePackage;
	protected final String _fileName;


	public AbstractFileGenerator(String rootOut, String filePackage, String fileName) {
		super();
		this.rootOut = rootOut;
		this.filePackage = filePackage;
		this._fileName = fileName;
	}

	public String getRootOut() {
		return rootOut;
	}

	public String getFilePackage() {
		return filePackage;
	}

	public String getFileName() {
		return _fileName;
	}



}
