package com.covid19.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetsXogo {

    // Gráficos do xogo
    public static Texture   imaxeFondo,
                            imaxeFondoInicial,
                            imaxeVirus1,
                            imaxeVirus2,
                            imaxeDesinfectador,
                            imaxePlay,
                            imaxeLogo;


    // Sons do xogo
    public static Sound sonInfeccion,
                        sonDesinfeccion,
                        sonPerdedor,
                        sonGanador,
                        sonInicio;

    // Método para cargar os gráficos
    public static void cargarTexturas() {

        imaxeFondo = new Texture(Gdx.files.internal("fondobotonslogo.png"));
        imaxeFondoInicial = new Texture(Gdx.files.internal("fondo.png"));
        imaxeVirus1 = new Texture(Gdx.files.internal("covid19-1.png"));
        imaxeVirus2 = new Texture(Gdx.files.internal("covid19-2.png"));
        imaxeDesinfectador = new Texture(Gdx.files.internal("desinfectador.png"));
        imaxePlay = new Texture(Gdx.files.internal("play.png"));
        imaxeLogo = new Texture(Gdx.files.internal("logo.png"));

    }

    // Método para cargar sons
    public static void cargarSons() {
        sonInicio = Gdx.audio.newSound(Gdx.files.internal("novavida.mp3"));
        sonInfeccion = Gdx.audio.newSound(Gdx.files.internal("infeccion.mp3"));
        sonDesinfeccion = Gdx.audio.newSound(Gdx.files.internal("desinfeccion.mp3"));
        sonPerdedor = Gdx.audio.newSound(Gdx.files.internal("perdedor.mp3"));
        sonGanador= Gdx.audio.newSound(Gdx.files.internal("ganador.mp3"));

    }

    // Método para liberar gráficos
    public static void liberarTexturas() {

        imaxeFondo.dispose();
        imaxeFondoInicial.dispose();
        imaxeVirus1.dispose();
        imaxeVirus2.dispose();
        imaxeDesinfectador.dispose();

    }

    // Método para liberar sons
    public static void liberarSons() {
        sonInicio.dispose();
        sonInfeccion.dispose();
        sonDesinfeccion.dispose();
        sonPerdedor.dispose();
        sonGanador.dispose();
    }
}
