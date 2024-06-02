package presentación;

import Negocio.entidades.frutas.Fruta;
import Negocio.entidades.objetos.Corazón;
import Negocio.entidades.enemigos.Enemigo;
import Negocio.entidades.Jugador;
import Negocio.escenario.HerramientaUtilidad;
import Negocio.escenario.PanelDeJuego;
import Negocio.escenario.bloques.Bloque;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * La clase GestorImagen se encarga de obtener y configurar las imágenes para diferentes entidades del juego.
 */
public class GestorImagen{

    /**
     * Configura las imágenes de un objeto corazón.
     * @param corazón El objeto corazón.
     */
    public static void obtenerImagenDeObjetos(Corazón corazón){
        corazón.imagen1 = configurarImagen("/datos/fuentes/IU/corazón1");
        corazón.imagen2 = configurarImagen("/datos/fuentes/IU/corazón2");
        corazón.imagen3 = configurarImagen("/datos/fuentes/IU/corazón3");
    }

    /**
     * Configura las imágenes de un enemigo.
     * @param enemigo El enemigo.
     */
    public static void obtenerImagenDeEnemigo(Enemigo enemigo) {
        enemigo.arriba1 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_arriba1");
        enemigo.arriba2 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_arriba2");
        enemigo.arriba3 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_arriba3");
        enemigo.arriba4 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_arriba4");
        enemigo.abajo1 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_abajo1");
        enemigo.abajo2 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_abajo2");
        enemigo.abajo3 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_abajo3");
        enemigo.abajo4 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_abajo4");
        enemigo.izquierda1 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_izquierda1");
        enemigo.izquierda2 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_izquierda2");
        enemigo.izquierda3 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_izquierda3");
        enemigo.izquierda4 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_izquierda4");
        enemigo.derecha1 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_derecha1");
        enemigo.derecha2 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_derecha2");
        enemigo.derecha3 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_derecha3");
        enemigo.derecha4 = configurarImagen("/datos/fuentes/enemigo/" + enemigo.getNombre() + "_derecha4");
    }

    /**
     * Configura las imágenes de una fruta.
     * @param fruta La fruta.
     */
    public static void obtenerImagenDeFruta(Fruta fruta){
        fruta.imagen1 = configurarImagen("/datos/fuentes/frutas/" + fruta.getNombre() + 1);
        fruta.imagen2 = configurarImagen("/datos/fuentes/frutas/" + fruta.getNombre() + 2);
        fruta.imagen3 = configurarImagen("/datos/fuentes/frutas/" + fruta.getNombre() + 3);
        fruta.imagen4 = configurarImagen("/datos/fuentes/frutas/" + fruta.getNombre() + 4);
        fruta.imagen5 = configurarImagen("/datos/fuentes/frutas/" + fruta.getNombre() + 5);
    }

    /**
     * Obtiene la imagen actual de una fruta basada en el índice de animación.
     * @param fruta La fruta.
     * @return La imagen actual de la fruta.
     */
    public static BufferedImage obtenerImagenActualDeFruta(Fruta fruta) {
        // Retorna la imagen actual basada en el índice de animación.
        return switch (fruta.animaciónIndex) {
            case 0 -> fruta.imagen1;
            case 1 -> fruta.imagen2;
            case 2 -> fruta.imagen3;
            case 3 -> fruta.imagen4;
            case 4 -> fruta.imagen5;
            // En caso de un índice inválido, retorna imagen1 como valor por defecto.
            default -> fruta.imagen1;
        };
    }
    /**
     * Actualiza la animación de una fruta.
     * @param fruta La fruta.
     */
    public static void actualizarAnimaciónDeFruta(Fruta fruta) {
        if (System.currentTimeMillis() - fruta.últimoTiempoCambio >= fruta.velocidadAnimación) {
            fruta.animaciónIndex++;
            // Asegura que el índice de animación varíe entre 0 y 4.
            if (fruta.animaciónIndex > 4) fruta.animaciónIndex = 0;
            fruta.últimoTiempoCambio = System.currentTimeMillis();
        }
    }

    /**
     * Configura las imágenes de un jugador.
     * @param jugador El jugador.
     */
    public static void obtenerImagenDeJugador(Jugador jugador){
        // Configurar las imágenes del jugador para la dirección hacia arriba
        jugador.arriba1 = configurarImagen("/datos/fuentes/jugador/jugador_arriba1");
        jugador.arriba2 = configurarImagen("/datos/fuentes/jugador/jugador_arriba2");
        jugador.arriba3 = configurarImagen("/datos/fuentes/jugador/jugador_arriba3");
        jugador.arriba4 = configurarImagen("/datos/fuentes/jugador/jugador_arriba4");

        // Configurar las imágenes del jugador para la dirección hacia abajo
        jugador.abajo1 = configurarImagen("/datos/fuentes/jugador/jugador_abajo1");
        jugador.abajo2 = configurarImagen("/datos/fuentes/jugador/jugador_abajo2");
        jugador.abajo3 = configurarImagen("/datos/fuentes/jugador/jugador_abajo3");
        jugador.abajo4 = configurarImagen("/datos/fuentes/jugador/jugador_abajo4");

        // Configurar las imágenes del jugador para la dirección hacia izquierda
        jugador.izquierda1 = configurarImagen("/datos/fuentes/jugador/jugador_izquierda1");
        jugador.izquierda2 = configurarImagen("/datos/fuentes/jugador/jugador_izquierda2");
        jugador.izquierda3 = configurarImagen("/datos/fuentes/jugador/jugador_izquierda3");
        jugador.izquierda4 = configurarImagen("/datos/fuentes/jugador/jugador_izquierda4");

        // Configurar las imágenes del jugador para la dirección hacia derecha
        jugador.derecha1 = configurarImagen("/datos/fuentes/jugador/jugador_derecha1");
        jugador.derecha2 = configurarImagen("/datos/fuentes/jugador/jugador_derecha2");
        jugador.derecha3 = configurarImagen("/datos/fuentes/jugador/jugador_derecha3");
        jugador.derecha4 = configurarImagen("/datos/fuentes/jugador/jugador_derecha4");
    }
    /**
     * Configura una imagen basada en su nombre.
     * @param nombreImagen El nombre de la imagen.
     * @return La imagen configurada.
     */
    public static BufferedImage configurarImagen(String nombreImagen) {
        HerramientaUtilidad ut = new HerramientaUtilidad();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(GestorImagen.class.getResourceAsStream(nombreImagen + ".png"));
            imagen = ut.escalarImagen(imagen, 42, 42);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagen;
    }

    /**
     * Obtiene la imagen de una entidad basada en su dirección y movimiento.
     * @param jugador La entidad.
     * @return La imagen correspondiente a la entidad.
     */
    public static BufferedImage obtenerImagen(Jugador jugador) {
        BufferedImage imagen = null;
        switch (jugador.getDirección()) {
            case ARRIBA:
                if (jugador.numeroDeMovimiento == 1) {
                    imagen = jugador.arriba1;
                }
                if (jugador.numeroDeMovimiento == 2) {
                    imagen = jugador.arriba2;
                }
                if (jugador.numeroDeMovimiento == 3) {
                    imagen = jugador.arriba3;
                }
                if (jugador.numeroDeMovimiento == 4) {
                    imagen = jugador.arriba4;
                }
                break;
            case ABAJO:
                if (jugador.numeroDeMovimiento == 1) {
                    imagen = jugador.abajo1;
                }
                if (jugador.numeroDeMovimiento == 2) {
                    imagen = jugador.abajo2;
                }
                if (jugador.numeroDeMovimiento == 3) {
                    imagen = jugador.abajo3;
                }
                if (jugador.numeroDeMovimiento == 4) {
                    imagen = jugador.abajo4;
                }
                break;
            case IZQUIERDA:
                if (jugador.numeroDeMovimiento == 1) {
                    imagen = jugador.izquierda1;
                }
                if (jugador.numeroDeMovimiento == 2) {
                    imagen = jugador.izquierda2;
                }
                if (jugador.numeroDeMovimiento == 3) {
                    imagen = jugador.izquierda3;
                }
                if (jugador.numeroDeMovimiento == 4) {
                    imagen = jugador.izquierda4;
                }
                break;
            case DERECHA:
                if (jugador.numeroDeMovimiento == 1) {
                    imagen = jugador.derecha1;
                }
                if (jugador.numeroDeMovimiento == 2) {
                    imagen = jugador.derecha2;
                }
                if (jugador.numeroDeMovimiento == 3) {
                    imagen = jugador.derecha3;
                }
                if (jugador.numeroDeMovimiento == 4) {
                    imagen = jugador.derecha4;
                }
                break;
        }
        return imagen;
    }

    /**
     * Carga las imágenes para cada bloque.
     */
    public static void cargarImagenesDeBloques(Bloque[] bloques) {
        for (int i = 0; i < bloques.length; i++) {
            Bloque bloque = bloques[i];
            if (bloque != null) {
                String nombreImagen = obtenerNombreImagenPorIndice(i);
                bloque.imagen = cargarImagen("/datos/fuentes/bloque/" + nombreImagen + ".png");
            }
        }
    }

    /**
     * Obtiene el nombre de la imagen basado en el índice del bloque.
     * @param índice El índice del bloque.
     * @return El nombre de la imagen correspondiente.
     */
    private static String obtenerNombreImagenPorIndice(int índice) {
        // Aquí retornas el nombre de la imagen basado en el índice.
        // Por ejemplo:
        return switch (índice) {
            case 0 -> "nieve";
            case 1 -> "esquina1";
            case 2 -> "esquina2";
            case 3 -> "esquina3";
            case 4 -> "esquina4";
            case 5 -> "muro";
            case 6 -> "florNieve";
            case 7 -> "bolaNieve";
            case 8 -> "hielo";
            default -> "default"; // O maneja una imagen por defecto.
        };
    }


    /**
     * Carga una imagen desde el sistema de archivos.
     * @param rutaImagen La ruta de la imagen.
     * @return La imagen cargada.
     */
    private static BufferedImage cargarImagen(String rutaImagen) {
        PanelDeJuego panelDeJuego = new PanelDeJuego();
        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(GestorImagen.class.getResourceAsStream(rutaImagen));
            HerramientaUtilidad uTool = new HerramientaUtilidad();
            imagen = uTool.escalarImagen(imagen, panelDeJuego.getTAMAÑO_DE_BLOQUE(), panelDeJuego.getTAMAÑO_DE_BLOQUE());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagen;
    }

}
