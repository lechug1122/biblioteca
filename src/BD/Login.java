/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author luisc
 */
public class Login {
    // 🔹 Método para loguear con número de trabajador y contraseña
public static boolean login(int numberWork, String password) {
 String sql = "SELECT COUNT(*) FROM public.\"Users\" " +
             "WHERE \"Number_work\" = ? AND password = encode(digest(?, 'sha256'), 'hex')";


    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, numberWork);
        ps.setString(2, password);  // 👉 Recuerda: en producción deberías encriptar la contraseña

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // true si encontró coincidencia
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

}
