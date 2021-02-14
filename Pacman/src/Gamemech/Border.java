package Gamemech;

public class Border extends UnbeweglichesObjekt
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Border(int x, int y, String dateiname) {
		super(dateiname);
		this.setBounds(x, y, this.getWidth(), this.getHeight());
	}
	

}
