package CustomPanels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageJPanel extends JPanel {

    private Image backgroundImage = null;

    public ImageJPanel(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
    }

    public void painComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
