package presentación.sonido;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sonido {
    private Clip clip;
    private URL soundURL[] = new URL[30];
    FloatControl fc;
    public int escalaDeVolumen=3;
    float volumen;

    /**
     * Constructor de la clase Sonido.
     * Configura los diferentes sonidos asociados a índices específicos.
     * Este constructor inicializa los sonidos con nombres predefinidos.
     */
    public Sonido() {
        configurarSonido(0, "Die");       // Sonido de muerte
        configurarSonido(1, "Fruit");     // Sonido de fruta
        configurarSonido(2, "Gamemusic"); // Música del juego
        configurarSonido(3, "IceDestroy");// Sonido de destrucción de hielo
        configurarSonido(4, "LoseMusic"); // Música de pérdida
        configurarSonido(5, "MenuMusic"); // Música del menú
        configurarSonido(6, "WinMusic");  // Música de victoria
    }

    /**
     * Configura un presentación.sonido en un índice específico.
     *
     * @param índice          El índice donde se configurará el presentación.sonido.
     * @param direcciónSonido La dirección del archivo de presentación.sonido.
     */
    public void configurarSonido(int índice, String direcciónSonido){
        this.soundURL[índice] = getClass().getResource("/datos/fuentes/sounds/" + direcciónSonido + ".wav");
    }

    /**
     * Coloca un archivo de presentación.sonido en el clip para reproducir.
     *
     * @param índiceSonido El índice del archivo de presentación.sonido en el arreglo de URLs de presentación.sonido.
     */
    public void colocarArchivo(int índiceSonido) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[índiceSonido]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            verificarVolumen();
        } catch (Exception e) {
        }
    }

    /**
     * Reproduce el sonido asociado al clip.
     * Este método inicia la reproducción del clip de presentación.sonido.
     */
    public void reproducir() {
        clip.start();
    }

    public void entrarEnBucle() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Para la reproducción del sonido asociado al clip.
     */
    public void parar() {
        clip.stop();
    }
    public void verificarVolumen(){
        switch (escalaDeVolumen){
            case 0: volumen=-80f; break;
            case 1: volumen=-20f; break;
            case 2: volumen=-12f; break;
            case 3: volumen=-5f; break;
            case 4: volumen=1f; break;
            case 5: volumen=6f; break;
        }
        fc.setValue(volumen);
    }

    public int getEscalaDeVolumen() {
        return escalaDeVolumen;
    }
    public void disminuirVolumen(){
        escalaDeVolumen--;
    }
    public void aumentarVolumen(){
        escalaDeVolumen++;
    }
}
