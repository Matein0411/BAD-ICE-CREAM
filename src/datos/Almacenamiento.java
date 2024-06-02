package datos;

import Negocio.entidades.Entidad;

import java.io.Serializable;

/**
 * La clase Almacenamiento la cual almacena los últimos datos
 * del jugador, como su posición, vida, puntuación, etc.
 * Estos datos serán salvados para luego ser cargados.
 */
public class Almacenamiento implements Serializable {
    //Estadísticas del jugador
    int puntuación;
    int númeroDeFrutas;
    int vidaMáxima;
    int vida;
    int posX;
    int posY;
    double tiempo;
    Entidad[] frutas;
    int[][] mapa;
    Entidad[] enemigos;

}
