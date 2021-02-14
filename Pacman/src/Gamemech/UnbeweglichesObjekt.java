package Gamemech;

import javax.swing.*; // Wegen JComponent
import java.awt.*; // Wegen Image, Graphics
import java.net.*; // Wegen URL
import java.util.Vector;

/**
 * Diese Klasse ermöglicht es, ein unbewegliches Objekt, welches durch ein Bild
 * dargestellt wird, anzuzeigen. Das Bild, welches das Objekt darstellt, wird im
 * Konstruktor übergeben. Weiters verfügt ein Objekt dieser Klasse über die
 * Möglichkeit zu erkennen, ob das Objekt, das an eine bestimmte Position
 * verschoben wird, mit einem anderen Objekt kollidiert
 * 
 * @author Michael Wild
 */
public class UnbeweglichesObjekt extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Bild welches das Objekt darstellt
	 */
	protected Image bild = null;
	/**
	 * Merkt sich, ob Objekt gestorben ist
	 */
	protected boolean gestorben = false;
	protected boolean eatAble = false;

	/**
	 * Konstruktor der das Bild, welches das unbewegliche Objekt darstellt, lädt
	 * 
	 * @param dateiname
	 *            und Pfad des Bildes
	 */
	public UnbeweglichesObjekt(String dateiname) {
		// Lade Bild das angezeigt werden soll. Dabei muss das Bild im Ordner dieser
		// Klasse positioniert werden
		URL url = this.getClass().getResource(dateiname);
		if (url == null)
			System.out.println("Datei nicht gefunden");
		else {
			this.bild = getToolkit().getImage(url);
			prepareImage(bild, this);
			// Warte bis die Eigenschaften des Bildes geladen sind
			while ((checkImage(bild, this) & PROPERTIES) != PROPERTIES) {
				try {
					// Pause, um dem Ladevorgang keine Ressourcen zu nehmen
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Stelle Größe des Objektes auf Größe des Bildes ein
			this.setSize(this.bild.getWidth(this), this.bild.getHeight(this));
		}
	}

	protected void chImage(String dateiname) {
		URL url = this.getClass().getResource(dateiname);
		if (url == null)
			System.out.println("Datei nicht gefunden");
		else {
			this.bild = getToolkit().getImage(url);
			prepareImage(bild, this);
			// Warte bis die Eigenschaften des Bildes geladen sind
			while ((checkImage(bild, this) & PROPERTIES) != PROPERTIES) {
				try {
					// Pause, um dem Ladevorgang keine Ressourcen zu nehmen
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			repaint();
		}
	}

	/**
	 * Methode, welche automatisch aufgerufen wird und das Bild in der Größe des
	 * Objektes darstellt
	 */
	public void paint(Graphics g) {
		if (bild != null)
			g.drawImage(this.bild, 0, 0, this);
	}

	/**
	 * Objekt stirbt, indem es sich selbständig vom contentPane des Formulars
	 * entfernt. Dabdurch wird das Objekt auch nicht mehr angezeigt
	 */
	public void stirb() {
		if (!this.gestorben && this.getParent() != null) {
			this.getParent().remove(this);
			this.gestorben = true;
		}
	}

	/**
	 * Ermittelt ob Objekt gestorben ist
	 * 
	 * @return true falls Objekt bereits gestorben ist, ansonsten false
	 */
	public boolean getGestorben() {
		return this.gestorben;
	}

	/**
	 * Kontrolliert ob das Objekt - verschoben zur übergebenen x- und y-Position -
	 * mit einem anderen Objekt kollidiert. Ist das der Fall, so wird das andere
	 * kollidierende Objekt zurück geliefert.<br>
	 * Es wird ebenfalls der contentPane des Formulars zurück geliefert, falls das
	 * Objekt außerhalb des Formulars positioniert werden sollte, d. h. über den
	 * Rand des Formulars hinaus ragen würde
	 * 
	 * @param x
	 *            die zu kontrollierende x-Koordinate
	 * @param y
	 *            die zu kontrollierende y-Koordinate
	 * @return das Objekt das mit dem Objekt kollidiert oder - falls das Objekt an
	 *         der übergebenen Position außerhalb des Frames positioniert wird -
	 *         wird der contentPane zurück geliefert. Liefert null zurück, falls das
	 *         Objekt ohne Überdeckung an der übergebenen Position positioniert
	 *         werden kann
	 */
	public Component[] getAlleObjekteBei(int x, int y) {
		Vector<Component> retVec = new Vector<Component>();
		if (this.getParent() != null) {
			// Kontrolliere ob neue Position außerhalb des Frames liegt
			if (x < 0 || y < 0 || x + this.getWidth() > this.getParent().getWidth()  ||
				y + this.getHeight() > this.getParent().getHeight())
			{
				// In diesem Fall wird der contentPane des Formulars übergeben
				//ret.add(this.getParent());
			}
				// Kontrolliere ob sich die neue Position mit anderen Objekten überdeckt
			Rectangle neuePosition = 
				new Rectangle(x,y,this.getWidth(),this.getHeight());
			// Gehe alle Objekte des Formulars durch und vergleiche ihre Position mit der 
			// neuen Position
			Component[] komponenten = this.getParent().getComponents();
			int i = 0;
		  while (komponenten != null && i < komponenten.length) {
		  	// Wenn das Objekt nicht das zu kontrollierende Objekt ist und das Objekt
		  	// mit dem zu Kontrollierendem zusammenfällt
		  	if (komponenten[i] != this && 
		  		neuePosition.intersects(komponenten[i].getBounds()))
		  			retVec.add(komponenten[i]);
		  	i++;
			  
		  }
		}
		
		Component[] ret = new Component[retVec.size()];
		
		for(int i = 0; i < ret.length; i++)
			ret[i] = retVec.elementAt(i);
		
		return ret;
	}
	
	public Component getObjektBei(int x, int y) {
		Component ret = null;
		if (this.getParent() != null) {
			// Kontrolliere ob neue Position außerhalb des Frames liegt
			if (x < 0 || y < 0 || x + this.getWidth() > this.getParent().getWidth()
					|| y + this.getHeight() > this.getParent().getHeight())
				// In diesem Fall wird der contentPane des Formulars übergeben
				ret = this.getParent();
			else {
				// Kontrolliere ob sich die neue Position mit anderen Objekten überdeckt
				Rectangle neuePosition = new Rectangle(x, y, this.getWidth(), this.getHeight());
				// Gehe alle Objekte des Formulars durch und vergleiche ihre Position mit der
				// neuen Position
				Component[] komponenten = this.getParent().getComponents();
				int i = 0;
				while (komponenten != null && i < komponenten.length && ret == null) {
					// Wenn das Objekt nicht das zu kontrollierende Objekt ist und das Objekt
					// mit dem zu Kontrollierendem zusammenfällt
					if (komponenten[i] != this && neuePosition.intersects(komponenten[i].getBounds()))
						ret = komponenten[i];
					i++;
				}
			}
		}
		return ret;
	}
	public boolean isEatable() {
		return this.eatAble;
	}
}