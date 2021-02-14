package Gamemech;

import java.util.ArrayList;
import java.util.Random;

public class Ghost extends BeweglichesObjekt
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String tilt = "/Ghosts/Tilted/Tilted";
	private boolean tilted = false;
	private int howlong = 0;
	private int minblock = 32;
	private int value = 200;
	
	
	public Ghost(int x, int y, int size, String s) {
		super(s,size);
		name = s.substring(0, s.length()-5);
		tilt = s.substring(0,23)+tilt;
		this.x = x;
		this.y = y;
		this.setBounds(x, y, this.getWidth(), this.getHeight());		
	}
	
	
	public void bewege() {
		
		if((howlong % minblock == 0 && howlong > 0 && x%this.size == 0 && y%this.size == 0) || this.xrichtung == this.yrichtung)
			newRandomdirection();
		howlong += standardmovspeed;
		super.bewege();
	}
	
	protected void newRandomdirection() {
		howlong = 0;
		int[] dir = {1,-1, 0};
		java.util.List<Integer> possibledir = new ArrayList<Integer>();
		
		for(int i = 0; i < 4; i++) {
			if(this.checkRichtung(dir[i < 2 ? i:2], dir[i > 1 ? i - 2 : 2]))
				possibledir.add(i);
		}
		Random r = new Random();
		if(possibledir.size() > 0) {
			
			int pos = r.nextInt(possibledir.size());
			
			int x, y, val = possibledir.get(pos);
			x = dir[val < 2 ? val:2];
			y = dir[val > 1 ? val - 2 : 2];
			
			this.setRichtung(x, y);
			chooseImage(x,y);
		}
	
		repaint();
		
	}

	private void chooseImage(int x, int y) {
		if(x == 1)
			chImage((tilted?tilt:name)+"R.png");
		if(x == -1)
			chImage((tilted?tilt:name)+"L.png");
		if(x == 0) {
			if(y == 1)
				chImage((tilted?tilt:name)+"D.png");
			if(y == -1)
				chImage((tilted?tilt:name)+"U.png");
		}
			
	}


	// ############## GETSET #####################
	
	public boolean isDead() {
		return gestorben;
	}
	public void setDead(boolean dead) {
		this.gestorben = dead;
	}
	public boolean isTilted() {
		return tilted;
	}
	public void setTilted() {
		this.tilted = true;
		this.eatAble = true;
		chImage(tilt+"D.png");
		repaint();
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}


	public void remTilted() {
		this.tilted = false;
		this.eatAble = false;
		chImage(name+"D.png");
		repaint();
		
	}
}
