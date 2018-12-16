package bola8.controles;

import bola8.entes.Mundo;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Jorge Berjano
 */
public interface ControlRaton {

    void inicializar(Mundo mundo);

    void movido(Point2D posicion);
    void arrastrado(Point2D posicion);
    void click(Point2D posicion);
    void dobleClick(Point2D posicion);
    void presionado(Point2D posicion);
    void liberado(Point2D posicion);
    void giroRueda(int pasos);

    public void pintar(Graphics2D graphics);
}
