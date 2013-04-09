package it.mengoni.generator;

import it.mengoni.exception.SystemError;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	public final static String FILE_SEPARATOR = System.getProperty("file.separator");

	public static boolean fileExists(final String filename) {
		if (isEmpty(filename)) return false;
		try {
			return fileExists(new File(filename));
		} catch (Exception e) {
			logger.error("Errore in lettura:" + filename, e);
			return false;
		}
	}

	public static boolean fileExists(final File aFile) {
		if (aFile.isDirectory()) throw new SystemError(aFile.getAbsolutePath() + " è una Directory");
		return aFile.exists();
	}


	public static boolean directoryExists(File aDirectory) {
		if (!aDirectory.isDirectory()) throw new SystemError("il file:" + aDirectory.getAbsolutePath() + " non è una Directory");
		return aDirectory.exists();
	}

	public static boolean directoryExists(String directoryPath) {
		try {
			return directoryExists(new File(directoryPath));
		} catch (Exception exc) {
			logger.error("Errore in lettura:" + directoryPath, exc);
			return false;
		}
	}

	/**
	 * Controlla che la stringa passata finisca con
	 * un carattere di seprarazione delle directory
	 * @param value percorso da controllare
	 * @return valore controllato
	 */
	public static String checkEndPath(String value) {
		String result = value;
		if ( !isEmpty(value) ) {
			if ( !value.endsWith(String.valueOf(File.separatorChar)) ) {
				result = value + File.separatorChar;
			}
		}
		return result;
	}

	private static boolean isEmpty(String value) {
		return value==null || value.isEmpty();
	}

	public static String forceUnixPathSeparator(String path) {
		return path.replace('\\', '/');
	}

	public static boolean checkfile(String filename){
		if (filename != null){
			File f = new File(filename);
			return f.exists() && f.isFile();
		}
		return false;
	}

	public static String checkFilePath(String path) {
		return checkFilePath(path,FILE_SEPARATOR);
	}

	public static String checkFilePath(String path, String sep) {
		if (path.endsWith(sep)) return path;
		return path + sep;
	}

	/**
	 * Cancellazione del file specificato, se il file non è presente non si fa nulla
	 * @param filepath il path del file da cancellare
	 */
	public static void deleteFile(String filePath) {
		if ( isEmpty(filePath) ) return;
		File theFile = new File( filePath );
		if (theFile != null) theFile.delete();
	}

	/**
	 * Cancellazione della directory specificata
	 * @param dirPath il path ASSOLUTO della directory da cancellare, se vuoto (o null) non viene fatto nulla
	 */
	public static void deleteDirectory(String dirPath) {
		deleteFile(dirPath);
	}

	/**
	 * Elimina un elenco di files indicato come parametro
	 * @param filenames la lista dei path ASSOLUTI dei files da eliminare
	 */
	public static void deleteFiles(List<String> filenames) {
		// Eliminazione di tutti i files scaricati (ZIP e CSV compresi)
		Iterator<String> itFiles = filenames.iterator();
		while (itFiles.hasNext()) {
			String filename = ((String) itFiles.next());
			// Ciclo su tutti i files per cancellarli dalla directory in cui li ho creati
			try {
				new File (filename).delete();
			} catch (Exception ex) {
				logger.error("Non è stato possibile cancellare il file <" + filename + ">", ex);
			}
		}
	}

	/**
	 * Forza la costruzione di un'alberatura di directory indicata
	 * @param dir l'alberatura di directory da costruire, se vuota (o null) non viene fatto nulla
	 */

	public static void forceDir(String dir) {
		if ( isEmpty(dir) ) return;
		String separator = File.separator;
		if (separator.startsWith("\\"))
			separator += "\\";
		String[] dirs = dir.split(separator);
		String path = null;
		if (dirs != null) {
			for (int n = 0; n < dirs.length; n++){
				path = (path != null) ? path + File.separator + dirs[n] : dirs[n];
				File file = new File(path);
				if ( !file.exists() ) {
					file.mkdir();
				}
			}
		}
	}

	public static String getUserProfilePath(){
		return System.getProperty("user.home");
	}

	public static String[] getNomiFiles(FilenameFilter filter, String directory){
		File dir = new File(directory);
		if (dir.exists() && dir.isDirectory()){
			return dir.list(filter);
		}
		return null;
	}


	public static void EliminaFiles(String directory, String [] nomifiles){
		if (nomifiles!=null && nomifiles.length>0){
			String percorsoFlussi = checkEndPath(directory);
			for(int j=0; j<nomifiles.length; j++) {
				File f = new File(percorsoFlussi + nomifiles[j]);
				if (f.exists())
					f.delete();
			}
		}
	}

	/**
	 * Legge il contenuto di un file e ne restituisce il contenuto
	 * @param fileName il nome (comprensivo del path) del file da leggere
	 * @throws IOException
	 */
	public static String readTextFile(String fileName) throws IOException {
		return readTextFile( new File(fileName) );
	}

	/**
	 * Legge il contenuto di un file di testo e ne restituisce il contenuto
	 * @throws IOException
	 */
	public static String readTextFile(File aFile) throws IOException {
		StringBuffer contents = new StringBuffer();
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			String line = null;
			String sep = System.getProperty("line.separator");
			while (( line = input.readLine()) != null){
				contents.append(line);
				contents.append(sep);
			}
			input.close();
		} catch (IOException ioe){
			throw ioe;
		}
		return contents.toString();
	}


	/**
	 * Metodo che restituisce il contenuto del file in un array di byte dato il path ASSOLUTO del file
	 * @param filePath il percorso assoluto del file di cui deve essere letto il contenuto
	 */
	public static byte[] readDataFile(String filePath) throws IOException {
		FileInputStream in = new FileInputStream(filePath);
		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		in.close();
		return bytes;
	}

	/**
	 * Scrive il contenuto indicato all'interno di un file di testo
	 * NB: se il file non esiste lo crea!
	 * @param fileName il nome (comprensivo del path) del file da scrivere
	 * @param content il contenuto da scrivere nel file
	 * @throws IOException
	 */
	public static void writeTextFile(String fileName, String content) throws IOException {
		writeTextFile( new File(fileName), content, true);
	}

	/**
	 * Scrive il contenuto indicato all'interno di un file di testo
	 * NB: se il file non esiste lo crea!
	 * @param fileName il nome (comprensivo del path) del file da scrivere
	 * @param content il contenuto da scrivere nel file
	 * @param append indica se la scrittura del contenuto avviene in modalità append
	 * @throws IOException
	 */
	public static void writeTextFile(String fileName, String content, boolean append) throws IOException {
		writeTextFile(new File(fileName), content, append);
	}

	/**
	 * Scrive il contenuto indicato all'interno di un file di testo
	 * @throws IOException
	 */
	public static void writeTextFile(File aFile, String content, boolean append) throws IOException {
		if (aFile == null)
			throw new IllegalArgumentException("File should not be null.");

		if (!aFile.exists()) {
			aFile.createNewFile();
		}

		Writer output = new BufferedWriter(new FileWriter(aFile, append));
		try {
			output.write( content );
		}
		finally {
			output.close();
		}
	}

	/**
	 * Scrive il contenuto indicato all'interno di un file di testo.
	 * Viene utilizzata la modalità <tt>append</tt>.
	 * @throws IOException
	 */
	public static void writeTextFile(File aFile, String content) throws IOException {
		writeTextFile(aFile, content, true);
	}

	/**
	 * Crazione di un file zip che contiene i files specificati
	 * @param outputFile file zip risultato dalla compressione
	 * @param filePathAndNames file (comprensivi di path assoluto) da includere nel file zip
	 * @throws IOException
	 */
	public static void createZipFile(String outputFile, List<String> filePathAndNames) {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream( new BufferedOutputStream( new FileOutputStream(outputFile) ) );
			byte[] data = new byte[1024];
			Iterator<String> itFiles = filePathAndNames.iterator();
			while (itFiles.hasNext()) {
				String filePath = (String) itFiles.next();
				String fileName = FileUtils.getFileName(filePath);
				BufferedInputStream	in = new BufferedInputStream( new FileInputStream(filePath) );
				out.putNextEntry(new ZipEntry(fileName));
				int len;
				while ((len = in.read(data)) > 0) {
					out.write(data, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			logger.error("Errore in creazione:" + outputFile, e);
			throw new SystemError("Errore in creazione:" + outputFile, e);
		} catch (IOException e) {
			logger.error("Errore in creazione:" + outputFile, e);
			throw new SystemError("Errore in creazione:" + outputFile, e);
		} catch (Exception e) {
			logger.error("Errore in creazione:" + outputFile, e);
			throw new SystemError("Errore in creazione:" + outputFile, e);
		}
	}


	public static String getCurrentDir(){
		File f = new File(".");
		return f.getAbsolutePath();
	}

	public static String getDirectoryPath(String path) {
		String buffer = checkFilePath(path);
		return buffer.substring(0, buffer.length() - 1);
	}

	/**
	 * Estrae il nome del file da un path assoluto o relativo, se il nome del file
	 * non è presente del path viene restituito il path stesso
	 * @param filePath il path assoluto o relativo (che contiene il nome del file)
	 * @return il nome del file estratto dal path
	 */
	public static String getFileName(String filePath) {
		int separatorIndex =  filePath.lastIndexOf(FILE_SEPARATOR);
		if (separatorIndex == -1) return filePath;
		return filePath.substring(separatorIndex + 1);
	}

	public static String getFileNameWithoutExt(String filePath) {
		String fileName = getFileName(filePath);
		int separatorPosition = fileName.lastIndexOf(".");
		if (separatorPosition > -1) fileName = fileName.substring(0, separatorPosition);
		return fileName;
	}

	public static String getFileExtension(String filePath) {
		String fileName = getFileName(filePath);
		int separatorPosition = fileName.lastIndexOf(".");
		if (separatorPosition > -1) fileName = fileName.substring(++separatorPosition);
		return fileName;
	}

	/**
	 * Recupera la dimensione (in bytes) del file indicato
	 * @param filePath il path del file
	 * @return la dimensione (in bytes) del file
	 */
	public static long getFileSize(String filePath) {
		try {
			return new File(filePath).length();
		} catch (Exception exc) {
			return 0;
		}
	}

	public static long getFileSize(File file) {
		return file.length();
	}

	public static String getFileDirectory(String filePath) {
		return getFileDirectory(filePath, FILE_SEPARATOR);
	}

	public static String getFileDirectory(String filePath, String sep) {
		int separatorIndex =  filePath.lastIndexOf(sep);
		if (separatorIndex == -1) return filePath;
		return checkFilePath(filePath.substring(0, separatorIndex), sep);
	}

	public static void createDirectory(File aDirectory) {
		aDirectory.mkdir();
	}

	/**
	 * Creazione di una directory con il path ASSOLUTO specificato
	 * @param dirPath il path ASSOLUTO della directory da creare
	 */
	public static void createDirectory(String dirPath) {
		new File(dirPath).mkdir();
	}


	public static String formatFileSize(long size) {
		if (size == 0) return "0";
		DecimalFormat df = new DecimalFormat("0.00");
		if (size > (1024 * 1024 * 1024)) {
			double mbytes = size / (1024 * 1024 * 1024);
			return df.format(mbytes) + " Gb";
		}
		double mbytes = size / (1024 * 1024);
		return df.format(mbytes) + " Mb";
	}

	/**
	 * Restituisce la struttura gerarchica dei folder contenuti in un folder
	 *
	 * @param folderRoot
	 * @return la lista delle directory della directory specificata
	 */
	public static List<String> getDirectoryStructure(String folderRoot) {
		String temp = recurseInDirFrom(folderRoot);
		String[] result = temp.split("\\|");
		return Arrays.asList(result);
	}

	/**
	 * Lettura ricorsiva della struttura di un folder
	 *
	 * @param dirItem il path root della directory
	 * @return una stringa con la struttura gerarchica delle directory
	 */
	private static String recurseInDirFrom(String dirItem) {
		String result = dirItem;
		File file = new File(dirItem);
		if (file.isDirectory()) {
			String[] list = file.list();
			for (int i = 0; i < list.length; i++) {
				result = result + "|" + recurseInDirFrom(dirItem + File.separatorChar + list[i]);
			}
		}
		return result;
	}

	/**
	 * Calcolo del checksum (CRC32) di un file (utile per confronti tra file)
	 * @param fileName il nome del file
	 * @return il valore numerico che rappresenta il checksum del file, -1 in caso di errore
	 */
	public static long calculateChecksum(String fileName) {
		try {
			if (!fileExists(fileName))
				return -1;
			CheckedInputStream cis = new CheckedInputStream(new FileInputStream(fileName), new CRC32());
			try{
				byte[] buf = new byte[128];
				while(cis.read(buf) >= 0) { }
				return cis.getChecksum().getValue();
			}finally{
				cis.close();
			}
		} catch (Throwable err) {
			return -1;
		}
	}

	/**
	 * Copia un file
	 * @param fileSource file di partenza
	 * @param fileDest file di destinazione
	 */
	public static void fileCopy(String fileSource, String fileDest) {
		File source = new File(fileSource);
		if (!source.exists()) return;
		File dest = new File(fileDest);
		FileReader in = null;
		FileWriter out = null;
		try {
			in = new FileReader(source);
			out = new FileWriter(dest);
			int c;
			while ( (c = in.read()) != -1 ) {
				out.write(c);
			}
			in.close();
			out.close();
		} catch(Throwable err) {
			logger.error("Errore in copia:" + fileSource + " -> " + fileDest, err);
		}
	}

	private static final String FileSeparator = ""+File.separatorChar;
	/**
	 * Concatenazione di due URL
	 * @param a
	 * @param b
	 * @return un URL che rappresenta la concatenazione dell'input
	 */
	public static String concatPath(String a, String b){
		StringBuffer result = new StringBuffer();
		if (a != null && b != null){
			boolean aCheck = a.endsWith(FileSeparator);
			boolean bCheck = b.startsWith(FileSeparator);
			if (aCheck && bCheck){
				result.append(a);
				result.append(b.substring(1));
			} else if (aCheck ^ bCheck){
				result.append(a);
				result.append(b);
			} else {
				result.append(a);
				result.append(FileSeparator);
				result.append(b);
			}
		} else if (a != null){
			result.append(a);
		} else if (b != null){
			result.append(b);
		}
		return result.toString();
	}
}