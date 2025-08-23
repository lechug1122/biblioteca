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
import java.time.LocalDate;
import java.util.Date;


/**
 *
 * @author luisc
 */
public class Register_works {
    
     public Register_works(String name, String apellido, String email, 
                          String numberControl, int numberWork, 
                          int typeUser, String password, LocalDate birthDate) {
    
        String sql = "INSERT INTO public.\"Users\" (\"Name\", apellido, email_institutional, " +
                     "\"Number_control\", \"Number_work\", type_user, password, birth_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, apellido);
            pstmt.setString(3, email);
            
            if (numberControl == null || numberControl.trim().isEmpty()) {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(4, numberControl);
            }

            pstmt.setInt(5, numberWork);
            pstmt.setInt(6, typeUser);
            pstmt.setString(7, password); // puedes encriptarla antes de guardar
            
            // 👇 Guardar la fecha de nacimiento
            if (birthDate != null) {
               pstmt.setDate(8, java.sql.Date.valueOf(birthDate));
            } else {
                pstmt.setNull(8, java.sql.Types.DATE);
            }
            
            pstmt.executeUpdate();
            System.out.println("✅ Usuario insertado correctamente con fecha de nacimiento.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// 🔹 Método para verificar si ya existe usuario
public static boolean existeUsuario(int nWork, String nombreCompleto, String email) {
    String sql = "SELECT COUNT(*) FROM public.\"Users\" " +
                 "WHERE \"Number_work\" = ? OR (\"Name\" || ' ' || apellido) = ? OR email_institutional = ?";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, nWork);
        ps.setString(2, nombreCompleto);
        ps.setString(3, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // true si encontró duplicado
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
