package com.covid19.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.covid19.game.AssetsXogo;
import com.covid19.game.Covid19;
import com.covid19.modelo.Mundo;

public class PantallaInicial implements Screen, InputProcessor {

    private Covid19 xogo;
    private OrthographicCamera camara2D;
    private SpriteBatch spriteBatch;
    private BitmapFont info;

    public PantallaInicial(Covid19 _xogo) {

        this.xogo = _xogo;
        camara2D = new OrthographicCamera();
        spriteBatch = new SpriteBatch();

        resize(Mundo.TAMANO_MUNDO_ANCHO,Mundo.TAMANO_MUNDO_ALTO);

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        info = new BitmapFont();

        Gdx.input.setInputProcessor(this);

        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        // Debuxar Fondo
        spriteBatch.draw(AssetsXogo.imaxeFondoInicial, 0, 0, Mundo.TAMANO_MUNDO_ANCHO,Mundo.TAMANO_MUNDO_ALTO);

        // Escribir información
        info.setColor(Color.BLUE);
        info.getData().setScale(1.5f);
        info.draw(spriteBatch, "Inicialmente aparecerán 3 virus Covid-19, que", 35, 800);
        info.draw(spriteBatch, "irán infectando a su alrededor cada segundo.", 35, 760);
        info.draw(spriteBatch, "Deberás mover el desinfectador con las teclas.", 35, 700);
        info.draw(spriteBatch, "La superfice por donde pasa el desinfectador", 35, 640);
        info.draw(spriteBatch, "queda desinfectada durante 10 segundos.", 35, 600);
        info.draw(spriteBatch, "Pulsa en la pantalla para pausar o reiniciar.", 35, 540);
        spriteBatch.draw(AssetsXogo.imaxeVirus1, 35, 455, 32, 32);
        info.draw(spriteBatch, "Virus inmaduro. No infecta.", 75, 480);
        info.draw(spriteBatch, "Se vuelve maduro en 1 segundo.", 75, 440);
        spriteBatch.draw(AssetsXogo.imaxeVirus2, 35, 355, 32, 32);
        info.draw(spriteBatch, "Virus maduro. Infecta en 1 segundo.", 75, 380);
        spriteBatch.draw(AssetsXogo.imaxeDesinfectador, 35, 295, 32, 32);
        info.draw(spriteBatch, "Desinfectador.", 75, 320);
        info.draw(spriteBatch, "Pulsa para comenzar.", 150, 200);
        spriteBatch.draw(AssetsXogo.imaxeLogo, 97, 30, 354,70);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

        camara2D.setToOrtho(false, Mundo.TAMANO_MUNDO_ANCHO, Mundo.TAMANO_MUNDO_ALTO);
        camara2D.update();

        spriteBatch.setProjectionMatrix(camara2D.combined);

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

    }

    @Override
    public void dispose() {

        Gdx.input.setInputProcessor(null);

        spriteBatch.dispose();
        info.dispose();
        camara2D = null;

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Non importa donde pulsamos
        xogo.setScreen(new PantallaXogo(this.xogo));
        return false;
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
