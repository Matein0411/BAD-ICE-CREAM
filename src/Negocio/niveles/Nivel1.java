package Negocio.niveles;

/**
 * La clase Nivel1 es una subclase de la clase Nivel y representa el primer nivel del juego.
 */
public class Nivel1 extends Nivel{
    private final static int númeroNivel = 1;
    private final static int CANTIDAD_MÁXIMA_FRUTAS = 12;
    private static final String mapa1 = "/datos/fuentes/datosDeJuego/mapa.txt";
    //Reemplazar las rutas por la de sus computadores o intentar con la del proyecto mismo
    private static final String frutas1 = "C:\\Users\\MY LENOVO\\IdeaProjects\\BAD-ICE-CREAM\\src\\datos\\fuentes\\entidades\\frutas.txt";
    private static final String enemigos1 = "C:\\Users\\MY LENOVO\\IdeaProjects\\BAD-ICE-CREAM\\src\\datos\\fuentes\\entidades\\enemigos.txt";


    /**
     * Constructor de la clase Nivel1.
     */
    public Nivel1() {
        super(númeroNivel, CANTIDAD_MÁXIMA_FRUTAS, mapa1, frutas1, enemigos1);
    }

}
