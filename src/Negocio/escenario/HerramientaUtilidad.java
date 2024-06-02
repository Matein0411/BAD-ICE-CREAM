package Negocio.escenario;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HerramientaUtilidad {
    /**
     * Escala una imagen dada a las dimensiones especificadas.
     *
     * @param original La imagen original que se va a escalar.
     * @param ancho    El ancho deseado para la imagen escalada.
     * @param alto     El alto deseado para la imagen escalada.
     * @return La imagen escalada al tamaño especificado.
     */
    public BufferedImage escalarImagen(BufferedImage original, int ancho, int alto) {
        // Crear una nueva imagen escalada con las dimensiones especificadas
        BufferedImage imagenEscalada = new BufferedImage(ancho, alto, original.getType());

        // Obtener un objeto Graphics2D para dibujar en la nueva imagen
        Graphics2D graphics2D = imagenEscalada.createGraphics();

        // Escalar y dibujar la imagen original en la nueva imagen
        graphics2D.drawImage(original, 0, 0, ancho, alto, null);

        //Libera recursos
        graphics2D.dispose();

        return imagenEscalada;
    }
}
