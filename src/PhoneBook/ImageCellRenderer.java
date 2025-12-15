package PhoneBook;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);

        String imagePath = value == null ? null : value.toString();
        label.setIcon(ImageUtils.getCircularImage(imagePath, 40));

        return label;
    }
}
