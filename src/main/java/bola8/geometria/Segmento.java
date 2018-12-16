package bola8.geometria;

import java.awt.geom.Point2D;

/**
 *
 * @author Jorge Berjano
 */
public class Segmento {

    private Point2D p1;
    private Point2D p2;

    public Segmento(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Segmento(int x1, int y1, int x2, int y2) {
        this.p1 = new Point2D.Double(x1, y1);
        this.p2 = new Point2D.Double(x2, y2);
    }

    public Segmento(Point2D p1, Vector2D direccion) {
        this.p1 = p1;
        this.p2 = direccion.trasladarPunto(p1);
    }

    @Override
    public String toString() {
        return p1 + ", " + p2;
    }

    public Point2D getP1() {
        return p1;
    }

    public Point2D interseccion(Recta recta) {
        Point2D interseccion = getRecta().interseccion(recta);
        if (interseccion == null) {
            return null;
        }
        if (this.contiene(interseccion)) {
            return interseccion;
        } else {
            return null;
        }

    }

    public void setP1(Point2D p1) {
        this.p1 = p1;
    }

    public Point2D getP2() {
        return p2;
    }

    public void setP2(Point2D p2) {
        this.p2 = p2;
    }

    public boolean contiene(Point2D punto) {
        if (!getRecta().contiene(punto)) {
            return false;
        }
        Vector2D v1 = new VectorPolar2D(getP1(), punto);
        Vector2D v2 = new VectorPolar2D(getP2(), punto);
        return !Geometria.iguales(v1.getAngulo().getGrados(), v2.getAngulo().getGrados());
    }

    public void desplazar(double dx, double dy) {
        p1.setLocation(p1.getX() + dx, p1.getY() + dy);
        p2.setLocation(p2.getX() + dx, p2.getY() + dy);
    }

    public Point2D getCentro() {
        return new Point2D.Double(
                (p1.getX() + p2.getX()) / 2,
                (p1.getY() + p2.getY()) / 2);
    }

    public void girar(Angulo angulo) {
        Point2D centro = getCentro();
        VectorPolar2D v1 = new VectorPolar2D(centro, p1);
        VectorPolar2D v2 = new VectorPolar2D(centro, p2);
        v1.setAngulo(v1.getAngulo().sumar(angulo));
        v2.setAngulo(v2.getAngulo().sumar(angulo));
        p1 = v1.trasladarPunto(centro);
        p2 = v2.trasladarPunto(centro);
    }

    public Recta getRecta() {
        return new Recta(p1, p2);
    }

    public Point2D interseccion(Segmento otroSegmento) {
        Recta linea = getRecta();
        Recta otraLinea = otroSegmento.getRecta();
        Point2D interseccion = linea.interseccion(otraLinea);

        if (interseccion != null && this.contiene(interseccion) && otroSegmento.contiene(interseccion)) {
            return interseccion;
        } else {
            return null;
        }
    }
}
