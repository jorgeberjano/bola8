package bola8.gui;

import bola8.controles.ControlRaton;
import bola8.controles.ControlRatonNulo;
import bola8.entes.Mundo;
import bola8.geometria.Angulo;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/**
 *
 * @author Jorge Berjano
 */
public class Lienzo extends JComponent {

    private Mundo mundo;
    private ControlRaton control = new ControlRatonNulo();

    public Lienzo(Mundo mundo) {
        this.mundo = mundo;
        setMinimumSize(new Dimension((int) mundo.getAncho(), (int) mundo.getAlto()));
        control.inicializar(mundo);
        inicializar();
    }

    public Point2D getCentro() {
        return new Point2D.Double(mundo.getAncho() / 2, mundo.getAlto() / 2);
    }
    
    private Point2D corregirPosicion(Point2D punto) {
        return new Point2D.Double(punto.getX(), (int) (mundo.getAlto() - punto.getY()));
    }

    private void inicializar() {
        addMouseWheelListener(new MouseWheelListener() {

            public void mouseWheelMoved(MouseWheelEvent e) {
                control.giroRueda(e.getWheelRotation());
            }

        });
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                control.movido(corregirPosicion(e.getPoint()));
            }

            public void mouseDragged(MouseEvent e) {
                control.arrastrado(corregirPosicion(e.getPoint()));
            }
        });
        addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    control.dobleClick(corregirPosicion(e.getPoint()));
                } else {
                    control.click(corregirPosicion(e.getPoint()));
                }
            }

            public void mousePressed(MouseEvent e) {
                control.presionado(corregirPosicion(e.getPoint()));
            }

            public void mouseReleased(MouseEvent e) {
                control.liberado(corregirPosicion(e.getPoint()));
            }

            public void mouseEntered(MouseEvent e) {                
            }

            public void mouseExited(MouseEvent e) {                
            }
        });
    }

    public Mundo getMundo() {
        return mundo;
    }

    public void setMundo(Mundo mundo) {
        this.mundo = mundo;
    }

    public ControlRaton getControl() {
        return control;
    }

    public void setControl(ControlRaton control) {
        this.control = control;
        control.inicializar(mundo);
    }

    public void girar(double gradosGiro) {
        mundo.setAngulo(mundo.getAngulo().sumar(new Angulo(gradosGiro)));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //ConfiguracionGraphics config = new ConfiguracionGraphics(graphics);
        
        graphics.drawString("En movimiento: " + mundo.getNumeroEntesEnMovimiento(), 0, 20);

        AffineTransform tx = AffineTransform.getTranslateInstance(0, mundo.getAlto());
        tx.scale(1, -1);
        tx.translate(mundo.getAlto() / 2, mundo.getAncho() / 2);
        tx.rotate(mundo.getAngulo().getRadianes());
        tx.translate(-mundo.getAlto() / 2, -mundo.getAncho() / 2);
        graphics.setTransform(tx);

        mundo.pintar(graphics);
        control.pintar(graphics);
    }
}
