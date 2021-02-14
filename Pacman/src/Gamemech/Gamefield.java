package Gamemech;

import java.awt.*;
import javax.swing.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Gamefield extends JPanel implements NativeKeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int zeit, ini;
	private int fps = 22;
	private int refreshrate = 60;

	private char[][] map;
	private JComponent[][] all = new JComponent[15][15];
	private int playerIndex;
	private char theme;
	private boolean secret;
	private String size;
	private int multi = 16;

	public Gamefield(char theme, char[][] field, boolean secret) {
		this.secret = secret;
		this.theme = theme;
		this.map = field;
		
		setSize(getSize());
		setBounds(80, 100 , 240, 240);
		setLayout(null);
		
		if (theme == 'c') {
			setBackground(new Color(1, 1, 50));
		} else if (theme == 'l' && !secret) {
			setBackground(new Color(86, 62, 5));
		} else {
			setBackground(new Color(165,0,0));
		}
		setFocusable(true);
		
		GlobalScreen.addNativeKeyListener(this);

		iniLevel(theme);
		setVisible(true);
	}

	private void iniLevel(char theme) {
		this.removeAll();
		String s = theme == 'c' ? "BlueWall" : "LightWall";
		size = !secret ? "/16x16-Sprites/" : "/32x32-Sprites/";
		for (int i = 0; i < 225; i++) {
			boolean nothing = false;
			switch (map[i % 15][i / 15]) {
			case 'g':	all[i / 15][i % 15] = new Ghost(i / 15 * multi, i % 15 * multi,multi,"/Gamemech" + size + "Ghosts/" + whichGhost());
						break;
			case 'w':	all[i / 15][i % 15] = new Border(i / 15 * multi, i % 15 * multi,"/Gamemech" + size + "Walls/" + s + ".png");
						break;
			case 'c':	all[i / 15][i % 15] = new Coin(i / 15 * multi, i % 15 * multi, "/Gamemech" + size + "Collect/Coin.png");
					break;
			case 'p': 	all[i / 15][i % 15] = new Player(i / 15 * multi, i % 15 * multi, multi ,"/Gamemech" + size + "Pacman/PacU.png");
						playerIndex = i;
						break;
			case 'b':	all[i / 15][i % 15] = new Bigcoin(i / 15 * multi, i % 15 * multi,"/Gamemech" + size + "Collect/CoinBig.png");
						break;
			default:	nothing = true;
						break;
			}
			if (!nothing)
				this.add(all[i / 15][i % 15]);
		}
		GamePanel.newHighscore(0);

	}
	//I bin ein riesen PEnishead und hon an kluanen penishead
	private String whichGhost() {
		double i = Math.random();
		return (i <= 0.25) ? "Green/GhostGreenD.png": (i <= 0.5) ? "Blue/GhostBlueD.png" : 
			   (i <= 0.75) ? "Red/GhostRedD.png" : "Pink/GhostPinkD.png";
	}

	public void paint(Graphics g) {
		
		// Hole alle Objekte des contentPanes des Formulars durch
		Component[] komponenten = this.getComponents();
		int amountCoin = 0;
		
		if(ini == -1 )
			startcountdown();
		if(zeit == 130) {
			playerDisableKiller(komponenten);
		}
		
		for (int i = 0; i < komponenten.length; i++) {
			int x,y;
			if (komponenten[i] instanceof Coin)
				amountCoin++;
			if (komponenten[i] instanceof BeweglichesObjekt) {
				BeweglichesObjekt u = (BeweglichesObjekt) komponenten[i];
				if (u instanceof Player) {
					GamePanel.newHighscore(((Player) u).getHighscore());
					GamePanel.newLifeammount(((Player) u).getLife());
					int[] coords = ((Player) u).getnextCoordinates(); 
					x = coords[0];
					y = coords[1];
				}else {
					int[] coords = u.getNextCordinates(); 
					x = coords[0];
					y = coords[1];
					
				}

				
				// lösen des Mauerbuggens
				if (u instanceof Ghost) {
					if (x < 0 || y < 0) {
						newGhost(u);
					}
				}
				if (u.isPossible(x, y)) {
					// Überprüfng um welchen Essbaren gegenstand es sich handelt
					// (Player)
					if(u instanceof Player && u.getObjektBei(x, y) != null) {
						if(u.getObjektBei(x, y) instanceof Bigcoin) {
							playerSetKiller(komponenten,u);
						}
						if((u.getObjektBei(x, y) instanceof Ghost) && ((Ghost)u.getObjektBei(x, y)).isTilted()) {
							 ((Player) u).setHighscore(((Ghost) u.getObjektBei(x, y)).getValue());
							 newGhost((BeweglichesObjekt)u.getObjektBei(x, y));
							 
						}
					}
					

					if (u instanceof Ghost && u.getObjektBei(x, y) != null) {
						if ((u.getObjektBei(x, y) instanceof Player) && ((UnbeweglichesObjekt)u.getObjektBei(x, y)).isEatable()) {

							((Player) u.getObjektBei(x, y)).setLife();
							newStart();
							// Bigcoin
						}
					}
					u.bewege();
				} else {
//					if(x % 16 == 0 && y % 16 == 0)
					u.newRandomdirection();
				}
			}
		}
		bremse((int) (1000 / this.refreshrate));

		if (amountCoin == 0) {
			newCoins();
			newStart();
		}

		super.paint(g);
		repaint();
	}

	private void playerDisableKiller(Component[] komponenten) {
		for (int n = 0; n < komponenten.length; n++) {
			if (komponenten[n] instanceof Ghost) {
				Ghost geist = (Ghost) komponenten[n];
				geist.remTilted();
			}
		}
		ini = 0;
		((Player) all[playerIndex/15][playerIndex%15]).remKiller();
	}

	private void playerSetKiller(Component[] komponenten, BeweglichesObjekt u) {
		for (int n = 0; n < komponenten.length; n++) {
			if (komponenten[n] instanceof Ghost) {
				Ghost geist = (Ghost) komponenten[n];
				geist.setTilted();
			}
		}
		((Player) u).setKiller();
		zeit = 0;
		ini = -1;
		startcountdown();
		
	}

	private void newGhost(BeweglichesObjekt u) {
		boolean stop = false;
		for (int i = 0; i < 225 && !stop; i++) {
			switch (map[i % 15][i / 15]) {
				case 'g':
					if(u.getObjektBei(i/15*multi, i%15*multi) == null) {
						u.setLocation(i / 15 *multi, i % 15 *multi);
						stop = true;
						u.setXY(i / 15 *multi, i % 15 *multi);
					}
					break;
				default:

					break;
			}
		}

	}

	private void newCoins() {
		for (int i = 0; i < 225; i++) {
			boolean nothing = false;
			switch (map[i % 15][i / 15]) {
			case 'c':	all[i / 15][i % 15] = new Coin(i / 15 * multi, i % 15 * multi, "/Gamemech" + size + "Collect/Coin.png");
						break;
			case 'b':	all[i / 15][i % 15] = new Bigcoin(i / 15 * multi, i % 15 * multi,"/Gamemech" + size + "Collect/CoinBig.png");
						break;
			default:	nothing = true;
						break;
			}
			if (!nothing)
				this.add(all[i / 15][i % 15]);
		}

	}

	private void newStart() {
		if (((Player) all[playerIndex / 15][playerIndex % 15]).getLife() > 0) {
			for (int i = 0; i < 225; i++) {
				switch (map[i % 15][i / 15]) {
				case 'g':	((BeweglichesObjekt) all[i / 15][i % 15]).setXY(i / 15 * multi, i % 15 * multi);
									((BeweglichesObjekt) all[i / 15][i % 15]).setLocation(i / 15 * multi, i % 15 * multi);
							break;
				case 'p':	((BeweglichesObjekt) all[i / 15][i % 15]).setXY(i / 15 * multi, i % 15 * multi);
							((BeweglichesObjekt) all[i / 15][i % 15]).setLocation(i / 15 * multi, i % 15 * multi);
							((Player) all[i / 15][i % 15]).setRichtung(0, 0);
							break;
				default:	break;
				}
			}
		bremse(100);
		} else {
			int highscore = ((Player) all[playerIndex / 15][playerIndex % 15]).getHighscore();
			this.removeAll();
			GamePanel.newLifeammount(0);
			JLabel lost = new JLabel("Highscore:" + highscore);
			if (theme != 'c') {
				lost.setForeground(Color.BLACK);
			} else {
				lost.setForeground(Color.white);
			}
			lost.setBounds(80, 0, 240, 240);
			this.add(lost);
		}

	}

	 private void startcountdown() {
		 zeit++;
	 }

	private void bremse(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		Player p = (Player) all[playerIndex / 15][playerIndex % 15];

		switch (e.getKeyCode()) {
		case NativeKeyEvent.VC_DOWN: {
			if(p.setPRichtung(0, 1));
				p.chImage("/Gamemech" + size + "Pacman/PacD.png");
			break;
		}
		case NativeKeyEvent.VC_UP: {
			if(p.setPRichtung(0, -1));
//				p.chImage("/Gamemech" + size + "Pacman/PacU.png");
			break;
		}
		case NativeKeyEvent.VC_RIGHT: {
			if(p.setPRichtung(1, 0));
//				p.chImage("/Gamemech" + size + "Pacman/PacR.png");
			break;
		}
		case NativeKeyEvent.VC_LEFT: {
			if(p.setPRichtung(-1, 0));
//				p.chImage("/Gamemech" + size + "Pacman/PacL.png");
			break;
		}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub

	}
}
