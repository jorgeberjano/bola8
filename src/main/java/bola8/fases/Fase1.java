package bola8.fases;

import bola8.controles.ControlArista;
import bola8.entes.Arista;
import bola8.entes.Bola;
import bola8.entes.Mundo;
import bola8.fuerzas.Gravedad;
import bola8.gui.Lienzo;
import bola8.materiales.MaterialBasico;
import java.awt.Color;

/**
 *
 * @author Jorge Berjano
 */
public class Fase1 implements Fase {

    private Lienzo lienzo;
    private Mundo mundo;

    public void inicializar(Lienzo lienzo) {
        this.lienzo = lienzo;
        mundo = lienzo.getMundo();

        lienzo.setControl(new ControlArista());

        Bola bola = new Bola(430, 400, 20);
        bola.setNombre("activa");
        bola.setColor(Color.BLUE);
        bola.setMaterial(MaterialBasico.GOMA);
//        bola.aplicarFuerza(new VectorPolar2D(new Angulo(0), 100));
        mundo.agregar(bola);

//        Bola pelota2 = new Bola(200, 410, 20);
//        pelota2.aplicarFuerza(new VectorPolar2D(new Angulo(0), 10));
//        pelota2.setNombre("pasiva");
//        mundo.agregar(pelota2);

//        crearCuadrado(10, 500);

        Arista a;

        a = new Arista(350, 200, 500, 350);
        a.setNombre("otra rampa");
        mundo.agregar(a);

        a = new Arista(0, 0, 500, 300);
        a.setNombre("rampa");
        mundo.agregar(a);

        a = new Arista(200, 100, 250, 300);
//        a.setPermitirIntrusion(true);
        a.setNombre("obstaculo");
        mundo.agregar(a);



        mundo.agregar(new Gravedad(9.8));
    }

}
