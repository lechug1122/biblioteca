/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.sql.ResultSet;
/**
 *
 * @author luisc
 */
public class register_books {
     public boolean register_books(String code_barras, String Autor, String categoria, 
                          int cantidad_copias, String name, String editorial, int num_pag) {
    
String sql = "INSERT INTO public.libros " +
             "(code_barras, autor, categoria, cantidad_copias, name_book, editorial, num_pag) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, code_barras);
            pstmt.setString(2, Autor);
            pstmt.setString(3, categoria);
            pstmt.setInt(4, cantidad_copias);
            pstmt.setString(5, name); // ← aquí agregas el nombre del libro
            pstmt.setString(6, editorial);
            pstmt.setInt(7, num_pag);
            pstmt.executeUpdate();
           
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
     
     public boolean exist_book(String code_barras, String name) {
    String sql = "SELECT 1 FROM public.\"libros\" WHERE \"code_barras\" = ? OR name_book = ? LIMIT 1";
    
    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, code_barras);
        pstmt.setString(2, name);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return true; // ✅ Ya existe (por código de barras o por nombre)
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false; // ❌ No existe
}

     
}
