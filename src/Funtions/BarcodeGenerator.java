/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funtions;

import java.util.Random;

/**
 *
 * @author luisc
 */
public class BarcodeGenerator {
    
  public static String generatebarcode(){
    Random random = new Random();
    StringBuilder barcode = new StringBuilder();
    
    //se genera los primero 12 digitos aleatorio
    for(int i = 0; i < 12; i++){
    barcode.append(random.nextInt(10));
}
    //calcula el digito del control
    
    int checksum = calculateChecksum(barcode.toString());
    
    //añade el digito de control al final
    
    barcode.append(checksum);
    
    return barcode.toString();
  
  }
    
     // Calcula el dígito de control utilizando el algoritmo de EAN-13
    private static int calculateChecksum(String code) {
        int sum = 0;

        // Alterna la suma de los dígitos según la posición
        for (int i = 0; i < code.length(); i++) {
            int digit = Character.getNumericValue(code.charAt(i));
            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += digit * 3;
            }
        }

        // Calcula el dígito de control
        int checksum = 10 - (sum % 10);
        return (checksum == 10) ? 0 : checksum;
    }
    
}
