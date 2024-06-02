package Negocio.escenario;

import Negocio.escenario.bloques.Bloque;
import Negocio.escenario.bloques.BloqueDeHielo;
import Negocio.escenario.bloques.BloqueEstático;
import Negocio.niveles.Nivel;
import presentación.GestorImagen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Clase que gestiona la creación y dibujo de los bloques en el tablero.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class AdministradorDeBloque implements Serializable {
    private PanelDeJuego panelDeJuego;
    private Bloque[] bloques;
    private int[][] mapa;
    private HerramientaUtilidad utilidad;
    private Nivel nivel;

    public AdministradorDeBloque(PanelDeJuego panelDeJuego, Nivel nivel) {
        this.setNivel(nivel);
        this.setTablero(panelDeJuego);
        setBloques(new Bloque[100]);
        setMapa(new int[panelDeJuego.getMaxColDeMundo()][panelDeJuego.getMaxFilasDeMundo()]);
        inicializarBloques();
        GestorImagen.cargarImagenesDeBloques(this.bloques);
        cargarMapa();
    }
    /**
     * Método privado para inicializar los tipos de bloques.
     */
    private void inicializarBloques() {
        getBloques()[0] = new Bloque();
        getBloques()[1] = new BloqueEstático();
        getBloques()[2] = new BloqueEstático();
        getBloques()[3] = new BloqueEstático();
        getBloques()[4] = new BloqueEstático();
        getBloques()[5] = new BloqueEstático();
        getBloques()[6] = new Bloque();
        getBloques()[7] = new Bloque();
        getBloques()[8] = new BloqueDeHielo();
    }

    /**
     * Lee el mapa de una ruta y la carga para generar los bloques respectivos
     */
    public void cargarMapa() {

        String direcciónArchivo = this.getNivel().getRutaMapa();

        try {
            InputStream mapaDeEntrada = getClass().getResourceAsStream(direcciónArchivo);
            BufferedReader br = new BufferedReader(new InputStreamReader(mapaDeEntrada));

            int columna = 0;
            int fila = 0;

            while (columna < getTablero().getMaxColDeMundo() && fila < getTablero().getMaxFilasDeMundo()) {
                String line = br.readLine();

                while (columna < getTablero().getMaxColDeMundo()) {
                    String numeros[] = line.split(" ");

                    int numero = Integer.parseInt(numeros[columna]);

                    getMapa()[columna][fila] = numero;
                    columna++;
                }
                if (columna == getTablero().getMaxColDeMundo()) {
                    columna = 0;
                    fila++;
                }

            }
            br.close();
        } catch (Exception e) {

        }
    }

    /**
     * Método para dibujar los bloques en el tablero.
     *
     * @param g2 El contexto gráfico en el que se dibujan los bloques.
     */
    public void dibujar(Graphics2D g2) {
        int columnasDeMundo = 0;
        int filasDeMundo = 0;

        while (columnasDeMundo < getTablero().getMaxColDeMundo() && filasDeMundo < getTablero().getMaxFilasDeMundo()) {

            int numBloque = getMapa()[columnasDeMundo][filasDeMundo];
            int mundoX = columnasDeMundo * getTablero().getTAMAÑO_DE_BLOQUE();
            int mundoY = filasDeMundo * getTablero().getTAMAÑO_DE_BLOQUE();
            int ventanaX = mundoX - getTablero().getJugador().getMundoX() + getTablero().getJugador().getVentanaX();
            int ventanaY = mundoY - getTablero().getJugador().getMundoY() + getTablero().getJugador().getVentanaY();

            if(jugadorEstáEnPantalla(mundoX, mundoY)) {
                g2.drawImage(getBloques()[numBloque].imagen, ventanaX, ventanaY, null);
            }
            columnasDeMundo++;

            if (columnasDeMundo == getTablero().getMaxColDeMundo()) {
                columnasDeMundo = 0;
                filasDeMundo++;
            }
        }
    }


    /**
     * Método para verificar si el jugador está en pantalla.
     *
     * @param mundoX La coordenada X en el mundo del juego.
     * @param mundoY La coordenada Y en el mundo del juego.
     * @return true si el jugador está en pantalla, false en caso contrario.
     */
    public boolean jugadorEstáEnPantalla(int mundoX, int mundoY) {
        return mundoX + getTablero().getTAMAÑO_DE_BLOQUE() *11 > getTablero().getJugador().getMundoX() - getTablero().getJugador().getVentanaX() &&
                mundoX - getTablero().getTAMAÑO_DE_BLOQUE() *12 < getTablero().getJugador().getMundoX() + getTablero().getJugador().getVentanaX() &&
                mundoY + (getTablero().getTAMAÑO_DE_BLOQUE() *2) > getTablero().getJugador().getMundoY() - getTablero().getJugador().getVentanaY() &&
                mundoY - (getTablero().getTAMAÑO_DE_BLOQUE() *2) < getTablero().getJugador().getMundoY() + getTablero().getJugador().getVentanaY();
    }


    public PanelDeJuego getTablero() {
        return panelDeJuego;
    }

    public void setTablero(PanelDeJuego panelDeJuego) {
        this.panelDeJuego = panelDeJuego;
    }

    public Bloque[] getBloques() {
        return bloques;
    }

    public void setBloques(Bloque[] bloques) {
        this.bloques = bloques;
    }

    public int[][] getMapa() {
        return mapa;
    }

    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }

    public HerramientaUtilidad getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(HerramientaUtilidad utilidad) {
        this.utilidad = utilidad;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
}
