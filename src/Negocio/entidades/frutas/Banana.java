package Negocio.entidades.frutas;

import Negocio.escenario.PanelDeJuego;

/**
 * Clase que representa una fruta específica: Banana.
 * Hereda de la clase Fruta.
 */
public class Banana extends Fruta{
    // Puntaje asociado a la banana
    private final static int PUNTAJE = -3;
    // Nombre de la fruta
    private final static String nombre = "guineo";

    /**
     * Constructor de la clase Banana.
     */
    public Banana(PanelDeJuego panelDeJuego) {
        super(panelDeJuego, PUNTAJE, nombre);
    }


}
