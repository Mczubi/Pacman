package Gamemech;

public class Bigcoin extends Coin {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bigcoin(int x, int y, String s) {
		super(x,y,s);
		this.setValue(80);
//		this.setBounds(x, y, this.getWidth(), this.getHeight());
		eatAble = true;
	}
	
}
