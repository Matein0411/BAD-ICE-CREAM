package Negocio.entidades;

import Negocio.entidades.frutas.Fruta;
import Negocio.entidades.enemigos.Enemigo;
import Negocio.escenario.PanelDeJuego;
import presentación.GestorImagen;
import Negocio.mecánicas.EstadoDeJuego;
import Negocio.mecánicas.Control;
import Negocio.mecánicas.Dirección;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Clase que representa al jugador en el juego.
 * Hereda de la clase Entidad.
 */
public class Jugador extends Entidad {
    // Puntaje del jugador
    private int puntaje;
    // Control del jugador
    private Control control;

    // Posición de la ventana
    private final int ventanaX;
    private final int ventanaY;

    //Cantidad de frutas
    private int númeroDeFrutas;

    //Estadísticas por defecto
    private int máximoVidas;
    private int vida;
    private int tiempoDeInvencibilidad = 0;
    private boolean invencible = false;
    private final int NUM_MAX_FRUTAS = 11;

    /**
     * Constructor de la clase Jugador.
     */
    public Jugador(PanelDeJuego panelDeJuego, Control control) {
        super(panelDeJuego);
        this.setControl(control);
        setÁreaSólida(new Rectangle());
        getÁreaSólida().x = 7;
        getÁreaSólida().y = 14;
        setÁreaSólidaPorDefectoX(getÁreaSólida().x);
        setÁreaSólidaPorDefectoY(getÁreaSólida().y);
        getÁreaSólida().width = 28;
        getÁreaSólida().height = 28;
        ventanaX = panelDeJuego.getTAMAÑO_DE_BLOQUE() * 7; //
        ventanaY = panelDeJuego.getTAMAÑO_DE_BLOQUE() * 7; //
        setNúmeroDeFrutas(0);
        //Configuraciones iniciales del jugador
        establecerValoresPredeterminados();
        //Configurar imagen del jugador
        GestorImagen.obtenerImagenDeJugador(this);
    }

    public void establecerPosiciónPredeterminada() {
        setMundoX(9 * getTablero().getTAMAÑO_DE_BLOQUE());
        setMundoY(10 * getTablero().getTAMAÑO_DE_BLOQUE());
        setDirección(Dirección.ABAJO);
    }

    public void reestablecerVida() {
        setVida(getMáximoVidas());
        setInvencible(false);
    }

    public void reestablecerFrutas() {
        setNúmeroDeFrutas(0);
    }

    /**
     * Establece los valores predeterminados para el jugador.
     * Este método inicializa la velocidad y la dirección del jugador, así como las estadísticas de vida.
     */
    public void establecerValoresPredeterminados() {
        //Coordenadas iniciales
        setMundoX(9 * getTablero().getTAMAÑO_DE_BLOQUE());
        setMundoY(10 * getTablero().getTAMAÑO_DE_BLOQUE());
        // Establecer la velocidad predeterminada del jugador
        setVelocidad(6);
        // Establecer la dirección predeterminada del jugador como hacia abajo
        setDirección(Dirección.ABAJO);
        // Inicializar las estadísticas de vida
        setMáximoVidas(3);
        setVida(getMáximoVidas());

    }
    /**
     * Método para actualizar el estado del jugador en el juego.
     */
    public void actualizar() {
        if (getControl().isArribaPresionado() || getControl().isAbajoPresionado() || getControl().isIzquierdaPresionado() || getControl().isDerechaPresionado()) {
            if (getControl().isArribaPresionado()) {
                setDirección(Dirección.ARRIBA);
            } else if (getControl().isAbajoPresionado()) {
                setDirección(Dirección.ABAJO);
            } else if (getControl().isIzquierdaPresionado()) {
                setDirección(Dirección.IZQUIERDA);
            } else if (getControl().isDerechaPresionado()) {
                setDirección(Dirección.DERECHA);
            }

            //verifica Colisión de bloque
            setColisiónActiva(false);
            getTablero().getCheckColisión().verificarBloque(this);

            //verificar colisión de objetos
            int index = getTablero().getCheckColisión().verificarObjeto(this, true);
            int enemigoIndex = getTablero().getCheckColisión().verificarEntidad(this, getTablero().getEnemigos());
            contactoConEnemigo(enemigoIndex);
            recogerFrutas(index);

            //Si la conlisión es falsa, el jugador se mueve
            if (!isColisiónActiva()) {
                switch (getDirección()) {
                    case ARRIBA:    setMundoY(getMundoY() - getVelocidad()); break;
                    case ABAJO:     setMundoY(getMundoY() + getVelocidad()); break;
                    case IZQUIERDA: setMundoX(getMundoX() - getVelocidad()); break;
                    case DERECHA:   setMundoX(getMundoX() + getVelocidad()); break;
                }
            }

            contadorMovimiento++;
            if (contadorMovimiento > 10) {
                if (numeroDeMovimiento == 1) {
                    numeroDeMovimiento = 2;
                } else if (numeroDeMovimiento == 2) {
                    numeroDeMovimiento = 1;
                } else if (numeroDeMovimiento == 3) {
                    numeroDeMovimiento = 2;
                } else if (numeroDeMovimiento == 4) {
                    numeroDeMovimiento = 3;
                }
                contadorMovimiento = 0;
            }
        }
        if (isInvencible()) {
            setTiempoDeInvencibilidad(getTiempoDeInvencibilidad() + 1);
            if (getTiempoDeInvencibilidad() > 60) {
                setInvencible(false);
                setTiempoDeInvencibilidad(0);
            }
        }
        if (comprobarSiEstáMuerto()) {
            getTablero().setEstadoActualDeJuego(EstadoDeJuego.DERROTA);
            getTablero().reproducirSE(4);
        }
        if (comprobarVictoria()) {
            getTablero().setEstadoActualDeJuego(EstadoDeJuego.VICTORIA);
            getTablero().reproducirSE(6);
        }
    }
    /**
     * Método para comprobar si el jugador ha alcanzado la victoria al recolectar todas las frutas.
     *
     * @return true si el jugador ha recolectado todas las frutas, false en caso contrario.
     */
    private boolean comprobarVictoria() {
        return getNúmeroDeFrutas() == NUM_MAX_FRUTAS;
    }
    /**
     * Método para comprobar si el jugador está muerto.
     *
     * @return true si el jugador está muerto (vida igual a 0), false en caso contrario.
     */
    public boolean comprobarSiEstáMuerto() {
        return this.getVida() == 0;
    }

    /**
     * Método para manejar el contacto o la colisión con un enemigo.
     *
     * @param índice El índice del enemigo en el arreglo de enemigos del tablero.
     */
    private void contactoConEnemigo(int índice) {
        if (índice != 999) {
            if (!isInvencible()) {
                Enemigo enemigo = (Enemigo) getTablero().getEnemigos()[índice];
                this.setVida(this.getVida() - enemigo.getDaño());
                if(this.getVida() <= 0){
                    this.setVida(0);
                }
                setInvencible(true);
            }
        }
    }

    /**
     * Recoge una fruta en la posición dada en el tablero.
     *
     * @param index El índice de la fruta en el arreglo de frutas del tablero.
     */
    public void recogerFrutas(int index) {
        if (index != 999) {
            Fruta fruta = (Fruta) getTablero().getFrutas()[index];
            setPuntaje(getPuntaje() + fruta.getPuntaje());
            getTablero().getFrutas()[index] = null;
            setNúmeroDeFrutas(getNúmeroDeFrutas() + 1);
            getTablero().reproducirSE(1);
            //  tablero.
            System.out.println("Frutas recolectadas: " + getNúmeroDeFrutas());
        }
    }

    /**
     * Método para dibujar al jugador en la pantalla.
     *
     * @param g2 El contexto gráfico en el que se dibuja el jugador.
     */
    public void dibujar(Graphics2D g2) {
        BufferedImage imagen = null;
        imagen = GestorImagen.obtenerImagen( this);

        if (isInvencible()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(imagen, getVentanaX(), getVentanaY(), null);
        //reseteo
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.drawRect(getVentanaX() + getÁreaSólida().x, getVentanaY() + getÁreaSólida().y, getÁreaSólida().width, getÁreaSólida().height); //HITBOX Jugador
        //g2.drawRect(126, 84, tablero.TAMANIO_DE_BLOQUE, tablero.TAMANIO_DE_BLOQUE); //HITBOX Bloque
    }

    /**
     * Método para romper o crear un bloque de hielo.
     */
    public void romperOCrearHielo() {
        // Validar si el jugador está sobre un bloque de hielo
        if (isColisiónActiva()) {
            getTablero().romperBloqueHielo();
        } else {
            getTablero().crearBloqueHielo();
        }
    }

    /**
     * Método para obtener la dirección actual del jugador.
     *
     * @return La dirección actual del jugador.
     */
    public Dirección getDireccion() {
        return getDirección();
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public int getVentanaX() {
        return ventanaX;
    }

    public int getVentanaY() {
        return ventanaY;
    }

    public int getNúmeroDeFrutas() {
        return númeroDeFrutas;
    }

    public void setNúmeroDeFrutas(int númeroDeFrutas) {
        this.númeroDeFrutas = númeroDeFrutas;
    }

    public int getMáximoVidas() {
        return máximoVidas;
    }

    public void setMáximoVidas(int máximoVidas) {
        this.máximoVidas = máximoVidas;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getTiempoDeInvencibilidad() {
        return tiempoDeInvencibilidad;
    }

    public void setTiempoDeInvencibilidad(int tiempoDeInvencibilidad) {
        this.tiempoDeInvencibilidad = tiempoDeInvencibilidad;
    }

    public boolean isInvencible() {
        return invencible;
    }

    public void setInvencible(boolean invencible) {
        this.invencible = invencible;
    }

    public void reestablecerPuntuación() {
        puntaje = 0;
    }
}
