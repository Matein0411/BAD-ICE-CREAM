package Negocio.niveles;

/**
 * La clase Nivel2 es una subclase de la clase Nivel y representa el primer nivel del juego.
 */
public class Nivel2 extends Nivel{
    private static final int númeroNivel = 2;
    private final static int CANTIDAD_MÁXIMA_FRUTAS = 18;
    private static final String mapa2 = "/datos/fuentes/datosDeJuego/mapa2.txt";
    //Reemplazar las rutas por la de sus computadores o intentar con la del proyecto mismo
    private static final String frutas2 = "C:\\Users\\Compu\\Documents\\workspace\\Clonación\\Proyecto---BadIceCream\\src\\datos\\fuentes\\entidades\\frutas2.txt";
    private static final String enemigos2 = "C:\\Users\\Compu\\Documents\\workspace\\Clonación\\Proyecto---BadIceCream\\src\\datos\\fuentes\\entidades\\enemigos2.txt";

    /**
     * Constructor de la clase Nivel2.
     */
    public Nivel2() {
        super(númeroNivel, CANTIDAD_MÁXIMA_FRUTAS, mapa2, frutas2, enemigos2);
    }
}
