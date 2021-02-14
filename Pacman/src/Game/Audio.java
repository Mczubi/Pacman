package Game;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Audio implements AutoCloseable, LineListener
{
	private AudioInputStream in;
	
	private Clip clip;
	
	public Audio(String resource) throws Exception{
			this(Audio.class.getResource(resource));
	}
	
	public Audio(URL url) throws Exception{
			this(AudioSystem.getAudioInputStream(url.openStream()));
	}
	
	public Audio(AudioInputStream in){
			this.in = in;
	}

	public void play() throws Exception {
			clip = AudioSystem.getClip();
			clip.open(in);
			clip.addLineListener(this);
			clip.start();
	}
	
	public void stop() throws Exception{
			clip.stop();
	}
	
	@Override
	public void update(LineEvent event) {
		 if (event.getType() == LineEvent.Type.CLOSE) {
			 	try {
			 			close();
			 	} catch (IOException ex){ }
		 }
	}
	
	@Override
	public void close() throws IOException {
		in.close();
		clip.close();
	}
}
