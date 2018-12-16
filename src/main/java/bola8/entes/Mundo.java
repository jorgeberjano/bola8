package bola8.entes;

import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import bola8.fuerzas.FuerzaExterna;
import bola8.geometria.Angulo;
import bola8.geometria.Geometria;
import bola8.gui.Lienzo;
import bola8.utilidades.ListaDoble;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Berjano
 */
public class Mundo {

    private List<Ente> entes;
    private List<Ente> entesEstaticos;
    private List<EnteMovil> entesMoviles;
    private List<EnteMovil> entesMovilesEnMovimiento;
    public static List<Colision> colisionesEjecutadas = new ArrayList<Colision>();
    public List<FuerzaExterna> fuerzas;
    public double ancho = 500;
    public double alto = 500;
    private Angulo angulo = new Angulo(0);

    public Mundo() {
        entesEstaticos = new ArrayList<Ente>();
        entesMoviles = new ArrayList<EnteMovil>();
        entes = new ListaDoble(entesEstaticos, entesMoviles);
        fuerzas = new ArrayList<FuerzaExterna>();

        entesMovilesEnMovimiento = new ArrayList<EnteMovil>();
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public Angulo getAngulo() {
        return angulo;
    }

    public void setAngulo(Angulo angulo) {
        this.angulo = angulo;
    }

    public void agregar(List listaEntes) {
        for (Object ente : listaEntes) {
            agregar(ente);
        }
    }

    public void agregar(Object ente) {
        if (ente instanceof EnteMovil) {
            entesMoviles.add((EnteMovil) ente);
        } else if (ente instanceof Ente) {
            entesEstaticos.add((Ente) ente);
        }

        if (ente instanceof FuerzaExterna) {
            fuerzas.add((FuerzaExterna) ente);
        }
    }

    public void eliminar(Ente ente) {
        entes.remove(ente);
    }

    public void visitarEntesMoviles(VisitadorEntesMoviles visitador) {
        for (EnteMovil enteMovil : entesMoviles) {
            visitador.visitar(enteMovil);
        }
    }

    public boolean esIntrusista(EnteMovil enteMovil) {
        if (enteMovil.permiteIntrusion()) {
            return false;
        }

        for (Ente ente : entes) {
            if (ente != enteMovil && !ente.permiteIntrusion()) {
                double separacion = enteMovil.calcularSeparacion(ente);
                if (Geometria.menor(separacion, 0)) {
                    System.out.println("Intrusismo: " + enteMovil + " con " + ente);
                    return true;
                }
            }
        }
        return false;
    }

    public void pintar(Graphics2D graphics) {

        for (Ente ente : entes) {
            ente.pintar(graphics);
        }

        for (Colision colision : colisionesEjecutadas) {
            colision.pintar(graphics);
        }
    }

    private void comprobarMovimientoEnte(EnteMovil enteMovil) {
        if (enteMovil == null) {
            return;
        }

        if (!entesMovilesEnMovimiento.contains(enteMovil)) {
            if (!enteMovil.haCompletadoMovimiento()) {
                entesMovilesEnMovimiento.add(enteMovil);
            }
        } else if (enteMovil.haCompletadoMovimiento()) {
            entesMovilesEnMovimiento.remove(enteMovil);
        }
    }

    public void mover() {

        aplicarFuerzasExternas();

        // Se separan en una lista los entes moviles que se esten moviendo
        for (EnteMovil enteMovil : entesMoviles) {
            if (enteMovil.prepararMovimiento()) {
                entesMovilesEnMovimiento.add(enteMovil);
            }
        }

        colisionesEjecutadas.clear();
//        System.out.println("----movimiento--------------------------------------------");
        while (!entesMovilesEnMovimiento.isEmpty()) {
            EnteMovil enteMovil = entesMovilesEnMovimiento.get(0);
            entesMovilesEnMovimiento.remove(enteMovil);
            Colision colision = calcularColision(enteMovil);
            if (colision != null) {
                colision.ejecutar();
                colisionesEjecutadas.add(colision);
                comprobarMovimientoEnte(colision.getEntePasivo());
                comprobarMovimientoEnte(colision.getEnteActivo());                
            } else {
                enteMovil.mover();
//                System.out.println("movimiento " + enteMovil);
            }
            comprobarIntrusismo(enteMovil);
            
        }
    }

//    /**
//     * Mueve el mundo un instante.
//     * Primero se calculan las fuerzas externas que afecan a los entes moviles.
//     * Luego se separan en una lista los entes moviles que se esten moviendo.
//     */
//    public void mover() {
//
//        aplicarFuerzasExternas();
//
//        // Se separan en una lista los entes moviles que se esten moviendo
//        for (EnteMovil enteMovil : entesMoviles) {
//            if (enteMovil.prepararMovimiento()) {
//                entesMovilesEnMovimiento.add(enteMovil);
//            }
//        }
//
//        colisionesEjecutadas.clear();
//
//        // Se ejecutan las colisiones de los entes móviles en movimiento
//        Colision colision = calcularProximaColision();
//        while (colision != null) {
//            colision.ejecutar();
//
//            comprobarMovimientoEnte(colision.getEnteActivo());
//            comprobarMovimientoEnte(colision.getEntePasivo());
//
//            comprobarIntrusismo(colision.getEnteActivo());
//
//            colisionesEjecutadas.add(colision);
//            colision = calcularProximaColision();
//        }
//
//        // Se ejecutan los movimientos del resto de las bolas
//        for (EnteMovil enteMovil : entesMovilesEnMovimiento) {
//
//            enteMovil.mover();
//
//            comprobarIntrusismo(enteMovil);
//        }
//
//        entesMovilesEnMovimiento.clear();
//    }

    void comprobarIntrusismo(EnteMovil enteMovil) {
        if (esIntrusista(enteMovil)) {            
            enteMovil.deshacerMovimiento();
        }
    }

//    /**
//     * Calcula la proxima colisión, que será la que se produzca a menos distancia.
//     */
//    private Colision calcularProximaColision() {
//        Colision proximaColision = null;
//
//        for (EnteMovil enteMovil : entesMovilesEnMovimiento) {
//            Colision colision = calcularColision(enteMovil);
//            if (colision != null
//                    && (proximaColision == null || proximaColision.getDistancia() > colision.getDistancia())) {
//                proximaColision = colision;
//            }
//        }
//        return proximaColision;
//    }

    private void aplicarFuerzasExternas() {
        for (EnteMovil enteMovil : entesMoviles) {
            Vector2D sumaResultantes = new VectorPolar2D();
            for (FuerzaExterna fuerza : fuerzas) {
                Vector2D vector = fuerza.getVectorFuerza(enteMovil);

                if (vector != null && !vector.esNulo()) {
                    if (fuerza.isAnularResto()) {
                        enteMovil.getVelocidad().setModulo(0);
                        sumaResultantes = vector;
                        break;
                    } else {
                        sumaResultantes = sumaResultantes.sumar(vector);
                    }
                }
            }
            enteMovil.aplicarFuerza(sumaResultantes);

            aplicarFuerzasPlanoInclinado(enteMovil);
        }
    }

    public void limpiar() {
        entesMoviles.clear();
        entesEstaticos.clear();
        fuerzas.clear();
    }

    /*!
     * Calcula la colision que ocurre primero (menos distancia de colision)
     */
    public Colision calcularColision(EnteMovil enteActivo) {

        if (enteActivo.getVelocidad().esNulo() ||
            Geometria.esCero(enteActivo.getProporcionDesplazamientoPendiente())) {
            return null;
        }

        Colision colision = null;
        for (Ente ente : entes) {
            if (ente == enteActivo) {
                continue;
            }
            Colision colisionCandidata = Colisionador.calcularColision(enteActivo, ente);
            if (colisionCandidata == null) {
                continue;
            }
            if (colision == null || colision.getProporcionDesplazamientoRealizado() > colisionCandidata.getProporcionDesplazamientoRealizado()) {
                colision = colisionCandidata;
            }
        }
        return colision;
    }

    private void aplicarFuerzasPlanoInclinado(EnteMovil enteMovil) {

        List<Ente> entesExcluidos = new ArrayList();
        entesExcluidos.add(enteMovil);
        entesExcluidos.add(null);

        for (Ente ente : entes) {
            if (ente == enteMovil) {
                continue;
            }

            Vector2D fuerza = enteMovil.calcularFuerzaPlanoInclinado(ente);
            if (fuerza != null) {
                EnteMovil copiaEnteMovil = (EnteMovil) enteMovil.clone();
                copiaEnteMovil.aplicarFuerza(fuerza);
                entesExcluidos.set(1, ente);
                Vector2D otraFuerza = calcularFuerzasPlanoInclinado(copiaEnteMovil, entesExcluidos);
                // Si no hay mas fuerzas de plano inclinado que afecten al movimiento resultante, se aplica la fuerza
                if (otraFuerza == null) {
                    enteMovil.aplicarFuerza(fuerza);
                    break;
                }
            }
        }
    }

    private Vector2D calcularFuerzasPlanoInclinado(EnteMovil enteMovil, List<Ente> entesExcluidos) {

        for (Ente ente : entes) {
            if (entesExcluidos.contains(ente)) {
                continue;
            }
            Vector2D fuerza = enteMovil.calcularFuerzaPlanoInclinado(ente);
            if (fuerza != null) {
                return fuerza;
            }
        }
        return null;
    }
}
