package Negocio.mecánicas;

import Negocio.escenario.PanelDeJuego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

/**
 * Clase que maneja los eventos del teclado para controlar el juego.
 * Implementa la interfaz KeyListener para detectar las pulsaciones de teclas.
 * Implementa Serializable para permitir la serialización de objetos.
 */
public class Control implements KeyListener, Serializable {
    private PanelDeJuego panelDeJuego;
    private boolean arribaPresionado;
    private boolean abajoPresionado;
    private boolean derechaPresionado;
    private boolean izquierdaPresionado;
    private boolean enterPresionado;


    /**
     * Constructor de la clase Control.
     *
     * @param panelDeJuego El panelDeJuego asociado al controlador.
     */
    public Control(PanelDeJuego panelDeJuego) {
        this.setTablero(panelDeJuego);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        // Manejo de teclas comunes
        switch (tecla) {
            case KeyEvent.VK_W:
                setArribaPresionado(true);
                break;
            case KeyEvent.VK_S:
                setAbajoPresionado(true);
                break;
            case KeyEvent.VK_A:
                setIzquierdaPresionado(true);
                break;
            case KeyEvent.VK_D:
                setDerechaPresionado(true);
                break;
            case KeyEvent.VK_ENTER:
                setEnterPresionado(true);
                break;
        }

        // Manejo de estados específicos
        switch (panelDeJuego.getEstadoActualDeJuego()) {
            case TÍTULO:
                estadoTítulo(tecla);
                break;
            case JUEGO:
                estadoJugador(tecla);
                break;
            case PAUSA:
                estadoPausa(tecla);
                break;
            case OPCIONES:
                estadoOpciones(tecla);
                break;
            case DERROTA:
                estadoDerrota(tecla);
                break;
            case VICTORIA:
                estadoVictoria(tecla);
                break;
        }
    }

    /**
     * Método que permite la entrada por teclado
     * Para modificar las distintas opciones
     * Dentro del estado de Victoria.
     */
    private void estadoVictoria(int tecla) {
        if (tecla == KeyEvent.VK_W) {
            panelDeJuego.getIu().comandoNum--;
            if (panelDeJuego.getIu().comandoNum < 0) {
                panelDeJuego.getIu().comandoNum = 1;
            }
        }
        if (tecla == KeyEvent.VK_S) {
            panelDeJuego.getIu().comandoNum++;
            if (panelDeJuego.getIu().comandoNum > 1) {
                panelDeJuego.getIu().comandoNum = 0;
            }
        }
        if (tecla == KeyEvent.VK_ENTER) {
            if (panelDeJuego.getIu().comandoNum == 0) {
                panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.JUEGO);
                panelDeJuego.reintentar();
            } else if (panelDeJuego.getIu().comandoNum == 1) {
                panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.TÍTULO);
                panelDeJuego.reestablecer();
            }
        }
    }


    private void estadoPausa(int tecla) {
        if (tecla == KeyEvent.VK_ESCAPE) {
            panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.JUEGO);
        }
    }


    /**
     * Método que permite la entrada por teclado
     * Para modificar las distintas opciones
     * Dentro del estado de Opciones
     * Como el submenú de Guardar y salir
     * Control del volumen
     * Y Guardado del progreso en un nivel.
     */
    private void estadoOpciones(int tecla) {
        if (tecla == KeyEvent.VK_ENTER) {
            setEnterPresionado(true);
        }
        if (tecla == KeyEvent.VK_ESCAPE) {
            panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.JUEGO);
        }
        int maxComandoNum = 0;
        switch (panelDeJuego.getIu().subEstado) {
            case 0:
                maxComandoNum = 4;
                break;
            case 2:
                maxComandoNum = 1;
                break;
        }
        if (tecla == KeyEvent.VK_W) {
            panelDeJuego.getIu().comandoNum--;
            if (panelDeJuego.getIu().comandoNum < 0) {
                panelDeJuego.getIu().comandoNum = maxComandoNum;
            }
        }
        if (tecla == KeyEvent.VK_S) {
            panelDeJuego.getIu().comandoNum++;
            //music
            if (panelDeJuego.getIu().comandoNum > maxComandoNum) {
                panelDeJuego.getIu().comandoNum = 0;
            }
        }
        if (tecla == KeyEvent.VK_A) {
            if (panelDeJuego.getIu().subEstado == 0) {
                if (panelDeJuego.getIu().comandoNum == 0 && panelDeJuego.getMúsica().getEscalaDeVolumen() > 0) {
                    panelDeJuego.getMúsica().disminuirVolumen();
                    panelDeJuego.getMúsica().verificarVolumen();
                    //tablero.reproducirSE();
                }
                if (panelDeJuego.getIu().comandoNum == 1 && panelDeJuego.getSe().getEscalaDeVolumen() > 0) {
                    panelDeJuego.getSe().disminuirVolumen();
                    //tablero.reproducirSE();
                }
            }
        }
        if (tecla == KeyEvent.VK_D) {
            if (panelDeJuego.getIu().subEstado == 0) {
                if (panelDeJuego.getIu().comandoNum == 0 && panelDeJuego.getMúsica().getEscalaDeVolumen() < 5) {
                    panelDeJuego.getMúsica().aumentarVolumen();
                    panelDeJuego.getMúsica().verificarVolumen();
                    //tablero.reproducirSE();
                }
            }
            if (panelDeJuego.getIu().comandoNum == 1 && panelDeJuego.getSe().getEscalaDeVolumen() < 5) {
                panelDeJuego.getSe().aumentarVolumen();
                //tablero.reproducirSE();
            }
        }
    }


    /**
     * Método que permite la entrada por teclado
     * Para modificar las distintas opciones
     * Dentro del estado de Juego.
     */
    private void estadoJugador(int tecla) {
        if (tecla == KeyEvent.VK_W) {
            setArribaPresionado(true);
        }
        if (tecla == KeyEvent.VK_S) {
            setAbajoPresionado(true);
        }
        if (tecla == KeyEvent.VK_A) {
            setIzquierdaPresionado(true);
        }
        if (tecla == KeyEvent.VK_D) {
            setDerechaPresionado(true);
        }
        if (tecla == KeyEvent.VK_SPACE) {
            panelDeJuego.getJugador().romperOCrearHielo();
        }
        if (tecla == KeyEvent.VK_F) {
            panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.OPCIONES);
        }
        if (tecla == KeyEvent.VK_P) {
            panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.PAUSA);
        }
    }

    /**
     * Método que permite la entrada por teclado
     * Para modificar las distintas opciones
     * Dentro del estado de Título.
     */
    public void estadoTítulo(int tecla) {
        if (tecla == KeyEvent.VK_W) {
            panelDeJuego.getIu().comandoNum--;
            if (panelDeJuego.getIu().comandoNum < 0) {
                panelDeJuego.getIu().comandoNum = 2;
            }
        }
        if (tecla == KeyEvent.VK_S) {
            panelDeJuego.getIu().comandoNum++;
            if (panelDeJuego.getIu().comandoNum > 2) {
                panelDeJuego.getIu().comandoNum = 0;
            }
        }

        if (tecla == KeyEvent.VK_ENTER) {
            if (panelDeJuego.getIu().comandoNum == 0) {
                panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.JUEGO);
                panelDeJuego.pararMúsica();
                panelDeJuego.reproducirMúsica(2);
            }
            if (panelDeJuego.getIu().comandoNum == 1) {
                panelDeJuego.getGuardarCargar().cargar(panelDeJuego.getNivel());
                panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.JUEGO);
                panelDeJuego.pararMúsica();
                panelDeJuego.reproducirMúsica(2);
            }
            if (panelDeJuego.getIu().comandoNum == 2) {
                System.exit(0);
            }
        }

    }


    /**
     * Método que permite la entrada por teclado
     * Para modificar las distintas opciones
     * Dentro del estado de Derrota.
     */
    private void estadoDerrota(int tecla) {
        if (tecla == KeyEvent.VK_W) {
            panelDeJuego.getIu().comandoNum--;
            if (panelDeJuego.getIu().comandoNum < 0) {
                panelDeJuego.getIu().comandoNum = 1;
            }
            //efecto presentación.sonido
        }
        if (tecla == KeyEvent.VK_S) {
            panelDeJuego.getIu().comandoNum++;
            if (panelDeJuego.getIu().comandoNum > 1) {
                panelDeJuego.getIu().comandoNum = 0;
            }
            //efecto presentación.sonido
        }
        if (tecla == KeyEvent.VK_ENTER) {
            if (panelDeJuego.getIu().comandoNum == 0) {
                panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.JUEGO);
                panelDeJuego.reintentar();
            } else if (panelDeJuego.getIu().comandoNum == 1) {
                panelDeJuego.setEstadoActualDeJuego(EstadoDeJuego.TÍTULO);
                panelDeJuego.reestablecer();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();

        // Actualización del estado de las teclas al soltarlas
        switch (tecla) {
            case KeyEvent.VK_W:
                setArribaPresionado(false);
                break;
            case KeyEvent.VK_S:
                setAbajoPresionado(false);
                break;
            case KeyEvent.VK_A:
                setIzquierdaPresionado(false);
                break;
            case KeyEvent.VK_D:
                setDerechaPresionado(false);
                break;
            case KeyEvent.VK_ENTER:
                setEnterPresionado(false);
                break;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public PanelDeJuego panelDeJuego() {
        return panelDeJuego;
    }

    public void setTablero(PanelDeJuego panelDeJuego) {
        this.panelDeJuego = panelDeJuego;
    }

    public boolean isArribaPresionado() {
        return arribaPresionado;
    }

    public void setArribaPresionado(boolean arribaPresionado) {
        this.arribaPresionado = arribaPresionado;
    }

    public boolean isAbajoPresionado() {
        return abajoPresionado;
    }

    public void setAbajoPresionado(boolean abajoPresionado) {
        this.abajoPresionado = abajoPresionado;
    }

    public boolean isDerechaPresionado() {
        return derechaPresionado;
    }

    public void setDerechaPresionado(boolean derechaPresionado) {
        this.derechaPresionado = derechaPresionado;
    }

    public boolean isIzquierdaPresionado() {
        return izquierdaPresionado;
    }

    public void setIzquierdaPresionado(boolean izquierdaPresionado) {
        this.izquierdaPresionado = izquierdaPresionado;
    }

    public boolean isEnterPresionado() {
        return enterPresionado;
    }

    public void setEnterPresionado(boolean enterPresionado) {
        this.enterPresionado = enterPresionado;
    }
}
