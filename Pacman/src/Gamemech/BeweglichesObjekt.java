package Gamemech;

import java.awt.Component;

public abstract class BeweglichesObjekt extends UnbeweglichesObjekt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int x;
	protected int y;
	protected int xrichtung = 0;
	protected int yrichtung = 0;
	protected int standardmovspeed = 4;
	protected int size = 16;

	public BeweglichesObjekt(String dateiname,int size) {
		super(dateiname);
		this.size = size;
		this.standardmovspeed = this.size == 16 ? 2:4; 
	}

	protected void bewege() {
		if (this.x + this.xrichtung * this.standardmovspeed < 0) { // linke seite begrenzung
			this.x = 0;
			this.xrichtung = 0;
		} else if (this.x + this.xrichtung * this.standardmovspeed > 240 - 16) { // rechte seite begrenzung
			this.x = 240;
			this.xrichtung = 0;
		} else {
			this.x += this.xrichtung * this.standardmovspeed;
		}
		
		if (this.y + this.yrichtung * this.standardmovspeed < 0) { // obere seite begrenzung
			this.y = 0;
			this.yrichtung = 0;
		} else if (this.y + this.yrichtung > 240 - 16) { // untere seite begrenzung
			this.y = 240 - 16;
			this.yrichtung = 0;
		} else {
			this.y += this.yrichtung * this.standardmovspeed;
		}
		this.setBounds(x,y,getWidth(),getHeight());
	}
	
	protected void newRandomdirection() {}

	public int[] getNextCordinates() {
		int[] ret = new int[2];
		ret[0] = this.x + xrichtung * standardmovspeed; 
		ret[1] = this.y + yrichtung * standardmovspeed; 
		
		return ret;
		
	}
	
	protected void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	protected void setRichtung(int x, int y) {
			this.xrichtung = x;
			this.yrichtung = y;
			
	}
	
	protected boolean isPossible(int x, int y) {
		Component[] r = this.getAlleObjekteBei(x, y);
		if (r == null) {
			return true;
		} else {
			for (int i = 0; i < r.length; i++) {
				if (r[i] instanceof Border || r[i] instanceof Ghost && !((Ghost)r[i]).isEatable() && !(this instanceof Player)
					|| r[i] instanceof Ghost && this instanceof Ghost)
					return false;
			}
		}
		return true;
	}
	protected boolean checkRichtung(int x2, int y2) {
		int newX = this.x + x2 * standardmovspeed;
		int newY = this.y + y2 * standardmovspeed;
		return this.isPossible(newX, newY);
	}

	public int getXrichtung() {
		return this.xrichtung;
	}
	public int getYrichtung() {
		return this.yrichtung;
	}
}
