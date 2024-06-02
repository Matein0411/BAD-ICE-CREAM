package Negocio.entidades.enemigos;

import Negocio.escenario.PanelDeJuego;
import Negocio.mecánicas.Dirección;

import java.util.Random;

/**
 * Clase que representa un enemigo específico: Ogro.
 * Hereda de la clase Enemigo.
 */
public class Ogro extends Enemigo{
    // Velocidad del Ogro
    private final static int VELOCIDAD = 2;

    // Daño que puede infligir el Ogro
    private final static int DAÑO = 1;
    // Nombre del enemigo
    public final static String NOMBRE = "ogro";
    public Ogro(PanelDeJuego panelDeJuego) {
        super(panelDeJuego, VELOCIDAD, NOMBRE, DAÑO);
    }

    /**
     * Método para establecer la acción del ogro.
     */
    @Override
    public void establecerAcción() {
        setContadorBloqueoDeAcción(getContadorBloqueoDeAcción() + 1);
        if (getContadorBloqueoDeAcción() == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                setDirección(Dirección.ARRIBA);
            }
            if (i > 25 && i <= 50) {
                setDirección(Dirección.ABAJO);
            }
            if (i > 50 && i <= 75) {
                setDirección(Dirección.IZQUIERDA);
            }
            if (i > 75 && i <= 100) {
                setDirección(Dirección.DERECHA);
            }
            setContadorBloqueoDeAcción(0);
        }
    }
}
