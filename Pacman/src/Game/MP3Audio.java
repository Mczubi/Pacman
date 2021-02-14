package Game;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MP3Audio extends Thread
{
	
	private AdvancedPlayer player;
	
	public MP3Audio(String src) throws JavaLayerException, IOException {
		this(MP3Audio.class.getResource(src));
	}
	
	public MP3Audio(URL url) throws JavaLayerException, IOException {
		this(url.openStream());
	}
	
	public MP3Audio(InputStream in) throws JavaLayerException {
		player = new AdvancedPlayer(in);
	}
	
	@Override
	public void run() {
		try {
			player.play();
		} catch (JavaLayerException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		player.close();
	}
}
