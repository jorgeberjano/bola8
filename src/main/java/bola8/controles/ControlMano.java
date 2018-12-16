package bola8.controles;

import bola8.entes.Arista;
import bola8.entes.Bola;
import bola8.entes.EnteMovil;
import bola8.entes.Mundo;
import bola8.entes.VisitadorEntesMoviles;
import bola8.geometria.Geometria;
import bola8.geometria.Segmento;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import bola8.materiales.MaterialBasico;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Control mediante una arista movil que sigue el movimiento del ratón y gira
 * con la rueda del mismo.
 * @author Jorge Berjano
 */
public class ControlMano extends ControlRatonNulo {

    private Mundo mundo;
    private Point2D posicionAnterior;
    private Point2D posicionRaton;
    private final double radioAspa = 3;

    @Override
    public void inicializar(Mundo mundo) {
        this.mundo = mundo;
    }

    @Override
    public void pintar(Graphics2D graphics) {
        if (posicionRaton == null) {
            return;
        }

        Rectangle2D rect = new Rectangle2D.Double(
                posicionRaton.getX() - radioAspa,
                posicionRaton.getY() - radioAspa,
                radioAspa,
                radioAspa);
        graphics.setColor(Color.RED);
        graphics.draw(new Line2D.Double(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY()));
        graphics.draw(new Line2D.Double(rect.getMaxX(), rect.getMinY(), rect.getMinX(), rect.getMaxY()));
    }

    @Override
    public void giroRueda(int pasos) {

    }

    @Override
    public void movido(final Point2D posicion) {
        posicionRaton = posicion;
        double x = posicion.getX();
        double y = posicion.getY();

        if (posicionAnterior != null) {
            final Vector2D direccionEmpuje = new VectorPolar2D(posicionAnterior, posicion);
            direccionEmpuje.setModulo(direccionEmpuje.getModulo());
            final Arista trayectoria = new Arista(new Segmento(posicionAnterior, direccionEmpuje));

            mundo.visitarEntesMoviles(new VisitadorEntesMoviles() {

                public void visitar(EnteMovil enteMovil) {
                    double separacion = enteMovil.calcularSeparacion(trayectoria);
                    if (Geometria.menor(separacion, 0)) {
                        double modulo = 4 * Math.sqrt(direccionEmpuje.getModulo());
                        direccionEmpuje.setModulo(modulo);
                        enteMovil.aplicarFuerza(direccionEmpuje);
                    }
                }
            });
        }

        posicionAnterior = posicion;
    }

    @Override
    public void click(Point2D punto) {
        Bola bola = new Bola(punto, 10);
        bola.setNombre("nueva");
        bola.setColor(Color.ORANGE);
        bola.setMaterial(MaterialBasico.GOMA);
        mundo.agregar(bola);
    }


}
