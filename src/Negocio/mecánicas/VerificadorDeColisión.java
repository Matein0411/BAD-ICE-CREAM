package Negocio.mecánicas;

import Negocio.entidades.Entidad;
import Negocio.escenario.bloques.BloqueEstático;
import Negocio.escenario.PanelDeJuego;

import java.io.Serializable;

public class VerificadorDeColisión implements Serializable {
    private PanelDeJuego panelDeJuego;

    /**
     * La clase VerificadorDeColisión se encarga de verificar colisiones entre entidades y bloques en un juego.
     */
    public VerificadorDeColisión(PanelDeJuego panelDeJuego) {
        this.setTablero(panelDeJuego);
    }



    /**
     * Verifica la colisión entre una entidad y los bloques en el tablero.
     *
     * @param entidad La entidad cuya colisión se va a verificar.
     */
    public void verificarBloque(Entidad entidad) {
        int entidadDimIzquierdaX = entidad.getMundoX() + entidad.getÁreaSólida().x;
        int entidadDimDerechaX = entidad.getMundoX() + entidad.getÁreaSólida().x + entidad.getÁreaSólida().width;
        int entidadDimSuperiorY = entidad.getMundoY() + entidad.getÁreaSólida().y;
        int entidadDimInferiorY = entidad.getMundoY() + entidad.getÁreaSólida().y + entidad.getÁreaSólida().height;

        int columnaIzquierdaDeEntidad = entidadDimIzquierdaX / getTablero().getTAMAÑO_DE_BLOQUE();
        int columnaDerechaDeEntidad = entidadDimDerechaX / getTablero().getTAMAÑO_DE_BLOQUE();
        int filaSuperiorDeEntidad = entidadDimSuperiorY / getTablero().getTAMAÑO_DE_BLOQUE();
        int filaInferiorDeEntidad = entidadDimInferiorY / getTablero().getTAMAÑO_DE_BLOQUE();

        int numBloque1, numBloque2;

        switch (entidad.getDirección()) {
            case ARRIBA:
                filaSuperiorDeEntidad = (entidadDimSuperiorY - entidad.getVelocidad()) / getTablero().getTAMAÑO_DE_BLOQUE();
                numBloque1 = getTablero().getAdminBlock().getMapa()[columnaIzquierdaDeEntidad][filaSuperiorDeEntidad];
                numBloque2 = getTablero().getAdminBlock().getMapa()[columnaDerechaDeEntidad][filaSuperiorDeEntidad];
                if (getTablero().getAdminBlock().getBloques()[numBloque1] instanceof BloqueEstático || getTablero().getAdminBlock().getBloques()[numBloque2] instanceof BloqueEstático) {
                    entidad.setColisiónActiva(true);
                }
                break;
            case ABAJO:
                filaInferiorDeEntidad = (entidadDimInferiorY + entidad.getVelocidad()) / getTablero().getTAMAÑO_DE_BLOQUE();
                numBloque1 = getTablero().getAdminBlock().getMapa()[columnaIzquierdaDeEntidad][filaInferiorDeEntidad];
                numBloque2 = getTablero().getAdminBlock().getMapa()[columnaDerechaDeEntidad][filaInferiorDeEntidad];
                if (getTablero().getAdminBlock().getBloques()[numBloque1] instanceof BloqueEstático || getTablero().getAdminBlock().getBloques()[numBloque2] instanceof BloqueEstático) {
                    entidad.setColisiónActiva(true);
                }
                break;
            case IZQUIERDA:
                columnaIzquierdaDeEntidad = (entidadDimIzquierdaX - entidad.getVelocidad()) / getTablero().getTAMAÑO_DE_BLOQUE();
                numBloque1 = getTablero().getAdminBlock().getMapa()[columnaIzquierdaDeEntidad][filaSuperiorDeEntidad];
                numBloque2 = getTablero().getAdminBlock().getMapa()[columnaIzquierdaDeEntidad][filaInferiorDeEntidad];
                if (getTablero().getAdminBlock().getBloques()[numBloque1] instanceof BloqueEstático || getTablero().getAdminBlock().getBloques()[numBloque2] instanceof BloqueEstático) {
                    entidad.setColisiónActiva(true);
                }
                break;
            case DERECHA:
                columnaDerechaDeEntidad = (entidadDimDerechaX + entidad.getVelocidad()) / getTablero().getTAMAÑO_DE_BLOQUE();
                numBloque1 = getTablero().getAdminBlock().getMapa()[columnaDerechaDeEntidad][filaSuperiorDeEntidad];
                numBloque2 = getTablero().getAdminBlock().getMapa()[columnaDerechaDeEntidad][filaInferiorDeEntidad];
                if (getTablero().getAdminBlock().getBloques()[numBloque1] instanceof BloqueEstático || getTablero().getAdminBlock().getBloques()[numBloque2] instanceof BloqueEstático) {
                    entidad.setColisiónActiva(true);
                }
                break;
        }
    }

    /**
     * Verifica la colisión entre una entidad y las frutas en el tablero.
     *
     * @param entidad   La entidad cuya colisión se va a verificar.
     * @param esJugador Indica si la entidad es el jugador principal.
     * @return El índice de la fruta con la que la entidad colisionó.
     */
    public int verificarObjeto(Entidad entidad, boolean esJugador) {
        int index = 999;
        for (int i = 0; i < getTablero().getFrutas().length; i++) {
            if (getTablero().getFrutas()[i] != null) {
                entidad.getÁreaSólida().x = entidad.getMundoX() + entidad.getÁreaSólida().x;
                entidad.getÁreaSólida().y = entidad.getMundoY() + entidad.getÁreaSólida().y;
                getTablero().getFrutas()[i].getÁreaSólida().x = getTablero().getFrutas()[i].getMundoX() + getTablero().getFrutas()[i].getÁreaSólida().x;
                getTablero().getFrutas()[i].getÁreaSólida().y = getTablero().getFrutas()[i].getMundoY() + getTablero().getFrutas()[i].getÁreaSólida().y;
                moverEntidad(entidad);
                if (entidad.getÁreaSólida().intersects(getTablero().getFrutas()[i].getÁreaSólida())) {
                    if (getTablero().getFrutas()[i].isColisión()) {
                        entidad.setColisiónActiva(true);
                    }
                    if (esJugador) {
                        index = i;
                    }
                }
                entidad.getÁreaSólida().x = entidad.getÁreaSólidaPorDefectoX();
                entidad.getÁreaSólida().y = entidad.getÁreaSólidaPorDefectoY();
                getTablero().getFrutas()[i].getÁreaSólida().x = getTablero().getFrutas()[i].getÁreaSólidaPorDefectoX();
                getTablero().getFrutas()[i].getÁreaSólida().y = getTablero().getFrutas()[i].getÁreaSólidaPorDefectoY();
            }

        }
        return index;
    }

    /**
     * Verifica la colisión entre una entidad y otras entidades en el tablero.
     *
     * @param entidad   La entidad cuya colisión se va a verificar.
     * @param objetivo  El array de entidades con las que se verificará la colisión.
     * @return El índice de la entidad con la que la entidad colisionó.
     */
    public int verificarEntidad(Entidad entidad, Entidad[] objetivo) {
        int index = 999;

        for (int i = 0; i < objetivo.length; i++) {
            if (objetivo[i] != null) {
                entidad.getÁreaSólida().x = entidad.getMundoX() + entidad.getÁreaSólida().x;
                entidad.getÁreaSólida().y = entidad.getMundoY() + entidad.getÁreaSólida().y;
                objetivo[i].getÁreaSólida().x = objetivo[i].getMundoX() + objetivo[i].getÁreaSólida().x;
                objetivo[i].getÁreaSólida().y = objetivo[i].getMundoY() + objetivo[i].getÁreaSólida().y;

                moverEntidad(entidad);

                if (entidad.getÁreaSólida().intersects(objetivo[i].getÁreaSólida())) {
                    if (objetivo[i] != entidad) {
                        entidad.setColisiónActiva(true);
                        index = i;
                    }
                }

                entidad.getÁreaSólida().x = entidad.getÁreaSólidaPorDefectoX();
                entidad.getÁreaSólida().y = entidad.getÁreaSólidaPorDefectoY();
                objetivo[i].getÁreaSólida().x = objetivo[i].getÁreaSólidaPorDefectoX();
                objetivo[i].getÁreaSólida().y = objetivo[i].getÁreaSólidaPorDefectoY();
            }
        }

        return index;
    }

    /**
     * Mueve la posición de una entidad según su dirección y velocidad.
     *
     * @param entidad La entidad que se va a mover.
     */
    private void moverEntidad(Entidad entidad) {
        switch (entidad.getDirección()) {
            case ARRIBA:
                entidad.getÁreaSólida().y -= entidad.getVelocidad();
                break;
            case ABAJO:
                entidad.getÁreaSólida().y += entidad.getVelocidad();
                break;
            case IZQUIERDA:
                entidad.getÁreaSólida().x -= entidad.getVelocidad();
                break;
            case DERECHA:
                entidad.getÁreaSólida().x += entidad.getVelocidad();
                break;
        }
    }

    /**
     * Verifica la colisión entre una entidad y el jugador principal en el tablero.
     *
     * @param entidad La entidad cuya colisión con el jugador se va a verificar.
     * @return true si hay contacto con el jugador, false en caso contrario.
     */
    public boolean verificarJugador(Entidad entidad) {
        boolean contactoConJugador = false;

        entidad.getÁreaSólida().x = entidad.getMundoX() + entidad.getÁreaSólida().x;
        entidad.getÁreaSólida().y = entidad.getMundoY() + entidad.getÁreaSólida().y;

        getTablero().getJugador().getÁreaSólida().x = getTablero().getJugador().getMundoX() + getTablero().getJugador().getÁreaSólida().x;
        getTablero().getJugador().getÁreaSólida().y = getTablero().getJugador().getMundoY() + getTablero().getJugador().getÁreaSólida().y;

        moverEntidad(entidad);

        if (entidad.getÁreaSólida().intersects(getTablero().getJugador().getÁreaSólida())) {
            entidad.setColisiónActiva(true);
            contactoConJugador = true;
        }

        entidad.getÁreaSólida().x = entidad.getÁreaSólidaPorDefectoX();
        entidad.getÁreaSólida().y = entidad.getÁreaSólidaPorDefectoY();
        getTablero().getJugador().getÁreaSólida().x = getTablero().getJugador().getÁreaSólidaPorDefectoX();
        getTablero().getJugador().getÁreaSólida().y = getTablero().getJugador().getÁreaSólidaPorDefectoY();

        return contactoConJugador;
    }

    public PanelDeJuego getTablero() {
        return panelDeJuego;
    }

    public void setTablero(PanelDeJuego panelDeJuego) {
        this.panelDeJuego = panelDeJuego;
    }
}
