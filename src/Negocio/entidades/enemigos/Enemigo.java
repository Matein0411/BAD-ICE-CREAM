package Negocio.entidades.enemigos;

import Negocio.entidades.Entidad;
import Negocio.escenario.PanelDeJuego;
import Negocio.mecánicas.Dirección;
import presentación.GestorImagen;

import java.util.Random;

/**
 * Clase abstracta que representa a un enemigo en el juego.
 * Hereda de la clase Entidad.
 */
public abstract class Enemigo extends Entidad {
    // Daño que el enemigo puede infligir.
    private int daño;

    /**
     * Constructor de la clase Enemigo.
     *
     * @param velocidad La velocidad de movimiento del enemigo.
     * @param nombre El nombre del enemigo.
     * @param daño El daño que el enemigo puede infligir.
     */
    public Enemigo(PanelDeJuego panelDeJuego, int velocidad, String nombre, int daño) {
        super(panelDeJuego);
        this.setNombre(nombre);
        this.daño = daño;
        this.setVelocidad(velocidad);
        getÁreaSólida().x = 3;
        getÁreaSólida().y = 10;
        getÁreaSólida().width = 42;
        getÁreaSólida().height = 30;
        setÁreaSólidaPorDefectoX(getÁreaSólida().x);
        setÁreaSólidaPorDefectoY(getÁreaSólida().y);
        GestorImagen.obtenerImagenDeEnemigo(this);
    }

    /**
     * Método para establecer la acción del enemigo.
     */
    public abstract void establecerAcción();
    /**
     * Método para obtener el daño que el enemigo puede infligir.
     * @return El daño que el enemigo puede infligir.
     */
    public int getDaño() {
        return daño;
    }
    /**
     * Método para obtener el nombre del enemigo.
     *
     * @return El nombre del enemigo.
     */
    public String getNombre(){
        return super.getNombre();
    }
}
