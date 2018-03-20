package lib.finlay.core.io;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class JTextAreaOutputStream extends OutputStream {

	private final JTextArea output;
	
	public JTextAreaOutputStream(JTextArea output) {
		this.output = output;
	}
	
	@Override
	public void write(int b) throws IOException {
		output.append(String.valueOf((char) b));
		output.setCaretPosition(output.getDocument().getLength());
	}
	
}
