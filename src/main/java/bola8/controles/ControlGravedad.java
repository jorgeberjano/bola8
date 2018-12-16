package bola8.controles;

import bola8.entes.Mundo;
import bola8.fuerzas.Gravedad;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Control que modifica la gravedad del mundo.
 * @author Jorge Berjano
 */
public class ControlGravedad extends ControlRatonNulo {

    private Gravedad gravedad = new Gravedad(0);
    private Point2D centro = new Point2D.Double();
    private Point2D extremo = new Point2D.Double();

    public void inicializar(Mundo mundo) {
        centro = new Point2D.Double(mundo.getAncho() / 2, mundo.getAlto() / 2);
        mundo.agregar(gravedad);
    }

    public void mover(Point2D posicion) {
        Vector2D vector = new VectorPolar2D(centro, posicion);
        gravedad.setAngulo(vector.getAngulo());
        gravedad.setModulo(vector.getModulo() / 20);
    }

    public void pintar(Graphics2D graphics) {
        if (centro == null || extremo == null) {
            return;
        }
        graphics.setColor(Color.ORANGE);
//        Stroke trazo = graphics.getStroke();
//        float grosor = (float) (10 / new VectorPolar2D(centro, extremo).getModulo());
//        graphics.setStroke(new BasicStroke(grosor));
        Line2D linea = new Line2D.Double(centro, extremo);
        graphics.draw(linea);
//        graphics.setStroke(trazo);
    }
}
