package bola8.geometria;

import java.awt.geom.Point2D;

/**
 *
 * @author Jorge
 */
public class VectorPolar2D implements Vector2D {

    private Angulo angulo;
    private double modulo;

    public VectorPolar2D() {
        angulo = new Angulo(0);
    }

    public VectorPolar2D(Angulo angulo, double modulo) {
        this.angulo = angulo;
        this.modulo = modulo;
        normalizar();
    }

    public VectorPolar2D(double dx, double dy) {
        this();
        modulo = Math.sqrt(dx * dx + dy * dy);
        angulo = new Angulo(Math.atan2(dy, dx), Angulo.RADIANES);
    }

    public VectorPolar2D(Point2D p1, Point2D p2) {
       this(p2.getX() - p1.getX(), p2.getY() - p1.getY());
    }

    public VectorPolar2D sumar(Vector2D otroVector) {
        return new VectorPolar2D(getX() + otroVector.getX(), getY() + otroVector.getY());
    }

    @Override
    public String toString() {
        return "Vector " + modulo + " " +  getAngulo().toString();
    }

    @Override
    public Object clone() {
        VectorPolar2D clon;
        try {
            clon = (VectorPolar2D) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
        clon.angulo = (Angulo) angulo.clone();
        return clon;
    }

    public Point2D trasladarPunto(Point2D p) {
        return new Point2D.Double(getX() + p.getX(), getY() + p.getY());
    }

    public double getModulo() {
        return modulo;
    }

    public void setModulo(double modulo) {
        this.modulo = modulo;
        normalizar();
    }

    public void setAngulo(Angulo angulo) {
        this.angulo = angulo;
    }

    public Angulo getAngulo() {
        return angulo;
    }

    public double getX() {
        return modulo * Math.cos(angulo.getRadianes());
    }

    public double getY() {
        return modulo * Math.sin(angulo.getRadianes());
    }

    public Vector2D getVectorNormalizado() {
        return new VectorPolar2D(angulo, 1);
    }

    public Vector2D getVectorInvertido() {
        return new VectorPolar2D(angulo.sumar(new Angulo(180)), modulo);
    }

    public Vector2D multiplicar(double factor) {
        return new VectorPolar2D((Angulo) angulo.clone(), modulo * factor);
    }

    public boolean esNulo() {
        return Geometria.iguales(modulo, 0);
    }

    private void normalizar() {
        if (modulo < 0) {
            angulo = angulo.sumarGrados(180);
            modulo = -modulo;
        }
    }
}
