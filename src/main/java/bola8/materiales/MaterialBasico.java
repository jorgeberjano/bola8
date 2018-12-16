package bola8.materiales;

/**
 *
 * @author Jorge Berjano
 */
public enum MaterialBasico implements Material {
    VACIO(0, 0, 1),
    GOMA(10, 0.5, 0.5),
    HELIO(-10, 1, 0.99),
    MONEDA(10, .5, .5),
    PLOMO(10, 1, 0.0001),
    BOLA_BILLAR(10, 0, 1);

    double densidad;
    double rozamiento;
    double elasticidad;

    private MaterialBasico(double densidad, double rozamiento, double elasticidad) {
        this.densidad = densidad;
        this.rozamiento = rozamiento;
        this.elasticidad = elasticidad;
    }

    public double getDensidad() {
        return densidad;
    }

    public double getElasticidad() {
        return elasticidad;
    }

    public double getRozamiento() {
        return rozamiento;
    }
}
