package PhoneBook;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class SearchNameForEdit extends JFrame implements ActionListener {

    JLabel l1, l2;
    JTextField tf1;
    JButton bt1, bt2;
    Font f, f1;
    JPanel p1, p2;

    public SearchNameForEdit() {
        super("Search name for edit");
        setLocation(450, 50);
        setSize(470, 180);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.PLAIN, 15);

        l1 = new JLabel("Search name for edit");
        l2 = new JLabel("Enter Name");

        tf1 = new JTextField();

        bt1 = new JButton("Search Contact");
        bt2 = new JButton("Back");

        l1.setHorizontalAlignment(JLabel.CENTER);

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        l1.setFont(f);
        l2.setFont(f1);
        tf1.setFont(f1);
        bt1.setFont(f1);
        bt2.setFont(f1);

        p1 = new JPanel(new GridLayout(1, 1));
        p1.add(l1);

        p2 = new JPanel(new GridLayout(2, 2, 10, 10));
        p2.add(l2);
        p2.add(tf1);
        p2.add(bt1);
        p2.add(bt2);

        setLayout(new BorderLayout(10, 20));
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bt1) {

            String name = tf1.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter at least one character",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                ConnectionClass obj = new ConnectionClass();

                // ✅ PARTIAL MATCH CHECK
                String checkQuery =
                        "select 1 from add_contact where name LIKE '%" + name + "%'";
                ResultSet rs = obj.stm.executeQuery(checkQuery);

                if (rs.next()) {
                    new SearchDatatableForEdit(name).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No matching contacts found",
                            "Search Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == bt2) {
            this.setVisible(false);
            new Home().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new SearchNameForEdit().setVisible(true);
    }
}
