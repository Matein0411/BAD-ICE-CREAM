package Negocio.escenario;

import Negocio.entidades.Entidad;
import Negocio.entidades.Jugador;
import Negocio.mecánicas.ColocadorDeObjetos;
import Negocio.mecánicas.Control;
import Negocio.mecánicas.Dirección;
import Negocio.mecánicas.VerificadorDeColisión;
import Negocio.niveles.Nivel;
import datos.GuardarCargar;
import presentación.interfazDeUsuario.Configuración;
import Negocio.mecánicas.EstadoDeJuego;
import presentación.interfazDeUsuario.IU;
import presentación.sonido.Sonido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PanelDeJuego extends JPanel implements Runnable {
    private final int TAMAÑO_BLOQUE_ORIGINAL = 13;
    private final int ESCALA = 3;
    private final int TAMAÑO_DE_BLOQUE = getTAMAÑO_BLOQUE_ORIGINAL() * getESCALA(); //42 pixeles
    private final int COLUMNAS_MAX = 16;
    private final int FILAS_MAX = 15;

    private final int ALTO = getTAMAÑO_DE_BLOQUE() * getCOLUMNAS_MAX(); // 674 pixeles
    private final int ANCHO = getTAMAÑO_DE_BLOQUE() * getFILAS_MAX(); // 504 pixeles

    //Configuración del mundo
    private final int maxColDeMundo = 33;
    private final int maxFilasDeMundo = 31;

    //Niveles
    private transient Nivel nivel;

    //FPS
    private static final int FPS = 60;

    //sistema
    private Configuración configuración=new Configuración(this);
    private GuardarCargar guardarCargar = new  GuardarCargar(this);
    private Control control = new Control(this);
    private transient Thread hiloDeJuego;
    private VerificadorDeColisión checkColisión = new VerificadorDeColisión(this);
    private transient IU iu = new IU(this);
    private transient Sonido música = new Sonido();
    private transient Sonido se = new Sonido();
    private AdministradorDeBloque adminBlock;
    private ColocadorDeObjetos colocador = new ColocadorDeObjetos(this);
    //jugador y Negocio.entidades
    private Jugador jugador = new Jugador(this, getControl());
    private Entidad[] frutas = new Entidad[20];
    private Entidad[] enemigos = new Entidad[10];
    private ArrayList<Entidad> entidades = new ArrayList<>();


    // estados de juego
    private EstadoDeJuego estadoActualDeJuego = EstadoDeJuego.NEUTRO;


    public PanelDeJuego() {
        this.setPreferredSize(new Dimension(getALTO(), getANCHO()));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(getControl());
        this.setFocusable(true);
    }

    public PanelDeJuego(Nivel nivel) {
        this.setNivel(nivel);
        setAdminBlock(new AdministradorDeBloque(this, nivel));
        this.setPreferredSize(new Dimension(getALTO(), getANCHO()));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(getControl());
        this.setFocusable(true);
    }

    public static int getFPS() {
        return FPS;
    }

    public void configurarJuego() {
        setEstadoActualDeJuego(EstadoDeJuego.TÍTULO);
        getColocador().colocarFrutas(getNivel().getRutaFrutas());
        getColocador().colocarEnemigos(getNivel().getRutaEnemigos());
        //colocador.colocarEnemigos();
        reproducirMúsica(5);

    }

    public void iniciarHiloDeJuego() {
        setHiloDeJuego(new Thread(this));
        getHiloDeJuego().start();
    }
    public void reintentar(){
        jugador.establecerPosiciónPredeterminada();
        getAdminBlock().cargarMapa();
        jugador.reestablecerVida();
        reestablecer();
        jugador.reestablecerFrutas();
        jugador.reestablecerPuntuación();
        getColocador().colocarFrutas(getNivel().getRutaFrutas());
        getColocador().colocarEnemigos(getNivel().getRutaEnemigos());
        getIu().resetearReloj();
    }

    public void reestablecer(){
        getAdminBlock().cargarMapa();
        jugador.establecerValoresPredeterminados();
        jugador.establecerPosiciónPredeterminada();
        jugador.reestablecerFrutas();
        jugador.reestablecerVida();
        getColocador().colocarEnemigos(getNivel().getRutaEnemigos());
        getColocador().colocarFrutas(getNivel().getRutaFrutas());
        getIu().resetearReloj();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / getFPS();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long temporizador = 0;
        int dibujarContar = 0;

        while (getHiloDeJuego() != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            temporizador += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                actualizar();
                repaint();
                delta--;
                dibujarContar++;
            }

            if (temporizador >= 1000000000) {
                System.out.println("FPS: " + dibujarContar);
                dibujarContar = 0;
                temporizador = 0;
            }

        }

    }

    public void actualizar() {
        if (getEstadoActualDeJuego() == EstadoDeJuego.JUEGO) {
            jugador.actualizar();

            for (Entidad enemigo : getEnemigos()) {
                if (enemigo != null) {
                    enemigo.actualizar();
                }
            }
        }

        if (getEstadoActualDeJuego() == EstadoDeJuego.PAUSA) {

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        //Titulo estado
        if (getEstadoActualDeJuego() == EstadoDeJuego.TÍTULO) {
            getIu().dibujar(g2);
        } else {
            //Bloques
            getAdminBlock().dibujar(g2);
            //Añadir al jugador a la lista de Negocio.entidades
            getEntidades().add(jugador);
            //agrega frutas a la lista de Negocio.entidades
            for (Entidad fruta : getFrutas()) {
                if (fruta != null) {
                    getEntidades().add(fruta);
                }
            }
            for (Entidad enemigo : getEnemigos()) {
                if (enemigo != null) {
                    getEntidades().add(enemigo);
                }
            }
            //ordenar
            Collections.sort(getEntidades(), new Comparator<Entidad>() {
                @Override
                public int compare(Entidad o1, Entidad o2) {
                    int resultado = Integer.compare(o1.getMundoY(), o2.getMundoY());
                    return resultado;
                }
            });
            //dibujar Negocio.entidades
            for (Entidad entidade : getEntidades()) {
                entidade.dibujar(g2);
            }
            //igualando la lista
            for (int i = 0; i < getEntidades().size(); i++) {
                getEntidades().remove(i);
            }
            //IU
            getIu().dibujar(g2);
            // g2.dispose();

        }
        if (jugador.getNúmeroDeFrutas() == 11) {
            setEstadoActualDeJuego(EstadoDeJuego.VICTORIA);
        }
        if (jugador.getVida() == 0) {
            setEstadoActualDeJuego(EstadoDeJuego.DERROTA);
        }
        //otros
    }

    public void crearBloqueHielo() {
        // Calcula el punto de inicio ajustando desde el centro del borde del área sólida en la dirección mirada
        int ajusteX = obtenerAjusteX();
        int ajusteY = obtenerAjusteY();

        int x = (jugador.getMundoX() + ajusteX) / getTAMAÑO_DE_BLOQUE();
        int y = (jugador.getMundoY() + ajusteY) / getTAMAÑO_DE_BLOQUE();

        Dirección dirección = jugador.getDireccion();

        while (true) {
            switch (dirección) {
                case ARRIBA:
                    y--;
                    break;
                case ABAJO:
                    y++;
                    break;
                case IZQUIERDA:
                    x--;
                    break;
                case DERECHA:
                    x++;
                    break;
            }

            if (x < 0 || y < 0 || x >= getMaxColDeMundo() || y >= getMaxFilasDeMundo() || esBloqueNoTransformable(getAdminBlock().getMapa()[x][y])) {
                break;
            }

            if (getAdminBlock().getMapa()[x][y] == 8) { // Si ya es un bloque de hielo, detener.
                break;
            }

            getAdminBlock().getMapa()[x][y] = 8; // Crear bloque de hielo.
        }
    }

    private boolean esBloqueNoTransformable(int bloque) {
        return bloque == 1 || bloque == 2 || bloque == 3 || bloque == 4 || bloque == 5;
    }

    public int obtenerAjusteX(){
        return getJugador().getDireccion().equals(Dirección.IZQUIERDA) ? getJugador().getÁreaSólida().x :
               getJugador().getDireccion().equals(Dirección.DERECHA) ? getJugador().getÁreaSólida().x + getJugador().getÁreaSólida().width :
               getJugador().getÁreaSólida().width / 2;
    }
    public int obtenerAjusteY(){
        return getJugador().getDireccion().equals(Dirección.ARRIBA) ? getJugador().getÁreaSólida().y :
               getJugador().getDireccion().equals(Dirección.ABAJO) ? getJugador().getÁreaSólida().y + getJugador().getÁreaSólida().height :
               getJugador().getÁreaSólida().height / 2;
    }

    public void romperBloqueHielo() {
        // Igual que el método crearBloqueHielo pero ajustando para romper
        int ajusteX = obtenerAjusteX();
        int ajusteY = obtenerAjusteY();

        int x = (getJugador().getMundoX() + ajusteX) / getTAMAÑO_DE_BLOQUE();
        int y = (getJugador().getMundoY() + ajusteY) / getTAMAÑO_DE_BLOQUE();
        Dirección dirección = getJugador().getDireccion();

        while (true) {
            switch (dirección) {
                case ARRIBA:
                    y--;
                    break;
                case ABAJO:
                    y++;
                    break;
                case IZQUIERDA:
                    x--;
                    break;
                case DERECHA:
                    x++;
                    break;
            }

            if (x < 0 || y < 0 || x >= getMaxColDeMundo() || y >= getMaxFilasDeMundo() || getAdminBlock().getMapa()[x][y] == 0 || getAdminBlock().getMapa()[x][y] == 6 || getAdminBlock().getMapa()[x][y] == 7) {
                break;
            }

            if (esBloqueNoTransformable(getAdminBlock().getMapa()[x][y])) {
                break;
            }

            getAdminBlock().getMapa()[x][y] = 0; // Eliminar bloque de hielo.
            reproducirSE(3);
        }
    }

    public void reproducirMúsica(int i) {
        música.colocarArchivo(i);
        música.reproducir();
        música.entrarEnBucle();
    }

    public void pararMúsica() {
        música.parar();
    }

    public void reproducirSE(int i) {
        se.colocarArchivo(i);
        se.reproducir();
    }

    public Sonido getMúsica() {
        return música;
    }
    public Sonido getSe(){
        return se;
    }

    public Control getControl() {
        return control;
    }

    public Configuración getConfiguración() {
        return configuración;
    }

    public int getTAMAÑO_BLOQUE_ORIGINAL() {
        return TAMAÑO_BLOQUE_ORIGINAL;
    }

    public int getESCALA() {
        return ESCALA;
    }

    public int getTAMAÑO_DE_BLOQUE() {
        return TAMAÑO_DE_BLOQUE;
    }

    public int getCOLUMNAS_MAX() {
        return COLUMNAS_MAX;
    }

    public int getFILAS_MAX() {
        return FILAS_MAX;
    }

    public int getALTO() {
        return ALTO;
    }

    public int getANCHO() {
        return ANCHO;
    }

    public int getMaxColDeMundo() {
        return maxColDeMundo;
    }

    public int getMaxFilasDeMundo() {
        return maxFilasDeMundo;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public GuardarCargar getGuardarCargar() {
        return guardarCargar;
    }

    public Thread getHiloDeJuego() {
        return hiloDeJuego;
    }

    public void setHiloDeJuego(Thread hiloDeJuego) {
        this.hiloDeJuego = hiloDeJuego;
    }

    public VerificadorDeColisión getCheckColisión() {
        return checkColisión;
    }

    public IU getIu() {
        return iu;
    }

    public AdministradorDeBloque getAdminBlock() {
        return adminBlock;
    }

    public void setAdminBlock(AdministradorDeBloque adminBlock) {
        this.adminBlock = adminBlock;
    }

    public ColocadorDeObjetos getColocador() {
        return colocador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Entidad[] getFrutas() {
        return frutas;
    }

    public void setFrutas(Entidad[] frutas) {
        this.frutas = frutas;
    }

    public Entidad[] getEnemigos() {
        return enemigos;
    }

    public ArrayList<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(ArrayList<Entidad> entidades) {
        this.entidades = entidades;
    }

    public EstadoDeJuego getEstadoActualDeJuego() {
        return estadoActualDeJuego;
    }

    public void setEstadoActualDeJuego(EstadoDeJuego estadoActualDeJuego) {
        this.estadoActualDeJuego = estadoActualDeJuego;
    }
}