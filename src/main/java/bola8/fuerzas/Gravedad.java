package bola8.fuerzas;

import bola8.geometria.Angulo;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import bola8.entes.EnteMovil;

/**
 *
 * @author Jorge
 */
public class Gravedad implements FuerzaExterna {

    private VectorPolar2D vectorFuerza;

    public Gravedad(double aceleracion) {
        this.vectorFuerza = new VectorPolar2D(0, -aceleracion);
    }

    public Gravedad(VectorPolar2D direccion) {
        this.vectorFuerza = direccion;
    }

    public Angulo getAngulo() {
        return vectorFuerza.getAngulo();
    }

    public void setAngulo(Angulo angulo) {
        vectorFuerza.setAngulo(angulo);
    }

    public void setModulo(double modulo) {
        vectorFuerza.setModulo(modulo);
    }

    public Vector2D getVectorFuerza(EnteMovil ente) {
        return vectorFuerza;
    }

    public void setVector(VectorPolar2D vectorFuerza) {
        this.vectorFuerza = vectorFuerza;
    }

    public boolean isAnularResto() {
        return false;
    }
}
