package bola8.geometria;

import java.awt.geom.Point2D;

/**
 *
 * @author Jorge
 */
public class Semirrecta extends Recta {

    public Semirrecta(Point2D origen, Angulo angulo) {
        super(origen, angulo);
    }

    public Semirrecta(Point2D origen, Point2D otroPunto) {
        super(origen, otroPunto);
    }

    public Semirrecta(Point2D origen, Vector2D direccion) {
        super(origen, direccion);
    }

    @Override
    public Point2D interseccion(Recta otraRecta) {
        Point2D interseccion = super.interseccion(otraRecta);
        if (interseccion != null && this.contiene(interseccion) && otraRecta.contiene(interseccion)) {
            return interseccion;
        } else {
            return null;
        }
    }

    @Override
    public boolean contiene(Point2D punto) {
        if (!super.contiene(punto)) {
            return false;
        }
        Vector2D v = new VectorPolar2D(getOrigen(), punto);
        return Geometria.iguales(v.getAngulo().getGrados(), getDireccion().getAngulo().getGrados());
    }
}
