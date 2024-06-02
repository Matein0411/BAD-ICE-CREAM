package Negocio.mecánicas;


import Negocio.entidades.Entidad;
import Negocio.escenario.PanelDeJuego;

import java.io.IOException;
import java.io.Serializable;

import static Negocio.mecánicas.LectorEntidades.leerEnemigos;

/**
 * Clase que se encarga de colocar frutas y enemigos en el tablero.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class ColocadorDeObjetos implements Serializable {
    private PanelDeJuego panelDeJuego;

    public ColocadorDeObjetos(PanelDeJuego panelDeJuego) {
        this.setTablero(panelDeJuego);
    }
    /**
     * Método para colocar frutas en el tablero a partir de un archivo.
     *
     * @param rutaDeFrutas La ruta del archivo que contiene la información de las frutas.
     */
    public void colocarFrutas(String rutaDeFrutas) {
        try {
            // Lee las frutas desde el archivo
            Entidad[] frutas = LectorEntidades.leerFrutas(rutaDeFrutas, getTablero());
            // Coloca las frutas en el tablero
            for (int i = 0; i < frutas.length; i++) {
                getTablero().getFrutas()[i] = frutas[i];
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de frutas: " + e.getMessage());
        }
    }
    /**
     * Método para colocar enemigos en el tablero a partir de un archivo.
     *
     * @param rutaDeEnemigos La ruta del archivo que contiene la información de los enemigos.
     */
    public void colocarEnemigos(String rutaDeEnemigos) {
        try {
            // Lee los enemigos desde el archivo
            Entidad[] enemigos = leerEnemigos(rutaDeEnemigos, getTablero());
            // Coloca los enemigos en el tablero
            for (int i = 0; i < enemigos.length; i++) {
                getTablero().getEnemigos()[i] = enemigos[i];
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de enemigos: " + e.getMessage());
        }
    }

    public PanelDeJuego getTablero() {
        return panelDeJuego;
    }

    public void setTablero(PanelDeJuego panelDeJuego) {
        this.panelDeJuego = panelDeJuego;
    }
}
