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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisc
 */
public class categoria {

    public boolean register_category(String nombre_categoria) {

        String sql = "INSERT INTO public.categoria (nombre_categoria) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre_categoria);
            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 🔹 Método auxiliar para verificar si ya existe una categoría

    public boolean categoryExists(String nombre_categoria) {
        String sql = "SELECT COUNT(*) FROM public.categoria WHERE LOWER(nombre_categoria) = LOWER(?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre_categoria);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // true si ya existe
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // 🔹 Obtener todas las categorías

    public List<String> getAllCategories() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre_categoria FROM public.categoria ORDER BY CASE WHEN id_categoria = 1 THEN 0 ELSE 1 END, nombre_categoria ASC";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre_categoria");
                categorias.add(rs.getString("nombre_categoria"));

                // 👉 Imprimir cada categoría en la consola
                //System.out.println(nombre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public int getCategoryIdByName(String nombre_categoria) {
        String sql = "SELECT id_categoria FROM public.categoria WHERE LOWER(nombre_categoria) = LOWER(?)";
        int id = -1;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre_categoria);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id_categoria");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public String getCategoryNameById(int idCategoria) {
        String nombre = null; // valor por defecto si no se encuentra
        String sql = "SELECT nombre_categoria FROM public.categoria WHERE id_categoria = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCategoria);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre_categoria");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre de la categoría: " + e.getMessage());
        }

        return nombre;
    }

    public boolean updateCategoryName(int idCategoria, String nuevoNombre) {
        String sql = "UPDATE public.categoria SET nombre_categoria = ? WHERE id_categoria = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, idCategoria);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategoryById(int idCategoria) {
        // Por seguridad, nunca borrar la categoría principal
        if (idCategoria == 1) {
            return false;
        }

        String sql = "DELETE FROM public.categoria WHERE id_categoria = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCategoria);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   public boolean categoryIdExists(int idCategoria) {
    String sql = "SELECT COUNT(*) FROM public.categoria WHERE id_categoria = ?";

    try (Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, idCategoria);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

    public int createNewCategory_xsls(String nombre) {

        String sql = "INSERT INTO public.categoria (nombre_categoria) VALUES (?) RETURNING id_categoria";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/biblioteca", "postgres", "admin"); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_categoria");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

}
