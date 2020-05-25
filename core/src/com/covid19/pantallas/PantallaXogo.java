package com.covid19.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.covid19.controlador.ControladorXogo;
import com.covid19.game.Covid19;
import com.covid19.modelo.Mundo;
import com.covid19.render.RenderXogo;

public class PantallaXogo implements Screen, InputProcessor {

    private Covid19 xogo;
    private RenderXogo renderXogo;
    private ControladorXogo controladorXogo;

    Mundo meuMundo;

    public PantallaXogo(Covid19 _xogo) {

        // Iniciamos o xogo con 3 vidas
        meuMundo = new Mundo(3);

        this.xogo = _xogo;
        renderXogo = new RenderXogo(meuMundo);
        controladorXogo = new ControladorXogo(meuMundo);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        renderXogo.render(delta);

        if (!meuMundo.isPausado()) {

            controladorXogo.update(delta);

            if (!meuMundo.getResultado().equals("")) {
                // Hai resultado (gañador ou perdedor) pasamos á pantalla fin
                xogo.setScreen(new PantallaFin(meuMundo.getResultado()));
            }
        }

    }

    @Override
    public void resize(int ancho, int alto) {

        renderXogo.resize(ancho, alto);
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        xogo.dispose();
        renderXogo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // desinfectamos a celda á que se moveu
        // aínda que nos se movera, por estar en calquera dos límites, desinfectamos a celda actual
        switch (keycode) {
            case 19:    //Arriba
                Mundo.desinfectador.mover('A');
                meuMundo.desinfectar();
                break;
            case 20:    //Abaixo
                Mundo.desinfectador.mover('B');
                meuMundo.desinfectar();
                break;
            case 21:    //Esquerda
                Mundo.desinfectador.mover('E');
                meuMundo.desinfectar();
                break;
            case 22:    //Dereita
                Mundo.desinfectador.mover('D');
                meuMundo.desinfectar();
                break;
            case 44:    //Pausa
                pausarSiNon();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 posTocado = new Vector3(screenX,screenY,0);
        renderXogo.getCamara2D().unproject(posTocado);

        // Comprobamos a posición da pantalla na que tocou o sogador
        if (posTocado.y >= 30 && posTocado.y <= 100 && !meuMundo.isPausado()) { // zona de botóns co xogo funcionando
            if (posTocado.x > 30 && posTocado.x <= 130) Mundo.desinfectador.mover('E');     // botón de mover á esquerda
            if (posTocado.x > 130 && posTocado.x <= 230) Mundo.desinfectador.mover('A');    // botón de mover arriba
            if (posTocado.x > 310 && posTocado.x <= 410) Mundo.desinfectador.mover('B');    // botón de mover abaixo
            if (posTocado.x > 410 && posTocado.x <= 510) Mundo.desinfectador.mover('D');    // botón de mover á dereita

            // desinfectamos a celda á que se moveu
            // aínda que nos se movera, por estar en calquera dos límites, desinfectamos a celda actual
            meuMundo.desinfectar();

        } else if (posTocado.x > 0 && posTocado.x < 560 && posTocado.y > 130 && posTocado.y < 960) {

            pausarSiNon();
        }

        return false;
    }

    private void pausarSiNon() {

        // pulsou na zona de xogo.
        // basculamos entre pausado e non pausado
        meuMundo.pausarXogo(!meuMundo.isPausado());

        if (meuMundo.isPausado()) {

            // se o xogo resulta pausado, paramos a infección
            controladorXogo.pararInfeccion();
        } else {

            // se o xogo se volve a activar, iniciamos a infección
            controladorXogo.iniciarInfeccion(1f);
        }
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
