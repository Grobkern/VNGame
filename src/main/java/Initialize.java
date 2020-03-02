import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Initialize {
	static JFrame jframe = null;
	static SceneClass currentScene = null;

	private static Dimension getWindowSize() { //Получить стандартный размер окна в соотношении 16:9, чтобы изображения выглядели нормально
		//TODO пофиксить метод(Почему-то не рабоатет?!?!?!)
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
	
	private static void createGUI() { // Создание окна для отрисовки на нём изображений
		//TODO упростить
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		jframe = new JFrame("ValueDataLog");
		URL iconURL = Initialize.class.getResource("icon.png");
		System.out.println(iconURL.getPath());
		ImageIcon imageIcon = new ImageIcon(iconURL);
		jframe.setIconImage(imageIcon.getImage());
		currentScene = new SceneClass(jframe);
		jframe.addWindowListener(new WindowAdapter() { // Event на уничтожение окна(закрытие)
			@Override
			public void windowClosed(WindowEvent e) {
				currentScene.WindowDestroyed();

			}
		});
		InputStream inputStream = Initialize.class.getResourceAsStream("cb2.jpg");
		File file = new File("");
		try {
			file = new Sound(inputStream).copyInputStreamToFile(inputStream);
		} catch (IOException e){
			e.printStackTrace();
		}
		currentScene.loadScene(file.getName());//TODO Переделать нормально, путём переписывания конструктора класса SceneClass(выглядит позорно)
		jframe.setPreferredSize(new Dimension(1080,1920));
		jframe.setVisible(true);
	}
	public static void main(String[] args) {
		createGUI();
	}

}
