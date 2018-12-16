package bola8.controles;

import bola8.entes.Arista;
import bola8.entes.Bola;
import bola8.entes.Colision;
import bola8.entes.Colisionador;
import bola8.entes.EnteMovil;
import bola8.entes.Mundo;
import bola8.entes.VisitadorEntesMoviles;
import bola8.geometria.Angulo;
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
public class ControlArista extends ControlRatonNulo {

    private Arista arista;
    private Mundo mundo;
    private Point2D posicionAnterior;
    private Point2D posicionRaton;
    private final double radioAspa = 3;
    private boolean bloquear;

    @Override
    public void inicializar(Mundo mundo) {
        this.mundo = mundo;
        arista = new Arista(400, 410, 500, 410);
        arista.setNombre("mando");
        arista.setPermitirIntrusion(true);
        mundo.agregar(arista);
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
        arista.getSegmento().girar(new Angulo(pasos * 10));
    }
    
    @Override
    public void movido(final Point2D posicion) {
        posicionRaton = posicion;
        double x = posicion.getX();
        double y = posicion.getY();
        if (arista != null) {
            arista.getSegmento().desplazar(x - arista.getSegmento().getCentro().getX(), y - arista.getSegmento().getCentro().getY());
        }
        posicionAnterior = posicion;
        bloquear = false;
        //arista.setPermiteTraspasar();
    }

    @Override
    public void arrastrado(final Point2D posicion) {
        if (bloquear) {
            return;
        }
        posicionRaton = posicion;
        double x = posicion.getX();
        double y = posicion.getY();

        if (posicionAnterior != null) {
            final Vector2D direccionEmpuje = new VectorPolar2D(posicionAnterior, posicion);
            direccionEmpuje.setModulo(direccionEmpuje.getModulo());
            

            mundo.visitarEntesMoviles(new VisitadorEntesMoviles() {
                public void visitar(EnteMovil enteMovil) {
                    if (enteMovil instanceof Bola) {
                        aplicarEmpuje(direccionEmpuje, (Bola) enteMovil);
                    }
                }
            });
        }
        if (arista != null) {
            arista.getSegmento().desplazar(x - arista.getSegmento().getCentro().getX(), y - arista.getSegmento().getCentro().getY());
        }
        posicionAnterior = posicion;
    }

    private void aplicarEmpuje(Vector2D direccionEmpuje, Bola bola) {
   
        Bola copiaBola = (Bola) bola.clone();
        copiaBola.setVelocidad(direccionEmpuje.getVectorInvertido());
        copiaBola.setProporcionDesplazamientoPendiente(1);
        Colision colision = Colisionador.calcularColisionBolaArista(copiaBola, arista);
        if (colision != null) {
            bola.aplicarFuerza(colision.getAceleracionEnteActivo().multiplicar(bola.getMasa()));
            bloquear = true;
        }
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
