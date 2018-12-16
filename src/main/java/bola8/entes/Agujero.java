package bola8.entes;

import bola8.fuerzas.FuerzaExterna;
import bola8.geometria.Geometria;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import bola8.materiales.Material;
import bola8.materiales.MaterialBasico;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Jorge Berjano
 */
public class Agujero implements Ente, FuerzaExterna {

    private Point2D posicion;
    private double radio;

    public Agujero(double x, double y, double radio) {
        posicion = new Point2D.Double(x, y);
        this.radio = radio;
    }

    public void pintar(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillArc((int) (posicion.getX() - radio), (int) (posicion.getY() - radio), (int) radio * 2, (int) radio * 2, 0, 360);
    }

    public boolean permiteIntrusion() {
        return true;
    }

    public Material getMaterial() {
        return MaterialBasico.VACIO;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    public Vector2D getVectorFuerza(EnteMovil ente) {
        if (ente instanceof Bola) {
            Bola bola = (Bola) ente;
            double distancia = Geometria.distancia(posicion, bola.getPosicion());

            if (distancia < 1) {
                bola.setRadio(bola.getRadio() - 1);
            }
            if (distancia < radio) {
                Vector2D v = new VectorPolar2D(posicion, bola.getPosicion());
                v.setModulo(v.getModulo() * 10);
                return v.getVectorInvertido();
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean isAnularResto() {
        return true;
    }

    public boolean permiteTraspasar() {
        return true;
    }
}
