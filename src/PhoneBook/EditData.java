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

public class EditData extends JFrame implements ActionListener {

    JLabel titleLabel, picLabel;
    JTextField tfId, tfName, tfPhone, tfEmail, tfAddress;
    JButton btUpdate, btBack;
    String profilePicPath;

    private static final int AVATAR_SIZE = 150;

    public EditData(int id) {
        super("Edit Contact");
        setLocation(450, 70);
        setSize(480, 580);

        titleLabel = new JLabel("Update Contact", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        picLabel = new JLabel();
        picLabel.setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
        picLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        picLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chooseProfileImage();
            }
        });

        tfId = new JTextField();
        tfName = new JTextField();
        tfPhone = new JTextField();
        tfEmail = new JTextField();
        tfAddress = new JTextField();

        tfId.setEditable(false);

        btUpdate = new JButton("Update Contact");
        btBack = new JButton("Back");

        btUpdate.addActionListener(this);
        btBack.addActionListener(this);

        loadData(id);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        top.add(titleLabel);
        top.add(Box.createVerticalStrut(10));
        top.add(picLabel);
        top.add(Box.createVerticalStrut(15));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.add(new JLabel("Id")); form.add(tfId);
        form.add(new JLabel("Name *")); form.add(tfName);
        form.add(new JLabel("Phone *")); form.add(tfPhone);
        form.add(new JLabel("Email")); form.add(tfEmail);
        form.add(new JLabel("Address")); form.add(tfAddress);
        form.add(btUpdate); form.add(btBack);

        setLayout(new BorderLayout(10, 10));
        add(top, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void loadData(int id) {
        try {
            ConnectionClass obj = new ConnectionClass();
            ResultSet rs = obj.stm.executeQuery(
                    "select * from add_contact where Id='" + id + "'");

            if (rs.next()) {
                tfId.setText(rs.getString("Id"));
                tfName.setText(rs.getString("name"));
                tfPhone.setText(rs.getString("phone"));
                tfEmail.setText(rs.getString("email"));
                tfAddress.setText(rs.getString("address"));
                profilePicPath = rs.getString("profile_pic");
                picLabel.setIcon(new ImageIcon(loadAvatar(profilePicPath)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void chooseProfileImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(
                new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File f = chooser.getSelectedFile();
                profilePicPath = f.getAbsolutePath();
                picLabel.setIcon(new ImageIcon(
                        makeCircularImage(ImageIO.read(f))));
            } catch (Exception ignored) {}
        }
    }

    private Image loadAvatar(String path) {
        try {
            if (path != null && new File(path).exists()) {
                return makeCircularImage(ImageIO.read(new File(path)));
            }
        } catch (Exception ignored) {}
        return createDefaultAvatar();
    }

    private Image makeCircularImage(BufferedImage src) {
        int min = Math.min(src.getWidth(), src.getHeight());
        BufferedImage crop = src.getSubimage(
                (src.getWidth() - min) / 2,
                (src.getHeight() - min) / 2,
                min, min);

        BufferedImage out = new BufferedImage(
                AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(crop, 0, 0, AVATAR_SIZE, AVATAR_SIZE, null);
        g.dispose();

        BufferedImage circle = new BufferedImage(
                AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);

        g = circle.createGraphics();
        g.setClip(new Ellipse2D.Float(0, 0, AVATAR_SIZE, AVATAR_SIZE));
        g.drawImage(out, 0, 0, null);
        g.dispose();

        return circle;
    }

    private Image createDefaultAvatar() {
        BufferedImage img = new BufferedImage(
                AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        g.setColor(new Color(220, 220, 220));
        g.fillOval(0, 0, AVATAR_SIZE, AVATAR_SIZE);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("👤", AVATAR_SIZE / 2 - 18, AVATAR_SIZE / 2 + 18);
        g.dispose();

        return img;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btUpdate) {
            try {
                ConnectionClass obj = new ConnectionClass();
                String q = "update add_contact set "
                        + "name='" + tfName.getText() + "', "
                        + "phone='" + tfPhone.getText() + "', "
                        + "email='" + tfEmail.getText() + "', "
                        + "address='" + tfAddress.getText() + "', "
                        + "profile_pic='" + profilePicPath + "' "
                        + "where Id='" + tfId.getText() + "'";
                obj.stm.executeUpdate(q);

                JOptionPane.showMessageDialog(this, "Contact Updated");
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
}
