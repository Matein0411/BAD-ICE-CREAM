package presentación.interfazDeUsuario;

import Negocio.escenario.*;


import java.io.*;

/**
 * La clase Configuración proporciona métodos para guardar y cargar la configuración del juego.
 */
public class Configuración implements Serializable{
    PanelDeJuego panelDeJuego;

    public Configuración(PanelDeJuego panelDeJuego){
        this.panelDeJuego = panelDeJuego;
    }

    /**
     * Guarda la configuración actual del juego en un archivo.
     */
    public void guardarConfig(){
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\MY LENOVO\\IdeaProjects\\Proyecto---BadIce\\src\\datos\\fuentes\\datosDeJuego\\configuración.txt"));
            //music
            bw.write(String.valueOf(panelDeJuego.getMúsica().getEscalaDeVolumen()));
            bw.newLine();

            //es
            bw.write(String.valueOf(panelDeJuego.getSe().getEscalaDeVolumen()));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Carga la configuración del juego desde un archivo.
     */
    public void cargarConfig(){
        try {
            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\MY LENOVO\\IdeaProjects\\Proyecto---BadIce\\src\\datos\\fuentes\\datosDeJuego\\configuración.txt"));
            String s;

            s=br.readLine();
            panelDeJuego.getMúsica().escalaDeVolumen=Integer.parseInt(s);

            s= br.readLine();
            panelDeJuego.getSe().escalaDeVolumen=Integer.parseInt(s);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
