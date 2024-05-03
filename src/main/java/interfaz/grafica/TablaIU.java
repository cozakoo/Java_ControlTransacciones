package interfaz.grafica;

import Utiles.Mensajes;
import Utiles.Utiles;
import com.mycompany.transacciones.DataBase;
import com.mycompany.transacciones.LineaHorizontalRenderer;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dgc06
 */
public class TablaIU extends javax.swing.JFrame {

    /**
     * Creates new form TablaIU
     *
     * @throws java.sql.SQLException
     */
    DataBase db;
    DefaultTableModel model;
    String sql, filtroConsulta = "";
    boolean ordAscLegajo = true;
    boolean ordAscCant = true;
    boolean ordAscImporte = true;

    public TablaIU(DataBase db) throws SQLException {
        this.db = db;
        initComponents();
        // configTable();

        sql = "SELECT \n"
                + "grupo Grupo, \n"
                + "empresa Empresa, \n"
                + "tipo_doc 'Tipo Doc', \n"
                + "nro_doc Documento, \n"
                + "secuencia Secuencia, \n"
                + "cod_concepto Concepto, \n"
                + "descrip_concepto 'Descripcion concepto', \n"
                + "cantidad Cantidad,\n"
                + "importe Importe,\n"
                + "fecha_aplicacion 'Fecha Aplicación',\n"
                + "fecha_csv 'Fecha CSV'\n"
                + "FROM (\n"
                + "SELECT \n"
                + "g.grupo, \n"
                + "g.empresa, \n"
                + "tipo_doc, \n"
                + "nro_doc, \n"
                + "secuencia, \n"
                + "cod_concepto, \n"
                + "descrip_concepto, \n"
                + "cantidad, \n"
                + "importe, \n"
                + "fechaDate AS fecha_aplicacion, \n"
                + "fechaString AS fecha_csv \n"
                + "FROM transaccion t \n"
                + "LEFT JOIN grupo_empresa g ON t.id_grup_emp = g.id_grup_emp) AS t";
        System.out.println(sql);
        jTable1.setModel(crearModel(db.consulta(sql)));

        cargarImagenes();
        alinearColumnas();
        generarTotales(filtroConsulta);
        cargarFechas();
        configurarElemtosSwing();
        configurarCierre();

    }

    private DefaultTableModel crearModel(ResultSet rs) throws SQLException {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        boolean columnasNoCargadas = true;
        int indexFila;
        String resultadoSQL = "";
        int numColumnas = 0;

        try {
            numColumnas = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                if (columnasNoCargadas) {
                    for (int i = 1; i <= numColumnas; i++) {
                        if (!"".equals(resultadoSQL)) {
                            if (i < numColumnas) {
                                resultadoSQL = rs.getMetaData().getColumnName(i);
                            } else {
                                resultadoSQL = rs.getMetaData().getColumnName(i) + resultadoSQL;
                            }
                        } else if (i < numColumnas) {
                            resultadoSQL = rs.getMetaData().getColumnName(i);
                        } else {
                            resultadoSQL = rs.getMetaData().getColumnName(i);
                        }

                        model.addColumn(resultadoSQL);
                        resultadoSQL = "";
                        columnasNoCargadas = false;
                    }
                }
                Object[] row = new Object[numColumnas];
                indexFila = 0;
                Object obj;
                resultadoSQL = " ";

                for (int i = 1; i <= numColumnas; i++) {
                    obj = rs.getObject(i);
                    if (obj != null) {
                        if (obj instanceof Double) {
                            DecimalFormat formatea = new DecimalFormat("###,##0.00");
                            row[indexFila] = formatea.format(obj);
                        } else {
                            row[indexFila] = obj;
                        }
                        indexFila++;

                        resultadoSQL = "";
                    }
                }
                model.addRow(row);

                resultadoSQL = "";

            }
        } catch (SQLException e) {
        }
        return model;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        grupoCheck = new javax.swing.JCheckBox();
        descripcionCheck = new javax.swing.JCheckBox();
        javax.swing.JButton filtrarBtn = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        exportarBtn = new javax.swing.JButton();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
        descripcionTextField = new javax.swing.JTextField();
        empresajField = new javax.swing.JTextField();
        empresaCheck = new javax.swing.JCheckBox();
        totalImporte = new javax.swing.JLabel();
        totalReg1 = new javax.swing.JLabel();
        totalLegajos = new javax.swing.JLabel();
        totalDoc = new javax.swing.JLabel();
        docField = new javax.swing.JTextField();
        documentoCheck = new javax.swing.JCheckBox();
        grupojField1 = new javax.swing.JTextField();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        legajoField = new javax.swing.JTextField();
        jLabel10CD = new javax.swing.JLabel();
        legajoCheck = new javax.swing.JCheckBox();
        javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel12 = new javax.swing.JLabel();
        conceptoCheck = new javax.swing.JCheckBox();
        conceptoTextField = new javax.swing.JTextField();
        FechaCheck = new javax.swing.JCheckBox();
        ordImporte = new javax.swing.JLabel();
        ordCant = new javax.swing.JLabel();
        ordLejago = new javax.swing.JLabel();
        FechaComboBox = new javax.swing.JComboBox<>();
        conceptoDistintoCheck1 = new javax.swing.JCheckBox();
        jLabel13CD = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel14CD = new javax.swing.JLabel();
        javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JSeparator jSeparator2 = new javax.swing.JSeparator();
        reporteBtn = new javax.swing.JButton();
        dniDistintosCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("distintos");

        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setEditingColumn(0);
        jTable1.setEditingRow(0);
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 102));
        jScrollPane1.setViewportView(jTable1);

        grupoCheck.setBackground(new java.awt.Color(255, 255, 255));
        grupoCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        grupoCheck.setForeground(new java.awt.Color(51, 51, 51));
        grupoCheck.setText("Grupo");
        grupoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grupoCheckActionPerformed(evt);
            }
        });

        descripcionCheck.setBackground(new java.awt.Color(255, 255, 255));
        descripcionCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        descripcionCheck.setForeground(new java.awt.Color(51, 51, 51));
        descripcionCheck.setText("Descripcion");
        descripcionCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descripcionCheckActionPerformed(evt);
            }
        });

        filtrarBtn.setBackground(new java.awt.Color(0, 153, 102));
        filtrarBtn.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        filtrarBtn.setForeground(new java.awt.Color(255, 255, 255));
        filtrarBtn.setText("Filtrar");
        filtrarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        filtrarBtn.setBorderPainted(false);
        filtrarBtn.setFocusable(false);
        filtrarBtn.setRequestFocusEnabled(false);
        filtrarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtrarBtnMouseClicked(evt);
            }
        });
        filtrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 102));
        jLabel1.setText("CONTROL DE TRANSACCIONES");

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("N°");

        exportarBtn.setBackground(new java.awt.Color(0, 153, 102));
        exportarBtn.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        exportarBtn.setForeground(new java.awt.Color(255, 255, 255));
        exportarBtn.setText("Exportar CSV");
        exportarBtn.setToolTipText("");
        exportarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        exportarBtn.setBorderPainted(false);
        exportarBtn.setFocusPainted(false);
        exportarBtn.setFocusable(false);
        exportarBtn.setRequestFocusEnabled(false);
        exportarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarBtnActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Importe Total:");
        jLabel3.setVerifyInputWhenFocusTarget(false);

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Total Registros:");
        jLabel6.setVerifyInputWhenFocusTarget(false);

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("distintos");
        jLabel8.setVerifyInputWhenFocusTarget(false);

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Total Documentos:");
        jLabel9.setVerifyInputWhenFocusTarget(false);

        descripcionTextField.setEnabled(false);
        descripcionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descripcionTextFieldActionPerformed(evt);
            }
        });
        descripcionTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                descripcionTextFieldKeyTyped(evt);
            }
        });

        empresajField.setEnabled(false);
        empresajField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresajFieldActionPerformed(evt);
            }
        });
        empresajField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                empresajFieldKeyTyped(evt);
            }
        });

        empresaCheck.setBackground(new java.awt.Color(255, 255, 255));
        empresaCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        empresaCheck.setForeground(new java.awt.Color(51, 51, 51));
        empresaCheck.setText("Empresa");
        empresaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresaCheckActionPerformed(evt);
            }
        });

        totalImporte.setText("--------");

        totalReg1.setText("--------");

        totalLegajos.setText("--------");

        totalDoc.setText("--------");

        docField.setEnabled(false);
        docField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docFieldActionPerformed(evt);
            }
        });
        docField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                docFieldKeyTyped(evt);
            }
        });

        documentoCheck.setBackground(new java.awt.Color(255, 255, 255));
        documentoCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        documentoCheck.setForeground(new java.awt.Color(51, 51, 51));
        documentoCheck.setText("Documento");
        documentoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentoCheckActionPerformed(evt);
            }
        });

        grupojField1.setEnabled(false);
        grupojField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grupojField1ActionPerformed(evt);
            }
        });
        grupojField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                grupojField1KeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Roboto Black", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 102));
        jLabel2.setText("FILTROS");

        legajoField.setEnabled(false);
        legajoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legajoFieldActionPerformed(evt);
            }
        });
        legajoField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                legajoFieldKeyTyped(evt);
            }
        });

        jLabel10CD.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel10CD.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10CD.setText("N°");
        jLabel10CD.setEnabled(false);

        legajoCheck.setBackground(new java.awt.Color(255, 255, 255));
        legajoCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        legajoCheck.setForeground(new java.awt.Color(51, 51, 51));
        legajoCheck.setText("Legajo");
        legajoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legajoCheckActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(51, 51, 51));
        jLabel11.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Total Legajos:");
        jLabel11.setVerifyInputWhenFocusTarget(false);

        jLabel12.setBackground(new java.awt.Color(51, 51, 51));
        jLabel12.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("distintos");
        jLabel12.setVerifyInputWhenFocusTarget(false);

        conceptoCheck.setBackground(new java.awt.Color(255, 255, 255));
        conceptoCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        conceptoCheck.setForeground(new java.awt.Color(51, 51, 51));
        conceptoCheck.setText("Cod concepto");
        conceptoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoCheckActionPerformed(evt);
            }
        });

        conceptoTextField.setEnabled(false);
        conceptoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoTextFieldActionPerformed(evt);
            }
        });
        conceptoTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                conceptoTextFieldKeyTyped(evt);
            }
        });

        FechaCheck.setBackground(new java.awt.Color(255, 255, 255));
        FechaCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        FechaCheck.setForeground(new java.awt.Color(51, 51, 51));
        FechaCheck.setText("Fecha. A");
        FechaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaCheckActionPerformed(evt);
            }
        });

        ordImporte.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        ordImporte.setForeground(new java.awt.Color(51, 51, 51));
        ordImporte.setText("Ord. Importe");
        ordImporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ordImporteMouseClicked(evt);
            }
        });

        ordCant.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        ordCant.setForeground(new java.awt.Color(51, 51, 51));
        ordCant.setText("Ord. Cantidad");
        ordCant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ordCantMouseClicked(evt);
            }
        });

        ordLejago.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        ordLejago.setForeground(new java.awt.Color(51, 51, 51));
        ordLejago.setText("Ord. Legajo");
        ordLejago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ordLejagoMouseClicked(evt);
            }
        });

        FechaComboBox.setEnabled(false);
        FechaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaComboBoxActionPerformed(evt);
            }
        });

        conceptoDistintoCheck1.setBackground(new java.awt.Color(255, 255, 255));
        conceptoDistintoCheck1.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        conceptoDistintoCheck1.setForeground(new java.awt.Color(51, 51, 51));
        conceptoDistintoCheck1.setText("conceptos");
        conceptoDistintoCheck1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoDistintoCheck1ActionPerformed(evt);
            }
        });

        jLabel13CD.setFont(new java.awt.Font("Roboto Medium", 0, 11)); // NOI18N
        jLabel13CD.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13CD.setText("Descripcion");
        jLabel13CD.setEnabled(false);

        jSpinner1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSpinner1.setEnabled(false);

        jLabel14CD.setFont(new java.awt.Font("Roboto Medium", 0, 11)); // NOI18N
        jLabel14CD.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14CD.setText("Caracteres");
        jLabel14CD.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("N°");

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        reporteBtn.setBackground(new java.awt.Color(255, 255, 255));
        reporteBtn.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        reporteBtn.setForeground(new java.awt.Color(51, 51, 51));
        reporteBtn.setText("Generar Reporte");
        reporteBtn.setToolTipText("");
        reporteBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reporteBtn.setBorderPainted(false);
        reporteBtn.setFocusPainted(false);
        reporteBtn.setFocusable(false);
        reporteBtn.setRequestFocusEnabled(false);
        reporteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporteBtnActionPerformed(evt);
            }
        });

        dniDistintosCheck.setBackground(new java.awt.Color(255, 255, 255));
        dniDistintosCheck.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        dniDistintosCheck.setForeground(new java.awt.Color(51, 51, 51));
        dniDistintosCheck.setText("Dni iguales");
        dniDistintosCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dniDistintosCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(grupoCheck)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(grupojField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(empresaCheck)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(empresajField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(documentoCheck)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(7, 7, 7)
                        .addComponent(docField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(legajoCheck)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(7, 7, 7)
                        .addComponent(legajoField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(descripcionCheck)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(descripcionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(conceptoCheck)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(conceptoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(conceptoDistintoCheck1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel5))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10CD)
                            .addComponent(jLabel13CD)
                            .addComponent(jLabel14CD))
                        .addGap(3, 3, 3)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FechaCheck)
                    .addComponent(FechaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dniDistintosCheck))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1)
                .addGap(7, 7, 7))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)
                        .addGap(460, 460, 460)
                        .addComponent(ordLejago, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ordImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(ordCant, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(filtrarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(jLabel6)
                                .addGap(6, 6, 6)
                                .addComponent(totalReg1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel12)))
                                .addGap(4, 4, 4)
                                .addComponent(totalLegajos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel8)))
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(469, 469, 469)
                                .addComponent(exportarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reporteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(totalDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3)
                                .addGap(4, 4, 4)
                                .addComponent(totalImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ordLejago, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ordImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ordCant, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(grupoCheck)
                        .addGap(6, 6, 6)
                        .addComponent(grupojField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(empresaCheck)
                        .addGap(6, 6, 6)
                        .addComponent(empresajField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(documentoCheck)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(docField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(legajoCheck)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(legajoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(descripcionCheck)
                        .addGap(6, 6, 6)
                        .addComponent(descripcionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(conceptoCheck)
                        .addGap(6, 6, 6)
                        .addComponent(conceptoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(conceptoDistintoCheck1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel5))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10CD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel13CD))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel14CD))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(FechaCheck)
                        .addGap(4, 4, 4)
                        .addComponent(FechaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dniDistintosCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(filtrarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6)
                    .addComponent(totalReg1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel12))
                    .addComponent(totalLegajos)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8))
                    .addComponent(totalDoc)
                    .addComponent(jLabel3)
                    .addComponent(totalImporte))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reporteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        totalImporte.getAccessibleContext().setAccessibleName("totalRegistros");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void filtrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filtrarBtnActionPerformed

    private void grupoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grupoCheckActionPerformed
        // TODO add your handling code here:
        if (grupoCheck.isSelected()) {
            grupojField1.setEnabled(true);
        } else {
            grupojField1.setEnabled(false);
        }

    }//GEN-LAST:event_grupoCheckActionPerformed

    private void descripcionCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descripcionCheckActionPerformed

        if (descripcionCheck.isSelected()) {
            descripcionTextField.setEnabled(true);
        } else {
            descripcionTextField.setEnabled(false);
        }
    }//GEN-LAST:event_descripcionCheckActionPerformed

    private void filtrarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtrarBtnMouseClicked

        filtroConsulta = "SELECT \n"
                + "grupo Grupo, \n"
                + "empresa Empresa, \n"
                + "tipo_doc 'Tipo Doc', \n"
                + "nro_doc Documento, \n"
                + "secuencia Secuencia, \n"
                + "cod_concepto Concepto, \n"
                + "descrip_concepto 'Descripcion concepto', \n"
                + "cantidad Cantidad,\n"
                + "importe Importe,\n"
                + "fecha_aplicacion 'Fecha Aplicación',\n"
                + "fecha_csv 'Fecha CSV'  "
                + " FROM (SELECT g.grupo, g.empresa, tipo_doc, nro_doc, secuencia, "
                + "cod_concepto, descrip_concepto, cantidad, importe, "
                + "fechaDate AS fecha_aplicacion, fechaString AS fecha_csv "
                + "FROM transaccion t "
                + "LEFT JOIN grupo_empresa g "
                + "ON t.id_grup_emp = g.id_grup_emp) AS t";

        String atributoSql = "";
        Statement stmt = null;

        if (grupoCheck.isSelected() && !grupojField1.getText().isEmpty()) {
            atributoSql = " WHERE " + "t.grupo = '" + limpiarCadena(grupojField1.getText()) + "'";
//            atributoSql = " WHERE " + "t.grupo = '" + grupojField1.getText().replace(" ", "").toUpperCase() + "'";
        }

        if (empresaCheck.isSelected() && !empresajField.getText().isEmpty()) {
            if (!"".equals(atributoSql)) {
                atributoSql += " AND t.empresa = '" + limpiarCadena(empresajField.getText()) + "'";
            } else {
                atributoSql = " WHERE " + "t.empresa = '" + limpiarCadena(empresajField.getText()) + "'";
            }
        }

        if (documentoCheck.isSelected() && !docField.getText().isEmpty()) {
            if (!"".equals(atributoSql)) {
                atributoSql += " AND t.nro_doc like '" + docField.getText() + "%'";
//                atributoSql += " AND t.nro_doc = '" + docField.getText() + "'";
            } else {
                atributoSql = " WHERE " + "t.nro_doc like '" + docField.getText() + "%'";
//                atributoSql = " WHERE " + "t.nro_doc = '" + docField.getText() + "'";
            }
        }

        if (legajoCheck.isSelected() && !legajoField.getText().isEmpty()) {
            if (!"".equals(atributoSql)) {
                //Lo que hace la consulta es concatenar las columnas y compara con un like + valor ingresado por el usuario
                atributoSql += " AND (t.tipo_doc || t.nro_doc || t.secuencia) like '" + limpiarCadena(legajoField.getText()) + "%'";
            } else {
                atributoSql = " WHERE (t.tipo_doc || t.nro_doc || t.secuencia) like '" + limpiarCadena(legajoField.getText()) + "%'";
            }
        }

        if (descripcionCheck.isSelected() && !descripcionTextField.getText().isEmpty()) {
            if (!"".equals(atributoSql)) {
                atributoSql += " AND descrip_concepto like '%" + descripcionTextField.getText() + "%'";
            } else {
                atributoSql = " WHERE " + "descrip_concepto like '%" + descripcionTextField.getText() + "%'";
            }
        }

        if (conceptoCheck.isSelected() && !conceptoTextField.getText().isEmpty()) {
            if (!"".equals(atributoSql)) {
                atributoSql += " AND t.cod_concepto = '" + conceptoTextField.getText() + "'";
            } else {
                atributoSql = " WHERE t.cod_concepto = '" + conceptoTextField.getText() + "'";
            }
        }

        if (FechaCheck.isSelected()) {
            if (!"".equals(atributoSql)) {
                atributoSql += " AND t.fecha_aplicacion = '" + FechaComboBox.getSelectedItem().toString() + "'";

            } else {
                atributoSql = " WHERE t.fecha_aplicacion = '" + FechaComboBox.getSelectedItem().toString() + "'";
            }
        }

        if (conceptoDistintoCheck1.isSelected() && !jSpinner1.getValue().toString().isEmpty()) {
            filtroConsulta = "    SELECT  DISTINCT g.grupo, g.empresa, cod_concepto, substr(descrip_concepto,1," + jSpinner1.getValue().toString() + ") as descripcion \n"
                    + "    FROM transaccion t\n"
                    + "    LEFT JOIN grupo_empresa g\n"
                    + "    ON t.id_grup_emp = g.id_grup_emp\n"
                    + "	ORDER BY 2, 3";
        }

        if (dniDistintosCheck.isSelected() && !jSpinner1.getValue().toString().isEmpty()) {
            filtroConsulta = "SELECT \n"
                    + "  t.grupo AS Grupo, \n"
                    + "  t.empresa AS Empresa, \n"
                    + "  t.tipo_doc AS 'Tipo Doc', \n"
                    + "  t.nro_doc AS Documento, \n"
                    + "  t.secuencia AS Secuencia, \n"
                    + "  t.cod_concepto AS Concepto, \n"
                    + "  t.descrip_concepto AS 'Descripcion concepto', \n"
                    + "  t.cantidad AS Cantidad,\n"
                    + "  t.importe AS Importe,\n"
                    + "  t.fecha_aplicacion AS 'Fecha Aplicación',\n"
                    + "  t.fecha_csv AS 'Fecha CSV'\n"
                    + "FROM (\n"
                    + "  SELECT \n"
                    + "    g.grupo, \n"
                    + "    g.empresa, \n"
                    + "    tipo_doc, \n"
                    + "    nro_doc, \n"
                    + "    secuencia, \n"
                    + "    cod_concepto, \n"
                    + "    descrip_concepto, \n"
                    + "    cantidad, \n"
                    + "    importe, \n"
                    + "    fechaDate AS fecha_aplicacion, \n"
                    + "    fechaString AS fecha_csv \n"
                    + "  FROM transaccion t \n"
                    + "  LEFT JOIN grupo_empresa g ON t.id_grup_emp = g.id_grup_emp\n"
                    + ") AS t\n"
                    + "WHERE t.nro_doc IN (\n"
                    + "  SELECT nro_doc\n"
                    + "  FROM (\n"
                    + "    SELECT nro_doc, COUNT(*) AS cantidad\n"
                    + "    FROM transaccion\n"
                    + "    GROUP BY nro_doc\n"
                    + "    HAVING COUNT(*) > 1\n"
                    + "  ) AS documentos_duplicados\n"
                    + ")";
        }

        filtroConsulta += atributoSql;

        System.out.println(filtroConsulta);
        if (!"".equals(filtroConsulta)) {
            try {
                System.out.println(filtroConsulta);
                jTable1.setModel(crearModel(db.consulta(filtroConsulta)));

                if (!tablaVacia()) {

                    alinearColumnas();

                    if (!conceptoDistintoCheck1.isSelected()) {
                        generarTotales(filtroConsulta);
                    } else {
                        limpiarTotales();
                    }
                }
                //habiltia boton exportar
                exportarBtn.setEnabled(true);
//                 redimensionar();
                /*jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(100);
                 */
            } catch (SQLException ex) {
                Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_filtrarBtnMouseClicked

    //limpia el String eliminando espacios vacios y letras en minuscula
    private String limpiarCadena(String cadena) {
        return cadena.toUpperCase().replace(" ", "");
    }

    private void generarTotales(String filtroConsulta1) {
        // Si mi numero total de registros es 0, entonces 
        //no tiene sentido que verifique el resto

        String totalRegistro = obtenerTotalRegistro();

        totalReg1.setText(totalRegistro);

        if (!"0".equals(totalRegistro)) {
            try {
                totalLegajos.setText(obtenerTotalLegajos());
                totalDoc.setText(obtenerTotalDocumentos());
                totalImporte.setText(obtenerTotalImporte());
            } catch (NullPointerException e) {
            }
        } else {
            //Se pone un 0 directamente
            totalLegajos.setText("0");
            totalDoc.setText("0");
            totalImporte.setText("0");
        }
    }

    private void exportarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarBtnActionPerformed
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        Utiles.configurarFileChooserGuardar(fileChooser, new String[]{"csv"}, "Archivos de CSV");
        fileChooser.setDialogTitle("Guardar");
        File archivo = new File("");
        fileChooser.setSelectedFile(archivo);

        int resultado = fileChooser.showDialog(null, "Guardar");

        if (resultado == JFileChooser.APPROVE_OPTION) {

            archivo = fileChooser.getSelectedFile();
            String nombreArchivo = archivo.getName();
            if (!nombreArchivo.toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getParentFile(), nombreArchivo + ".csv");
            }

//            String nombreArchivoPredeterminado = "[REP]";
//            int maxLength = 15;
//
//            if (nombreArchivo.length() > maxLength) {
//                nombreArchivo = nombreArchivo.substring(0, maxLength);
//            }
            if (archivo.exists()) {

                int opcion = JOptionPane.showConfirmDialog(null, "El archivo ya existe. ¿Desea reemplazarlo?", "Confirmar reemplazo", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    try {
                        this.db.exportarCSV(archivo, sql, nombreArchivo);
                    } catch (Exception ex) {
                        Mensajes.error("No se pudo exportar el CSV - COD:0003");
                    }
                }
            } else {
                try {
                    this.db.exportarCSV(archivo, sql, nombreArchivo);
                } catch (Exception ex) {
                    Mensajes.error("No se pudo exportar el CSV - COD:0003");
                }
            }
        }
    }//GEN-LAST:event_exportarBtnActionPerformed

    private void subParcialCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subParcialCheckActionPerformed
    }//GEN-LAST:event_subParcialCheckActionPerformed

    private void descripcionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descripcionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descripcionTextFieldActionPerformed

    private void descripcionTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripcionTextFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_descripcionTextFieldKeyTyped

    private void empresajFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresajFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_empresajFieldActionPerformed

    private void empresajFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empresajFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_empresajFieldKeyTyped

    /**
     * Alineamos las columnas a nuestra conveniencia
     */
    private void alinearColumnas() {

        // Crear un renderizador de celdas personalizado para alinear a la derecha en la segunda columna
        DefaultTableCellRenderer rightRenderer = new LineaHorizontalRenderer(true);//        
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        TableModel model = jTable1.getModel();
        int i = 0;
        
        int TOTAL_COLUMNAS;
        if (conceptoDistintoCheck1.isSelected()){
            TOTAL_COLUMNAS = 3;
        }else
        {
            TOTAL_COLUMNAS = 10;
        }
        while (i <= TOTAL_COLUMNAS) {
            if ((i == 0) || (i == 1) || (i == 2) || (i == 4) || (i == 5) || (i == 7)) {
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(30);
            }
            jTable1.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            i++;
        }
    }

    private void empresaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresaCheckActionPerformed
        // TODO add your handling code here:
        if (empresaCheck.isSelected()) {
            empresajField.setEnabled(true);
        } else {
            empresajField.setEnabled(false);
        }
    }//GEN-LAST:event_empresaCheckActionPerformed

    private void docFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_docFieldActionPerformed

    private void docFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_docFieldKeyTyped
        // TODO add your handling code here:
        char teclaPulsada = evt.getKeyChar(); // Recogemos, a partir del evento, la tecla que se pulsó
        int limite = 8;
        if (!Character.isDigit(teclaPulsada)) { // Si la tecla pulsada no es un dígito, entonces...
            evt.consume(); // Borramos la última letra introducida (lo hace muy rápido, la
            // letra nunca se escribe en el textfield)
        }
        if (docField.getText().length() == limite) {
            evt.consume();
        }
    }//GEN-LAST:event_docFieldKeyTyped

    private void documentoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentoCheckActionPerformed
        // TODO add your handling code here:

        if (documentoCheck.isSelected()) {
            docField.setEnabled(true);
            legajoCheck.setSelected(false);
            legajoCheck.setEnabled(false);
            legajoField.setEnabled(false);
        } else {
            docField.setEnabled(false);
            legajoCheck.setEnabled(true);
        }
    }//GEN-LAST:event_documentoCheckActionPerformed

    private void grupojField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grupojField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_grupojField1ActionPerformed

    private void grupojField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grupojField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_grupojField1KeyTyped

    private void legajoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legajoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_legajoFieldActionPerformed

    private void legajoFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_legajoFieldKeyTyped
        // TODO add your handling code here:
        int limite = 13;
        char teclaPulsada = evt.getKeyChar(); // Recogemos, a partir del evento, la tecla que se pulsó
        if (!Character.isDigit(teclaPulsada)) { // Si la tecla pulsada no es un dígito, entonces...
            evt.consume(); // Borramos la última letra introducida (lo hace muy rápido, la
            // letra nunca se escribe en el textfield)
        }
        if (legajoField.getText().length() == limite) {
            evt.consume();
        }
    }//GEN-LAST:event_legajoFieldKeyTyped

    private void legajoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legajoCheckActionPerformed
        // TODO add your handling code here:
        if (legajoCheck.isSelected()) {
            legajoField.setEnabled(true);
            documentoCheck.setSelected(false);
            documentoCheck.setEnabled(false);
            docField.setEnabled(false);
        } else {
            documentoCheck.setEnabled(true);
            legajoField.setEnabled(false);
        }
    }//GEN-LAST:event_legajoCheckActionPerformed

    private void conceptoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoCheckActionPerformed
        // TODO add your handling code here:
        if (conceptoCheck.isSelected()) {
            conceptoTextField.setEnabled(true);
        } else {
            conceptoTextField.setEnabled(false);
        }

    }//GEN-LAST:event_conceptoCheckActionPerformed

    private void conceptoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoTextFieldActionPerformed

    private void conceptoTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conceptoTextFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoTextFieldKeyTyped

    private void FechaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaCheckActionPerformed
        // TODO add your handling code here:
        if (FechaCheck.isSelected()) {
            FechaComboBox.setEnabled(true);
        } else {
            FechaComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_FechaCheckActionPerformed

    private void ordLejagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordLejagoMouseClicked

        if (!conceptoDistintoCheck1.isSelected()) {
            String consultaOrd;
            try {
                // TODO add your handling code here:
                if (ordAscLegajo) {
                    if (filtroConsulta.isEmpty()) {
                        consultaOrd = sql + " ORDER BY tipo_doc || nro_doc || secuencia ASC";

                    } else {
                        consultaOrd = filtroConsulta + " ORDER BY t.tipo_doc || t.nro_doc || t.secuencia ASC";

                    }
                    ordAscLegajo = false;
                    //! ME PEDIA UN MERGE ASI QUE COMENTE PORQUE NO SABIA QUE VA
//                sql = filtroConsulta;
//                " ORDER BY t.tipo_doc , t.nro_doc , t.secuencia ASC";
                } else {
                    if (filtroConsulta.isEmpty()) {
                        consultaOrd = sql + " ORDER BY tipo_doc || nro_doc || secuencia DESC";

                    } else {
                        consultaOrd = filtroConsulta + " ORDER BY t.tipo_doc || t.nro_doc || t.secuencia DESC";
                    }
                    ordAscLegajo = true;
                }

                jTable1.setModel(crearModel(db.consulta(consultaOrd)));
                alinearColumnas();
            } catch (SQLException ex) {
                Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_ordLejagoMouseClicked

    private void FechaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FechaComboBoxActionPerformed

    private void conceptoDistintoCheck1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoDistintoCheck1ActionPerformed
        // TODO add your handling code here:
        if (conceptoDistintoCheck1.isSelected()) {
            jLabel10CD.setEnabled(true);
            jLabel13CD.setEnabled(true);
            jLabel14CD.setEnabled(true);
            jSpinner1.setEnabled(true);
            grupoCheck.setEnabled(false);
            grupoCheck.setSelected(false);
            empresaCheck.setEnabled(false);
            empresaCheck.setSelected(false);
            documentoCheck.setEnabled(false);
            documentoCheck.setSelected(false);
            descripcionCheck.setEnabled(false);
            descripcionCheck.setSelected(false);
            conceptoCheck.setEnabled(false);
            conceptoCheck.setSelected(false);
            FechaCheck.setEnabled(false);
            FechaCheck.setSelected(false);
            legajoCheck.setEnabled(false);
            legajoCheck.setSelected(false);
            grupojField1.setEnabled(false);
            empresajField.setEnabled(false);
            docField.setEnabled(false);
            descripcionTextField.setEnabled(false);
            conceptoTextField.setEnabled(false);
            FechaComboBox.setEnabled(false);
            legajoField.setEnabled(false);
            dniDistintosCheck.setEnabled(false);

        } else {
            jLabel10CD.setEnabled(false);
            jLabel13CD.setEnabled(false);
            jLabel14CD.setEnabled(false);
            jSpinner1.setEnabled(false);
            grupoCheck.setEnabled(true);
            empresaCheck.setEnabled(true);
            documentoCheck.setEnabled(true);
            descripcionCheck.setEnabled(true);
            conceptoCheck.setEnabled(true);
            FechaCheck.setEnabled(true);
            legajoCheck.setEnabled(true);
            dniDistintosCheck.setEnabled(true);

        }
    }//GEN-LAST:event_conceptoDistintoCheck1ActionPerformed

    private void ordImporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordImporteMouseClicked
        if (!conceptoDistintoCheck1.isSelected()) {
            String consultaOrd;
            try {
                // TODO add your handling code here:
                if (ordAscImporte) {
                    if (filtroConsulta.isEmpty()) {
                        consultaOrd = sql + " ORDER BY t.importe ASC";

                    } else {
                        consultaOrd = filtroConsulta + " ORDER BY t.importe ASC";
                    }
                    ordAscImporte = false;
                    //! ME PEDIA UN MERGE ASI QUE COMENTE PORQUE NO SABIA QUE VA
//                sql = filtroConsulta;
//                " ORDER BY t.tipo_doc , t.nro_doc , t.secuencia ASC";
                } else {
                    if (filtroConsulta.isEmpty()) {
                        consultaOrd = sql + " ORDER BY t.importe DESC";

                    } else {
                        consultaOrd = filtroConsulta + " ORDER BY t.importe DESC";
                    }
                    ordAscImporte = true;
                }
                jTable1.setModel(crearModel(db.consulta(consultaOrd)));
                alinearColumnas();
            } catch (SQLException ex) {

                Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ordImporteMouseClicked

    private void ordCantMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordCantMouseClicked
        // TODO add your handling code here:
        if (!conceptoDistintoCheck1.isSelected()) {
            String consultaOrd;
            try {
                // TODO add your handling code here:
                if (ordAscCant) {
                    if (filtroConsulta.isEmpty()) {
                        consultaOrd = sql + " ORDER BY t.cantidad ASC";
                    } else {
                        consultaOrd = filtroConsulta + " ORDER BY t.cantidad ASC";
                    }
                    ordAscCant = false;
                    //! ME PEDIA UN MERGE ASI QUE COMENTE PORQUE NO SABIA QUE VA
//                sql = filtroConsulta;
//                " ORDER BY t.tipo_doc , t.nro_doc , t.secuencia ASC";
                } else {
                    if (filtroConsulta.isEmpty()) {
                        consultaOrd = sql + " ORDER BY t.cantidad DESC";

                    } else {
                        consultaOrd = filtroConsulta + " ORDER BY t.cantidad DESC";
                    }
                    ordAscCant = true;
                }
                jTable1.setModel(crearModel(db.consulta(consultaOrd)));
                alinearColumnas();
            } catch (SQLException ex) {

                Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ordCantMouseClicked

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        if (evt.equals(Frame.MAXIMIZED_BOTH)) {
            // Si se maximiza, ajusta el JFrame al tamaño de la pantalla
            this.setExtendedState(Frame.NORMAL);
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
    }//GEN-LAST:event_formComponentResized

    private void reporteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporteBtnActionPerformed

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        Utiles.configurarFileChooserGuardar(fileChooser, new String[]{"xls", "xlsx"}, "Archivos de Excel");
        fileChooser.setDialogTitle("Guardar");
        File archivo = new File("");
        fileChooser.setSelectedFile(archivo);

        int resultado = fileChooser.showDialog(null, "Guardar");

        if (resultado == JFileChooser.APPROVE_OPTION) {

            archivo = fileChooser.getSelectedFile();
            String nombreArchivo = archivo.getName();
            try {
                //genera y guarda
                generarReporteExcel(archivo);
            } catch (IOException ex) {
                Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_reporteBtnActionPerformed

    private void dniDistintosCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dniDistintosCheckActionPerformed

        if (dniDistintosCheck.isSelected()) {
            jLabel5.setEnabled(false);
            grupoCheck.setEnabled(false);
            grupoCheck.setSelected(false);
            empresaCheck.setEnabled(false);
            empresaCheck.setSelected(false);
            documentoCheck.setEnabled(false);
            documentoCheck.setSelected(false);
            descripcionCheck.setEnabled(false);
            descripcionCheck.setSelected(false);
            conceptoCheck.setEnabled(false);
            conceptoCheck.setSelected(false);
            FechaCheck.setEnabled(false);
            FechaCheck.setSelected(false);
            legajoCheck.setEnabled(false);
            legajoCheck.setSelected(false);
            grupojField1.setEnabled(false);
            empresajField.setEnabled(false);
            docField.setEnabled(false);
            descripcionTextField.setEnabled(false);
            conceptoTextField.setEnabled(false);
            FechaComboBox.setEnabled(false);
            legajoField.setEnabled(false);
            conceptoDistintoCheck1.setEnabled(false);

        } else {
            jLabel5.setEnabled(true);
            grupoCheck.setEnabled(true);
            empresaCheck.setEnabled(true);
            documentoCheck.setEnabled(true);
            descripcionCheck.setEnabled(true);
            conceptoCheck.setEnabled(true);
            FechaCheck.setEnabled(true);
            legajoCheck.setEnabled(true);
            conceptoDistintoCheck1.setEnabled(true);

        }

    }//GEN-LAST:event_dniDistintosCheckActionPerformed

    private String obtenerTotalRegistro() {

        try {
            String condicion = "count(*)";
            String consultaSQL;
            if ("".equals(filtroConsulta)) {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + sql + ") as l";
            } else {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + filtroConsulta + ") as l";
            }
            System.out.println("GENERANDO LOS TOTALES");
            System.out.println(consultaSQL);
            ResultSet resultSet = db.consulta(consultaSQL);
            return resultSet.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(TablaIU.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }

    private String obtenerTotalLegajos() {

        try {
            String condicion = "COUNT(DISTINCT 'Tipo Doc' || Documento|| Secuencia)";

            String consultaSQL;

            if ("".equals(filtroConsulta)) {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + sql + ") as l";
            } else {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + filtroConsulta + ") as l";
            }
            ResultSet resultSet = db.consulta(consultaSQL);
            return resultSet.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(TablaIU.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }

    private String obtenerTotalDocumentos() {
        try {
            String condicion = "count(DISTINCT l.Documento)";
            String consultaSQL;

            if ("".equals(filtroConsulta)) {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + sql + ") as l";
            } else {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + filtroConsulta + ") as l";
            }
            ResultSet resultSet = db.consulta(consultaSQL);
            return resultSet.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(TablaIU.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }

    private String obtenerTotalImporte() {

        //no se puede usar SUM(l.importe) ya que solo suma los valores enteros solamente
        String condicion = "Importe";

        try {
            String consultaSQL;
            if ("".equals(filtroConsulta)) {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + sql + ") as l";
            } else {
                consultaSQL = "SELECT " + condicion + " as total FROM (" + filtroConsulta + ") as l";
            }
            ResultSet resultSet = db.consulta(consultaSQL);
            double importe = procesarValoresImportes(resultSet);
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            // System.out.println(consultaSQL);
            return "$ " + formato.format(importe);

        } catch (SQLException ex) {
            Logger.getLogger(TablaIU.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }

    // Recorro todo mi resulset y devuelto todo mi importe
    private double procesarValoresImportes(ResultSet rs) throws SQLException {

        double acumulador = 0;
        String monto;
        while (rs.next()) {

            monto = rs.getString(1).replace(";", ".");
            monto = monto.replace(",", ".");
            acumulador += Double.parseDouble(monto);
        }
        return acumulador;
    }

    private void cargarFechas() {
        try {
            String consulta = "SELECT DISTINCT fechaDate FROM transaccion";
            ResultSet fechas = db.consulta(consulta);
            List<String> listaDeDatos = new ArrayList<>();

            int i = 1;
            while (fechas.next()) {
                FechaComboBox.addItem(fechas.getString(i));
            }

        } catch (SQLException ex) {

            Logger.getLogger(TablaIU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarImagenes() {
        String dirActual = System.getProperty("user.dir");
        ordLejago.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\ord.png"));
        ordImporte.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\ord.png"));
        ordCant.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\ord.png"));
        reporteBtn.setIcon(new javax.swing.ImageIcon(dirActual + "\\images\\reporte.png"));
        try {
            BufferedImage iconImage = ImageIO.read(new File(dirActual + "\\images\\transaccion.png"));

            // Establecer la imagen como ícono de la aplicación
            this.setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configurarElemtosSwing() {
        SpinnerModel model = new SpinnerNumberModel(10, 1, 30, 1);
        jSpinner1.setModel(model);
        JFormattedTextField textField = ((JSpinner.DefaultEditor) jSpinner1.getEditor()).getTextField();
        textField.setEditable(false);

    }

    private void limpiarTotales() {
        totalDoc.setText("");
        totalImporte.setText("");
        totalDoc.setText("");
        totalLegajos.setText("");
        totalReg1.setText(obtenerTotalRegistro());

    }

    private boolean tablaVacia() {
        return jTable1.getModel().getRowCount() == 0;
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void generarReporteExcel(File archivo) throws FileNotFoundException, IOException, SQLException {
        ResultSet resultSet = db.consulta(sql);
        Workbook workbook = new XSSFWorkbook();
        // Crear un estilo para la cabecera
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);

        Sheet sheet = workbook.createSheet("Resultado de Consulta");
        System.out.println("EXPORT INICIADO");
        // Crear una fila para la cabecera
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("GRUPO");
        headerRow.createCell(1).setCellValue("EMPRESA");
        headerRow.createCell(2).setCellValue("LEGAJO");
        headerRow.createCell(3).setCellValue("CODIGO CONCEPTO");
        headerRow.createCell(4).setCellValue("DESCRIP COD_CONCEPTO");
        headerRow.createCell(5).setCellValue("CANTIDAD");
        headerRow.createCell(6).setCellValue("IMPORTE");
        headerRow.createCell(7).setCellValue("FECHA APLICACION");
        // Agregar más celdas según sea necesario para otras columnas

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        int rowNum = 1; // Comenzar desde la segunda fila para los datos
        double totalCantidad = 0;
        double totalImporte = 0;
        int ultimaFila;
        double importe = 0;
        while (resultSet.next()) {

            int i = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(resultSet.getString(++i));
            row.createCell(1).setCellValue(resultSet.getString(++i));
            row.createCell(2).setCellValue(resultSet.getString(++i) + resultSet.getString(++i) + resultSet.getString(++i));
            row.createCell(3).setCellValue(resultSet.getString(++i));
            row.createCell(4).setCellValue(resultSet.getString(++i));
            row.createCell(5).setCellValue(resultSet.getString(++i));
            totalCantidad += resultSet.getInt(i);
            importe = Double.parseDouble(resultSet.getString(++i).replace(",", "."));
            // System.out.println(importe);
            row.createCell(6).setCellValue(importe);

            totalImporte += importe;
            row.createCell(7).setCellValue(resultSet.getString(++i));

            // Agrega más celdas según sea necesario para otras columnas
        }
        //escribe totales

        Row row = sheet.createRow(++rowNum);

        row.createCell(4).setCellValue("TOTALES");

        row.createCell(5).setCellValue("CANTIDAD");

        row.createCell(6).setCellValue("IMPORTE ");

        for (int i = 4; i < 7; i++) {
            row.getCell(i).setCellStyle(headerStyle);
        }

        row = sheet.createRow(++rowNum);
        row.createCell(5).setCellValue(totalCantidad);

        row.createCell(6).setCellValue(totalImporte);
        sheet.autoSizeColumn(6);

        rowNum = rowNum + 3;
        row = sheet.createRow(rowNum);
        row.createCell(4).setCellValue("ENPRESA");
        row.createCell(5).setCellValue("CANTIDAD LEGAJOS");
        row.createCell(6).setCellValue("TOTAL IMPORTE");

        for (int i = 4; i < 7; i++) {
            row.getCell(i).setCellStyle(headerStyle);
        }

        resultSet = db.consulta("SELECT empresa, count(empresa) as cantidad_legajos, sum(CAST (importe as REAL )) total from (SELECT * FROM (SELECT g.grupo, g.empresa, tipo_doc, nro_doc, secuencia, \n"
                + "              cod_concepto, descrip_concepto, replace(cantidad, ',', '.') AS cantidad, replace(importe, ',', '.') as importe,\n"
                + "               fechaDate AS fecha_aplicacion, fechaString AS fecha_csv \n"
                + "              FROM transaccion t \n"
                + "              LEFT JOIN grupo_empresa g \n"
                + "              ON t.id_grup_emp = g.id_grup_emp))as l \n"
                + "              GROUP BY empresa" + "\n");

        while (resultSet.next()) {
            int i = 0;
            row = sheet.createRow(++rowNum);
            row.createCell(4).setCellValue(resultSet.getString(++i));
            row.createCell(5).setCellValue(resultSet.getString(++i));
            row.createCell(6).setCellValue(resultSet.getDouble(++i));

        }

        rowNum = rowNum + 3;
        row = sheet.createRow(rowNum);
        row.createCell(4).setCellValue("LEGAJOS DISTINTOS");
        row.createCell(5).setCellValue("TOTAL REGISTROS");

        for (int i = 4; i < 6; i++) {
            row.getCell(i).setCellStyle(headerStyle);
        }
        row = sheet.createRow(++rowNum);
        System.out.println("CASI FINALIZADO");
        row.createCell(4).setCellValue(obtenerTotalLegajos());
        row.createCell(5).setCellValue(obtenerTotalRegistro());

        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        // Guarda el archivo de Excel
//       
//        try (FileOutputStream outputStream = new FileOutputStream("resultado.xlsx")) {
//            workbook.write(outputStream);
//        }
        workbook.write(new FileOutputStream(archivo + ".xlsx"));
        System.out.println("Consulta exportada a Excel correctamente.");
        Mensajes.exito("Reporte generado correctamente");

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JCheckBox FechaCheck;
    javax.swing.JComboBox<String> FechaComboBox;
    javax.swing.JCheckBox conceptoCheck;
    javax.swing.JCheckBox conceptoDistintoCheck1;
    javax.swing.JTextField conceptoTextField;
    javax.swing.JCheckBox descripcionCheck;
    javax.swing.JTextField descripcionTextField;
    javax.swing.JCheckBox dniDistintosCheck;
    javax.swing.JTextField docField;
    javax.swing.JCheckBox documentoCheck;
    javax.swing.JCheckBox empresaCheck;
    javax.swing.JTextField empresajField;
    javax.swing.JButton exportarBtn;
    javax.swing.JCheckBox grupoCheck;
    javax.swing.JTextField grupojField1;
    javax.swing.JLabel jLabel10CD;
    javax.swing.JLabel jLabel13CD;
    javax.swing.JLabel jLabel14CD;
    javax.swing.JLabel jLabel5;
    javax.swing.JSpinner jSpinner1;
    javax.swing.JTable jTable1;
    javax.swing.JCheckBox legajoCheck;
    javax.swing.JTextField legajoField;
    javax.swing.JLabel ordCant;
    javax.swing.JLabel ordImporte;
    javax.swing.JLabel ordLejago;
    javax.swing.JButton reporteBtn;
    javax.swing.JLabel totalDoc;
    javax.swing.JLabel totalImporte;
    javax.swing.JLabel totalLegajos;
    javax.swing.JLabel totalReg1;
    // End of variables declaration//GEN-END:variables

    private void configTable() {
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void configurarCierre() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Deshabilita el cierre predeterminado
        JFrame form = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (Mensajes.confirmar("¿Desea cerrar el programa?", "Cerrar programa")) {
                    // Cierra la ventana si el usuario elige "Sí"
                    form.dispose();
                    System.exit(0);
                }
            }
        });
    }

}
