package bola8.fases;

import bola8.controles.ControlAgarre;
import bola8.entes.Arista;
import bola8.entes.Bola;
import bola8.entes.ConstructorFormas;
import bola8.entes.Mundo;
import bola8.fuerzas.Gravedad;
import bola8.fuerzas.Rozamiento;
import bola8.geometria.Angulo;
import bola8.geometria.VectorPolar2D;
import bola8.gui.Lienzo;
import bola8.materiales.MaterialBasico;
import java.awt.Color;

/**
 *
 * @author Jorge Berjano
 */
public class TestBolasTaspasaAristas implements Fase {

    private Lienzo lienzo;
    private Mundo mundo;

    final int radioAgujero = 21;
    final int radioBolas = 10;

    public void inicializar(Lienzo lienzo) {
        this.lienzo = lienzo;
        mundo = lienzo.getMundo();

        lienzo.setControl(new ControlAgarre());     


        Bola bola3 = new Bola(400.001, 100, radioBolas);
        bola3.setNombre("negra");
        bola3.setMaterial(MaterialBasico.BOLA_BILLAR);
        bola3.setColor(Color.BLACK);
        bola3.aplicarFuerza(new VectorPolar2D(new Angulo(-90), 1000));
        mundo.agregar(bola3);

        Bola bola = new Bola(400, radioBolas, radioBolas);
        bola.setNombre("blanca");
        bola.setMaterial(MaterialBasico.BOLA_BILLAR);
        bola.setColor(Color.WHITE);
       // bola.aplicarFuerza(new VectorPolar2D(new Angulo(-90), 100));
        mundo.agregar(bola);

//        Bola bola2 = new Bola(200, 410, radioBolas);
//        bola2.setNombre("azul");
//        bola2.setMaterial(MaterialBasico.BOLA_BILLAR);
//        bola2.setColor(Color.BLUE);
//
//        bola2.aplicarFuerza(new VectorPolar2D(new Angulo(0), 100));
//        mundo.agregar(bola2);

//        for (int i = 0; i < 10; i++) {
//            Bola bola3 = new Bola(480 + i * radioBolas * 2.1, 300, radioBolas);
//            bola3.setNombre("rosa");
//            bola3.setMaterial(MaterialBasico.BOLA_BILLAR);
//            bola3.setColor(Color.PINK);
//            //bola.aplicarFuerza(new VectorPolar2D(new Angulo(0), 0));
//            mundo.agregar(bola3);
//        }
//
//        for (int i = 0; i < 10; i++) {
//            Bola bola3 = new Bola(180, 200 + i * radioBolas * 2.1, radioBolas);
//            bola3.setNombre("rosa");
//            bola3.setMaterial(MaterialBasico.BOLA_BILLAR);
//            bola3.setColor(Color.GREEN);
//            //bola.aplicarFuerza(new VectorPolar2D(new Angulo(0), 0));
//            mundo.agregar(bola3);
//        }

        int x = 10;
        int y = -10;
        int ancho = 800;
        int alto = 500;


        mundo.agregar(ConstructorFormas.construirCuadrado(0, 0, ancho, alto));
        //crearMesa(x, y, ancho, alto);
       
//        Agujero agujero1 = new Agujero(x + radioAgujero / 2, y + radioAgujero / 2, radioAgujero);
//        mundo.agregar(agujero1);

        mundo.agregar(new Gravedad(9.8));
        mundo.agregar(new Rozamiento(.5, .2));

       // mundo.agregar(new Arista(0, 0, 500, 300));
//        Arista a = new Arista(200, 100, 250, 300);
////        a.setPermitirIntrusion(true);
//        a.setNombre("obstaculo");
//        mundo.agregar(a);
    }

    public void crearMesa(int x, int y, int ancho, int alto) {
        mundo.agregar(new Arista(x + radioAgujero, y, x + ancho - radioAgujero, y));
        mundo.agregar(new Arista(x, y + radioAgujero, x, y + alto- radioAgujero));
        mundo.agregar(new Arista(x + ancho, y + radioAgujero, x + ancho, y + alto - radioAgujero));
        mundo.agregar(new Arista(x + radioAgujero, y + alto, x + ancho - radioAgujero , y + alto));
    }

}
