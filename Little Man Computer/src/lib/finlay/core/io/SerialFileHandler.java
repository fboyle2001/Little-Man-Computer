package lib.finlay.core.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Serial file reading and writing class
 * @author Finlay
 * @version 1.0
 * @since 1.0
 */
public class SerialFileHandler extends AbstractFileHandler {
	
	/**
	 * Creates a new instance using a file object.
	 * @param file The file to be handled by this instance.
	 * @throws IOException If the file creation fails (assuming it did not already exist) this is thrown.
	 */
	public SerialFileHandler(File file) throws IOException {
		super(file);
		getContents(true);
	}
	
	/**
	 * Creates a new instance using strings to create the file object.
	 * @param directory The directory containing the file.
	 * @param fileName The name of the file.
	 * @throws IOException If the file creation fails (assuming it did not already exist) this is thrown.
	 */
	public SerialFileHandler(String directory, String fileName) throws IOException {
		super(new File(directory, fileName));
		getContents(true);
	}
	
	/**
	 * Duplicates a file handler. The file to handle and the cached contents are the same.
	 * @param fileHandler The file handler to share the file with.
	 */
	public SerialFileHandler(AbstractFileHandler fileHandler) {
		super(fileHandler);
	}

	/**
	 * Retrieves the contents of the file from the disk.
	 * @param updateData Used to decide whether to read the data from disk (true) or from cache (false)
	 * @return An {@link ArrayList} containing the lines of content.
	 * @throws IOException IOException If the file cannot be read, this is thrown.
	 * @see {@link #getContents()}, {@link FileOperations#serialFileRead(File)}
	 */
	@Override
	public ArrayList<String> getContents(boolean updateData) throws IOException {
		if(!updateData) {
			if(cachedContents != null && cachedContents.size() != 0) {
				return cachedContents;
			}
		}
		
		ArrayList<String> contents = FileOperations.serialFileRead(file);
		
		this.cachedContents = contents;
		return contents;
	}
	
	/**
	 * Gets the cached contents as a stream.
	 * @return A stream of each line.
	 */
	public Stream<String> asStream() {
		return getCachedContents().stream();
	}
	
	/**
	 * Checks if the file meets the predicate.
	 * @param predicate The predicate to match.
	 * @return True if any matches are found. False otherwise.
	 */
	public boolean has(Predicate<String> predicate) {
		return asStream().anyMatch(predicate);
	}
	
	/**
	 * Writes the specified content to the disk.
	 * @param content The content (per line) to write. Each element is separated by a new line during writing.
	 * @param append Decides whether to append to a file or not.
	 * @throws IOException If the file cannot be written to, this is thrown.
	 * @see {@link FileOperations#serialFileWrite(File, ArrayList)}
	 */
	@Override
	public void writeContents(Collection<String> content, boolean append) throws IOException {
		FileOperations.serialFileWrite(file, content, append);
	}
	
	/**
	 * Duplicates a file handler and the content's of the file.
	 * @param newFile The new file that will contain the duplicated content.
	 * @return A new {@link SerialFileHandler} containing the contents.
	 * @throws IOException Thrown if the duplication process of the files is unsuccessful.
	 */
	@Override
	public SerialFileHandler duplicateFile(File newFile) throws IOException {
		SerialFileHandler duplicate = new SerialFileHandler(newFile);
		duplicate.writeContents(this.getContents(), false);
		return duplicate;
	}
	
}
