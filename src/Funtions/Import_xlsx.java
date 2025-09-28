/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funtions;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author luisc
 */
public class Import_xlsx {
   
  public void seleccionarArchivo() {
        try {
            // Aplicar tema nativo de Windows
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Look and Feel de Windows, se usará el predeterminado.");
        }

        // Crear FileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione un archivo Excel");

        // Filtrar solo archivos .xlsx
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        // Abrir diálogo
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Mostrar ruta seleccionada
          LeerXLSX lector = new LeerXLSX();
          lector.leerArchivo(archivoSeleccionado);


        }
}}
