/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bola8.fases;

import bola8.controles.ControlGiroMonedas;
import bola8.entes.Agujero;
import bola8.entes.Arista;
import bola8.entes.Bola;
import bola8.entes.ConstructorFormas;
import bola8.entes.Mundo;
import bola8.fuerzas.Gravedad;
import bola8.fuerzas.Rozamiento;
import bola8.gui.Lienzo;
import bola8.materiales.MaterialBasico;

/**
 *
 * @author Jorge
 */
public class Fase2 implements Fase {

    private Lienzo lienzo;
    private Mundo mundo;
    private Agujero agujeroExito;
    private Agujero agujeroFracaso;

    public void inicializar(Lienzo lienzo) {
        this.lienzo = lienzo;
        mundo = lienzo.getMundo();

        lienzo.setControl(new ControlGiroMonedas());

        Bola bola = new Bola(250, 450, 20);
        bola.setMaterial(MaterialBasico.MONEDA);
        bola.setNombre("activa");
//        bola.aplicarFuerza(new VectorPolar2D(new Angulo(0), 200));
        mundo.agregar(bola);

//        for (int i = 0; i < 2; i++) {
//            Bola bolaPasiva = new Bola(250 + 40 * i, 200, 20);
//            bolaPasiva.setNombre("pasiva-" + (i + 1));
//            mundo.agregar(bolaPasiva);
//        }

        for (int i = 1; i < 8; i += 2) {
            int y = 400 - 45 * i;
            mundo.agregar(new Arista(200, y + 45, 380, y + 45));
            mundo.agregar(new Arista(150, y, 340, y));
        }

//        Point2D[] puntos = crearPoligonoRegular(lienzo.getCentro(), mundo.getAncho() / 2.1, 30);
//        for (int i = 0; i < puntos.length - 1; i++) {
//            Point2D punto1 = puntos[i];
//            Point2D punto2 = puntos[i + 1];
//            mundo.agregar(new Arista(punto1, punto2));
//        }
        mundo.agregar(ConstructorFormas.construirPoligonoRegular(lienzo.getCentro(), mundo.getAncho() / 2.1, 60));

        agujeroFracaso = new Agujero(250, 40, 21);
        mundo.agregar(agujeroFracaso);

        agujeroExito = new Agujero(348, 105, 21);
        mundo.agregar(agujeroExito);

    }

//    public Point2D[] crearPoligonoRegular(Point2D centro, double radio, int numeroLados) {
//        Point2D[] puntos = new Point2D[numeroLados + 1];
//        double incrementoAngulo = 360.0 / (double) numeroLados;
//
//        PolarPoint2D punto = new PolarPoint2D(radio, new Angulo(0));
//
//        for (int i = 0; i <= numeroLados; i++) {
//            punto.setAngulo(new Angulo(i * incrementoAngulo));
//            puntos[i] = new Point2D.Double(punto.getX() + centro.getX(), punto.getY() + centro.getY());
//        }
//        return puntos;
//    }
}
