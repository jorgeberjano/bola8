package bola8.entes;

import bola8.geometria.Segmento;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import bola8.materiales.Material;
import bola8.materiales.MaterialBasico;

/**
 *
 * @author Jorge Berjano
 */
public class Arista implements Ente {

    private Segmento segmento;
    private String nombre = "pared";
    private Color color = Color.BLACK;
    private boolean permiteIntrusion;
    private boolean permiteTraspasar;
            private Material material = MaterialBasico.GOMA;

    public Arista(Point2D p1, Point2D p2) {
        segmento = new Segmento(p1, p2);
    }

    public Arista(int x1, int y1, int x2, int y2) {
        segmento = new Segmento(x1, y1, x2, y2);
    }

    public Arista(Segmento segmento) {
        this.segmento = segmento;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
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

    public void pintar(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.drawLine((int) segmento.getP1().getX(), (int) segmento.getP1().getY(), (int)segmento.getP2().getX(), (int) segmento.getP2().getY());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

        public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public boolean permiteIntrusion() {
        return permiteIntrusion;
    }

    public void setPermitirIntrusion(boolean permiteIntrusion) {
        this.permiteIntrusion = permiteIntrusion;
    }

    public boolean permiteTraspasar() {
        return true;
    }

    public void setPermitirTraspasar(boolean permiteTraspasar) {
        this.permiteIntrusion = permiteTraspasar;
    }
}
