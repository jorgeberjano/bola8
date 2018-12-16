package bola8.controles;

import bola8.geometria.Angulo;
import java.awt.Graphics2D;

/**
 *
 * @author Jorge
 */
public class ControlGiroMonedas extends ControlGiro {

    @Override
    public void giroRueda(int pasos) {
        asignarAngulo(mundo.getAngulo().sumar(new Angulo(pasos)));
    }

    @Override
    public void pintar(Graphics2D graphics) {
    }
}
