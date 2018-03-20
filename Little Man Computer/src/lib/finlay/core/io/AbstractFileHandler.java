package lib.finlay.core.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract file handler that is implemented by all file handlers
 * @author Finlay
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractFileHandler implements Iterable<String> {

	protected final File file;
	protected ArrayList<String> cachedContents;
	
	public AbstractFileHandler(File file) throws IOException {
		this.file = file;
		this.cachedContents = new ArrayList<>();
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		if(file.isDirectory()) {
			throw new IllegalArgumentException("The file cannot be a directory.");
		}
	}
	
	public AbstractFileHandler(String directory, String fileName) throws IOException {
		this(new File(directory, fileName));
	}
	
	public AbstractFileHandler(AbstractFileHandler other) {
		this.file = other.getFile();
		this.cachedContents = other.getCachedContents();
	}
	
	/**
	 * Retrieves the cached contents from the last file reading update.
	 * @return The cached contents.
	 * @see {@link #getContents()}, {@link #getContents(boolean)}
	 */
	public ArrayList<String> getCachedContents() {
		return cachedContents;
	}

	/**
	 * Retrieves the file being handles by this instance.
	 * @return The file in the instance.
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Compares the content of two files to find if they are an exact match.
	 * @param other The other file to compare the contents of.
	 * @return True if they match, false if they do not.
	 */
	public boolean contentEqual(SerialFileHandler other) {
		try {
			if(other.getContents().size() != getContents(true).size()) {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		
		for(int i = 0; i < getCachedContents().size(); i++) {
			if(!getCachedContents().get(i).equals(other.getCachedContents().get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public Iterator<String> iterator() {
		return cachedContents.iterator();
	}
	
	/**
	 * Retrieves the contents of the file from the disk <br>
	 * This uses the {@link #getContents(boolean)} function with a default argument of true.
	 * @return An {@link ArrayList} containing the lines of content.
	 * @throws IOException If the file cannot be read, this is thrown.
	 * @see {@link #getContents(boolean)}, {@link FileOperations#serialFileWrite(File, ArrayList)}
	 */
	public ArrayList<String> getContents() throws IOException {
		return getContents(true);
	}
	
	/**
	 * Duplicates a file handler and the content's of the file.
	 * @param directory The directory that will contain the file.
	 * @param fileName The new file's name.
	 * @return A new {@link SerialFileHandler} containing the contents.
	 * @throws IOException Thrown if the duplication process of the files is unsuccessful.
	 */
	public AbstractFileHandler duplicateFile(String directory, String fileName) throws IOException {
		return duplicateFile(new File(directory, fileName));
	}
	
	public abstract void writeContents(Collection<String> content, boolean append) throws UnsupportedOperationException, IOException;
	
	public abstract ArrayList<String> getContents(boolean updateData) throws UnsupportedOperationException, IOException;
	
	public abstract AbstractFileHandler duplicateFile(File newFile) throws UnsupportedOperationException, IOException;
	
}
