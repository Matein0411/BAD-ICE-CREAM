/*package test;

import Negocio.entidades.Entidad;
import Negocio.entidades.Jugador;
import Negocio.entidades.frutas.Fruta;
import Negocio.entidades.frutas.Mora;
import Negocio.escenario.PanelDeJuego;
import Negocio.mecánicas.Dirección;
import Negocio.niveles.Nivel;
import Negocio.niveles.Nivel2;
import junit.framework.TestCase;
import org.junit.Test;

public class BadIceCreamTest extends TestCase {
    @Test
    public void testDeberíaCambiarDePosiciónAlApretarUnaTecla(){
        // Dado un jugador, y un panelDeJuego
        // Sus posiciones iniciales y una dirección
        PanelDeJuego panelDeJuego = new PanelDeJuego();
        Jugador jugador = new Jugador(panelDeJuego, panelDeJuego.getControl()); // Obtener jugador del panelDeJuego
        int posXInicial = jugador.getMundoX();
        int posYInicial = jugador.getMundoY();
        Dirección direcciónInicial = jugador.getDireccion();

        // Cuando se detecte una tecla
        panelDeJuego.getControl().setArribaPresionado(true);
        panelDeJuego.actualizar(); // Actualizar el panelDeJuego

        // Entonces su posición debería cambiar
        if (direcciónInicial == Dirección.ARRIBA) {
            assertEquals(posYInicial - jugador.getVelocidad(), jugador.getMundoY());
        }
    }
    @Test
    public void testSeDeberíaCrearBloques() {
        //Dado un nivel, un panel de juego y un jugador
        Nivel nivel = new Nivel2();
        PanelDeJuego panelDeJuego = new PanelDeJuego(nivel);
        Jugador jugador = new Jugador(panelDeJuego, panelDeJuego.getControl());

        // Act
        // Cuando se active el mecanismo de romper o crear hielo
        jugador.romperOCrearHielo();

        // Debería haber un tipo de bloque de hielo en esa posición
        assertEquals(8, panelDeJuego.getAdminBlock().getMapa()[9][22]);
    }
    @Test
    public void testSeDeberíaDestruirBloques() {
        //Dado un nivel, un panel de juego y un jugador
        Nivel nivel = new Nivel2();
        PanelDeJuego panelDeJuego = new PanelDeJuego(nivel);
        Jugador jugador = new Jugador(panelDeJuego, panelDeJuego.getControl());

        // Act
        // Cuando se active el mecanismo de romper o crear hielo
        jugador.romperOCrearHielo();

        //Activando la colisión para que se rompa
        jugador.setColisiónActiva(true);

        //Cuando se active el mecanismo de romper o crear hielo
        jugador.romperOCrearHielo();

        // Entonces, en la posición anterior debería ahora haber otro tipo de bloque
        assertEquals(0, panelDeJuego.getAdminBlock().getMapa()[9][22]);
    }
    @Test
    public void testRecogerFrutasYAumentarLasFrutasRecogidasDelJugador(){
        //Dado una Fruta y un jugador al entrar ambos en contacto
        //Posiciones donde coincidan
        PanelDeJuego panelDeJuego = new PanelDeJuego();
        Jugador jugador = new Jugador(panelDeJuego, panelDeJuego.getControl()); // Obtener jugador del panelDeJuego
        int posXInicial = jugador.getMundoX();
        int posYInicial = jugador.getMundoY();
        Dirección direcciónInicial = jugador.getDireccion();
        Fruta mora = new Mora(panelDeJuego);
        Entidad[] frutas = panelDeJuego.getFrutas();
        frutas[0] = mora;

        //Al estar en la misma posición, se detecta una colisión
        mora.setMundoX(posXInicial);
        mora.setMundoY(posYInicial);
        //El jugador podrá recoger una fruta
        jugador.recogerFrutas(panelDeJuego.getCheckColisión().verificarObjeto(jugador, true));

        //Entonces el contador de frutas del jugador debera haber aumentado en 1
        assertEquals(1,jugador.getNúmeroDeFrutas());
    }
    @Test
    public void testDeberíaDetectarColisiónConBloqueEstático() {
        // Dado un panelDeJuego y un jugador posicionado cerca de un Bloque Estático
        PanelDeJuego panelDeJuego = new PanelDeJuego(new Nivel2());
        Jugador jugador = new Jugador(panelDeJuego, panelDeJuego.getControl());
        jugador.setMundoX(10 * panelDeJuego.getTAMAÑO_DE_BLOQUE()); // Asumiendo que en esta posición hay un Bloque Estático cercano
        jugador.setMundoY(10 * panelDeJuego.getTAMAÑO_DE_BLOQUE());
        jugador.setDirección(Dirección.ABAJO); // Dirección hacia el Bloque Estático
        // Cuando se verifica la colisión
        panelDeJuego.getCheckColisión().verificarBloque(jugador);
        // Entonces, debería activarse la colisión
        assertTrue(jugador.isColisiónActiva());
    }

}
*/