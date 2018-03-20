package lib.finlay.core.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Performs basic file operations.
 * @author Finlay
 * @version 1.0
 * @since 1.0
 */
public final class FileOperations {

	private FileOperations() {}
	
	/**
	 * Reads an input stream to a collection.
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> readInputStream(InputStream stream) throws IOException {
		ArrayList<String> contents = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			String line;
			
			while((line = reader.readLine()) != null) {
				contents.add(line);
			}
		} catch (IOException e) {
			throw new IOException("Unable to read stream contents.", e);
		}
		
		return contents;
	}
	
	/**
	 * Reads a file in serial order
	 * @param file The file to read
	 * @return An {@link ArrayList} of the contents of the file.
	 * @throws IOException Thrown if the file cannot be read successfully.
	 */
	public static ArrayList<String> serialFileRead(File file) throws IOException {
		return readInputStream(new FileInputStream(file));
	}
	
	/**
	 * Writes contents to a file.
	 * @param file The file to write to.
	 * @param content The content to write (each element is a seperate line)
	 * @param append Whether or not to append t	o the file.
	 * @throws IOException Thrown if the file cannot be written to successfully.
	 */
	public static void serialFileWrite(File file, Collection<String> content, boolean append) throws IOException {
		if(content == null) {
			return;
		}
		
		if(content.isEmpty()) {
			return;
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
			for(String line : content) {
				if(line == null) {
					continue;
				}
				
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			throw new IOException("Unable to write file contents", e);
		}
	}
	
}
