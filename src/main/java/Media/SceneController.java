package Media;

import java.io.IOException;
import java.util.HashMap;

public class SceneController {
	/**
	 * Один, чтобы управлять ими всеми
	 * Контроллер всех сцен
	 * */

	String savedState;
	HashMap<Integer, String> storyTale = new HashMap<>();
	public SceneController(String savedState) throws IOException {
		createHashList();
		this.savedState = savedState;
		if (this.savedState.equals("") || this.savedState.equals(" ")) {
			throw new IOException("File not found");
		}
	}
	void createHashList() {
		storyTale.put(0, "scenes/startStreet");
		storyTale.put(1, "scenes/secondStreet");
		storyTale.put(2, "scenes/firstHome");
		storyTale.put(3, "scenes/homeWindow");
	}
}
