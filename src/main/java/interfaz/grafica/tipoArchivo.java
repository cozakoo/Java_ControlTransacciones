package interfaz.grafica;

import Utiles.Mensajes;
import Utiles.Utiles;
import com.mycompany.transacciones.Csv;
import com.mycompany.transacciones.DataBase;
import com.mycompany.transacciones.FileFilterExcel;
import com.mycompany.transacciones.ExcelTranssacion;
import com.mycompany.transacciones.Mensaje;
import com.mycompany.transacciones.NotificacionList;
import com.opencsv.exceptions.CsvException;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author dgc06
 */
public class tipoArchivo extends javax.swing.JFrame {

    /**
     * Creates new form FormInicio
     */
    private final int TAMANIO_MAXIMO_ARCHIVO = 2000000;
    DataBase db;
    Loading loading;

    public tipoArchivo(DataBase db) {
        initComponents();
        cargarImagenes();
        this.db = db;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        preguntaLabel = new javax.swing.JLabel();
        importarExcelBtn = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        importCsvBtn = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        loadingLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DE COMPUTO");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 230, 180, 20));

        preguntaLabel.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        preguntaLabel.setForeground(new java.awt.Color(51, 51, 51));
        preguntaLabel.setText("Seleccione el formato del archivo de transaccion ");
        getContentPane().add(preguntaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        importarExcelBtn.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        importarExcelBtn.setText("Excel");
        importarExcelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importarExcelBtnMouseClicked(evt);
            }
        });
        getContentPane().add(importarExcelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DIRECCION GENERAL");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 200, 230, 30));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, -1, 510));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("CONTROL DE TRANSACCIONES");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 70, -1, -1));

        importCsvBtn.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        importCsvBtn.setText(".CSV");
        importCsvBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importCsvBtnMouseClicked(evt);
            }
        });
        getContentPane().add(importCsvBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, -1, -1));

        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 130, -1, -1));

        loadingLabel.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        loadingLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\dgc06\\Documents\\nuevo repo\\control_transacciones\\Images\\clock.png")); // NOI18N
        loadingLabel.setText("   Procesando Archivo ...");
        getContentPane().add(loadingLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void importarExcelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importarExcelBtnMouseClicked

        tipoArchivo form = this;
        new Thread() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                Utiles.configurarFileChooser(fileChooser, new String[]{"xls", "xlsx"}, "Archivos de Excel");
                form.setVisible(false);
                // Abre el cuadro de diálogo para seleccionar un archivo
                int returnValue = fileChooser.showOpenDialog(null);

                // Verifica si se seleccionó un archivo
                if (returnValue == javax.swing.JFileChooser.APPROVE_OPTION) {

                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        long pesoEnBytes = selectedFile.length();

                        //controla el peso(pluma) del archivo
                        if (pesoEnBytes < TAMANIO_MAXIMO_ARCHIVO) {

                            String ruta = selectedFile.getAbsolutePath();
                            ExcelTranssacion excel = new ExcelTranssacion(ruta, db);
                            // Intenta abrir el archivo

                            boolean archivoInvalido = false;
                            try {
                                excel.abrir();

                                //Controla la cantidad de columas 
                                if (!excel.isValid()) {
                                    archivoInvalido = true;
                                }

                            } catch (Exception ex) {
                                archivoInvalido = true;
                            }

                            if (!archivoInvalido) {
                                // El archivo no está dañado y es valido

                                excel.listarErrores();
                                if (!excel.getErrores().isEmpty()) {
                                    new FormError(excel.getErrores()).setVisible(true);
                                } else {
                                    try {
                                        Loading load = new Loading();
                                        load.setVisible(true);
                                        //importacion
                                        form.db.importarExcel(excel);
                                        //
                                        load.dispose();
                                        new TablaIU(db).setVisible(true);
                                        NotificacionList.getInstance().MostrarNotificaciones();
                                        form.dispose();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(tipoArchivo.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (Exception ex) {

                                        Logger.getLogger(tipoArchivo.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } else {
                                Mensajes.error("El archivo es incorrecto o esta dañado.");
                            }
                        } else {
                            Mensajes.error(new Mensaje().excedeTamaño());
                            form.setVisible(false);
                        }
                    } catch (Exception ex) {

                        Logger.getLogger(tipoArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    form.setVisible(true);
                }
            }
        }.start();
    }//GEN-LAST:event_importarExcelBtnMouseClicked




    private void importCsvBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importCsvBtnMouseClicked

        tipoArchivo form = this;
        new Thread() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                 Utiles.configurarFileChooser(fileChooser, new String[]{"csv"}, "Archivos de CSV");

                form.setVisible(false);

                // Abre el cuadro de diálogo para seleccionar un archivo
                int returnValue = fileChooser.showOpenDialog(null);

                // Verifica si se seleccionó un archivo
                if (returnValue == javax.swing.JFileChooser.APPROVE_OPTION) {

                    File selectedFile = fileChooser.getSelectedFile();
                    long pesoEnBytes = selectedFile.length();

                    //controla el peso(pluma) del archivo
                    //    if (pesoEnBytes < TAMANIO_MAXIMO_ARCHIVO) {
                    try {
                        String ruta = selectedFile.getAbsolutePath();
                        Csv csv;
                        csv = new Csv(ruta, db);

                        boolean archivoInvalido = false;

                        if (csv.isValid()) {
                            archivoInvalido = true;
                            csv.listarErrores();
                            if (csv.getErrores().isEmpty()) {
                                try {
                                    Loading load = new Loading();
                                    load.setVisible(true);

                                    form.db.importarCsv(csv);

                                    load.dispose();
                                    new TablaIU(db)
                                            .setVisible(true);
                                    NotificacionList.getInstance()
                                            .MostrarNotificaciones();
                                    form.dispose();
                                } catch (SQLException ex) {
                                    Logger.getLogger(tipoArchivo.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                } catch (Exception ex) {
                                    Logger.getLogger(tipoArchivo.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                FormError formE = new FormError(csv.getErrores());
                                formE.setVisible(true);
                            }
                        } else {
                            Mensajes.error("El archivo no cumple con el formato o esta dañado.");
                            form.setVisible(true);

                        }
                    } catch (IOException | CsvException ex) {
                        Logger.getLogger(tipoArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //   } else {
                    //     new Notificacion(new Mensaje().excedeTamaño()).setVisible(true);
                    //   form.setVisible(true);
                    // }
                } else {
                    form.setVisible(true);
                }
            }
        }.start();
    }//GEN-LAST:event_importCsvBtnMouseClicked

    private void cargarImagenes() {

        loadingLabel.setVisible(false);
        String dirActual = System.getProperty("user.dir");
        importarExcelBtn.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\importar.png"));
        importCsvBtn.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\importar.png"));
        jLabel1.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\city.png"));
        try {
            BufferedImage iconImage = ImageIO.read(new File(dirActual + "\\images\\transaccion.png"));

            // Establecer la imagen como ícono de la aplicación
            this.setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // loadingLabel.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\clock.png"));
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JLabel importCsvBtn;
    javax.swing.JLabel importarExcelBtn;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel loadingLabel;
    javax.swing.JLabel preguntaLabel;
    // End of variables declaration//GEN-END:variables

}
