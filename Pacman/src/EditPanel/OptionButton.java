package EditPanel;

import java.awt.Color;
import javax.swing.JButton;

public class OptionButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char c;

	public OptionButton(char c) {
		super();
		
		this.c = c;
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
	}

	public OptionButton() {

	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}
}
