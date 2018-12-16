package bola8.controles;

import bola8.entes.Mundo;
import bola8.fuerzas.Gravedad;
import bola8.geometria.Angulo;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Control que modifica el giro del mundo.
 * @author Jorge Berjano
 */
public class ControlGiro extends ControlRatonNulo {

    private Gravedad gravedad = new Gravedad(270);
    private Point2D centro = new Point2D.Double();
    private Point2D extremo = new Point2D.Double();
    protected Mundo mundo;

    public void inicializar(Mundo mundo) {
        this.mundo = mundo;
        mundo.agregar(gravedad);
        gravedad.setModulo(9.8);
        centro = new Point2D.Double(mundo.getAncho() / 2, mundo.getAlto() / 2);
    }

    public void posicionRaton(int x, int y) {
        extremo = new Point2D.Double(x, y);
        Vector2D vector = new VectorPolar2D(centro, extremo);
        asignarAngulo(vector.getAngulo());
    }

    protected void asignarAngulo(Angulo angulo) {
        gravedad.setAngulo(new Angulo(-90).restar(angulo));
        mundo.setAngulo(angulo);
    }

    public void giroRuedaRaton(int pasos) {
    }

    public void pintar(Graphics2D graphics) {
        if (centro == null || extremo == null) {
            return;
        }
        graphics.setColor(Color.ORANGE);
//        Stroke trazo = graphics.getStroke();
//        float grosor = (float) (10 / new VectorPolar2D(centro, extremo).getModulo());
//        graphics.setStroke(new BasicStroke(grosor));
        Vector2D vector = new VectorPolar2D(centro, extremo);
        vector.setAngulo(new Angulo(0));
        Line2D linea = new Line2D.Double(centro, vector.trasladarPunto(centro));
        graphics.draw(linea);
//        graphics.setStroke(trazo);
    }
}
