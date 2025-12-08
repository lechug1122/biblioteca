/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funtions;

/**
 *
 * @author luisc
 */
import javax.swing.border.Border;
import java.awt.*;
import javax.swing.Icon;

// ...

public class IconBorder implements Border {
    private final Icon icon;
    private final int gap;  // espacio entre icono y texto

    public IconBorder(Icon icon, int gap) {
        this.icon = icon;
        this.gap = gap;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (icon == null) return;

        int iconY = y + (height - icon.getIconHeight()) / 2;
        int iconX = x + 5;  // margen izquierdo

        icon.paintIcon(c, g, iconX, iconY);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Dejamos espacio a la izquierda para el icono + gap
        int left = (icon != null ? icon.getIconWidth() + gap + 8 : 8);
        return new Insets(2, left, 2, 4);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
