package bola8.entes;

import java.awt.Graphics2D;
import bola8.materiales.Material;

/**
 *
 * @author Jorge Berjano
 */
public interface Ente extends Cloneable {

    void pintar(Graphics2D graphics);
    boolean permiteIntrusion();
    boolean permiteTraspasar();
    Material getMaterial();
    Object clone();
}
