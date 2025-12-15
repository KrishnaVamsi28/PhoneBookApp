package PhoneBook;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class DeleteContact extends JFrame implements ActionListener {

    String x[] = {"Id", "Name", "Phone", "Email", "Address"};
    String y[][] = new String[20][5];

    JTable t;
    JLabel l1;
    JTextField tf;
    JButton bt;
    Font f;

    int i = 0, j = 0;

    public DeleteContact() {
        super("Delete Contact");
        setLocation(100, 100);
        setSize(700, 350);

        f = new Font("Arial", Font.BOLD, 14);

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "select Id, name, phone, email, address from add_contact";
            ResultSet rs = obj.stm.executeQuery(q);

            while (rs.next()) {
                y[i][j++] = rs.getString("Id");
                y[i][j++] = rs.getString("name");
                y[i][j++] = rs.getString("phone");
                y[i][j++] = rs.getString("email");
                y[i][j++] = rs.getString("address");
                i++;
                j = 0;
            }

            t = new JTable(y, x);
            t.setFont(f);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(t);

        l1 = new JLabel("Enter Contact Id to Delete:");
        tf = new JTextField();
        bt = new JButton("Delete");

        bt.setBackground(Color.BLACK);
        bt.setForeground(Color.RED);
        bt.addActionListener(this);

        JPanel p = new JPanel(new GridLayout(1, 3, 10, 10));
        p.add(l1);
        p.add(tf);
        p.add(bt);

        setLayout(new BorderLayout(10, 10));
        add(sp, BorderLayout.CENTER);
        add(p, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt) {
            try {
                int id = Integer.parseInt(tf.getText());
                ConnectionClass obj = new ConnectionClass();

                String q = "delete from add_contact where Id='" + id + "'";
                int res = obj.stm.executeUpdate(q);

                if (res == 1) {
                    JOptionPane.showMessageDialog(null, "Contact Deleted Successfully");
                    this.setVisible(false);
                    new DeleteContact().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Contact ID");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new DeleteContact().setVisible(true);
    }
}
