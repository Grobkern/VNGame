import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class SceneController {
	/**
	 * Один, чтобы управлять ими всеми
	 * Контроллер всех сцен
	 * */
	String sceneName = "";
	JFrame jframe = null;
	public SceneController(String sceneName, JFrame jframe) { //Конструктор класса контроллера сцены, принимает имя сцены
		this.sceneName = sceneName;
		this.jframe = jframe;
	}
	public void getInfoAboutObjectScene() {
		switch (sceneName) {
			//TODO Что за херня ниже!?!?!?
			case "cbWallpaper.jpg":
				loadScene(sceneName);
				break;
			case "myPerson.png":
				loadPerson(sceneName);
			default:
				System.out.println("There is an another error \\_(00)_/, undefined scene name"); //При отсутствии сцены в базе,
				break;
		}
	}

	private void loadPerson(String sceneName) { //Подгрузка персонажа на объект класса JFrame
		if(jframe!=null) {
			return;
		}
	}

	private void loadScene(String sceneName) { //Метод для подгрузки сцены на объект класса JFrame
		if(jframe!=null) {
			return;
		}
		return;
	}
}
