package PhoneBook;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EntryData extends JFrame implements ActionListener {

    JLabel titleLabel, nameLabel, phoneLabel, emailLabel, addressLabel;
    JTextField tfName, tfPhone, tfEmail, tfAddress;
    JButton btAdd, btBack;
    JPanel pTop, pForm;
    Font titleFont, fieldFont;

    JLabel profilePicLabel;
    String profilePicPath = null;

    private static final int AVATAR_SIZE = 150;

    public EntryData() {
        super("Add Contact");
        setLocation(450, 70);
        setSize(480, 580);

        titleFont = new Font("Arial", Font.BOLD, 24);
        fieldFont = new Font("Arial", Font.PLAIN, 14);

        titleLabel = new JLabel("Add Contact", JLabel.CENTER);
        titleLabel.setFont(titleFont);

        profilePicLabel = new JLabel();
        profilePicLabel.setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
        profilePicLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePicLabel.setIcon(new ImageIcon(createDefaultAvatar()));

        profilePicLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chooseProfileImage();
            }
        });

        nameLabel = new JLabel("<html>Name <font color='red'>*</font></html>");
        phoneLabel = new JLabel("<html>Phone No <font color='red'>*</font></html>");
        emailLabel = new JLabel("Email (Optional)");
        addressLabel = new JLabel("Address (Optional)");

        tfName = new JTextField();
        tfPhone = new JTextField();
        tfEmail = new JTextField();
        tfAddress = new JTextField();

        btAdd = new JButton("Add Contact");
        btBack = new JButton("Back");

        btAdd.addActionListener(this);
        btBack.addActionListener(this);

        pTop = new JPanel();
        pTop.setLayout(new BoxLayout(pTop, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        pTop.add(titleLabel);
        pTop.add(Box.createVerticalStrut(12));
        pTop.add(profilePicLabel);
        pTop.add(Box.createVerticalStrut(20));

        pForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pForm.add(nameLabel);    pForm.add(tfName);
        pForm.add(phoneLabel);   pForm.add(tfPhone);
        pForm.add(emailLabel);   pForm.add(tfEmail);
        pForm.add(addressLabel); pForm.add(tfAddress);
        pForm.add(btAdd);        pForm.add(btBack);

        setLayout(new BorderLayout(10, 10));
        add(pTop, BorderLayout.NORTH);
        add(pForm, BorderLayout.CENTER);
    }

    /* ================= FILE PICKER ================= */
    private void chooseProfileImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(
                new FileNameExtensionFilter("Image Files (JPG, PNG)", "jpg", "jpeg", "png")
        );

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                BufferedImage img = ImageIO.read(file);

                if (img == null) {
                    JOptionPane.showMessageDialog(this, "Invalid image file");
                    return;
                }

                profilePicPath = file.getAbsolutePath();
                profilePicLabel.setIcon(new ImageIcon(makeCircularImage(img)));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unable to load image");
            }
        }
    }

    /* ================= SHARP CIRCULAR IMAGE ================= */
    private Image makeCircularImage(BufferedImage src) {

        int min = Math.min(src.getWidth(), src.getHeight());
        int x = (src.getWidth() - min) / 2;
        int y = (src.getHeight() - min) / 2;

        BufferedImage cropped = src.getSubimage(x, y, min, min);

        BufferedImage scaled = new BufferedImage(
                AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(cropped, 0, 0, AVATAR_SIZE, AVATAR_SIZE, null);
        g2.dispose();

        BufferedImage circle = new BufferedImage(
                AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);

        g2 = circle.createGraphics();
        g2.setClip(new Ellipse2D.Float(0, 0, AVATAR_SIZE, AVATAR_SIZE));
        g2.drawImage(scaled, 0, 0, null);
        g2.dispose();

        return circle;
    }

    /* ================= DEFAULT AVATAR ================= */
    private Image createDefaultAvatar() {
        BufferedImage img = new BufferedImage(
                AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(220, 220, 220));
        g.fillOval(0, 0, AVATAR_SIZE, AVATAR_SIZE);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("👤", AVATAR_SIZE / 2 - 18, AVATAR_SIZE / 2 + 18);

        g.dispose();
        return img;
    }

    /* ================= ACTION ================= */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btAdd) {

            if (!tfPhone.getText().matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone must be exactly 10 digits");
                return;
            }

            try {
                ConnectionClass obj = new ConnectionClass();
                String q = "insert into add_contact (name, phone, email, address, profile_pic) values ('"
                        + tfName.getText() + "','"
                        + tfPhone.getText() + "','"
                        + tfEmail.getText() + "','"
                        + tfAddress.getText() + "','"
                        + profilePicPath + "')";

                obj.stm.executeUpdate(q);
                JOptionPane.showMessageDialog(this, "Contact Added Successfully");
                this.setVisible(false);
                new Home().setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == btBack) {
            this.setVisible(false);
            new Home().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new EntryData().setVisible(true);
    }
}
