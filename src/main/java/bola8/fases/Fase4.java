package bola8.fases;

import bola8.controles.ControlArista;
import bola8.entes.Arista;
import bola8.entes.Bola;
import bola8.entes.Mundo;
import bola8.fuerzas.Gravedad;
import bola8.geometria.Angulo;
import bola8.geometria.VectorPolar2D;
import bola8.gui.Lienzo;
import java.awt.Color;

/**
 *
 * @author Jorge Berjano
 */
public class Fase4 implements Fase {

    private Lienzo lienzo;
    private Mundo mundo;

    public void inicializar(Lienzo lienzo) {
        this.lienzo = lienzo;
        mundo = lienzo.getMundo();

        lienzo.setControl(new ControlArista());

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                Bola bola = new Bola(50 + 42 * i, 300 + j * 42, 20);
                bola.setNombre("bola" + i);
//            bola.setColor(Color.BLUE);
                bola.aplicarFuerza(new VectorPolar2D(new Angulo(Math.random() * 360), 200));
                mundo.agregar(bola);
            }
        }

        crearCuadrado(10, 500);
        mundo.agregar(new Arista(0, 0, 500, 300));
//        Arista a = new Arista(200, 100, 250, 300);
////        a.setPermitirIntrusion(true);
//        a.setNombre("obstaculo");
//        mundo.agregar(a);

        mundo.agregar(new Gravedad(1));
    }

    public void crearCuadrado(int x, int y, int ancho, int alto) {
        mundo.agregar(new Arista(x, y, x + ancho, y));
        mundo.agregar(new Arista(x, y, x, y + alto));
        mundo.agregar(new Arista(x + ancho, y, x + ancho, y + alto));
        mundo.agregar(new Arista(x, y + alto, x + ancho, y + alto));
    }

    public void crearCuadrado(int m, int l) {
        mundo.agregar(new Arista(m, m, l - m, m));
        mundo.agregar(new Arista(m, l - m, l - m, l - m));
        mundo.agregar(new Arista(m, m, m, l - m));
        mundo.agregar(new Arista(l - m, m, l - m, l - m));
    }
}
