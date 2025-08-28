/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

/**
 *
 * @author luisc
 */
public class SesionUser {

    private static int id;
    private static String nombre;
    private static String apellidos;

    public static void iniciarSesion(int idUser, String nombreUser) {
        id = idUser;
        nombre = nombreUser;
       
    }

    public static int getId() { return id; }
    public static String getNombre() { return nombre; }

    public static void cerrarSesion() {
        id = 0;
        nombre = null;
    }
}


