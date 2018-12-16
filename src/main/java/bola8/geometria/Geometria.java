package bola8.geometria;

import java.awt.geom.Point2D;

/**
 * Constantes y funciones referentes a geometria.
 *
 * @author Jorge Berjano
 */
public class Geometria {
    // Constante para las comparaciones entre numeros double.
    // Debido a la imprecision de los numeros reales representados por el
    // tipo double la comparacion entre ellos se hace con un margen de
    // tolerancia definido por epsilon.

    static private double epsilon = 0.001;


    // No se puede instanciar esta clase
    private Geometria() {
    }

    /**
     * Obtiene la constante para las comparaciones entre numeros double.
     */
    static public double getEpsilon() {
        return epsilon;
    }

    /**
     * Asigna la constante para las comparaciones entre numeros double.
     */
    static public void setEpsilon(double epsilon) {
        Geometria.epsilon = epsilon;
    }

    /**
     * Comprueba si un número real es cero con un margen de tolerancia defindo por epsilon.
     * @return Devuelve true si el valor absoluto del operandor es menor que epsilon.
     */
    static public boolean esCero(double operando) {
        return Math.abs(operando) < epsilon;
    }

    /**
     * Compara dos números reales con un margen de tolerancia defindo por epsilon.
     * @return Devuelve true si la diferencia entre los operandos es menor que epsilon.
     */
    static public boolean iguales(double operando1, double operando2) {
        return Math.abs(operando1 - operando2) < epsilon;
    }

    static public boolean mayor(double operando1, double operando2) {
        return !iguales(operando1, operando2) && operando1 > operando2;
    }

    static public boolean menor(double operando1, double operando2) {
        return !iguales(operando1, operando2) && operando1 < operando2;
    }

    public static boolean mayorIgual(double operando1, double operando2) {
        return iguales(operando1, operando2) || operando1 > operando2;
    }

    public static boolean menorIgual(double operando1, double operando2) {
        return iguales(operando1, operando2) || operando1 < operando2;
    }

    public static boolean iguales(Point2D p1, Point2D p2) {
        return Geometria.iguales(p1.getX(), p2.getX()) &&
                Geometria.iguales(p1.getY(), p2.getY());
    }

    public static boolean iguales(Vector2D p1, Vector2D p2) {
        return Geometria.iguales(p1.getX(), p2.getX()) &&
                Geometria.iguales(p1.getY(), p2.getY());
    }

    /**
     * Devuelve el punto medio de un segmento definido por dos puntos.
     */
    public static Point2D puntoMedio(Point2D punto1, Point2D punto2) {
        return new Point2D.Double((punto1.getX() + punto2.getX()) / 2, (punto1.getY() + punto2.getY()) / 2);
    }

    /**
     * Devuelve la distancia entre dos puntos dados.
     */
    public static double distancia(Point2D punto1, Point2D punto2) {
        return Math.sqrt(Math.pow(punto2.getX() - punto1.getX(), 2) + Math.pow(punto2.getY() - punto1.getY(), 2));
    }
}
