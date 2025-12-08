/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import datos.Libro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisc
 */
public class register_books {
    
     public boolean register_books(String code_barras, String Autor, int categoria, 
                          int cantidad_copias, String name, String editorial, int num_pag) {
    
String sql = "INSERT INTO public.libros " +
             "(code_barras, autor, categoria, cantidad_copias, name_book, editorial, num_pag) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, code_barras);
            pstmt.setString(2, Autor);
            pstmt.setInt(3, categoria);
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
public Libro getBook(String barcode) {
    String sql = "SELECT code_barras, name_book, autor, categoria, cantidad_copias, editorial, num_pag " +
                 "FROM public.libros WHERE code_barras = ?";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, barcode);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new Libro(
                    rs.getString("code_barras"),
                    rs.getString("name_book"),
                    rs.getString("autor"),
                    rs.getInt("categoria"),
                    rs.getInt("cantidad_copias"),
                    rs.getString("editorial"),
                    rs.getInt("num_pag")
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // no encontrado
}

public boolean deleteBook(String barcode) {
    String sql = "DELETE FROM public.libros WHERE code_barras = ?";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, barcode);
        int rowsAffected = pstmt.executeUpdate();

        return rowsAffected > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
 public boolean updateBook(String code_barras, String Autor, int categoria, 
                          int cantidad_copias, String name, String editorial, int num_pag) {
    String sql = "UPDATE public.libros SET " +
                 "name_book = ?, " +
                 "autor = ?, " +
                 "categoria = ?, " +
                 "cantidad_copias = ?, " +
                 "editorial = ?, " +
                 "num_pag = ? " +
                 "WHERE code_barras = ?";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, name);
        pstmt.setString(2, Autor);
        pstmt.setInt(3, categoria);
        pstmt.setInt(4, cantidad_copias);
        pstmt.setString(5, editorial);
        pstmt.setInt(6, num_pag);
        pstmt.setString(7, code_barras);

        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0; // true si actualizó algo

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false; // no se actualizó
 }
 public List<Libro> getBooksByCategory(int idCategoria) {
    List<Libro> libros = new ArrayList<>();

    String sql = "SELECT code_barras, name_book, autor, categoria, " +
                 "cantidad_copias, editorial, num_pag " +
                 "FROM public.libros " +
                 "WHERE categoria = ? " +
                 "ORDER BY name_book";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, idCategoria);
        try (ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getString("code_barras"),
                        rs.getString("name_book"),
                        rs.getString("autor"),
                        rs.getInt("categoria"),
                        rs.getInt("cantidad_copias"),
                        rs.getString("editorial"),
                        rs.getInt("num_pag")
                );
                libros.add(libro);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return libros;   // lista vacía si no hay libros o si hubo error
}
 public List<Libro> searchBooksByText(String texto) {
    List<Libro> resultado = new ArrayList<>();

    String sql = "SELECT code_barras, name_book, autor, categoria, " +
                 "cantidad_copias, editorial, num_pag " +
                 "FROM public.libros " +
                 "WHERE LOWER(name_book) LIKE LOWER(?) " +     // ← aquí puedes cambiar a descripcion
                 "ORDER BY name_book ASC";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, "%" + texto + "%");

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getString("code_barras"),
                        rs.getString("name_book"),
                        rs.getString("autor"),
                        rs.getInt("categoria"),
                        rs.getInt("cantidad_copias"),
                        rs.getString("editorial"),
                        rs.getInt("num_pag")
                );
                resultado.add(libro);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultado;
}
public boolean moveBooksToCategory(int fromCategoriaId, int toCategoriaId) {
    String sql = "UPDATE public.libros SET categoria = ? WHERE categoria = ?";

    try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, toCategoriaId);
        pstmt.setInt(2, fromCategoriaId);

        pstmt.executeUpdate();  // aunque no haya libros, no es error
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

}
