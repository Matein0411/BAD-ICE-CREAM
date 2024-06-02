package Negocio.mecánicas;

/**
 * Enumeración de los posibles estados que tendrá el juego
 * en su capa de presentación.
 */
public enum EstadoDeJuego {
    TÍTULO,    //Estado de Inicial del juego.
    JUEGO,     //Estado de ejecución del juego.
    PAUSA,     //Estado de pausa del juego, enemigos y jugador no pueden moverse.
    VICTORIA,  //Estado de victoria que se establece al recoger todas las frutas del nivel.
    DERROTA,   //Estado en donde el jugador pierde todas sus vidas y no cumple el objetivo del juego.
    NEUTRO,    //Estado que extra antes de entrar al estado de TÍTULO.
    OPCIONES   //Estado en donde se despliegan opciones del juego.
}
