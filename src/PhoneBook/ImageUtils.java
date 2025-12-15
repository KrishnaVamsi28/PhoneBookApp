package PhoneBook;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageUtils {

    public static ImageIcon getCircularImage(String path, int size) {
        try {
            if (path == null || path.trim().isEmpty() || !new File(path).exists()) {
                return getDefaultAvatar(size);
            }

            BufferedImage src = ImageIO.read(new File(path));
            BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = img.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setClip(new Ellipse2D.Float(0, 0, size, size));
            g.drawImage(src, 0, 0, size, size, null);
            g.dispose();

            return new ImageIcon(img);

        } catch (Exception e) {
            return getDefaultAvatar(size);
        }
    }

    private static ImageIcon getDefaultAvatar(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(220, 220, 220));
        g.fillOval(0, 0, size, size);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, size / 2));
        g.drawString("👤", size / 2 - 12, size / 2 + 12);

        g.dispose();
        return new ImageIcon(img);
    }
}
