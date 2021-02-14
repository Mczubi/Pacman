package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Image image;
	
	public ImagePanel(String resource) throws IOException{
		this(ImagePanel.class.getResource(resource));
	}
	
	public ImagePanel(URL url) throws IOException{
		this(url.openStream());
	}
	
	public ImagePanel(InputStream stream) throws IOException {
		this(ImageIO.read(stream));
	}
	
	public ImagePanel(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, 400, 400, null);
	}
}
