import javax.swing.*;					// Wegen JFrame

import Gamemech.UnbeweglichesObjekt;

import java.awt.*;						// Wegen Container, Graphics, usw.
import java.awt.event.*;			// Wegen WindowAdapter, WindowEvent

/**
 * Programm welches zeigen soll, wie unbewegliche Objekte erstellt, positioniert
 * und dargestellt werden können. Das Programm zeigt auch, wie auf Tastaturereignisse
 * im Formular reagiert werden kann
 * @author Michael Wild
 */
public class SpielVorlageGUI extends JFrame
{
	private final int ANZAHL_UNBEWEGLICHE_OBJEKTE = 30;
	/*
	 * Merkt sich die X-Richtung der Verschiebung. Ist richtung negativ, wird nach links
	 * geschoben, ist richtung positiv, dann nach rechts
	 */
	private int richtung = 1;
	
	/**
	 * Konstruktor
	 */
	public SpielVorlageGUI() {
		super("SpielVorlageGUI");
		setBounds(10,10,400,400);
		setResizable(false);
		
		// Oberfläche des Fensters auf welcher die Objekte platziert werden sollen
		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);
		setVisible(true);
		
		// Positioniert unbewegliche Objekte im Fenster an zufällig ausgewählten Positionen
		// so dass diese sich nicht überdecken
		for (int i = 0; i < ANZAHL_UNBEWEGLICHE_OBJEKTE; i++) {
			UnbeweglichesObjekt o = new UnbeweglichesObjekt("mauer.gif");
			contentPane.add(o);
			int x = 0;
			int y = 0;
			do {
				x = (int)(Math.random()*400);
				y = (int)(Math.random()*400);
			} while (o.getObjektBei(x,y) != null);
			o.setLocation(x,y);
		}
		
    // Registrieren des Fenster-Schließen-Abhörers der das Fenster und folglich das
		// Programm beendet
		addWindowListener(
 			new WindowAdapter() {
 				public void windowClosing(WindowEvent e) {
 				  setVisible(false);
 				  dispose();
 				  System.exit(0);
 				}
 			}
 		);
		
    // Registrieren des Tastatur-Abhörers der die Richtung des Verschiebens bestimmen lässt
    addKeyListener(
     	new KeyAdapter() {
     		public void keyPressed(KeyEvent e) {
     			switch (e.getKeyCode()) {
 	  				case 37: {
 	  					// Links
 	  					richtung = -1;
 	  					break;
 	  				}
    				case 39: {
    					// Rechts
    					richtung = 1;
    					break;
    				}
    			}
    		}
    	}
    );
	}
	
	/**
	 * Diese Methode wird automatisch aufgerufen und zwar dann, wenn das Fenster neu 
	 * gezeichnet werden muss. Diese Methode ruft automatisch alle paint-Methoden jener
	 * Objekte auf, die dem contentPane des Formulars zugeordnet wurden. Am Ende dieser
	 * Methode wird für eine gewisse Zeit das Programm angehalten und dann über repaint()
	 * die Methode paint wieder rekursiv gestartet 
	 */
	public void paint(Graphics g) {
		// Hole alle Objekte des contentPanes des Formulars durch
		Component[] komponenten = this.getContentPane().getComponents();
		if (komponenten != null && komponenten.length > 0) {
			for (int i = 0; i < komponenten.length; i = i + 1) {
				// Kontrolliere für alle Objekte im Formular ob sie in Richtung richtung
				// verschoben werden können, ohne dass sie mit anderen Objekten oder dem 
				// Fensterrand kollidieren
				UnbeweglichesObjekt u = (UnbeweglichesObjekt)(komponenten[i]);
				if (u.getObjektBei(u.getX() + richtung, u.getY()) == null)
					// Sollten die Objekte nicht kollidieren, werden sie in Richtung richtung
					// verschoben
					u.setLocation(u.getX() + richtung, u.getY());
			}
		}
		// Dieser Aufruf bewirkt, dass die normale (geerbte) paint-Methode des Formulars 
		// aufgerufen wird, welche ihrerseits dafür sorgt, dass für alle Objekte des 
		// contentPane des Formulars die paint-Methode gestartet wird
		super.paint(g);
		// Programm pausiert
		bremse(100);
		// Sorgt dafür, dass paint wiederum aufgerufen wird
		repaint();
	}

	/**
	 * Lässt das Programm für die angegebene Anzahl von Millisekunden pausieren
	 * @param millis
	 */
	private void bremse(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}	
	}
}
