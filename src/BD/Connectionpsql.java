/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author luisc
 */
public class Connectionpsql {
    private final String Host = "localhost";
    private final String Puerto = "5432";
    private final String Db = "biblioteca";
   private final String user = "postgres";

    private final String pass = "admin";
    
    public Connection getConnection(){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
           String url = "jdbc:postgresql://" + Host + ":" + Puerto + "/" + Db;

            conexion = DriverManager.getConnection(url,user,pass);
            System.out.println("conexion exitosa");
        } catch (Exception e) {
                 System.out.println("conexion no exitosa"+  e.getMessage());
        }
        return conexion;
    }
}
