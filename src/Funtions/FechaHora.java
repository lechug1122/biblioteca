/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funtions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author luisc
 */
public class FechaHora {
    public String fechaHora;
    public FechaHora(){
    
    //sacar la fecha
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        //formato de la hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
        //
         fechaHora = fechaHoraActual.format(formato);
        
         
    }
     // Método opcional para obtener la fecha y hora formateadas
    public String getFechaHora() {
        return fechaHora;
    }
}
