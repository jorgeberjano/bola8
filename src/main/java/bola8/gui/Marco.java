package bola8.gui;

import bola8.entes.Mundo;
import bola8.fases.Fase;
import bola8.fases.Fase1;
import bola8.fases.Fase2;
import bola8.fases.Fase3;
import bola8.fases.Fase4;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Marco (ventana) donde se muestra el juego.
 * @author Jorge Berjano
 */
public class Marco extends javax.swing.JFrame {

    private Mundo mundo;
    private Lienzo lienzo;
    private Fase fase;
    private ActionListener tarea;
    private Timer timer;

    /** Creates new form Marco */
    public Marco() {
        initComponents();

        mundo = new Mundo();
        lienzo = new Lienzo(mundo);

        panelJuego.add(lienzo, BorderLayout.CENTER);
        pack();

        iniciar();

        int latencia = 20; // milisegundos
        tarea = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                mundo.mover();
                lienzo.validate();
                lienzo.repaint();
            }
        };
        timer = new Timer(latencia, tarea);
    }

    private void iniciar() {
//        ticInicio = System.currentTimeMillis();
        mundo.limpiar();

        int i = comboPrueba.getSelectedIndex();
        switch (i) {
            case 0:
                fase = new Fase3();
                break;
            case 1:
                fase = new Fase2();
                break;
            case 2:
                fase = new Fase1();
                break;
            case 3:
                fase = new Fase4();
                break;
        }
        fase.inicializar(lienzo);
        lienzo.validate();
        lienzo.repaint();
    }

    private void actualizarEstado() {
        if (botonAuto.isSelected()) {
            timer.start();
        } else {
            timer.stop();
        }
    }

//    public void iniciar1() {
//
//        lienzo.setControl(new ControlArista());
//
//        bola = new Bola(100, 200, 20);
//        bola.setNombre("activa");
//        bola.setColor(Color.BLUE);
//        bola.aplicarFuerza(new VectorPolar2D(new Angulo(0), 100));
//        mundo.agregar(bola);
//
//        Bola pelota2 = new Bola(200, 210, 20);
//        pelota2.aplicarFuerza(new VectorPolar2D(new Angulo(0), 10));
//        pelota2.setNombre("pasiva");
//        mundo.agregar(pelota2);
//
//        crearCuadrado(10, 500);
//    }
//
//    public void iniciar2() {
//
//        lienzo.setControl(new ControlGiro());
//
//        bola = new Bola(100, 200, 20);
//        bola.setNombre("activa");
//        bola.aplicarFuerza(new VectorPolar2D(new Angulo(0), 200));
//        mundo.agregar(bola);
//
//        for (int i = 0; i < 3; i++) {
//            Bola bolaPasiva = new Bola(250 + 40 * i, 200, 20);
//            bolaPasiva.setNombre("pasiva-" + (i + 1));
//            mundo.agregar(bolaPasiva);
//        }
//
//        crearCuadrado(10, 500);
//    }
//
//    public void iniciar3() {
//
//        lienzo.setControl(new ControlArista());
//
//        bola = new Bola(200, 200, 20);
//        mundo.agregar(bola);
//
//        crearCuadrado(5, 5, 500, 500);
//        for (int i = 0; i < 10; i++) {
//            crearCuadrado((int) (500 * Math.random()), (int) (500 * Math.random()), 40, 40);
//        }
//
//        mundo.agregar(new Arista(0, 0, 500, 300));
//    }

//    public void crearCuadrado(int x, int y, int ancho, int alto) {
//        mundo.agregar(new Arista(x, y, x + ancho, y));
//        mundo.agregar(new Arista(x, y, x, y + alto));
//        mundo.agregar(new Arista(x + ancho, y, x + ancho, y + alto));
//        mundo.agregar(new Arista(x, y + alto, x + ancho, y + alto));
//    }
//
//    public void crearCuadrado(int m, int l) {
//        mundo.agregar(new Arista(m, m, l - m, m));
//        mundo.agregar(new Arista(m, l - m, l - m, l - m));
//        mundo.agregar(new Arista(m, m, m, l - m));
//        mundo.agregar(new Arista(l - m, m, l - m, l - m));
//    }
//    private void girar(int pasos) {
//        if (!habilitarInteraccion) {
//            return;
//        }
//        mando.getSegmento().girar(new Angulo(pasos * 10));
//    }
//
//    public void desplazar(int x, int y) {
//        if (!habilitarInteraccion) {
//            return;
//        }
////        segmentoMando.desplazar(0, getHeight() - y - segmentoMando.getP1().getY() - 50);
//        if (mando != null) {
//            mando.getSegmento().desplazar(x - mando.getSegmento().getCentro().getX(), mundo.getAlto() - y - mando.getSegmento().getCentro().getY());
//        }
//
//        if (gravedad != null) {
////            double cx = getWidth() / 2;
////            double cy = getHeight() / 2;
//            Vector2D v = new VectorPolar2D(250 - x, y - 250);
//            gravedad.setAngulo(v.getAngulo());
//            gravedad.setModulo(v.getModulo() / 50);
//        }
//    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelConsola = new javax.swing.JPanel();
        comboPrueba = new javax.swing.JComboBox();
        botonPaso = new javax.swing.JButton();
        botonAuto = new javax.swing.JToggleButton();
        botonIniciar = new javax.swing.JButton();
        etiquetaTiempo = new javax.swing.JLabel();
        panelBorde = new javax.swing.JPanel();
        panelJuego = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboPrueba.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Prueba 1", "Prueba 2", "Prueba 3", "Prueba 4", "Prueba 5", "Prueba 6" }));
        comboPrueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPruebaActionPerformed(evt);
            }
        });
        panelConsola.add(comboPrueba);

        botonPaso.setText("paso");
        botonPaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPasoActionPerformed(evt);
            }
        });
        panelConsola.add(botonPaso);

        botonAuto.setText("auto");
        botonAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAutoActionPerformed(evt);
            }
        });
        panelConsola.add(botonAuto);

        botonIniciar.setText("iniciar");
        botonIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarActionPerformed(evt);
            }
        });
        panelConsola.add(botonIniciar);

        etiquetaTiempo.setText("00:00");
        panelConsola.add(etiquetaTiempo);

        getContentPane().add(panelConsola, java.awt.BorderLayout.SOUTH);

        panelBorde.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBorde.setLayout(new java.awt.BorderLayout());

        panelJuego.setPreferredSize(new java.awt.Dimension(500, 500));
        panelJuego.setLayout(new java.awt.BorderLayout());
        panelBorde.add(panelJuego, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelBorde, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPasoActionPerformed
        mundo.mover();
        lienzo.validate();
        lienzo.repaint();
}//GEN-LAST:event_botonPasoActionPerformed

    private void botonAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAutoActionPerformed
        actualizarEstado();
    }//GEN-LAST:event_botonAutoActionPerformed

    private void botonIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarActionPerformed
        iniciar();
//        botonAuto.setSelected(true);
        actualizarEstado();
    }//GEN-LAST:event_botonIniciarActionPerformed

    private void comboPruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPruebaActionPerformed
        iniciar();
    }//GEN-LAST:event_comboPruebaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Marco().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton botonAuto;
    private javax.swing.JButton botonIniciar;
    private javax.swing.JButton botonPaso;
    private javax.swing.JComboBox comboPrueba;
    private javax.swing.JLabel etiquetaTiempo;
    private javax.swing.JPanel panelBorde;
    private javax.swing.JPanel panelConsola;
    private javax.swing.JPanel panelJuego;
    // End of variables declaration//GEN-END:variables
}
