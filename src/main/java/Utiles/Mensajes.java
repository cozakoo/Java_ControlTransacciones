package Utiles;

import javax.swing.JOptionPane;

/**
 *
 * @author DGC
 */
public class Mensajes {

    public static boolean confirmar(String pregunta, String titulo) {
        // Pregunta al usuario para confirmar la operación
        int confirmacion = JOptionPane.showConfirmDialog(null,
                pregunta, titulo,
                JOptionPane.YES_NO_OPTION);

        return confirmacion == JOptionPane.YES_OPTION;
    }

    public static void advertencia(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void error(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void exito(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Exito",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
