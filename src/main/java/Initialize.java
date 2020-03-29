import Media.SceneClass;
import CustomPanels.ImageJPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;


public class Initialize {
	//TODO: Пофиксить выключение программы, она остаётся в памяти после завершения основного процесса, хотя я закрываю все потоки(мне так кажется)
	/**
	 * Главный класс, в котором происходит инициализация, и запуск основных методов
	 * */
	private static JFrame jframe = null;
	private static SceneClass currentScene = null;
	private static ImageIcon imageIcon = null;
	private static File imageScene = null;
	private static URL iconURL = null;

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
	
	private static void setupJFrame() {
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
		var fontFile = new File(Initialize.class.getResource("/assets/fonts/OS.ttf").getFile());
		currentScene.changeFont(fontFile,"header");
		System.out.println(imageScene.getName());
		currentScene.loadScene(imageScene);
		iconURL = Initialize.class.getResource("/assets/pictures/icon.png");
		imageIcon = new ImageIcon(iconURL);
		jframe.setIconImage(imageIcon.getImage());
		jframe.setVisible(true);
	}

	public static void main(String[] args) {
		createGUI();
	}

}
