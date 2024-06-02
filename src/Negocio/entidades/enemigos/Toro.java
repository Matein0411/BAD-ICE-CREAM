package Negocio.entidades.enemigos;

import Negocio.escenario.PanelDeJuego;
import Negocio.mecánicas.Dirección;

import java.util.Random;

/**
 * Clase que representa un enemigo específico: Toro.
 * Hereda de la clase Enemigo.
 */
public class Toro extends Enemigo{
    // Velocidad del Toro
    public final static int VELOCIDAD = 1;
    // Daño infligido por el Toro
    private final static int DAÑO = 2;
    // Nombre del enemigo
    public final static String NOMBRE = "toro";

    /**
     * Constructor de la clase Toro.
     */
    public Toro(PanelDeJuego panelDeJuego) {
        super(panelDeJuego, VELOCIDAD, NOMBRE, DAÑO);
    }

    /**
     * Método para establecer la acción del toro.
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
