package Negocio.entidades;

import Negocio.escenario.PanelDeJuego;
import Negocio.mecánicas.Dirección;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Clase que representa una entidad en el juego.
 * Las entidades pueden ser objetos, jugadores o enemigos.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class Entidad implements Serializable {

    private PanelDeJuego panelDeJuego;
    //Coordenadas de la entidad
    private int mundoX;
    private int mundoY;

    //Área sólida y colisión
    private Rectangle áreaSólida = new Rectangle(0, 0, 42, 42);
    private int áreaSólidaPorDefectoX;
    private int áreaSólidaPorDefectoY;
    private boolean colisiónActiva = false;
    private boolean colisión = false;

    //Velocidad del jugador
    private int velocidad;

    //Nombre de la Entidad
    private String nombre;

    //Dirección por defecto
    private Dirección dirección = Dirección.ABAJO;

    //Imágenes que usarán las entidades
    public transient BufferedImage imagen1, imagen2, imagen3, imagen4, imagen5;
    public transient BufferedImage arriba1, arriba2, arriba3, arriba4, abajo1, abajo2, abajo3, abajo4, izquierda1, izquierda2, izquierda3, izquierda4, derecha1, derecha2, derecha3, derecha4;


    private int contadorBloqueoDeAcción = 0;
    public int contadorMovimiento = 0;
    public int numeroDeMovimiento = 1;

    /**
     * Constructor de la clase Entidad.
     */
    public Entidad(PanelDeJuego panelDeJuego) {
        this.setTablero(panelDeJuego);
    }

    // Métodos de actualización y configuración de la entidad
    // ...
    public void establecerAcción() {}

    public void actualizar() {

        establecerAcción();
        setColisiónActiva(false);
        getTablero().getCheckColisión().verificarBloque(this);
        getTablero().getCheckColisión().verificarObjeto(this, false);
        getTablero().getCheckColisión().verificarEntidad(this, getTablero().getEnemigos());
        boolean contactoConJugador = getTablero().getCheckColisión().verificarJugador(this);

        if (contactoConJugador) {
            if (!getTablero().getJugador().isInvencible()) {
//                tablero.jugador.vida -= 1;
                getTablero().getJugador().setInvencible(true);
            }
        }

        //entidad puede moverse
        if (!isColisiónActiva()) {
            switch (getDirección()) {
                case ARRIBA:
                    setMundoY(getMundoY() - getVelocidad());
                    break;
                case ABAJO:
                    setMundoY(getMundoY() + getVelocidad());
                    break;
                case IZQUIERDA:
                    setMundoX(getMundoX() - getVelocidad());
                    break;
                case DERECHA:
                    setMundoX(getMundoX() + getVelocidad());
                    break;
            }
        }

        contadorMovimiento++;
        if (contadorMovimiento > 10) {
            if (numeroDeMovimiento == 1) {
                numeroDeMovimiento = 2;
            } else if (numeroDeMovimiento == 2) {
                numeroDeMovimiento = 1;
            }
            contadorMovimiento = 0;
        }
    }

    /**
     * Método para dibujar la entidad en el tablero.
     *
     * @param graphics2D El contexto gráfico en el que se dibuja la entidad.
     */
    public void dibujar(Graphics2D graphics2D) {
        BufferedImage imagen = null;

        int ventanaX = getMundoX() - getTablero().getJugador().getMundoX() + getTablero().getJugador().getVentanaX();
        int ventanaY = getMundoY() - getTablero().getJugador().getMundoY() + getTablero().getJugador().getVentanaY();

        if (getMundoX() + getTablero().getTAMAÑO_DE_BLOQUE() * 11 > getTablero().getJugador().getMundoX() - getTablero().getJugador().getVentanaX() &&
                getMundoX() - getTablero().getTAMAÑO_DE_BLOQUE() * 12 < getTablero().getJugador().getMundoX() + getTablero().getJugador().getVentanaX() &&
                getMundoY() + (getTablero().getTAMAÑO_DE_BLOQUE() * 2) > getTablero().getJugador().getMundoY() - getTablero().getJugador().getVentanaY() &&
                getMundoY() - (getTablero().getTAMAÑO_DE_BLOQUE() * 2) < getTablero().getJugador().getMundoY() + getTablero().getJugador().getVentanaY()) {
            switch (getDirección()) {
                case ARRIBA:
                    if (numeroDeMovimiento == 1) {
                        imagen = arriba1;
                    }
                    if (numeroDeMovimiento == 2) {
                        imagen = arriba2;
                    }
                    if (numeroDeMovimiento == 3) {
                        imagen = arriba3;
                    }
                    if (numeroDeMovimiento == 4) {
                        imagen = arriba4;
                    }
                    break;
                case ABAJO:
                    if (numeroDeMovimiento == 1) {
                        imagen = abajo1;
                    }
                    if (numeroDeMovimiento == 2) {
                        imagen = abajo2;
                    }
                    if (numeroDeMovimiento == 3) {
                        imagen = abajo3;
                    }
                    if (numeroDeMovimiento == 4) {
                        imagen = abajo4;
                    }
                    break;
                case IZQUIERDA:
                    if (numeroDeMovimiento == 1) {
                        imagen = izquierda1;
                    }
                    if (numeroDeMovimiento == 2) {
                        imagen = izquierda2;
                    }
                    if (numeroDeMovimiento == 3) {
                        imagen = izquierda3;
                    }
                    if (numeroDeMovimiento == 4) {
                        imagen = izquierda4;
                    }
                    break;
                case DERECHA:
                    if (numeroDeMovimiento == 1) {
                        imagen = derecha1;
                    }
                    if (numeroDeMovimiento == 2) {
                        imagen = derecha2;
                    }
                    if (numeroDeMovimiento == 3) {
                        imagen = derecha3;
                    }
                    if (numeroDeMovimiento == 4) {
                        imagen = derecha4;
                    }
                    break;
            }
            graphics2D.drawImage(imagen, ventanaX, ventanaY, getTablero().getTAMAÑO_DE_BLOQUE(), getTablero().getTAMAÑO_DE_BLOQUE(), null);
        }
    }

    /**
     * Método para obtener el nombre de la entidad.
     *
     * @return El nombre de la entidad.
     */
    public String getNombre() {
        return nombre;
    }

    public PanelDeJuego getTablero() {
        return panelDeJuego;
    }

    public void setTablero(PanelDeJuego panelDeJuego) {
        this.panelDeJuego = panelDeJuego;
    }

    public int getMundoX() {
        return mundoX;
    }

    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    public int getMundoY() {
        return mundoY;
    }

    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Rectangle getÁreaSólida() {
        return áreaSólida;
    }

    public void setÁreaSólida(Rectangle áreaSólida) {
        this.áreaSólida = áreaSólida;
    }

    public int getÁreaSólidaPorDefectoX() {
        return áreaSólidaPorDefectoX;
    }

    public void setÁreaSólidaPorDefectoX(int áreaSólidaPorDefectoX) {
        this.áreaSólidaPorDefectoX = áreaSólidaPorDefectoX;
    }

    public int getÁreaSólidaPorDefectoY() {
        return áreaSólidaPorDefectoY;
    }

    public void setÁreaSólidaPorDefectoY(int áreaSólidaPorDefectoY) {
        this.áreaSólidaPorDefectoY = áreaSólidaPorDefectoY;
    }

    public boolean isColisiónActiva() {
        return colisiónActiva;
    }

    public void setColisiónActiva(boolean colisiónActiva) {
        this.colisiónActiva = colisiónActiva;
    }

    public boolean isColisión() {
        return colisión;
    }

    public int getContadorBloqueoDeAcción() {
        return contadorBloqueoDeAcción;
    }

    public void setContadorBloqueoDeAcción(int contadorBloqueoDeAcción) {
        this.contadorBloqueoDeAcción = contadorBloqueoDeAcción;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Dirección getDirección() {
        return dirección;
    }

    public void setDirección(Dirección dirección) {
        this.dirección = dirección;
    }
}
