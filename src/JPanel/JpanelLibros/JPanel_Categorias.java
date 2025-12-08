/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package JPanel.JpanelLibros;

import Funtions.Text.TextPrompt;
import JDialog.create_departamento;
import Jpanel_Advert.Advert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import BD.categoria;
import java.awt.Font;
import java.util.List;
import javax.swing.DefaultListModel;
import BD.register_books;
import Funtions.IconBorder;
import JDialog.modify_categorias;
import datos.Libro;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luisc
 */
public class JPanel_Categorias extends javax.swing.JPanel {

    private DefaultListModel<String> modeloLista;
    private categoria catDAO = new categoria(); 
    private register_books booksDAO = new register_books();
// Colores que combinan con tu diseño
    private static final Color LAVANDA = new Color(204, 198, 255); // barra lila
    private static final Color BORDE_GRIS = new Color(180, 180, 180);
    private javax.swing.JPanel pnlBusqueda;
    private javax.swing.JLabel lblIconoBuscar;
    //ID que no se puede eiminar
    private static final int ID_SIN_CATEGORIA = 1;


    /**
     * Creates new form JPanel_Categorias
     */
    public JPanel_Categorias() {

        initComponents();
        // Fondo de la “franja” donde va el JList
        this.setBackground(Color.WHITE);
        List_depart.setBackground(LAVANDA); // si tienes un panel contenedor

        // Borde con título en el scroll
        jScrollPane1.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDE_GRIS),
                        "Categorías",
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Inter", Font.BOLD, 12),
                        Color.DARK_GRAY
                )
        );

        configurarListaCategorias();   // 👈 aquí vamos a darle estilo al JList

        Icon icon = new ImageIcon("/IMG/lupa.png");
        javax.swing.JButton[] botones = {btn_nuevo, btn_nuevo1, btn_nuevo2};
        // 🔍 Aplicar icono al JTextField sin cambiar el layout
        Icon iconoLupa = new ImageIcon(getClass().getResource("/IMG/lupa.png"));
        Border iconBorder = new IconBorder(iconoLupa, 4);

        // Si ya tenías padding, lo combinamos con el borde del icono
        txt_buscar.setBorder(
                BorderFactory.createCompoundBorder(
                        iconBorder,
                        BorderFactory.createEmptyBorder(0, 0, 0, 0)
                )
        );
        TextPrompt code_bar = new TextPrompt("Titulo del libro", txt_buscar, Color.GRAY);
        
        // 🔍 Buscar mientras escribe (cuando tenga al menos 3 caracteres)
    txt_buscar.getDocument().addDocumentListener(new DocumentListener() {
        private void onChange() {
            String texto = txt_buscar.getText();
            if (texto != null && texto.trim().length() >= 3) {
                buscarLibroPorTexto(texto);
            } else if (texto.trim().isEmpty()) {
                // si borra todo, recargamos la categoría seleccionada actual
                String catSel = List_depart.getSelectedValue();
                if (catSel != null) {
                    cargarLibrosPorCategoria(catSel);
                }
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) { onChange(); }

        @Override
        public void removeUpdate(DocumentEvent e) { onChange(); }

        @Override
        public void changedUpdate(DocumentEvent e) { onChange(); }
    });
    

        // 🔹 Modelo para la JList (ya no lo declares local)
        modeloLista = new DefaultListModel<>();
        List_depart.setModel(modeloLista);

        // 🔹 Cargar categorías al iniciar
        cargarCategorias();
        // 2) Seleccionar por defecto la categoría con id 1

        // 🔹 Cuando seleccionen una categoría en el JList...
        List_depart.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // evita eventos dobles
                    String categoriaSeleccionada = List_depart.getSelectedValue();
                    if (categoriaSeleccionada != null) {
                        cargarLibrosPorCategoria(categoriaSeleccionada);
                    }
                }
            }
        });

        DefaultTableModel modeloTabla = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código barras", "Título", "Autor", "Editorial", "Ejemplares", "Páginas"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // ❌ Ninguna celda editable
                return false;
            }
        };

        tblLibros.setModel(modeloTabla);

        configurarTablaLibros();  // 👈 después de setModel
        for (javax.swing.JButton boton : botones) {
            boton.setBackground(Color.WHITE);
            boton.setOpaque(true);
            boton.setContentAreaFilled(true);
            boton.setFocusPainted(false);
            boton.setBorderPainted(true);
            boton.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
        }
        seleccionarCategoriaPrincipalYMostrarLibros();
    }

    private void buscarLibroPorTexto(String texto) {
        texto = texto.trim();
        if (texto.isEmpty()) {
            // si borran el texto puedes volver a cargar la categoría seleccionada actual
            if (List_depart.getSelectedValue() != null) {
                cargarLibrosPorCategoria(List_depart.getSelectedValue());
            }
            return;
        }

        // Buscar en BD
        java.util.List<Libro> libros = booksDAO.searchBooksByText(texto);

        if (libros.isEmpty()) {
            // Si no hay resultados, limpias la tabla pero no cambias la categoría
            DefaultTableModel modeloTabla
                    = (DefaultTableModel) tblLibros.getModel();
            modeloTabla.setRowCount(0);
            return;
        }

        // Tomamos el primer libro encontrado
        Libro libro = libros.get(0);

        // 1) Seleccionar su categoría en el JList
        int idCat = libro.getCategoria();                 // asegúrate de tener este getter
        String nombreCat = catDAO.getCategoryNameById(idCat);

        if (nombreCat != null) {
            for (int i = 0; i < modeloLista.getSize(); i++) {
                String nombreEnLista = modeloLista.getElementAt(i);
                if (nombreEnLista.equalsIgnoreCase(nombreCat)) {
                    List_depart.setSelectedIndex(i);
                    List_depart.ensureIndexIsVisible(i);
                    break;
                }
            }
        }

        // 2) Mostrar SOLO ese libro en la tabla
        DefaultTableModel modeloTabla
                = (DefaultTableModel) tblLibros.getModel();
        modeloTabla.setRowCount(0); // limpiar

        Object[] fila = new Object[]{
            libro.getCode_barras(),
            libro.getName_book(),
            libro.getAutor(),
            libro.getEditorial(),
            libro.getCantidad_copias(),
            libro.getNum_pag()
        };
        modeloTabla.addRow(fila);
    }

    private void seleccionarCategoriaPrincipalYMostrarLibros() {
        // Obtener el nombre de la categoría con id 1
        String nombrePrincipal = catDAO.getCategoryNameById(1);
        if (nombrePrincipal == null) {
            return;
        }

        // Buscar ese nombre en el modelo del JList
        for (int i = 0; i < modeloLista.getSize(); i++) {
            String nombreEnLista = modeloLista.getElementAt(i);
            if (nombreEnLista.equalsIgnoreCase(nombrePrincipal)) {

                // 1) Seleccionar en el JList
                List_depart.setSelectedIndex(i);
                List_depart.ensureIndexIsVisible(i);

                // 2) Cargar libros de esa categoría directamente
                cargarLibrosPorCategoria(nombrePrincipal);

                break;
            }
        }
    }

    private void configurarTablaLibros() {

        // Azul gris suave para encabezado
        Color HEADER_BG = new Color(209, 225, 240);
        Color HEADER_FG = new Color(40, 40, 40);

        // Selección azul suave
        Color SELECT_BG = new Color(144, 194, 231);
        Color SELECT_FG = Color.BLACK;

        // Gris claro para filas alternadas
        Color ROW_ALT = new Color(245, 247, 250);

        // --- CONFIG TABLA ---
        tblLibros.setRowHeight(24);
        tblLibros.setFont(new Font("Inter", Font.PLAIN, 13));
        tblLibros.setForeground(new Color(50, 50, 50));
        tblLibros.setBackground(Color.WHITE);
        tblLibros.setSelectionBackground(SELECT_BG);
        tblLibros.setSelectionForeground(SELECT_FG);

        tblLibros.setShowGrid(true);
        tblLibros.setGridColor(new Color(220, 220, 220));

        // --- ENCABEZADO ---
        javax.swing.table.JTableHeader header = tblLibros.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(HEADER_BG);
        header.setForeground(HEADER_FG);
        header.setFont(new Font("Inter", Font.BOLD, 13));
        header.setOpaque(true);

        // --- RENDERER DE FILAS ---
        tblLibros.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    // Filas alternadas
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(ROW_ALT);
                    }
                }

                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6)); // padding

                return c;
            }
        });
    }

    private void cargarLibrosPorCategoria(String nombreCategoria) {

        // 1) Buscar el ID de la categoría en la BD
        int idCat = catDAO.getCategoryIdByName(nombreCategoria);

        javax.swing.table.DefaultTableModel modeloTabla
                = (javax.swing.table.DefaultTableModel) tblLibros.getModel();
        modeloTabla.setRowCount(0); // limpiar

        if (idCat == -1) {
            return;
        }

        java.util.List<Libro> libros = booksDAO.getBooksByCategory(idCat);

        for (Libro libro : libros) {
            Object[] fila = new Object[]{
                libro.getCode_barras(), // ajusta a tus getters reales
                libro.getName_book(),
                libro.getAutor(),
                libro.getEditorial(),
                libro.getCantidad_copias(),
                libro.getNum_pag()
            };
            modeloTabla.addRow(fila);
        }
    }

    // ⬇️ ESTE MÉTODO es el que podrás llamar cada vez que quieras refrescar la lista
    public void cargarCategorias() {
        modeloLista.clear();

        List<String> categorias = catDAO.getAllCategories();
        for (String nombreCat : categorias) {
            modeloLista.addElement(nombreCat);
        }
    }

    private void configurarListaCategorias() {
        List_depart.setFont(new Font("Inter", Font.BOLD, 14));
        List_depart.setForeground(Color.DARK_GRAY);
        List_depart.setBackground(Color.WHITE);
        List_depart.setSelectionBackground(new Color(177, 156, 217)); // lila más intenso
        List_depart.setSelectionForeground(Color.BLACK);
        List_depart.setFixedCellHeight(22);

        List_depart.setCellRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setFont(new Font("Inter", Font.BOLD, 13));
                label.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 6));

                if (!isSelected) {
                    // Filas alternadas
                    if (index % 2 == 0) {
                        label.setBackground(Color.WHITE);
                    } else {
                        label.setBackground(new Color(245, 245, 255));
                    }
                }

                return label;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        btn_nuevo = new javax.swing.JButton();
        btn_nuevo1 = new javax.swing.JButton();
        btn_nuevo2 = new javax.swing.JButton();
        txt_buscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        List_depart = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLibros = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Inter", 3, 20)); // NOI18N
        jLabel1.setText("Categorias");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        btn_nuevo.setFont(new java.awt.Font("Inter", 1, 14)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/panel.png"))); // NOI18N
        btn_nuevo.setText("Nueva Categoria");
        btn_nuevo.setBorder(null);
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_nuevo1.setFont(new java.awt.Font("Inter", 1, 14)); // NOI18N
        btn_nuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/editar-texto.png"))); // NOI18N
        btn_nuevo1.setText("Editar nombre de categoria");
        btn_nuevo1.setBorder(null);
        btn_nuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo1ActionPerformed(evt);
            }
        });

        btn_nuevo2.setFont(new java.awt.Font("Inter", 1, 14)); // NOI18N
        btn_nuevo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JDialog/Images_JDialog_products/cerca.png"))); // NOI18N
        btn_nuevo2.setText("Eliminar Categoria");
        btn_nuevo2.setBorder(null);
        btn_nuevo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo2ActionPerformed(evt);
            }
        });

        txt_buscar.setFont(new java.awt.Font("Inter", 3, 14)); // NOI18N
        txt_buscar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_nuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_nuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_nuevo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_nuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        // 🔍 Panel contenedor
        pnlBusqueda = new javax.swing.JPanel(new BorderLayout());
        pnlBusqueda.setBackground(Color.WHITE);

        // 🔍 Icono
        lblIconoBuscar = new javax.swing.JLabel(
            new ImageIcon(getClass().getResource("/IMG/lupa.png"))
        );
        lblIconoBuscar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Añadir al panel
        pnlBusqueda.add(lblIconoBuscar, BorderLayout.WEST);
        pnlBusqueda.add(txt_buscar, BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        List_depart.setFont(new java.awt.Font("Inter", 3, 18)); // NOI18N
        jScrollPane1.setViewportView(List_depart);

        tblLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblLibros);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Creamos el JPanel que ya diseñaste
        create_departamento create_depa = new create_departamento(this);

        // Creamos un JDialog modal
        JDialog dialog = new JDialog(frame, "Creación de categoría", true);
        dialog.setContentPane(create_depa);
        dialog.pack();                       // ajusta al tamaño preferido del panel
        dialog.setLocationRelativeTo(frame); // lo centra respecto al frame
        dialog.setResizable(false);

        dialog.setVisible(true);             // se queda aquí hasta que se cierre
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_nuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo1ActionPerformed

        String categoriaSeleccionada = List_depart.getSelectedValue();
        int index = List_depart.getSelectedIndex();

        if (categoriaSeleccionada == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona una categoría de la lista para poder editarla.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2) Verificar si es la primera o la de id = 1 (por seguridad)
        int idCat = catDAO.getCategoryIdByName(categoriaSeleccionada);
        if (index == 0 || idCat == 1) {
            JOptionPane.showMessageDialog(
                    this,
                    "La categoría principal (\"Sin categoría\") no se puede modificar.",
                    "Acción no permitida",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Creamos el JPanel que ya diseñaste
        modify_categorias modify = new modify_categorias(this, categoriaSeleccionada);

        // Creamos un JDialog modal
        JDialog dialog = new JDialog(frame, "Modificar Categoría", true);
        dialog.setContentPane(modify);
        dialog.pack();                       // ajusta al tamaño preferido del panel
        dialog.setLocationRelativeTo(frame); // lo centra respecto al frame
        dialog.setResizable(false);

        dialog.setVisible(true);             // se queda aquí hasta que se cierre
    }//GEN-LAST:event_btn_nuevo1ActionPerformed

    private void btn_nuevo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo2ActionPerformed
        // TODO add your handling code here:
         String categoriaSeleccionada = List_depart.getSelectedValue();
    int index = List_depart.getSelectedIndex();

    if (categoriaSeleccionada == null) {
        JOptionPane.showMessageDialog(
                this,
                "Selecciona una categoría de la lista para poder eliminarla.",
                "Sin selección",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // Id de la categoría seleccionada
    int idCat = catDAO.getCategoryIdByName(categoriaSeleccionada);

    // No permitir eliminar la categoría principal
    if (index == 0 || idCat == ID_SIN_CATEGORIA) {
        JOptionPane.showMessageDialog(
                this,
                "La categoría principal \"Sin categoría\" no se puede eliminar.",
                "Acción no permitida",
                JOptionPane.INFORMATION_MESSAGE
        );
        return;
    }

    // Confirmación
    int resp = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que deseas eliminar la categoría \"" + categoriaSeleccionada + "\"?\n"
          + "Todos los libros de esta categoría se moverán a \"Sin categoría\".",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (resp != JOptionPane.YES_OPTION) {
        return; // canceló
    }

    // 1) Mover libros a "Sin categoría"
    boolean movidos = booksDAO.moveBooksToCategory(idCat, ID_SIN_CATEGORIA);

    // 2) Eliminar la categoría
    boolean eliminada = catDAO.deleteCategoryById(idCat);

    if (eliminada) {
        JOptionPane.showMessageDialog(
                this,
                "La categoría se eliminó correctamente.\n"
              + "Los libros asociados se movieron a \"Sin categoría\".",
                "Categoría eliminada",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Recargar lista de categorías
        cargarCategorias();

        // Seleccionar de nuevo "Sin categoría" y mostrar sus libros
        seleccionarCategoriaPrincipalYMostrarLibros();

    } else {
        JOptionPane.showMessageDialog(
                this,
                "Ocurrió un error al eliminar la categoría.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btn_nuevo2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> List_depart;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_nuevo1;
    private javax.swing.JButton btn_nuevo2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblLibros;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
