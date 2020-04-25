package com.covid19.game;

import com.badlogic.gdx.Game;
import com.covid19.pantallas.PantallaInicial;

public class Covid19 extends Game {

    @Override
    public void create() {

        // Cargamos gráficos e sons
        AssetsXogo.cargarTexturas();
        AssetsXogo.cargarSons();

        // Pasamos control á pantalla inicial
        setScreen(new PantallaInicial(this));

    }

    @Override
    public void dispose() {

        super.dispose();

        // Liberamos gráficos e sonidos
        AssetsXogo.liberarTexturas();
        AssetsXogo.liberarSons();

    }

}
