package Media;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	/**
	 * Класс отвечающий за управление звуком. Проще говоря - плеер.
	 * В качестве аргемента для конструктора принимает InputStream(Файл с аудиофайлом).
	 * Воспринимается исключительно WAV формат
	 * */
	private InputStream musicPath;
	boolean isPlaying = false;
	boolean isCreated = false; // Не самая важная переменная, но я же пишу на жабе, так что наплевать, ХА!
	private File file = new File("test.wav");
	private Clip clip = null;
	AudioInputStream ais;
	Sound(InputStream musicPath) {
		this.musicPath = musicPath;
	}

	void createSound() {
		isCreated = true;
		try {
			file = copyInputStreamToFile(musicPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		isPlaying = false; // Почему?
		try {
			ais = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setFramePosition(0);
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
			exc.printStackTrace();
		}
	}
	
	void playSound() { // Начать воспроизведение
		isPlaying = true;
		clip.start();

	}
	
	void stopSound() { // Пауза
		isPlaying = false;
		clip.stop();
	}
	
	void closeSound() { // Остановить поток звука
		isCreated = false;
		isPlaying = false;
		this.stopSound();
		clip.close();
		try {
			ais.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void setFramePosition(int framePosition) { // Начать воспроизведение с конкретной точки
		clip.setFramePosition(framePosition);
	}
	
	public void changeSound(String newPath) { // Поменять песню(Не самое удачное решение
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

	private  File copyInputStreamToFile(InputStream inputStream) throws IOException { // Метод отвечающий за запись InputStream в File. Публичный, так как используется не только здесь
		//TODO: можно переписать, а то выглядит как костыль какой-то
		File file = new File("test.kern");
		try (var outputStream = new FileOutputStream(file)) {
			int read;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
		return file;
	}
}
