import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.InputStream;

public class SceneClass {
	//TODO переписать логику класса на использование вместо пути(строки) - файла
	//TODO Вообще убрать логику и пренести её в SceneController(ЗАЧЕМ?!?!)
	//TODO Добавить метод создания и подгрузки персонажа(Почему сразу не сделал?)
	//TODO Разобраться куда делась вся опертива?!!?!
	/**
	 * Класс, отвечающий за взаимодействие со ценой(а.к.а Картинк, персонаж и песня)
	 * */
	//TODO скорее всего удалить то что внизу :)
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	JFrame jframe;
	Sound player = null;
	public File imageScene = null;
	public File musicScene = null;
	RainPanel rainPanel = new RainPanel(); // Бред, не самая важная штука
	public SceneClass(JFrame jframe) { //Конструктор класса, принимающий в качестве аргумента объект окна
		this.jframe = jframe;
	}


	private void loadSceneFiles(String imageScenePath, String musicScenePath) { //TODO доделать(зачёркнуто) сделать поочерёдную подгрузку или удлаить
			imageScene = new File(imageScenePath);
			musicScene = new File(musicScenePath);
	}

	public void WindowDestroyed() { //Метод закрывающий поток плеера при уничтожении окна
		if(player!=null){
			if(player.isCreated){
				player.closeSound();

			}
		}
		if(!rainPanel.isStopped()){
			rainPanel.stop();
		}
	}

	public void loadScene(String imageScenePath) { // Метод для загрузки конкретной сцены
		//TODO уменьшить размер метода, и перенести всю логику в SceneController
		JLabel background = new JLabel(new ImageIcon(imageScenePath)); // Создание панели(обоев), которая ставится на фон
		jframe.setContentPane(background);
		background.setLayout(new FlowLayout());
		JLabel jbutton = new JLabel("<html><h1 color=white>Васян : Привет</h1></html>");
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ntpp.wav");
		try {
			player = new Sound(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		player.createSound();
		jbutton.addMouseListener(new MouseListener() { // Добавление слушателя мышки к кнопке(JLabel)
			@Override
			public void mouseClicked(MouseEvent arg0) {
			 player.closeSound();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (player.isPlaying) {
					player.stopSound();
				} else {
					player.playSound();
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (player.isPlaying) {
					player.stopSound();
				} else {
					player.playSound();
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}
		}
		);
		background.add(jbutton);
	}
}
