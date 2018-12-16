package bola8.entes;

import bola8.geometria.Geometria;
import bola8.geometria.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Representa la colision entre dos entes
 * Separar segun el tipo de colision
 * Para colisiones entre bolas se crea un objeto con todos los datos:
 * posicion inicial, de choque y final tanto del ente activo como del pasivo
 * la colision se ejecutara parcialmente solo hasta la posicion de choque si en
 * las posiciones resultantes los entes sean intrusivos.
 * @author Jorge Berjano
 */
public class Colision implements Comparable {

    private Point2D puntoColision;
    private double proporcionDesplazamientoRealizado;

    private EnteMovil enteActivo;
    private Point2D posicionColisionEnteActivo;
    private Vector2D aceleracionEnteActivo;
   
    private EnteMovil entePasivo;
    //private Point2D posicionColisionEntePasivo;
    private Vector2D aceleracionEntePasivo;


    public EnteMovil getEnteActivo() {
        return enteActivo;
    }

    public void setEnteActivo(EnteMovil enteActivo) {
        this.enteActivo = enteActivo;
    }

    public Point2D getPosicionColisionEnteActivo() {
        return posicionColisionEnteActivo;
    }

    public void setPosicionColisionEnteActivo(Point2D posicicionColisionEnteActivo) {
        this.posicionColisionEnteActivo = posicicionColisionEnteActivo;
    }

//    public Point2D getPosicionColisionEntePasivo() {
//        return posicionColisionEntePasivo;
//    }
//
//    public void setPosicionColisionEntePasivo(Point2D posicicionColisionEntePasivo) {
//        this.posicionColisionEntePasivo = posicicionColisionEntePasivo;
//    }

    public Vector2D getAceleracionEnteActivo() {
        return aceleracionEnteActivo;
    }

    public void setAceleracionEnteActivo(Vector2D acelreacion) {
        this.aceleracionEnteActivo = acelreacion;
    }

    public Vector2D getAceleracionEntePasivo() {
        return aceleracionEntePasivo;
    }

    public void setAceleracionEntePasivo(Vector2D acelreacion) {
        this.aceleracionEntePasivo = acelreacion;
    }

    public double getProporcionDesplazamientoRealizado() {
        return proporcionDesplazamientoRealizado;
    }

    public void setProporcionDesplazamientoRealizado(double proporcion) {
        this.proporcionDesplazamientoRealizado = proporcion;
    }

    public EnteMovil getEntePasivo() {
        return entePasivo;
    }

    public void setEntePasivo(EnteMovil entePasivo) {
        this.entePasivo = entePasivo;
    }

    public Point2D getPuntoColision() {
        return puntoColision;
    }

    public void setPuntoColision(Point2D puntoColision) {
        this.puntoColision = puntoColision;
    }

    

    public void ejecutar() {
//        System.out.println("colision de " + enteActivo + " contra " + entePasivo);
        enteActivo.getVelocidad().setModulo(0);
        enteActivo.aplicarAceleracion(aceleracionEnteActivo);
        enteActivo.setPosicion(posicionColisionEnteActivo);

        enteActivo.setProporcionDesplazamientoPendiente(
                enteActivo.getProporcionDesplazamientoPendiente() - proporcionDesplazamientoRealizado);

        if (entePasivo != null && aceleracionEntePasivo != null) {
            entePasivo.aplicarAceleracion(aceleracionEntePasivo);
            //entePasivo.setPosicion(posicionColisionEntePasivo);
            entePasivo.setProporcionDesplazamientoPendiente(
                    entePasivo.getProporcionDesplazamientoPendiente() - proporcionDesplazamientoRealizado);
        }
    }

    public int compareTo(Object o) {
        if (o instanceof Colision) {
            Colision otraColision = (Colision) o;
            if (Geometria.iguales(proporcionDesplazamientoRealizado, otraColision.proporcionDesplazamientoRealizado)) {
                return 0;
            } else if (proporcionDesplazamientoRealizado > otraColision.proporcionDesplazamientoRealizado) {
                return 1;
            } else {
                return -1;
            }
        }
        return 1;
    }

    public void pintar(Graphics2D graphics) {
        graphics.setColor(Color.RED);

        if (puntoColision != null) {
            Point2D punto = puntoColision;
            graphics.drawOval((int) punto.getX() - 1, (int) punto.getY() - 1, 2, 2);
        }

        if (aceleracionEnteActivo != null) {
            Point2D p2 = aceleracionEnteActivo.trasladarPunto(posicionColisionEnteActivo);
            graphics.drawLine((int) posicionColisionEnteActivo.getX(), (int) posicionColisionEnteActivo.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }
}
