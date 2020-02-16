import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Initialize {
	static JFrame jframe = null;
	static JButton jbutton = null;
	static SceneClass currentScene = null;
	static Sound player = null;

	private static Dimension getWindowSize() {
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
	
	private static void createGUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		jframe = new JFrame("ValueDataLog"); 
		currentScene = new SceneClass(jframe);
		currentScene.loadScene("/home/rob/Kernux/development/VNGame/ValueDataLog/src/cbwallpaper.jpg");
		jframe.setPreferredSize(getWindowSize());
		jframe.setVisible(true);
	}
	
	public static void main(String[] args) {
		createGUI();
	}

}
