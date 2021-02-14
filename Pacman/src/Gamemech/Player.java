package Gamemech;

public class Player extends BeweglichesObjekt
{

	private static final long serialVersionUID = 1L;
	private int life = 3;
	private int highscore = 0;
	private int[] last_key = new int[2];
	private String name;
	
	
	public Player(int x, int y, int i, String s) {
		super(s,i);
		this.x = x;
		this.y = y;
		this.name = s.substring(0, s.length()-5);
		this.eatAble = true;
		last_key[0] = -10;
		last_key[1] = -10;
		this.setBounds(x, y, this.getWidth(), this.getHeight());
	}
	
	public int[] getnextCoordinates() {
		if(last_key[0] != -10)
			if(this.checkRichtungMoveable(last_key[0], last_key[1])) {
				super.setRichtung(last_key[0], last_key[1]);
				last_key[0] = -10;
				last_key[1] = -10;
			}
		chPImage();
		return super.getNextCordinates();
	}
	
	public void bewege() {
		if (this.getObjektBei(x, y) != null) {
			// Coin
			if ((this.getObjektBei(x, y) instanceof Coin)) {
				this.setHighscore(((Coin) this.getObjektBei(x, y)).getValue());
				getParent().remove(this.getObjektBei(x, y));
				// Bigcoin
			} else
				if ((this.getObjektBei(x, y) instanceof Bigcoin)) {
					getParent().remove(this.getObjektBei(x, y));
				}
		}
		super.bewege();
	}

	public boolean setPRichtung(int x, int y) {
		if (this.checkRichtungMoveable(x, y) ) {
			super.setRichtung(x, y);
			return true;
		} 
		
		last_key[0] = x;
		last_key[1] = y;
		return false;
			
	}

	public void chPImage() {
		switch(this.xrichtung) {
			case 1:
				this.chImage(this.name + "R.png");
				break;
			case -1:
				this.chImage(this.name + "L.png");
				break;
			case 0: 
				switch(this.yrichtung) {
					case 1:	this.chImage(this.name + "D.png");
							break;
					case -1:this.chImage(this.name + "U.png");
							break;	
					default:break;
				}
				break;
			default: break;
		}
	}

	private boolean checkRichtungMoveable(int x2, int y2) {
		if(!(this.x % 16 == 0 && this.y % 16 == 0))
			return false;
		return this.checkRichtung(x2, y2);

	}
	

	public int getLife() {
		return life;
	}

	public void setLife() {
		this.life--;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore += highscore;
	}

	public void setKiller() {
		this.eatAble = false;
	}

	public void remKiller() {
		this.eatAble = true;
	}
}
