/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funtions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author luisc
 */
public class GuardarXLSX {

    public void guardarArchivo(File archivoOriginal) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        if (!archivoOriginal.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo original no existe.");
            return;
        }
        // Look and Feel de Windows
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        // FileChooser para seleccionar dónde guardar
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fileChooser.setDialogTitle("Guardar formato Excel");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File carpetaDestino = fileChooser.getSelectedFile();

            // Crear archivo con nombre fijo dentro de la carpeta elegida
            File archivoDestino = new File(carpetaDestino, "formato.xlsx");

            // Copiar archivo
            try (InputStream in = new FileInputStream(archivoOriginal); OutputStream out = new FileOutputStream(archivoDestino)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                JOptionPane.showMessageDialog(null,
                        "Archivo guardado en:\n" + archivoDestino.getAbsolutePath());

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Error al guardar el archivo: " + e.getMessage());
            }
        }
    }
}
