package PhoneBook;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class SearchDatatableForEdit extends JFrame implements ActionListener {

    String x[] = {"Id", "Name", "Phone", "Email", "Address"};
    String y[][] = new String[20][5];

    JTable t;
    Font f;
    JLabel l1;
    JTextField tf1;
    JButton bt1;
    JPanel p1;

    int i = 0, j = 0;

    public SearchDatatableForEdit(String name) {
        super("Contact Information");
        setLocation(50, 50);
        setSize(700, 350);

        f = new Font("Arial", Font.BOLD, 14);

        try {
            ConnectionClass obj = new ConnectionClass();

            // ✅ PARTIAL MATCH QUERY
            String q =
                    "select Id, name, phone, email, address " +
                    "from add_contact where name LIKE '%" + name + "%'";

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

        l1 = new JLabel("Enter Contact Id");
        tf1 = new JTextField();
        bt1 = new JButton("Edit");
        bt1.addActionListener(this);

        p1 = new JPanel(new GridLayout(1, 3, 10, 10));
        p1.add(l1);
        p1.add(tf1);
        p1.add(bt1);

        setLayout(new BorderLayout(10, 10));
        add(sp, BorderLayout.CENTER);
        add(p1, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            int idno = Integer.parseInt(tf1.getText());
            new EditData(idno).setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new SearchDatatableForEdit("").setVisible(true);
    }
}
