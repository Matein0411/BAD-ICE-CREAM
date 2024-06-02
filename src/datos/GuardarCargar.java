package datos;

import Negocio.entidades.Entidad;
import Negocio.escenario.PanelDeJuego;
import Negocio.niveles.Nivel;

import java.io.*;

/**
 * La clase GuardarCargar proporciona métodos para guardar y cargar el estado del juego.
 */
public class GuardarCargar implements Serializable {

    PanelDeJuego panelDeJuego;

    public GuardarCargar(PanelDeJuego panelDeJuego){
        this.panelDeJuego = panelDeJuego;
    }

    /**
     * Guarda el estado actual del juego en un archivo.
     *
     * @param nivel El nivel actual del juego.
     */
    public void guardar(Nivel nivel){

        try{

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("C:\\Users\\MY LENOVO\\IdeaProjects\\Proyecto---BadIce\\src\\datos\\fuentes\\datosDeJuego\\" + nivel.getNivel() + ".dat")));

            Almacenamiento almacenamiento = new Almacenamiento();

            // Almacenar información del jugador y del tablero
            almacenamiento.númeroDeFrutas = panelDeJuego.getJugador().getNúmeroDeFrutas();
            almacenamiento.puntuación     = panelDeJuego.getJugador().getPuntaje();
            almacenamiento.vidaMáxima     = panelDeJuego.getJugador().getMáximoVidas();
            almacenamiento.vida           = panelDeJuego.getJugador().getVida();
            almacenamiento.posX           = panelDeJuego.getJugador().getMundoX();
            almacenamiento.posY           = panelDeJuego.getJugador().getMundoY();
            almacenamiento.tiempo         = panelDeJuego.getIu().playTime;
            almacenamiento.mapa           = panelDeJuego.getAdminBlock().getMapa();

            // Almacenar información de los enemigos
            almacenamiento.enemigos = new Entidad[panelDeJuego.getEnemigos().length];
            for(int i = 0; i < panelDeJuego.getEnemigos().length; i++){
                almacenamiento.enemigos[i] = new Entidad(panelDeJuego);
            }

            for(int i = 0; i < panelDeJuego.getEnemigos().length; i++){
                if(panelDeJuego.getEnemigos()[i] != null){
                    almacenamiento.enemigos[i].setMundoX(panelDeJuego.getEnemigos()[i].getMundoX());
                    almacenamiento.enemigos[i].setMundoY(panelDeJuego.getEnemigos()[i].getMundoY());
                }
            }

            // Almacenar información de las frutas
            almacenamiento.frutas = new Entidad[panelDeJuego.getFrutas().length];
            for(int i = 0; i < panelDeJuego.getFrutas().length; i++){
                almacenamiento.frutas[i] = new Entidad(panelDeJuego);
            }

            for(int i = 0; i < panelDeJuego.getFrutas().length; i++){
                if(panelDeJuego.getFrutas()[i] != null){
                    almacenamiento.frutas[i].setMundoX(panelDeJuego.getFrutas()[i].getMundoX());
                    almacenamiento.frutas[i].setMundoY(panelDeJuego.getFrutas()[i].getMundoY());
                } else {
                    almacenamiento.frutas[i] = null;
                }
            }

            //Escribir en el archivo
            oos.writeObject(almacenamiento);
            oos.close();

        }catch (Exception e){
            System.out.println("Excepción de guardado!");
            e.printStackTrace();
        }
    }

    /**
     * Carga el estado del juego desde un archivo.
     *
     * @param nivel El nivel actual del juego.
     */
    public void cargar(Nivel nivel){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("C:\\Users\\MY LENOVO\\IdeaProjects\\Proyecto---BadIce\\src\\datos\\fuentes\\datosDeJuego\\" + nivel.getNivel() + ".dat")));

            //Leer del archivo
            Almacenamiento almacenamiento = (Almacenamiento) ois.readObject();

            // Restaurar información del jugador y del tablero
            panelDeJuego.getJugador().setPuntaje(almacenamiento.puntuación);
            panelDeJuego.getJugador().setNúmeroDeFrutas(almacenamiento.númeroDeFrutas);
            panelDeJuego.getJugador().setMáximoVidas(almacenamiento.vidaMáxima);
            panelDeJuego.getJugador().setVida(almacenamiento.vida);
            panelDeJuego.getJugador().setMundoX(almacenamiento.posX);
            panelDeJuego.getJugador().setMundoY(almacenamiento.posY);
            panelDeJuego.getAdminBlock().setMapa(almacenamiento.mapa);
            panelDeJuego.getIu().playTime = almacenamiento.tiempo;

            // Restaurar información de los enemigos
            for(int i = 0; i < almacenamiento.enemigos.length; i++){
                if(panelDeJuego.getEnemigos()[i] != null){
                    panelDeJuego.getEnemigos()[i].setMundoX(almacenamiento.enemigos[i].getMundoX());
                    panelDeJuego.getEnemigos()[i].setMundoY(almacenamiento.enemigos[i].getMundoY());
                }
            }

            // Restaurar información de las frutas
            for(int i = 0; i < almacenamiento.frutas.length; i++){
                if(almacenamiento.frutas[i] != null){
                    panelDeJuego.getFrutas()[i].setMundoX(almacenamiento.frutas[i].getMundoX());
                    panelDeJuego.getFrutas()[i].setMundoY(almacenamiento.frutas[i].getMundoY());
                } else {
                    panelDeJuego.getFrutas()[i] = null;
                }
            }

        }catch (Exception e){
            System.out.println("Excepción de cargado");
            e.printStackTrace();
        }
    }
}
