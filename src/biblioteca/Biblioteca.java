/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package biblioteca;

import GUI.Home_page;
import GUI.LoadScreen;
import GUI.register.JF_Login;

/**
 *
 * @author luisc
 */
public class Biblioteca {
    public static void main(String[] args) {
        // TODO code application logic here
       LoadScreen loadSc = new LoadScreen(); 
        JF_Login loginscreen = new JF_Login();
       loadSc.setLocationRelativeTo(null);
              // Simular carga del programa (puedes reemplazar esto con la lógica real)
        try {
            Thread.sleep(5000); // Simula 3 segundos de carga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cerrar la pantalla de carga cuando haya terminado
        loadSc.dispose();
       loginscreen.setVisible(true);
       loginscreen.setLocationRelativeTo(null);
    }
    
}
