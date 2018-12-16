package bola8.geometria;

/**
 * Representa un angulo. Almacena su valor tanto en grados como en radianes
 * permitiendo su conversion automatica.
 * Es un objeto inmutable.
 * @author Jorge Berjano
 */
public class Angulo implements Cloneable {

    private double grados;
    private double radianes;
    private static final double dosPI = 2 * Math.PI;
    public static final int RADIANES = 0;
    public static final int GRADOS = 1;

    /** Creates a new instance of Angulo */
    public Angulo(double grados) {
        this(grados, GRADOS);
    }

    public Angulo(double valor, int tipo) {
        switch (tipo) {
            case GRADOS:
                setGrados(valor);
                break;
            case RADIANES:
                setRadianes(valor);
                break;
        }
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        String str = grados + "º";
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Angulo)) {
            return false;
        }

        Angulo otroAngulo = (Angulo) obj;
        if (Geometria.iguales(this.getGrados(), otroAngulo.getGrados())) {
            return true;
        }
        if (Geometria.iguales(this.getGrados() + 360, otroAngulo.getGrados())) {
            return true;
        }
        if (Geometria.iguales(this.getGrados(), otroAngulo.getGrados() + 360)) {
            return true;
        }
        return false;
    }

    private void setRadianes(double radianes) {
        while (radianes < 0) {
            radianes = radianes + dosPI;
        }
        while (radianes >= dosPI) {
            radianes = radianes - dosPI;
        }

        this.radianes = radianes;
        this.grados = radianes * 360.0 / dosPI;
    }

    public double getRadianes() {
        return radianes;
    }

    private void setGrados(double grados) {
        grados = grados % 360;
        if (grados < 0) {
            grados = grados + 360.0;
        }
        if (grados == 360) {
            grados = 0.0;
        }
        this.radianes = grados * dosPI / 360.0;
        this.grados = grados;
    }

    public double getGrados() {
        return grados;
    }

    public double getGradosSigno() {
        if (grados < 180) {
            return grados;
        } else {
            return grados - 360;
        }
    }

    public Angulo sumarGrados(double grados) {
        return new Angulo(this.grados + grados);
    }

    public Angulo sumar(Angulo angulo) {
        return new Angulo(grados + angulo.getGrados());
    }

    public Angulo restar(Angulo angulo) {
        return new Angulo(grados - angulo.getGrados());
    }

    public boolean estaEntre(Angulo angulo1, Angulo angulo2) {
        boolean esCierto;

        if (angulo1.getGrados() < angulo2.getGrados()) {
            esCierto = angulo1.getGrados() < this.getGrados()
                    && this.getGrados() < angulo2.getGrados();
        } else {
            esCierto = (this.getGrados() < angulo2.getGrados())
                    || (angulo1.getGrados() < this.getGrados());
        }

        return esCierto;
    }

    public static Angulo anguloMedio(Angulo angulo1, Angulo angulo2) {
        double grados = (angulo2.getGrados() + angulo1.getGrados()) / 2;
        if (angulo1.getGrados() > angulo2.getGrados()) {
            grados = grados + 180.0;
        }
        return new Angulo(grados);
    }
}
