package Negocio.niveles;

import java.io.Serializable;

/**
 * La clase abstracta Nivel representa un nivel en el juego.
 */
public abstract class Nivel implements Serializable {
    private int númeroNivel;
    private String rutaMapa;
    private String rutaFrutas;
    private String rutaEnemigos;
    private int cantidadMáximaFrutas;

    /**
     * Constructor de la clase Nivel.
     *
     * @param númeroNivel   El número del nivel.
     * @param rutaMapa      La ruta del archivo del mapa del nivel.
     * @param rutaFrutas    La ruta del archivo de las frutas del nivel.
     * @param rutaEnemigos  La ruta del archivo de los enemigos del nivel.
     */
    public Nivel(int númeroNivel, int cantidadMáximaFrutas,String rutaMapa, String rutaFrutas, String rutaEnemigos) {
        this.cantidadMáximaFrutas = cantidadMáximaFrutas;
        this.númeroNivel = númeroNivel;
        this.rutaMapa = rutaMapa;
        this.rutaFrutas = rutaFrutas;
        this.rutaEnemigos = rutaEnemigos;
    }

    //Getters
    public String getNivel(){
        return "nivel" + this.númeroNivel;
    }

    public String getRutaMapa() {
        return rutaMapa;
    }

    public String getRutaFrutas(){
        return rutaFrutas;
    }

    public String getRutaEnemigos() {
        return rutaEnemigos;
    }

    public int getCantidadDeFrutas() {
        return cantidadMáximaFrutas;
    }
}
