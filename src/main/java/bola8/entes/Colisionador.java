package bola8.entes;

import bola8.geometria.Angulo;
import bola8.geometria.Geometria;
import bola8.geometria.Recta;
import bola8.geometria.Semirrecta;
import bola8.geometria.Vector2D;
import bola8.geometria.VectorPolar2D;
import java.awt.geom.Point2D;

/**
 * Calcula las colisoiones entre los entes.
 * @author Jorge
 */
public class Colisionador {

    static public Colision calcularColision(EnteMovil enteActivo, Ente entePasivo) {
        if (!(enteActivo instanceof Bola)) {
            return null;
        }
        Bola bola = (Bola) enteActivo;

        Colision colision = null;
        if (entePasivo instanceof Arista) {
            Arista arista = (Arista) entePasivo;

            colision = calcularColisionBolaArista(bola, arista);

            if (colision == null) {
                colision = calcularColisionBolaVertice(bola, arista.getSegmento().getP1());
            }
            if (colision == null) {
                colision = calcularColisionBolaVertice(bola, arista.getSegmento().getP2());
            }
        } else if (entePasivo instanceof Bola) {
            colision = calcularColisionBolas(bola, (Bola) entePasivo);
        }
        return colision;
    }

   /**
     * Calcula la variable lambda del sistema de ecuaciones:
     * P1 = lambda * VD1 + O1
     * P2 = lambda * VD2 + O2
     * |P1 P2| = r1 + r2
     * Dondde:
     * P1 = posicion de choque bola 1
     * P2 = posicion de choche bola 2
     * r1 = radio bola 1
     * r2 = radio bola 2
     * VD1 = vector desplazamiento bola 1
     * VD2 = vector desplazamiento bola 2
     * O1 = posicion inicial bola 1
     * O2 = posicion final bola 2
     */
    static private Double calcularLambdaColisionBolas(Bola bolaActiva, Bola otraBola) {

        Vector2D vectorDesplazamiento1 = bolaActiva.getVectorDesplazamiento();

        double dx1 = vectorDesplazamiento1.getX();
        double dy1 = vectorDesplazamiento1.getY();
        double ox1 = bolaActiva.getPosicion().getX();
        double oy1 = bolaActiva.getPosicion().getY();

        Vector2D vectorDesplazamiento2 = otraBola.getVectorDesplazamiento();
        double dx2 = 0;//vectorDesplazamiento2.getX();
        double dy2 = 0;//vectorDesplazamiento2.getY();
        double ox2 = otraBola.getPosicion().getX();
        double oy2 = otraBola.getPosicion().getY();

        double rt = bolaActiva.getRadio() + otraBola.getRadio();

        double dx = dx1 - dx2;
        double dy = dy1 - dy2;
        double ox = ox1 - ox2;
        double oy = oy1 - oy2;

        double a = dx * dx + dy * dy;
        double b = 2 * (dx * ox + dy * oy);
        double c = ox * ox + oy * oy - rt * rt;

        double lambda;

        if (a == 0) {
            if (b == 0) {
                return null;
            }
            lambda = -c / b;
        } else {
            double b4ac = b * b - 4 * a * c;
            if (b4ac < 0) {
                return null;
            }
            double raiz = Math.sqrt(b4ac);
            double lambda1 = (-b + raiz) / 2 / a;
            double lambda2 = (-b - raiz) / 2 / a;
            if (lambda1 < 0) {
                lambda = lambda2;
            } else if (lambda2 < 0) {
                lambda = lambda1;
            } else {
                lambda = Math.min(lambda1, lambda2);
            }
        }

        return lambda;
    }

    static public Colision calcularColisionBolas(Bola bolaActiva, Bola otraBola) {

        if (Geometria.iguales(bolaActiva.getVelocidad(), otraBola.getVelocidad())) {
            return null;
        }
        Double lambda = calcularLambdaColisionBolas(bolaActiva, otraBola);
        if (lambda == null || Geometria.menor(lambda, 0) || Geometria.mayor(lambda, 1)) {
            return null;
        }

        Vector2D vectorDesplazamiento1 = bolaActiva.getVectorDesplazamiento();

        double dx1 = vectorDesplazamiento1.getX();
        double dy1 = vectorDesplazamiento1.getY();
        double ox1 = bolaActiva.getPosicion().getX();
        double oy1 = bolaActiva.getPosicion().getY();

        Vector2D vectorDesplazamiento2 = otraBola.getVectorDesplazamiento();
        double dx2 = 0;// vectorDesplazamiento2.getX();
        double dy2 = 0;//vectorDesplazamiento2.getY();
        double ox2 = otraBola.getPosicion().getX();
        double oy2 = otraBola.getPosicion().getY();

        double x1 = lambda * dx1 + ox1;
        double y1 = lambda * dy1 + oy1;

        double x2 = lambda * dx2 + ox2;
        double y2 = lambda * dy2 + oy2;

        // Las posiciones de colision son las que ocupan los entes en el instante
        // en que se produce la colision
        Point2D posicionColision1 = new Point2D.Double(x1, y1);
        Point2D posicionColision2 = new Point2D.Double(x2, y2);

        double distanciaColision = Geometria.distancia(bolaActiva.getPosicion(), posicionColision1);

        Recta rectaUnion = new Recta(posicionColision1, posicionColision2);
        Point2D puntoColision = rectaUnion.calcularPuntoDistancia(bolaActiva.getRadio());

        Colision colision = new Colision();
        colision.setPuntoColision(puntoColision);
        colision.setEnteActivo(bolaActiva);
        //colision.setDistancia(distanciaColision);

        double distanciaPrevista = vectorDesplazamiento1.getModulo();
        colision.setProporcionDesplazamientoRealizado(distanciaPrevista == 0 ? 1 : distanciaColision / distanciaPrevista);

        colision.setPosicionColisionEnteActivo(posicionColision1);

        Vector2D direccionChoque = new VectorPolar2D(bolaActiva.getPosicion(), otraBola.getPosicion());

        Semirrecta semirectaFuerzaEntePasivo = new Semirrecta(otraBola.getPosicion(), direccionChoque);

        Vector2D fuerzaAplicadaEntePasivo = semirectaFuerzaEntePasivo.proyectar(bolaActiva.getFuerzaCinetica());
        if (fuerzaAplicadaEntePasivo == null) {
            return null;
        }
        fuerzaAplicadaEntePasivo.multiplicar(bolaActiva.getMaterial().getElasticidad() * otraBola.getMaterial().getElasticidad());

        colision.setEntePasivo(otraBola);
        colision.setAceleracionEntePasivo(fuerzaAplicadaEntePasivo.multiplicar(1 / otraBola.getMasa()));
        //colision.setPosicionColisionEntePasivo(posicionColision2);

        //if (distanciaColision > 0) {
        Vector2D fuerzaAplicadaEnteActivo = fuerzaAplicadaEntePasivo.getVectorInvertido().sumar(bolaActiva.getFuerzaCinetica());
        fuerzaAplicadaEnteActivo.multiplicar(bolaActiva.getMaterial().getElasticidad() * otraBola.getMaterial().getElasticidad());
        colision.setAceleracionEnteActivo(fuerzaAplicadaEnteActivo.multiplicar(1 / bolaActiva.getMasa()));
        //}

        return colision;
    }

    static public Colision calcularColisionBolaArista(Bola bola, Arista arista) {

        Vector2D vectorDesplazamiento = bola.getVectorDesplazamiento();

        Recta lineaDireccion = new Recta(bola.getPosicion(), vectorDesplazamiento);
        Recta lineaSegmento = new Recta(arista.getSegmento().getP1(), arista.getSegmento().getP2());
        Point2D puntoCorte = lineaSegmento.interseccion(lineaDireccion);
        if (puntoCorte == null) {
            return null;
        }

        Angulo anguloIncidencia = lineaSegmento.calcularMenorAngulo(lineaDireccion);

        double hipotenusa = bola.getRadio() / Math.sin(anguloIncidencia.getRadianes());

        Vector2D vectorHipotenusa = vectorDesplazamiento.getVectorInvertido();
        vectorHipotenusa.setModulo(hipotenusa);
        Point2D posicionColision = vectorHipotenusa.trasladarPunto(puntoCorte);

        Vector2D vectorDireccionColision = new VectorPolar2D(bola.getPosicion(), posicionColision);
        double distanciaColision = vectorDireccionColision.getModulo();

        if (!vectorDireccionColision.esNulo()
                && !Geometria.iguales(vectorDireccionColision.getAngulo().getGrados(), vectorDesplazamiento.getAngulo().getGrados())) {
            return null;
        }

        double distanciaPrevista = vectorDesplazamiento.getModulo();
        double proporcionMovimientoRealizado = distanciaColision / distanciaPrevista;

        if (Geometria.mayor(proporcionMovimientoRealizado, 1)) {
            return null;
        }

        // Esto evita que las bolas contiguas generen una colision
//        if (Geometria.iguales(distanciaColision, 0)) {
//            return null;
//        }

        Recta perpendicular = lineaSegmento.perpendicular(posicionColision);
        Point2D puntoColision = lineaSegmento.interseccion(perpendicular);
        if (puntoColision == null || !arista.getSegmento().contiene(puntoColision)) {
            return null;
        }

        Colision colision = new Colision();
        colision.setPuntoColision(puntoColision);
        colision.setProporcionDesplazamientoRealizado(proporcionMovimientoRealizado);

        colision.setEnteActivo(bola);
        colision.setPosicionColisionEnteActivo(posicionColision);
        //colision.setDistancia(distanciaColision);

        Angulo anguloSegmento = lineaSegmento.getAngulo();
        Angulo anguloEntrada = vectorDesplazamiento.getAngulo().restar(anguloSegmento);
        Angulo anguloSalida = new Angulo(-anguloEntrada.getGrados());
        anguloSalida = anguloSalida.sumar(anguloSegmento);
        Vector2D aceleracionResultante = new VectorPolar2D(anguloSalida,
                vectorDesplazamiento.getModulo());
        aceleracionResultante = aceleracionResultante.multiplicar(bola.getMaterial().getElasticidad() * arista.getMaterial().getElasticidad());
//        if (aceleracionResultante.getModulo() < 1)
//            return null;

        colision.setAceleracionEnteActivo(aceleracionResultante);

        return colision;
    }

    static private Colision calcularColisionBolaVertice(Bola bola, Point2D puntoVertice) {

        double elasticidadVertice = 1; // TODO: pasa como parametro la elasticidad del vertice
        Vector2D vectorDesplazamiento = bola.getVectorDesplazamiento();

        if (Geometria.iguales(vectorDesplazamiento.getModulo(), 0)) {
            return null;
        }
        Recta lineaDireccion = new Recta(bola.getPosicion(), vectorDesplazamiento);
        Recta lineaPerpendicular = lineaDireccion.perpendicular(puntoVertice);

        Point2D interseccion = lineaDireccion.interseccion(lineaPerpendicular);
        if (interseccion == null) {
            System.out.println("error: dos rectas perpendiculares deben tener interseccion");
            return null;
        }

        VectorPolar2D direccionColision = new VectorPolar2D(bola.getPosicion(), interseccion);
        if (!Geometria.iguales(direccionColision.getAngulo().getGrados(), vectorDesplazamiento.getAngulo().getGrados())) {
            return null;
        }

        double separacionLateral = Geometria.distancia(puntoVertice, interseccion);

        if (separacionLateral > bola.getRadio()) {
            return null;
        }

        double separacionFrontal = Math.sqrt(bola.getRadio() * bola.getRadio() - separacionLateral * separacionLateral);

        double distanciaColision = Geometria.distancia(bola.getPosicion(), interseccion) - separacionFrontal;

        if (distanciaColision > vectorDesplazamiento.getModulo()/* || distanciaColision <= 0*/) {
            return null;
        }

        Vector2D vectorDesplazamientoColision =
                vectorDesplazamiento.multiplicar(bola.getMaterial().getElasticidad() * elasticidadVertice);
        vectorDesplazamientoColision.setModulo(distanciaColision);
        Point2D posicionColision = vectorDesplazamientoColision.trasladarPunto(bola.getPosicion());

        double distanciaPrevista = vectorDesplazamiento.getModulo();
        double proporcionMovimientoRealizado = distanciaColision / distanciaPrevista;

        if (Geometria.mayor(proporcionMovimientoRealizado, 1)) {
            return null;
        }

        Colision colision = new Colision();
        colision.setPuntoColision(posicionColision);
        colision.setProporcionDesplazamientoRealizado(proporcionMovimientoRealizado);

        colision.setEnteActivo(bola);
        colision.setPosicionColisionEnteActivo(posicionColision);
        //colision.setDistancia(distanciaColision);
//        colision.setPuntoColision(puntoVertice);

        VectorPolar2D vectorRadio = new VectorPolar2D(posicionColision, puntoVertice);
        Angulo anguloRadio = vectorDesplazamiento.getAngulo().restar(vectorRadio.getAngulo());
        double radianes = vectorDesplazamiento.getAngulo().getRadianes();
        double radianesDesviacion = Math.PI - 2 * Math.asin(separacionLateral / bola.getRadio());
        if (anguloRadio.getGrados() >= 180) {
            radianes -= radianesDesviacion;
        } else {
            radianes += radianesDesviacion;
        }

        Angulo anguloRebote = new Angulo(radianes, Angulo.RADIANES);
        VectorPolar2D aceleracionResultante = new VectorPolar2D(anguloRebote, vectorDesplazamiento.getModulo());
        colision.setAceleracionEnteActivo(aceleracionResultante);

        return colision;
    }


}
