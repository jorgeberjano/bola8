package bola8.entes;

import bola8.geometria.Vector2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Jorge Berjano
 */
public interface EnteMovil extends Ente, Cloneable {

    boolean activo();
        
    void mover();

    void deshacerMovimiento();

    double calcularSeparacion(Ente ente);

    Vector2D getVelocidad();

    double getMasa();

    boolean prepararMovimiento();

    boolean haCompletadoMovimiento();

    Vector2D getFuerzaCinetica();

    Point2D getPosicion();

    void setPosicion(Point2D posicion);

    void setProporcionDesplazamientoPendiente(double proporcion);

    double getProporcionDesplazamientoPendiente();

    void aplicarFuerza(Vector2D fuerza);

    void aplicarAceleracion(Vector2D aceleracion);

    Vector2D calcularFuerzaPlanoInclinado(Ente ente);
}
