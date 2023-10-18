/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

//import com.sun.jdi.connect.spi.Connection;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Dilshara Mithum
 */
public class DbConnect {
    //create method to connect database
    public static Connection getConnection(){
        Connection conn=null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb","root","");
        
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        return conn;
    }
}
