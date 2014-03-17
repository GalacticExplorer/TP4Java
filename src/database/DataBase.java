/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author ychabcho
 */
import java.sql.*;

public class DataBase {

    Connection conn;

    public static void main(String[] args) {
        new DataBase();
    }

    public DataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/coffeebreak";
            conn = DriverManager.getConnection(url, "root", "");
            
            doDeleteAll();
            doInsert("Chocolat", 5);
            doInsert("Capuccino", 6);
            
            add1EuroToPrice("Capuccino");
            //doDelete();

            
            conn.close();
            
            
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.err.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void doSelectTest() {
        System.out.println("[OUTPUT FROM SELECT]");
        String query = "SELECT COF_NAME, PRICE FROM COFFEES";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String s = rs.getString("COF_NAME");
                float n = rs.getFloat("PRICE");
                System.out.println(s + "   " + n);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    // INSERT A COFFEE AND A PRICE FOR THE COFFEE
    private void doInsert(String typeCoffee, int price) {
        System.out.print("\n[INSERTING " + typeCoffee + " WITH THE PRICE : " + price + "] ... ");
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO COFFEES "
                    + "VALUES ('" + typeCoffee + "', " + price + ")");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    // ADD 1 EURO TO A PRICE OF COFFEE
    private void add1EuroToPrice(String typeCoffee) {
        System.out.print("\n[UPDATING PRICE ADDING 1 EURO] ... ");
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE COFFEES SET PRICE = PRICE + 1 WHERE COF_NAME='" + typeCoffee + "'");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    // DELETE A COFFEE WHERE PRICE > 6
    private void doDelete() {
        System.out.print("\n[DELETING COFFEES HAVING A PRICE > 6] ... ");
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM COFFEES WHERE PRICE > 6");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    // DELETE ALL FROM COFFEES
    private void doDeleteAll() {
        System.out.print("\n[DELETING ALL] ... ");
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("TRUNCATE TABLE COFFEES");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
