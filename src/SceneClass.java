import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SceneClass {
	public int currentState = 0;
	JFrame jframe = null;
	Sound player = null;
	public SceneClass(JFrame jframe) {
		this.jframe = jframe;
	}
	public File imageScene = null;
	public File musicScene = null;
	private void loadSceneFiles(String imageScenePath, String musicScenePath) {
			imageScene = new File(imageScenePath);
			musicScene = new File(musicScenePath);
	}
	public void loadScene(String imageScenePath) {
		JLabel background = new JLabel(new ImageIcon(imageScenePath));
		jframe.setContentPane(background);
		background.setLayout(new FlowLayout());
		JLabel jbutton = new JLabel("<html><h1 color=yellow>Jopa</h1></html>");
		player = new Sound("/home/rob/Kernux/development/VNGame/ValueDataLog/src/ntpp.wav");
		player.createSound();
		jbutton.addMouseListener(new MouseListener(){ 
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (player.isPlaying){
					player.stopSound();
				} else {
					player.playSound();
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			}
		);
		final RainPanel rainPanel = new RainPanel();
		rainPanel.setPreferredSize(new Dimension(1920,1080));
//		jframe.add(rainPanel);
//		background.add(rainPanel);
		background.add(jbutton);
		
		
	}

}
