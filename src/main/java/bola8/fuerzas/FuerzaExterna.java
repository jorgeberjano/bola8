package bola8.fuerzas;

import bola8.geometria.Vector2D;
import bola8.entes.*;

/**
 *
 * @author Jorge Berjano
 */
public interface FuerzaExterna {
    Vector2D getVectorFuerza(EnteMovil ente);
    boolean isAnularResto();
}
