import Media.SceneClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;


public class Initialize {
	//TODO: Пофиксить выключение программы, она остаётся в памяти после завершения основноо процесса, хотя я пикрываю все потоки(мне так кажется)
	/**
	 * Главный класс, в котором происходит инициализация, и запуск основных методов
	 * */
	static JFrame jframe = null;
	static SceneClass currentScene = null;
	static ImageIcon imageIcon = null;
	static File imageScene = null;
	static URL iconURL = null;

	private static Dimension getWindowSize() { //Получить стандартный размер окна в соотношении 16:9, чтобы изображения выглядели нормально
		int windowWidth = 1920;
		int windowHeight = 1080;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double userScreenHeight = screenSize.getHeight();
		double userScreenWidth = screenSize.getWidth();
		if (userScreenWidth/userScreenHeight == 1.7) {
			return new Dimension((int) userScreenWidth,(int) userScreenHeight);
		} else {
			windowWidth = (int) ((int) (userScreenHeight*userScreenWidth*1.7)/userScreenHeight);
			return new Dimension(windowWidth,windowHeight);
		}
			
	}
	
	private static void setupJFrame(){
		if(jframe != null) {
			jframe.addWindowListener(new WindowAdapter() { // Event на уничтожение окна(закрытие)
				@Override
				public void windowClosed(WindowEvent e) {
					if (currentScene != null) {
						currentScene.WindowDestroyed();
					}
				}
			});
			jframe.setPreferredSize(getWindowSize());
			jframe.pack();
		}
	}

	private static void createGUI() { // Создание окна для отрисовки на нём изображений
		jframe = new JFrame("CBEVN");
		setupJFrame();
		currentScene = new SceneClass((jframe != null) ? jframe : new JFrame("CBEVN"));
		imageScene = new File(Initialize.class.getResource("/assets/pictures/cb2.jpg").getFile());
		File fontFile = new File(Initialize.class.getResource("/assets/fonts/OS.ttf").getFile());
		currentScene.changeFont(fontFile,"header");
		System.out.println(imageScene.getName());
		currentScene.loadScene(imageScene);//TODO: Переделать нормально, путём переписывания конструктора класса SceneClass(выглядит позорно)
		iconURL = Initialize.class.getResource("/assets/pictures/icon.png");
		imageIcon = new ImageIcon(iconURL);
		jframe.setIconImage(imageIcon.getImage());
		jframe.setVisible(true);
	}

	public static void main(String[] args) {
		createGUI();
	}

}
