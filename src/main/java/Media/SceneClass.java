package Media;

import CustomPanels.ImageJPanel;
import CustomPanels.RainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class SceneClass {
	//TODO: переписать логику класса на использование вместо пути(строки) - файла
	//TODO: Вообще убрать логику и пренести её в SceneController(ЗАЧЕМ?!?!)
	//TODO: Добавить метод создания и подгрузки персонажа(Почему сразу не сделал?)
	//TODO: Разобраться куда делась вся опертива?!!?!(Не буду(1.5 гб  норм для одной фотки и одной песенки:D))
	/**
	 * Класс, отвечающий за взаимодействие со ценой(а.к.а картинка, персонаж и песня)
	 * */
	//TODO: скорее всего удалить то что внизу :)
	public boolean firstStart = true;
	String sceneName = "";
	public Font customFont;
	private JFrame jframe;
	private ImageJPanel background;
	private JLabel jbutton;
	private Sound player = null;
	private File imageScene = null;
	private File musicScene = null;
	private RainPanel rainPanel = new RainPanel(); // Бред, не самая важная штука

	public SceneClass(JFrame jframe) { //Конструктор класса, принимающий в качестве аргумента объект окна
		this.jframe = jframe;
	}

	private boolean isCustomFont() {
		return customFont == null;
	}

	private void loadSceneFiles(String imageScenePath, String musicScenePath) { //TODO: доделать(зачёркнуто) сделать поочерёдную подгрузку или удлаить (склоняюсь к удалению)
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

	public void changeFont(File fontFile, String type) {
		int size;
		switch (type) {
			case "header":
				size = 48;
				break;
			case "text":
				size = 20;
				break;
			case "info":
				size = 30;
				break;
			default:
				size = 40;
				break;
		}
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) size);
			var fontGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
			fontGE.registerFont(customFont);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	private void createSound() {
		var inputStream = getClass().getClassLoader().getResourceAsStream("assets/music/ntpp.wav");
		try {
			player = new Sound(inputStream);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		player.createSound();
	}

	private void loadNextScene(String sceneName) {

	}

	private String getSceneInfo() {
		return sceneName;
	}

	private void setBackgroundMouseListener(ImageJPanel backgroundButton) {
		backgroundButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				String sceneName = getSceneInfo();
				loadNextScene(sceneName);
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});
	}

	private void setJButtonMouseSoundListener(JLabel jbutton) {  // Метод для добавления слушателя мышки к кнопке(JLabel)
		jbutton.addMouseListener(new MouseListener() {
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
                System.out.println("Mouse Entered");
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			 if (player.isPlaying) {
				 player.stopSound();
			 } else {
				 player.playSound();
			 }
                System.out.println("Mouse Exited");
			}
			@Override
			public void mousePressed(MouseEvent arg0) { //TODO: пофиксить
			    if(player.isPlaying){
			        player.stopSound();
				}
				player.setFramePosition(0);
				player.playSound();
				System.out.println("Mouse Pressed");
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Mouse Released");
			}
		});
	}


	public void loadScene(File imageScenePath) { // Метод для загрузки конкретной сцены
		sceneName = "";
		createSound();
		if(firstStart) {
			System.out.println(imageScenePath);
			try {
				background = new ImageJPanel(imageScenePath.getAbsolutePath()); // Создание панели(обоев), которая ставится на фон
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		background.setLayout(new FlowLayout());
		setBackgroundMouseListener(background);
		jbutton = new JLabel();
		try {
			jbutton.setFont(customFont);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		System.out.println(isCustomFont());
		System.out.println(customFont.getSize());
		jbutton.setText("New Game");
		setJButtonMouseSoundListener(jbutton);
		background.add(jbutton);
		jframe.setContentPane(background);
	}
}
