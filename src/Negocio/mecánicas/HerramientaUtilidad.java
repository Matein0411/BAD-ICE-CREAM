package Negocio.mecánicas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HerramientaUtilidad {

    public BufferedImage configurar(String nombreImagen, int tamañoBloque) {
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream("/fuentes/jugador/" + nombreImagen + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imagen;
    }


}
