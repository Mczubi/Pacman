package Gamemech;

public class Coin extends UnbeweglichesObjekt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value = 20;
	
	public Coin(int x, int y, String s) {
		super(s);
		this.setBounds(x, y, this.getWidth(), this.getHeight());
		this.eatAble = true;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
}
