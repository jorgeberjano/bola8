package bola8.geometria;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Linea recta de longitud infinita definida por la ecuacion vectorial:
 *      punto = lambda * direccion + origen
 * Donde:
 *      punto: cualquier punto de la recta
 *      lambda: el parametro
 *      direccion: el vector de direccion
 *      origen: el punto de origen
 *
 * @author Jorge Berjano
 */
public class Recta implements Serializable {

    private Vector2D direccion;
    private Point2D origen;
//    static private final Vector2D vectorNulo = new VectorPolar2D();

    /**
     * Construye la linea recta que pasa por un punto y tiene direccion definida
     * por un vector.
     * @param origen El punto de origen
     * @param direccion El vector que define la direccion de la linea.
     */
    public Recta(Point2D origen, Vector2D direccion) {
        this.direccion = direccion;
        this.origen = origen;
    }

    /**
     * Contruye una linea recta a partir de dos puntos.
     */
    public Recta(Point2D p1, Point2D p2) {
        this.direccion = new VectorPolar2D(p1, p2);
        this.origen = p1;
    }

    @Override
    public String toString() {
        return "Linea " + origen + " " + direccion;
    }

    /**
     * Construye la linea recta que pasa por un punto y tiene direccion definida
     * por un angulo.
     */
    public Recta(Point2D origen, Angulo angulo) {
        this.origen = origen;
        this.direccion = new VectorPolar2D(angulo, 1);
    }

    public Vector2D getDireccion() {
        return direccion;
    }

    public void setDireccion(Vector2D direccion) {
        this.direccion = direccion;
    }

    public Point2D getOrigen() {
        return origen;
    }

    public void setOrigen(Point2D origen) {
        this.origen = origen;
    }

    /**
     * Obtiene el angulo de la linea recta. El angulo siempre esta entre 0 y 180 grados.
     */
    public Angulo getAngulo() {
        double grados = direccion.getAngulo().getGrados();
        if (grados >= 180) {
            grados -= 180;
        }
        return new Angulo(grados);
    }

    /**
     * Calcula el menor de los angulos que forma esta linea recta con otra.
     */
    public Angulo calcularMenorAngulo(Recta otraLinea) {
        Angulo anguloIncidencia = getAngulo().restar(otraLinea.getDireccion().getAngulo());
        double grados = anguloIncidencia.getGrados();
        if (grados >= 270) {
            grados = 360 - grados;
        } else if (grados >= 180) {
            grados -= 180;
        } else if (grados >= 90) {
            grados = 180 - grados;
        }
        anguloIncidencia = new Angulo(grados);
        return anguloIncidencia;
    }

    /**
     * Indica si la linea recta es paralela a otra.
     */
    public boolean esParalela(Recta otraLinea) {
        Vector2D vectorDireccionNormalizado1 = direccion.getVectorNormalizado();
        Vector2D vectorDireccionNormalizado2 = otraLinea.getDireccion().getVectorNormalizado();
        return Geometria.iguales(vectorDireccionNormalizado1, vectorDireccionNormalizado2) ||
                Geometria.iguales(vectorDireccionNormalizado1, vectorDireccionNormalizado2.getVectorInvertido());
    }

    /**
     * Devuelve el punto donde se corta la linea con otra linea dada o null
     * si las lineas son paralelas.
     *
     * @param otraLinea La otra linea.
     */
    public Point2D interseccion(Recta otraLinea) {
        Point2D interseccion = calcularInterseccion(otraLinea);
        if (interseccion == null) {
            return null;
        }
        if (!this.contiene(interseccion)) {
            return null;
        }
        if (!otraLinea.contiene(interseccion)) {
            return null;
        }
        return interseccion;
    }

    private Point2D calcularInterseccion(Recta otraLinea) {
        Vector2D direccion2 = otraLinea.getDireccion();
        Point2D origen2 = otraLinea.getOrigen();

        double Ox1 = origen.getX();
        double Oy1 = origen.getY();

        double Ox2 = origen2.getX();
        double Oy2 = origen2.getY();

        double Vx1 = direccion.getX();
        double Vy1 = direccion.getY();

        double Vx2 = direccion2.getX();
        double Vy2 = direccion2.getY();

        // En el caso de que alguno de los vectores sea nulo se devuelve null
        if (Geometria.iguales(direccion.getModulo(), 0) || Geometria.iguales(direccion2.getModulo(), 0)) {
            return null;
        }

        // En el caso de que sean paralelas se devuelve null
        if (esParalela(otraLinea)) {
            return null;
        }

        // Se resuelve la ecuacion vectorial segun el valor de Vx1 y Vx2
        double lambda1;
        double fd; // Factor de despeje

        if (Math.abs(Vy2) > Math.abs(Vx2)) {
            fd = -Vx2 / Vy2;
            lambda1 = ((Ox2 - Ox1) + (Oy2 - Oy1) * fd) / (Vx1 + Vy1 * fd);
        } else {
            fd = -Vy2 / Vx2;
            lambda1 = ((Ox2 - Ox1) * fd + (Oy2 - Oy1)) / (Vx1 * fd + Vy1);
        }

        return new Point2D.Double(lambda1 * Vx1 + Ox1, lambda1 * Vy1 + Oy1);
    }

    public Point2D calcularPuntoDistancia(double distancia) {
        double angulo = direccion.getAngulo().getRadianes();
        double x = origen.getX() + distancia * Math.cos(angulo);
        double y = origen.getY() + distancia * Math.sin(angulo);
        return new Point2D.Double(x, y);
    }

    /**
     * Calcula la linea recta perpendicular a esta recta que pasa por un punto.
     */
    public Recta perpendicular(Point2D punto) {
        return new Recta(punto, getAngulo().sumar(new Angulo(90)));
    }

    /**
     * Calcula la semirecta perpendicular a esta recta que pasa por un punto.
     */
    public Semirrecta semirrectaPerpendicular(Point2D punto) {
        Recta rectaPermendicular = perpendicular(punto);
        return new Semirrecta(punto, interseccion(rectaPermendicular));
    }

    /**
     * Indica si la recta contiene un determinado punto
     */
    public boolean contiene(Point2D punto) {
        if (Geometria.iguales(punto, origen)) {
            return true;
        }
        Vector2D v = new VectorPolar2D(origen, punto);
        return v.getAngulo().equals(direccion.getAngulo()) ||
                v.getAngulo().equals(direccion.getAngulo().sumar(new Angulo(180)));
    }

    /**
     * Calcula la proyección perpendicular a la recta de un punto.
     */
    public Point2D proyeccionPerpendicular(Point2D punto) {
        return interseccion(perpendicular(punto));
    }

    /**
     * Calcula el punto reflejado teiendo como eje de reflexión la recta.
     */
    public Point2D reflejar(Point2D punto) {
        Point2D proyeccion = interseccion(perpendicular(punto));
        Vector2D v = new VectorPolar2D(punto, proyeccion);
        return v.trasladarPunto(proyeccion);
    }

    /**
     * Calcula la proyección de un vector sobre la recta partiendo de que
     * el origen del vector es el origen de la recta.
     */
    public Vector2D proyectar(Vector2D vector) {
        Point2D puntaVector = vector.trasladarPunto(origen);
        Point2D proyeccionPunta = proyeccionPerpendicular(puntaVector);
        if (proyeccionPunta == null || !contiene(proyeccionPunta)) {
            return null;
        }
        return new VectorPolar2D(origen, proyeccionPunta);
    }
}
