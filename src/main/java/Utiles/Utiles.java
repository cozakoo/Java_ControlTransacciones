package Utiles;

import java.awt.Dimension;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author DGC
 */
public class Utiles {

    public static Timestamp obtenerFechaActual() {
        // Obtén la fecha y hora actual del sistema
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }

    public static void configurarFileChooser(JFileChooser fileChooser, String[] extensiones, String descripcion) {
        String userDownloadsDir = System.getProperty("user.home") + File.separator + "Downloads";
        fileChooser.setCurrentDirectory(new File(userDownloadsDir));

        // Ajusta el tamaño del JFileChooser (opcional)
        fileChooser.setPreferredSize(new Dimension(800, 600));  // Cambia los valores según tus necesidades

        // Crea un filtro para las extensiones especificadas
        FileFilter filtro = new FileNameExtensionFilter(descripcion, extensiones);
        fileChooser.addChoosableFileFilter(filtro);

        // Deshabilita la opción "Todos los archivos"
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    public static void configurarFileChooserGuardar(JFileChooser fileChooser, String[] extensiones, String descripcion) {
        // Directorio predeterminado (en este caso, la carpeta "Downloads" del usuario)
        String userDownloadsDir = System.getProperty("user.home") + File.separator + "Downloads";
        fileChooser.setCurrentDirectory(new File(userDownloadsDir));

        // Tamaño del JFileChooser (puedes ajustar los valores según tus necesidades)
        fileChooser.setPreferredSize(new Dimension(800, 600));

        // Filtro para las extensiones especificadas
        FileFilter filtro = new FileNameExtensionFilter(descripcion, extensiones);
        fileChooser.addChoosableFileFilter(filtro);

        // Deshabilita la opción "Todos los archivos"
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Modo para guardar archivo
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    }

}
