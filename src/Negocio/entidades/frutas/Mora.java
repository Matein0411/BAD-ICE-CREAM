package Negocio.entidades.frutas;

import Negocio.escenario.PanelDeJuego;

/**
 * Clase que representa una fruta específica: Mora.
 * Hereda de la clase Fruta.
 */
public class Mora extends Fruta {
    // Puntaje asociado a la mora
    private final static int PUNTAJE = 2;
    // Nombre de la mora

    private final static String nombre = "mora";

    /**
     * Constructor de la clase Mora.
     */
    public Mora(PanelDeJuego panelDeJuego) {
        super(panelDeJuego, PUNTAJE, nombre);
    }

}
