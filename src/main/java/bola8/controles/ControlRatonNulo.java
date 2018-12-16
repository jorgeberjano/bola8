/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bola8.controles;

import bola8.entes.Mundo;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Jorge
 */
public class ControlRatonNulo implements ControlRaton {

    @Override
    public void inicializar(Mundo mundo) {
    }

    @Override
    public void pintar(Graphics2D graphics) {
    }

    @Override
    public void movido(Point2D punto) {
    }

    @Override
    public void arrastrado(Point2D posicion) {
    }

    @Override
    public void click(Point2D punto) {
    }

    @Override
    public void dobleClick(Point2D punto) {
    }

    @Override
    public void presionado(Point2D punto) {
    }

    @Override
    public void liberado(Point2D punto) {
    }

    @Override
    public void giroRueda(int pasos) {
    }
}
