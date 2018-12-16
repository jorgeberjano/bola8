package bola8.entes;

import bola8.geometria.Angulo;
import bola8.geometria.Geometria;
import bola8.geometria.Recta;
import bola8.geometria.Segmento;
import bola8.geometria.Semirrecta;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import java.awt.Color;
import java.awt.Graphics2D;
import bola8.materiales.MaterialBasico;
import bola8.materiales.Material;
import java.awt.geom.Point2D;

/**
 * Representa un ente redondo con capacidad de rebote y de calculo de fuerzas
 * sobre planos inclinados.
 * @author Jorge Berjano
 */
 public class Bola implements EnteMovil {

    private String nombre;
    private Point2D posicion;
    private Point2D posicionAnterior;
    private double radio;
    private Vector2D velocidad;
    private Material material = MaterialBasico.GOMA;
    private Color color = Color.ORANGE;
    private Vector2D fuerzasAplicadas;
    private double proporcionDesplazamientoPendiente;
    //private Vector2D vectorDesplazamiento;

    public Bola(Point2D p, double radio) {
        this(p.getX(), p.getY(), radio);
    }

    public Bola(double x, double y, double radio) {
        posicion = new Point2D.Double(x, y);
        posicionAnterior = new Point2D.Double(x, y);
        this.radio = radio;
        velocidad = new VectorPolar2D();
        fuerzasAplicadas = new VectorPolar2D();
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @Override
    public EnteMovil clone() {
        try {
            return (EnteMovil) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void pintar(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillArc((int) (posicion.getX() - radio), (int) (posicion.getY() - radio), (int) radio * 2, (int) radio * 2, 0, 360);
        graphics.setColor(Color.BLACK);
        graphics.drawArc((int) (posicion.getX() - radio), (int) (posicion.getY() - radio), (int) radio * 2, (int) radio * 2, 0, 360);

//        graphics.drawArc((int) (posicion.getX() - 1), (int) (posicion.getY() - 1), 2, 2, 0, 360);
//        graphics.setColor(Color.GREEN);
//        graphics.drawLine((int) posicion.getX(), (int) posicion.getY(), (int) (posicion.getX() + velocidad.getX()), (int) (posicion.getY() + velocidad.getY()));
    }

    public Vector2D getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Vector2D velocidad) {
        this.velocidad = velocidad;
    }

    public boolean haCompletadoMovimiento() {
        return !Geometria.mayor(proporcionDesplazamientoPendiente, 0);
    }

    public Vector2D getVectorDesplazamiento() {
        return velocidad.multiplicar(proporcionDesplazamientoPendiente);
    }

    public Point2D getPosicion() {
        return posicion;
    }

    public void setPosicion(Point2D posicion) {
        setPosicion(posicion.getX(), posicion.getY());
    }

    public void setPosicion(double x, double y) {
        guardarPosicionAnterior();
        this.posicion.setLocation(x, y);
    }

    private void guardarPosicionAnterior() {
        posicionAnterior.setLocation(posicion);
    }

    /*!
     * Prepara el movimiento ejecutando la fuerzas aplicadas y calculando el vector
     * de desplazamiento
     */
    public boolean prepararMovimiento() {
        if (!Geometria.iguales(fuerzasAplicadas.getModulo(), 0)) {
            Vector2D aceleracion = new VectorPolar2D(fuerzasAplicadas.getAngulo(), fuerzasAplicadas.getModulo() / getMasa());
            aplicarAceleracion(aceleracion);
        }
        fuerzasAplicadas.setModulo(0);
        proporcionDesplazamientoPendiente = velocidad.esNulo() ? 0 : 1;
        return proporcionDesplazamientoPendiente == 1;
    }

    public void finalizarMovimiento() {
        proporcionDesplazamientoPendiente = 0;
    }

    /*
     * Mueve la bola en base al vector de desplazamiento.
     */
    public void mover() {
        guardarPosicionAnterior();

        Vector2D vectorDesplazamiento = getVectorDesplazamiento();
        setPosicion(posicion.getX() + vectorDesplazamiento.getX(), posicion.getY() + vectorDesplazamiento.getY());

        finalizarMovimiento();
    }

    public void deshacerMovimiento() {
        posicion.setLocation(posicionAnterior);
        velocidad.setModulo(0);
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean contiene(Point2D p) {
        double distancia = Geometria.distancia(posicion, p);

        return Geometria.menor(distancia, radio);
    }

    public boolean permiteIntrusion() {
        return false;
    }

    public boolean permiteTraspasar() {
        return false;
    }


    public void aplicarFuerza(Vector2D fuerza) {
        if (fuerza == null || fuerza.getModulo() == 0) {
            return;
        }
        fuerzasAplicadas = fuerzasAplicadas.sumar(fuerza);
    }

    /*!
     * Aplica una aceleracion directamente sobre la velocidad
     */
    public void aplicarAceleracion(Vector2D aceleracion) {
        velocidad = velocidad.sumar(aceleracion);
    }

    public double getMasa() {
        return radio;//4 / 3 * Math.PI * radio * radio * radio * material.getDensidad() / 10000;
    }

    public void setProporcionDesplazamientoPendiente(double proporcion) {
        proporcionDesplazamientoPendiente = proporcion;
    }

    public double getProporcionDesplazamientoPendiente() {
        return proporcionDesplazamientoPendiente;
    }

    public Vector2D getFuerzaCinetica() {
        return getVelocidad().multiplicar(getMasa());
    }

    public Vector2D calcularFuerzaPlanoInclinado(Ente ente) {

        if (ente instanceof Bola) {
            return (fuerzaPlanoInclinado((Bola) ente));

        } else if (ente instanceof Arista) {
            return (fuerzaPlanoInclinado((Arista) ente));
        }

        return null;
    }

    /**
     * Fuerza que ejerce otra bola sobre esta cuando estan en contacto.
     */
    private Vector2D fuerzaPlanoInclinado(Bola otraBola) {
        double distancia = Geometria.distancia(posicion, otraBola.getPosicion());
        if (distancia - (radio + otraBola.getRadio()) > 0) {
            return null;
        }

        Recta rectaUnion = new Recta(posicion, otraBola.getPosicion());
        Point2D puntoContacto = rectaUnion.calcularPuntoDistancia(radio);
        Recta rectaPlanoInclinado = rectaUnion.perpendicular(puntoContacto);

        Semirrecta semirrectaFuerza = new Semirrecta(posicion, fuerzasAplicadas);
        Point2D interseccion = rectaPlanoInclinado.interseccion(semirrectaFuerza);
        if (interseccion == null) {
            return null;
        }
        return fuerzaPlanoInclinado(rectaPlanoInclinado);
    }

    private Vector2D fuerzaPlanoInclinado(Arista arista) {

        Recta rectaPlanoInclinado = arista.getSegmento().getRecta();

        // El movimiento debe ser contrario al plano inclinado
        Semirrecta semirrectaFuerza = new Semirrecta(posicion, fuerzasAplicadas);
        if (rectaPlanoInclinado.interseccion(semirrectaFuerza) == null) {
            return null;
        }

        // La perpendicular desde la posicion de la bola al plano debe cortar con la arista
        Semirrecta semirectaPerpendicular = rectaPlanoInclinado.semirrectaPerpendicular(posicion);
        Point2D interseccion = arista.getSegmento().interseccion(semirectaPerpendicular);
        if (interseccion == null) {
            // En caso contrario se verifica si pueden aplicar fuerzas de plano inclinado con los vertices de la arista
            Vector2D fuerza = fuerzaPlanoInclinado(arista.getSegmento().getP1());
            if (fuerza != null) {
                return fuerza;
            }
            fuerza = fuerzaPlanoInclinado(arista.getSegmento().getP2());
            if (fuerza != null) {
                return fuerza;
            }
            return null;
        }
        return fuerzaPlanoInclinado(rectaPlanoInclinado);
    }

    private Vector2D fuerzaPlanoInclinado(Recta rectaPlanoInclinado) {

        Recta perpendicularArista = rectaPlanoInclinado.perpendicular(posicion);
        Point2D puntoApoyo = rectaPlanoInclinado.interseccion(perpendicularArista);
        if (puntoApoyo == null) {
            return null;
        }
        double distancia = Geometria.distancia(posicion, puntoApoyo);
        if (distancia - radio > 1) {
            return null;
        }

        Point2D extremoFuerza = fuerzasAplicadas.trasladarPunto(posicion);
        Point2D proyeccion = perpendicularArista.proyeccionPerpendicular(extremoFuerza);

        Vector2D fuerzaReaccion = new VectorPolar2D(posicion, proyeccion);
        fuerzaReaccion.setAngulo(fuerzaReaccion.getAngulo().sumar(new Angulo(180)));

        return fuerzaReaccion;
    }

    private Vector2D fuerzaPlanoInclinado(Point2D p1) {
        double distancia = Geometria.distancia(posicion, p1);

        if (!Geometria.menor(distancia - radio, 1)) {
            return null;
        }
        Recta rectaUnion = new Recta(posicion, p1);
        Point2D puntoContacto = rectaUnion.calcularPuntoDistancia(radio);
        Recta rectaPlanoInclinado = rectaUnion.perpendicular(puntoContacto);
        return fuerzaPlanoInclinado(rectaPlanoInclinado);
    }

//    public boolean ocupaEspacioComun(Ente ente) {
//        return Geometria.menor(calcularSeparacion(ente), 0);
//    }

    public double calcularSeparacion(Ente ente) {
        if (ente instanceof Arista) {
            return calcularSeparacionConArista((Arista) ente);
        } else if (ente instanceof Bola) {
            return calcularSeparacionConBola((Bola) ente);
        }
        return Double.MAX_VALUE;
    }

    double calcularSeparacionConBola(Bola otraBola) {
        return Geometria.distancia(posicion, otraBola.getPosicion())
                - radio - otraBola.getRadio();
    }

    double calcularSeparacionConArista(Arista arista) {

        Segmento segmentoArista = arista.getSegmento();

        if (segmentoArista.contiene(posicion)) {
            return -radio;
        }
        Recta perpendicular = segmentoArista.getRecta().perpendicular(posicion);

        Point2D puntoCorte = segmentoArista.interseccion(perpendicular);
        if (puntoCorte != null) {
            double distanciaPerpendicular = new VectorPolar2D(posicion, puntoCorte).getModulo();
            return distanciaPerpendicular - radio;
        }

        double separacionP1 = Geometria.distancia(posicion, segmentoArista.getP1());
        double separacionP2 = Geometria.distancia(posicion, segmentoArista.getP2());
        return Math.min(separacionP1, separacionP2);
    }

    public boolean ocupaEspacioComun2(Ente ente) {
        if (ente instanceof Arista) {
            Arista arista = (Arista) ente;
            if (contiene(arista.getSegmento().getP1())) {
                return true;
            } else if (contiene(arista.getSegmento().getP2())) {
                return true;
            } else {
                Recta lineaSegmento = new Recta(arista.getSegmento().getP1(), arista.getSegmento().getP2());
                Recta perpendicular = lineaSegmento.perpendicular(posicion);
                Point2D puntoCorte = lineaSegmento.interseccion(perpendicular);
                if (puntoCorte == null) {
                    return false;
                }
//                if (arista.getSegmento().contiene(p) && this.contiene(p)) {
//                    return true;
//                }
                if (!arista.getSegmento().contiene(puntoCorte)) {
                    return false;
                }
                double distanciaPerpendicular = new VectorPolar2D(posicion, puntoCorte).getModulo();
                if (Geometria.menor(distanciaPerpendicular, radio)) {
                    return true;
                }
            }
        } else if (ente instanceof Bola) {
            Bola otraPelota = (Bola) ente;
            double distanciaEntreBolas = Geometria.distancia(posicion, otraPelota.getPosicion());
            if (Geometria.menor(distanciaEntreBolas, radio + otraPelota.getRadio())) {
                return true;
            }
        }

        return false;
    }
}
