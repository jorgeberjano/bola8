package bola8.geometria;

import java.awt.geom.Point2D;

/**
 *
 * @author Jorge Berjano
 */
public interface Vector2D extends Cloneable {

    double getModulo();

    void setModulo(double modulo);

    void setAngulo(Angulo angulo);

    Angulo getAngulo();

    double getX();

    double getY();

    Point2D trasladarPunto(Point2D p);

    Vector2D getVectorNormalizado();

    Vector2D getVectorInvertido();

    Vector2D sumar(Vector2D otroVector);

    Vector2D multiplicar(double factor);
    
    boolean esNulo();

    Object clone();
}
