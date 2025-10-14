/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package JPanel.JpanelLibros;

import BD.categoria;
import BD.register_books;
import Funtions.BarcodeGenerator;
import Funtions.Text.TextPrompt;
import Funtions.selecWindow;
import Jpanel_Advert.Advert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.sourceforge.jbarcodebean.JBarcodeBean;

/**
 *
 * @author luisc
 */
public class JPanel_nuevo extends javax.swing.JPanel {

    private BufferedImage ImageCR = null;
    public String codigo_barras;
    public String name_book;
    public String Autor_book;
    public int Categoria;
    public int cant_copias;
    public String editorial;
    public int num_pag;

    private void cargarCategoriasEnComboBox() {
        categoria cat = new categoria();
        List<String> listaCategorias = cat.getAllCategories();

        jComboBox1.removeAllItems(); // Limpia el combo por si tenía datos anteriores

        // Aseguramos que "Sin categoria" esté al principio
        if (!listaCategorias.contains("Sin categoria")) {
            listaCategorias.add(0, "Sin categoria");
        }

        // Cargar categorías en el combo
        for (String nombre : listaCategorias) {
            jComboBox1.addItem(nombre);
        }

        // Seleccionar "Sin categoria" como predeterminado
        jComboBox1.setSelectedItem("Sin categoria");
    }

    /**
     * Creates new form JPanel_nuevo
     */
    public JPanel_nuevo() {
        initComponents();
        cargarCategoriasEnComboBox();
        TextPrompt codigo_barras = new TextPrompt("Codigo de Barras", code_barras, Color.DARK_GRAY);
        TextPrompt Nombre_libro = new TextPrompt("Nombre del Libro", Name_book, Color.DARK_GRAY);
        TextPrompt nombre_Autor = new TextPrompt("Nombre del Autor del libro.", Autor_jTextfield, Color.DARK_GRAY);
        TextPrompt editorial_libros = new TextPrompt("Editorial del libro.", editorial_jTextField, Color.DARK_GRAY);
        JFormattedTextField txt = ((JSpinner.NumberEditor) jSpinner1.getEditor()).getTextField();
        NumberFormatter formatter = (NumberFormatter) ((DefaultFormatterFactory) txt.getFormatterFactory()).getDefaultFormatter();
        formatter.setAllowsInvalid(false); // solo permite números
        formatter.setCommitsOnValidEdit(true); // actualiza el valor al salir del campo
        jButton3.setBackground(Color.white);
        jButton4.setBackground(Color.white);
        jComboBox1.setBackground(Color.white);
        // Validacion de entrada de numero de control
        cant_Jtext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Verificar si el carácter no es un número (0-9) y no es una tecla de control como Backspace
                if (!Character.isDigit(c)) {
                    e.consume(); // Bloquea la entrada de ese carácter
                }
            }
        });

    }

    private boolean validarCampos() {
        // Obtener valores de los campos
        String codigo = code_barras.getText().trim();
        String nombre = Name_book.getText().trim();
        String autor = Autor_jTextfield.getText().trim();
        String cantCopias = cant_Jtext.getText().trim();
        String editorial = editorial_jTextField.getText().trim();
        Object categoriaSeleccionada = jComboBox1.getSelectedItem();
        int numPag = (Integer) jSpinner1.getValue();

        // Validar cada campo
        if (codigo.isEmpty() || nombre.isEmpty() || autor.isEmpty()
                || cantCopias.isEmpty() || editorial.isEmpty() || categoriaSeleccionada == null || numPag <= 0) {

            Advert advertPanel = new Advert("Campos vacios.", Color.YELLOW);

            // Agrega el panel de advertencia al JFrame
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().add(advertPanel, BorderLayout.CENTER);

            // Forza el redibujado del JFrame
            frame.revalidate();
            frame.repaint();

            // Centrar el panel de advertencia
            Dimension frameSize = frame.getSize();
            Dimension panelSize = advertPanel.getPreferredSize();
            advertPanel.setBounds(
                    (frameSize.width - panelSize.width) / 2,
                    (frameSize.height - panelSize.height) / 2,
                    panelSize.width,
                    panelSize.height
            );

            advertPanel.setVisible(true);

            // Crea un Timer que oculta el panel después de 1.5 segundos (1500 ms)
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    advertPanel.setVisible(false);
                    frame.getContentPane().remove(advertPanel);
                    frame.revalidate();
                    frame.repaint();
                }
            });
            timer.setRepeats(false); // El Timer solo debe ejecutarse una vez
            timer.start();
            return false; // hay campos vacíos
        }

        return true; // todos los campos están completos
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        code_barras = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Name_book = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ImagenCR = new javax.swing.JPanel();
        LabelCR = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Autor_jTextfield = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cant_Jtext = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        editorial_jTextField = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Inter", 3, 20)); // NOI18N
        jLabel1.setText("Nuevo Libro");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel2.setText("Codigo de Barras:");

        code_barras.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        code_barras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        code_barras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                code_barrasActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel3.setText("Nombre del Libro:");

        Name_book.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        Name_book.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setBackground(new java.awt.Color(153, 255, 153));
        jButton1.setFont(new java.awt.Font("Inter", 3, 12)); // NOI18N
        jButton1.setText("Generar Codigo de Barras");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Inika", 1, 18)); // NOI18N
        jLabel4.setText("Codigo de Barras");

        javax.swing.GroupLayout ImagenCRLayout = new javax.swing.GroupLayout(ImagenCR);
        ImagenCR.setLayout(ImagenCRLayout);
        ImagenCRLayout.setHorizontalGroup(
            ImagenCRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelCR, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );
        ImagenCRLayout.setVerticalGroup(
            ImagenCRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagenCRLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(LabelCR, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jButton2.setBackground(new java.awt.Color(153, 255, 153));
        jButton2.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JDialog/Images_JDialog_products/subir.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel5.setText("Autor del Libro:");

        Autor_jTextfield.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        Autor_jTextfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Autor_jTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Autor_jTextfieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel6.setText("Categoría:");

        jComboBox1.setFont(new java.awt.Font("Inter", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setBorder(null);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel7.setText("Cantidad de Copias:");

        cant_Jtext.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jButton3.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JDialog/Images_JDialog_products/cheque.png"))); // NOI18N
        jButton3.setText("Guardar Libro");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JDialog/Images_JDialog_products/cerca.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel8.setText("Editorial del Libro:");

        jLabel9.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N
        jLabel9.setText("Numero de paginas:");

        editorial_jTextField.setFont(new java.awt.Font("Inika", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(Name_book, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
                                                .addComponent(jLabel4))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(code_barras, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Autor_jTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(editorial_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cant_Jtext, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(286, 286, 286))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(285, 285, 285))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ImagenCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(code_barras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Name_book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ImagenCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Autor_jTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(editorial_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cant_Jtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void code_barrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_code_barrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_code_barrasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (!Name_book.getText().trim().isEmpty()) {
            //habilita el boton de guardar 
            jButton2.setEnabled(true);
            // Genera el código de barras
            String barcode = BarcodeGenerator.generatebarcode();
            // Llena el JTextField con el código de barras
            code_barras.setText(barcode);

            JBarcodeBean jbarcode = new JBarcodeBean();
            jbarcode.setCode(barcode);
            ImageCR = jbarcode.draw(new BufferedImage(LabelCR.getWidth(), LabelCR.getHeight(), BufferedImage.TYPE_INT_BGR));
            ImageIcon mcd = new ImageIcon(new ImageIcon(ImageCR).getImage().getScaledInstance(LabelCR.getWidth(), LabelCR.getHeight(), 0));

            LabelCR.setIcon(mcd);

        } else {  // Crear y mostrar el panel de advertencia
            Advert advertPanel = new Advert("Ingrese un Titulo de un libro.", Color.YELLOW);

            // Agrega el panel de advertencia al JFrame
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().add(advertPanel, BorderLayout.CENTER);

            // Forza el redibujado del JFrame
            frame.revalidate();
            frame.repaint();

            // Centrar el panel de advertencia
            Dimension frameSize = frame.getSize();
            Dimension panelSize = advertPanel.getPreferredSize();
            advertPanel.setBounds(
                    (frameSize.width - panelSize.width) / 2,
                    (frameSize.height - panelSize.height) / 2,
                    panelSize.width,
                    panelSize.height
            );

            advertPanel.setVisible(true);

            // Crea un Timer que oculta el panel después de 1.5 segundos (1500 ms)
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    advertPanel.setVisible(false);
                    frame.getContentPane().remove(advertPanel);
                    frame.revalidate();
                    frame.repaint();
                }
            });
            timer.setRepeats(false); // El Timer solo debe ejecutarse una vez
            timer.start();

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        guadarcr(ImageCR);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Autor_jTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Autor_jTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Autor_jTextfieldActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        //limpiar los campos 
        code_barras.setText("");
        Name_book.setText("");
        Autor_jTextfield.setText("");
        cant_Jtext.setText("");
        editorial_jTextField.setText("");


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        codigo_barras = code_barras.getText();
        name_book = Name_book.getText();
        Autor_book = Autor_jTextfield.getText();
        cant_copias = Integer.parseInt(cant_Jtext.getText());
        editorial = editorial_jTextField.getText();
        if (!validarCampos()) {
            return;
        }
        int num_pag = (Integer) jSpinner1.getValue();
        // 🔹 Obtener la categoría seleccionada del combo
        String categoriaSeleccionada = (String) jComboBox1.getSelectedItem();
        categoria cat = new categoria();
        int idCategoria = cat.getCategoryIdByName(categoriaSeleccionada);

        // 🔹 Validar que se haya encontrado el ID
        if (idCategoria == -1) {
            Advert advertPanel = new Advert("Error: Categoría no encontrada", Color.RED);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().add(advertPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            return;
        }
        register_books reg = new register_books();
        //verifica si no existe el libro
        if (!reg.exist_book(codigo_barras, name_book)) {
            if (reg.register_books(codigo_barras, Autor_book, idCategoria, cant_copias, name_book, editorial, num_pag)) {
                // Crear el panel de advertencia
                Advert advertPanel = new Advert("Libro Guardado", Color.GREEN);
                advertPanel.setSize(300, 100); // tamaño fijo del panel

// Obtener el frame
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

// Calcular posición centrada
                int x = (frame.getWidth() - advertPanel.getWidth()) / 2;
                int y = (frame.getHeight() - advertPanel.getHeight()) / 2;
                advertPanel.setLocation(x, y);

// Añadir a la capa POPUP para que esté encima de todos los componentes
                frame.getLayeredPane().add(advertPanel, JLayeredPane.POPUP_LAYER);
                advertPanel.setVisible(true);

// Temporizador para ocultar después de 1.5 segundos
                Timer timer = new Timer(1500, e -> {
                    advertPanel.setVisible(false);
                    frame.getLayeredPane().remove(advertPanel);
                    frame.revalidate();
                    frame.repaint();
                });
                timer.setRepeats(false);
                timer.start();
                //limpiar los campos 
                code_barras.setText("");
                Name_book.setText("");
                Autor_jTextfield.setText("");
                cant_Jtext.setText("");
            } else {
                //panel 
                Advert advertPanel = new Advert("Error en guardar el libro", Color.YELLOW);
                // Agrega el panel de advertencia al JFrame
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.getContentPane().add(advertPanel, BorderLayout.CENTER);

                // Forza el redibujado del JFrame
                frame.revalidate();
                frame.repaint();

                // Centrar el panel de advertencia
                Dimension frameSize = frame.getSize();
                Dimension panelSize = advertPanel.getPreferredSize();
                advertPanel.setBounds(
                        (frameSize.width - panelSize.width) / 2,
                        (frameSize.height - panelSize.height) / 2,
                        panelSize.width,
                        panelSize.height
                );

                advertPanel.setVisible(true);

                // Crea un Timer que oculta el panel después de 1.5 segundos (1500 ms)
                Timer timer = new Timer(1500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        advertPanel.setVisible(false);
                        frame.getContentPane().remove(advertPanel);
                        frame.revalidate();
                        frame.repaint();
                    }
                });
                timer.setRepeats(false); // El Timer solo debe ejecutarse una vez
                timer.start();

            }
        } else {
            //panel
            Advert advertPanel = new Advert("Libro ya existente en la base de datos", Color.GREEN);
            // Agrega el panel de advertencia al JFrame
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().add(advertPanel, BorderLayout.CENTER);

            // Forza el redibujado del JFrame
            frame.revalidate();
            frame.repaint();

            // Centrar el panel de advertencia
            Dimension frameSize = frame.getSize();
            Dimension panelSize = advertPanel.getPreferredSize();
            advertPanel.setBounds(
                    (frameSize.width - panelSize.width) / 2,
                    (frameSize.height - panelSize.height) / 2,
                    panelSize.width,
                    panelSize.height
            );

            advertPanel.setVisible(true);

            // Crea un Timer que oculta el panel después de 1.5 segundos (1500 ms)
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    advertPanel.setVisible(false);
                    frame.getContentPane().remove(advertPanel);
                    frame.revalidate();
                    frame.repaint();
                }
            });
            timer.setRepeats(false); // El Timer solo debe ejecutarse una vez
            timer.start();

        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private boolean guadarcr(BufferedImage imagecr) {
        selectWindows();

        String nombreArchivo = Name_book.getText().trim();
        if (nombreArchivo.isEmpty()) {
            nombreArchivo = "imagen_guardada";
        }
        if (!nombreArchivo.endsWith(".png")) {
            nombreArchivo += ".png";
        }

        JFileChooser mchooser = new JFileChooser("src\\Files");
        mchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo carpetas
        int seleccion = mchooser.showOpenDialog(this); // usar openDialog

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File directorioSeleccionado = mchooser.getSelectedFile();
            File archivoGuardado = new File(directorioSeleccionado, nombreArchivo);

            try {
                ImageIO.write(imagecr, "png", archivoGuardado);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    private void selectWindows() {
        try {
            // TODO add your handling code here:    
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Autor_jTextfield;
    private javax.swing.JPanel ImagenCR;
    private javax.swing.JLabel LabelCR;
    private javax.swing.JTextField Name_book;
    private javax.swing.JTextField cant_Jtext;
    private javax.swing.JTextField code_barras;
    private javax.swing.JTextField editorial_jTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
