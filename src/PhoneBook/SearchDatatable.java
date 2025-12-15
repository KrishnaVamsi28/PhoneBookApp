package PhoneBook;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchDatatable extends JFrame {

    JTable table;
    DefaultTableModel model;

    public SearchDatatable(String name) {
        setTitle("Contact Information");
        setSize(850, 400);
        setLocationRelativeTo(null);

        // Table model
        model = new DefaultTableModel(
                new String[]{"Photo", "Id", "Name", "Phone", "Email", "Address"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(50);

        // Image renderer ONLY for photo column
        table.getColumnModel().getColumn(0)
                .setCellRenderer(new ImageCellRenderer());

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT profile_pic, Id, name, phone, email, address "
                     + "FROM add_contact WHERE name LIKE '%" + name + "%'";
            ResultSet rs = obj.stm.executeQuery(q);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("profile_pic"),
                        rs.getInt("Id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table));
    }
}
