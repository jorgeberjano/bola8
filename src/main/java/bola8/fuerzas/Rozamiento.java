/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bola8.fuerzas;

import bola8.entes.EnteMovil;
import bola8.geometria.Vector2D;

/**
 *
 * @author Administrador
 */
public class Rozamiento implements FuerzaExterna {

    double coeficienteRozamientoEstatico;
    double coeficienteRozamientoDinamico;

    public Rozamiento() {
        this.coeficienteRozamientoDinamico = 1;
        this.coeficienteRozamientoEstatico = 1;
    }
    
    public Rozamiento( double coeficienteRozamientoEstatico, double coeficienteRozamientoDinamico) {
        this.coeficienteRozamientoEstatico = coeficienteRozamientoEstatico;
        this.coeficienteRozamientoDinamico = coeficienteRozamientoDinamico;
    }
    public Vector2D getVectorFuerza(EnteMovil ente) {
        Vector2D fuerzaCinetica = ente.getFuerzaCinetica();
        if (fuerzaCinetica.esNulo()) {
            return null;
        }

        double factorRozamientoEnte =  ente.getMaterial().getRozamiento() * ente.getMasa();
        Vector2D fuerzaRozamiento;
        if (fuerzaCinetica.getModulo() < coeficienteRozamientoEstatico * factorRozamientoEnte) {
            fuerzaRozamiento = fuerzaCinetica.multiplicar(-1);
        } else {
            fuerzaRozamiento = (Vector2D) fuerzaCinetica.clone();
            fuerzaRozamiento.setModulo(- coeficienteRozamientoDinamico * factorRozamientoEnte);
        }
        return fuerzaRozamiento;
    }

    public boolean isAnularResto() {
        return false;
    }
    
}
