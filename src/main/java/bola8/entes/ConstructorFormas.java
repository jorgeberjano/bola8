/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bola8.entes;

import bola8.geometria.Angulo;
import bola8.geometria.PolarPoint2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class ConstructorFormas {

    static public List<Arista> construirPoligonoRegular(Point2D centro, double radio, int numeroLados) {
        Point2D[] puntos = new Point2D[numeroLados + 1];
        double incrementoAngulo = 360.0 / (double) numeroLados;

        PolarPoint2D punto = new PolarPoint2D(radio, new Angulo(0));

        for (int i = 0; i <= numeroLados; i++) {
            punto.setAngulo(new Angulo(i * incrementoAngulo));
            puntos[i] = new Point2D.Double(punto.getX() + centro.getX(), punto.getY() + centro.getY());
        }

        List<Arista> listaAristas = new ArrayList<Arista>();
        for (int i = 0; i < puntos.length - 1; i++) {
            Arista arista =new Arista(puntos[i], puntos[i + 1]);
            arista.setNombre("Poligono regular de " + numeroLados + "lados");
            listaAristas.add(arista);
        }
        return listaAristas;
    }

    static public List<Arista> construirCuadrado(int x, int y, int ancho, int alto) {
        List<Arista> listaAristas = new ArrayList<Arista>();
        Arista arista = new Arista(x, y, x + ancho, y);
        arista.setNombre("abajo");
        listaAristas.add(arista);
        arista = new Arista(x, y, x, y + alto);
        arista.setNombre("izquierda");
        listaAristas.add(arista);
        arista = new Arista(x + ancho, y, x + ancho, y + alto);
        arista.setNombre("derecha");
        listaAristas.add(arista);
        arista = new Arista(x, y + alto, x + ancho, y + alto);
        arista.setNombre("arriba");
        listaAristas.add(arista);
        return listaAristas;
    }

}
