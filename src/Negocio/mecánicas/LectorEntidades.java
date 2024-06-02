package Negocio.mecánicas;
import Negocio.entidades.frutas.Banana;
import Negocio.entidades.frutas.Fruta;
import Negocio.entidades.frutas.Mora;
import Negocio.entidades.enemigos.Enemigo;
import Negocio.entidades.enemigos.Ogro;
import Negocio.entidades.enemigos.Toro;
import Negocio.escenario.PanelDeJuego;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * La clase LectorEntidades proporciona métodos estáticos para leer información sobre frutas y enemigos desde un archivo
 * y crear instancias de las clases correspondientes (Fruta y Enemigo) basadas en esa información.
 */
public class LectorEntidades {
    /**
     * Lee información sobre frutas desde un archivo y crea un array de instancias de Fruta.
     *
     * @param nombreArchivo Nombre del archivo desde el cual se leerán los datos.
     * @param panelDeJuego       Objeto de tipo Tablero necesario para la creación de instancias de Fruta.
     * @return Un array de instancias de Fruta.
     * @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
     */
    public static Fruta[] leerFrutas(String nombreArchivo, PanelDeJuego panelDeJuego) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
        Fruta[] frutas = new Fruta[11]; // Suponiendo que hay 11 frutas en el archivo

        String linea;
        int indice = 0;
        while ((linea = lector.readLine()) != null && indice < frutas.length) {
            String[] partes = linea.split(",");
            String tipoFruta = partes[0];
            int mundoX = Integer.parseInt(partes[1]);
            int mundoY = Integer.parseInt(partes[2]);

            if (tipoFruta.equals("Mora")) {
                frutas[indice] = new Mora(panelDeJuego);
            } else if (tipoFruta.equals("Banana")) {
                frutas[indice] = new Banana(panelDeJuego);
            } // Añade más condiciones para otros tipos de frutas si es necesario

            frutas[indice].setMundoX(mundoX * panelDeJuego.getTAMAÑO_DE_BLOQUE());
            frutas[indice].setMundoY(mundoY * panelDeJuego.getTAMAÑO_DE_BLOQUE());

            indice++;
        }

        lector.close();
        return frutas;
    }

    /**
     * Lee información sobre enemigos desde un archivo y crea un array de instancias de Enemigo.
     *
     * @param nombreArchivo Nombre del archivo desde el cual se leerán los datos.
     * @param panelDeJuego       Objeto de tipo Tablero necesario para la creación de instancias de Enemigo.
     * @return Un array de instancias de Enemigo.
     * @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
     */
    public static Enemigo[] leerEnemigos(String nombreArchivo, PanelDeJuego panelDeJuego) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
        Enemigo[] enemigos = new Enemigo[panelDeJuego.getEnemigos().length]; // Suponiendo que hay 5 enemigos en el archivo

        String linea;
        int indice = 0;
        while ((linea = lector.readLine()) != null && indice < enemigos.length) {
            String[] partes = linea.split(",");
            String tipoEnemigo = partes[0];
            int mundoX = Integer.parseInt(partes[1]);
            int mundoY = Integer.parseInt(partes[2]);

            if (tipoEnemigo.equals("Toro")) {
                enemigos[indice] = new Toro(panelDeJuego);
            } else if (tipoEnemigo.equals("Ogro")) {
                enemigos[indice] = new Ogro(panelDeJuego);
            } // Añade más condiciones para otros tipos de enemigos si es necesario

            enemigos[indice].setMundoX(mundoX * panelDeJuego.getTAMAÑO_DE_BLOQUE());
            enemigos[indice].setMundoY(mundoY * panelDeJuego.getTAMAÑO_DE_BLOQUE());

            indice++;
        }

        lector.close();
        return enemigos;
    }
}
