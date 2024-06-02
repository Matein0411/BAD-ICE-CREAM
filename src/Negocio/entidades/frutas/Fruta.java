package Negocio.entidades.frutas;

import Negocio.entidades.Entidad;
import Negocio.escenario.PanelDeJuego;
import presentación.GestorImagen;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Clase que representa una fruta en el juego.
 */
public abstract class Fruta extends Entidad {
    //Puntaje que recibirá cada fruta
    private int puntaje;

    public int animaciónIndex = 0;
    public long últimoTiempoCambio = System.currentTimeMillis();
    public int velocidadAnimación = 100; // Cambia cada 100ms
    /**
     * Constructor de la clase Fruta.
     * @param puntaje El puntaje asociado a la fruta.
     * @param nombre El nombre de la fruta.
     */
    public Fruta(PanelDeJuego panelDeJuego, int puntaje, String nombre) {
        super(panelDeJuego);
        this.setPuntaje(puntaje);
        this.setNombre(nombre);
        // Obtiene la imagen de la fruta mediante el gestor de imágenes de frutas.
        GestorImagen.obtenerImagenDeFruta(this);
    }

    public void dibujar(Graphics2D g2) {
        GestorImagen.actualizarAnimaciónDeFruta(this);

        int ventanaX = getMundoX() - getTablero().getJugador().getMundoX() + getTablero().getJugador().getVentanaX();
        int ventanaY = getMundoY() - getTablero().getJugador().getMundoY() + getTablero().getJugador().getVentanaY();

        if (cámaraSigueAJugador()) {
            BufferedImage imagenActual = GestorImagen.obtenerImagenActualDeFruta(this);
            g2.drawImage(imagenActual, ventanaX, ventanaY, getTablero().getTAMAÑO_DE_BLOQUE(), getTablero().getTAMAÑO_DE_BLOQUE(), null);
        }
    }

    private boolean cámaraSigueAJugador() {
        return getMundoX() + getTablero().getTAMAÑO_DE_BLOQUE() * 11 > getTablero().getJugador().getMundoX() - getTablero().getJugador().getVentanaX() &&
                getMundoX() - getTablero().getTAMAÑO_DE_BLOQUE() * 12 < getTablero().getJugador().getMundoX() + getTablero().getJugador().getVentanaX() &&
                getMundoY() + (getTablero().getTAMAÑO_DE_BLOQUE() * 2) > getTablero().getJugador().getMundoY() - getTablero().getJugador().getVentanaY() &&
                getMundoY() - (getTablero().getTAMAÑO_DE_BLOQUE() * 2) < getTablero().getJugador().getMundoY() + getTablero().getJugador().getVentanaY();
    }

    /**
     * Método para obtener el puntaje asociado a la fruta.
     *
     * @return El puntaje asociado a la fruta.
     */
    public int getPuntaje() {
        // Devuelve el puntaje de la fruta.
        return this.puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
