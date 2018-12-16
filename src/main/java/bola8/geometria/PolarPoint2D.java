package bola8.geometria;

import java.awt.geom.Point2D;

/**
 * Punto geometrico expresado en coordenadas polares.
 * @author Jorge Berjano
 */
public class PolarPoint2D extends Point2D {
    private double radio;
    private Angulo angulo = new Angulo(0);

    public PolarPoint2D() {         
    }
             
    public PolarPoint2D(double radio, Angulo angulo) {
        this.radio = radio;
        this.angulo = angulo;
    }

    @Override
    public Object clone() {
        PolarPoint2D clon = (PolarPoint2D) super.clone();
        clon.angulo = (Angulo) angulo.clone();
        return clon;
    }
    
    
    @Override
    public double getX() {
        return radio * Math.cos(angulo.getRadianes());
    }

    @Override
    public double getY() {
        return radio * -Math.sin(angulo.getRadianes());
    }

    @Override
    public void setLocation(double x, double y) {
        radio = Math.sqrt(x * x + y * y);
        angulo = new Angulo(Math.atan2(y, x), Angulo.RADIANES);
    }

    public Angulo getAngulo() {
        return angulo;
    }

    public void setAngulo(Angulo angulo) {
        this.angulo = angulo;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
    
    
}
