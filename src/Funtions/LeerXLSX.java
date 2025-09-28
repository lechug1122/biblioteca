/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funtions;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import BD.register_books;

/**
 *
 * @author luisc
 */
public class LeerXLSX {

    private register_books db = new register_books();

    public void leerArchivo(File archivoExcel) {
        try (FileInputStream fis = new FileInputStream(archivoExcel); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Tomar la primera hoja
            int filaActual = 2; // Empezar desde la fila 3 (índice 2)
            int librosRegistrados = 0;
            while (true) {
                Row row = sheet.getRow(filaActual);
                if (row == null || filaVacia(row)) {
                    break; // Termina si la fila está vacía
                }

                // Leer columnas A-G
                String nombreLibro = getCeldaString(row.getCell(0));
                String autor = getCeldaString(row.getCell(1));
                String codigoBarras = getCeldaString(row.getCell(2));
                String categoria = getCeldaString(row.getCell(3));
                String editorial = getCeldaString(row.getCell(4));
                int numPaginas = (int) getCeldaNumerica(row.getCell(5));
                int cantidadCopias = (int) getCeldaNumerica(row.getCell(6));
                // Verificar si el libro ya existe usando exist_book
                if (db.exist_book(codigoBarras, nombreLibro)) {
                    // Mostrar mensaje simple
                    JOptionPane.showMessageDialog(
                            null, // null centra el mensaje en la pantalla principal
                            "El libro ya existe en la base de datos: " + nombreLibro,
                            "Libro existente",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    // Guardar en la base de datos si no existe
                    boolean exito = db.register_books(codigoBarras, autor, categoria, cantidadCopias,
                            nombreLibro, editorial, numPaginas);
                    librosRegistrados++;
                }

                filaActual++;

            }
            JOptionPane.showMessageDialog(
                    null,
                    "Libros registrados correctamente: " + librosRegistrados,
                    "Registro completado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        }
    }

    // Verificar si la fila está vacía
    private boolean filaVacia(Row row) {
        for (int i = 0; i <= 6; i++) { // Columnas A-G
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    // Obtener valor como String
    private String getCeldaString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue(); // Texto normal
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Fechas
                } else {
                    return String.valueOf((long) cell.getNumericCellValue()); // Números
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getStringCellValue(); // Fórmulas
            default:
                return "";
        }
    }

    // Obtener valor numérico
    private double getCeldaNumerica(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
