import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private String musicPath;
	boolean isPlaying = false;
	boolean isDestroyed = false;
	public Sound(String musicPath) {
		this.musicPath = musicPath;
	}
	public Clip clip = null;
	
	public void createSound() {
		isPlaying = false; //why?
		File file = new File(musicPath);
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setFramePosition(0);
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
			exc.printStackTrace();
		}
	}
	
	public void playSound() {
		isPlaying = true;
		clip.start();
	}
	
	public void stopSound() {
		isPlaying = false;
		clip.stop();
	}
	
	public void closeSound() {
		isPlaying = false;
		this.stopSound();
		clip.close();
	}
	
	public void setFramePosition(int framePosition) {
		clip.setFramePosition(framePosition);
	}
	
	public void changeSound(String newPath) {
		isPlaying = false;
		File file = new File(newPath);
		try {
		this.closeSound();
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setFramePosition(0);
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
				exc.printStackTrace();
	}
	}
}
