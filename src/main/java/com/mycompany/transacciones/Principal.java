package com.mycompany.transacciones;

import interfaz.grafica.Loading;
import interfaz.grafica.SesionUsuario;
import interfaz.grafica.tipoArchivo;
import java.sql.SQLException;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatIntelliJLaf;

/**
 *
 * @author dgc06
 */
public class Principal {

    public static void main(String[] args) throws Exception {

        try {
            DataBase db = new DataBase();
            db.inicializar();
            
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (Exception ex) {
                System.err.println("Failed to initialize LaF");
            }
            if (!db.existeSesion()) {
                db.cleanDB();
                db.cargarEmpresa();
                new tipoArchivo(db).setVisible(true);

            } else {
                SesionUsuario sesion = new SesionUsuario(db);
                sesion.setVisible(true);
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
