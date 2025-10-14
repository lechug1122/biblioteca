/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author luisc
 */
public class Libro {
    private String code_barras;
    private String name_book;
    private String autor;
    private int categoria;
    private int cantidad_copias;
    private String editorial;
    private int num_pag;

public Libro(String code_barras, String name_book, String autor, 
             int categoria, int cantidad_copias, 
             String editorial, int num_pag) {
    this.code_barras = code_barras;
    this.name_book = name_book;
    this.autor = autor;
    this.categoria = categoria;
    this.cantidad_copias = cantidad_copias;
    this.editorial = editorial;   // 👈 se estaba olvidando
    this.num_pag = num_pag;
}


    // Getters
    public String getCode_barras() { return code_barras; }
    public String getName_book() { return name_book; }
    public String getAutor() { return autor; }
    public int getCategoria() { return categoria; }
    public int getCantidad_copias() { return cantidad_copias; }
    public String getEditorial() { return editorial; }
    public int getNum_pag() { return num_pag; }
}

