package Negocio.escenario.bloques;


import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Clase que representa un bloque en el juego.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class Bloque implements Serializable {
    public transient BufferedImage imagen;
}
