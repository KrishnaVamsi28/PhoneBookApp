package PhoneBook;

import java.sql.*;

public class ConnectionClass {
    Connection con;
    Statement stm;

    public ConnectionClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/PhoneBookManagement",
                "root",
                "28112004"
            );
            stm = con.createStatement();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConnectionClass();
    }
}
