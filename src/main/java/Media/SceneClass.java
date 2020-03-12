package Media;
import CustomPanels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SceneClass {
	//TODO: переписать логику класса на использование вместо пути(строки) - файла
	//TODO: Вообще убрать логику и пренести её в SceneController(ЗАЧЕМ?!?!)
	//TODO: Добавить метод создания и подгрузки персонажа(Почему сразу не сделал?)
	//TODO: Разобраться куда делась вся опертива?!!?!(Не буду(1.5 гб  норм для одной фотки и одной песенки:D))
	/**
	 * Класс, отвечающий за взаимодействие со ценой(а.к.а картинка, персонаж и песня)
	 * */
	//TODO: скорее всего удалить то что внизу :)
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	Font customFont = null;
	JFrame jframe;
	JLabel background;
	JLabel jbutton;
	Sound player = null;
	public File imageScene = null;
	public File musicScene = null;
	RainPanel rainPanel = new RainPanel(); // Бред, не самая важная штука

	public SceneClass(JFrame jframe) { //Конструктор класса, принимающий в качестве аргумента объект окна
		this.jframe = jframe;
	}

	private boolean isCustomFont() {
		return customFont == null ? true : false;
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

//	public void createMenu() {
//		SwingUtilities.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				JLabel background1 = new JLabel(new ImageIcon("/home/rob/Desktop/CBEVN/src/main/resources/cbwallpaper.jpg"));
//				JLabel jLabel = new JLabel("<html><h1>New Game</h1></html>");
//				Image image = Toolkit.getDefaultToolkit().createImage("/home/rob/Desktop/CBEVN/src/main/resources/menu.gif");
//				ImageIcon imageIcon = new ImageIcon(image);
//				imageIcon.setImageObserver(jLabel);
//				jLabel.setIcon(imageIcon);
//				createSound();
//				setJButtonMouseSoundListener(jLabel);
//				background.add(jLabel);
//				jframe.add(background1);
//			}
//		});
//
//	}
	public void changeFont(File fontFile, String type) {
		int size = 0;
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
			customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(60f);
			GraphicsEnvironment fontGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
			fontGE.registerFont(customFont);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	private void createSound() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("assets/music/ntpp.wav");
		try {
			player = new Sound(inputStream);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		player.createSound();
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
			        player.setFramePosition(0);
			        player.playSound();
                } else {
			        player.setFramePosition(0);
			        player.playSound();
                }
                System.out.println("Mouse Pressed");
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
                //TODO: удалить
			}
			}
		);
	}


	public void loadScene(File imageScenePath) { // Метод для загрузки конкретной сцены
		createSound();
		System.out.println(imageScenePath);
		ImageIcon sceneBackground = new ImageIcon(imageScenePath.getAbsolutePath());
		background = new JLabel(sceneBackground); // Создание панели(обоев), которая ставится на фон
		background.setLayout(new FlowLayout());
		jbutton = new JLabel();
		if (isCustomFont()) {
			jbutton.setFont(customFont);
		}
		jbutton.setText("New Game");
		System.out.println(customFont.getSize());
		System.out.println(isCustomFont());
		setJButtonMouseSoundListener(jbutton);
		background.add(jbutton);
		jframe.setContentPane(background);
	}
}