package EditPanel;

import javax.swing.JButton;

public class MatrixButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xpos;
	private int ypos;
	
	public MatrixButton(int x , int y) {
		super();
		this.setXpos(x);
		this.setYpos(y);
	}
	
	public int getXpos() {
		return xpos;
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
}
