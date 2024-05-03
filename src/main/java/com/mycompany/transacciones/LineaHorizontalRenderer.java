package com.mycompany.transacciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class LineaHorizontalRenderer extends DefaultTableCellRenderer {

    private boolean mostrarLinea;

    public LineaHorizontalRenderer(boolean mostrarLinea) {
        this.mostrarLinea = mostrarLinea;
    }

    LineaHorizontalRenderer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setValue(Object value) {
        // Este método se llama automáticamente al establecer el valor de la celda
        if (value != null && value.toString().contains("-")) {
            setForeground(new Color(255, 0, 0, 191)); // 128 es la opacidad, puedes ajustarlo según tus preferencias
        } else {
            setForeground(Color.BLACK);
        }
        setOpaque(true);

        super.setValue(value);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mostrarLinea) {
            // Dibujar línea horizontal en la parte inferior de la celda
            g.setColor(Color.LIGHT_GRAY); // Puedes ajustar el color según tus preferencias
            g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setOpaque(true);

        return component;
    }
}
