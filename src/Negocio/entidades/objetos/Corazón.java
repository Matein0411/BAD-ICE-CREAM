package Negocio.entidades.objetos;

import Negocio.entidades.Entidad;
import Negocio.escenario.PanelDeJuego;
import presentación.GestorImagen;

/**
 * Clase que representa un corazón en el juego.
 * Hereda de la clase Entidad.
 */
public class Corazón extends Entidad {

    /**
     * Constructor de la clase Corazón.
     */
    public Corazón(PanelDeJuego panelDeJuego) {
        super(panelDeJuego);
        this.setNombre("Corazón");
        // Obtiene la imagen del corazón mediante el gestor de imágenes de objetos.
        GestorImagen.obtenerImagenDeObjetos(this);
    }

}
