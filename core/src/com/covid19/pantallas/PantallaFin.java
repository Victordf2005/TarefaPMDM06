package com.covid19.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.covid19.game.AssetsXogo;
import com.covid19.modelo.Mundo;

public class PantallaFin implements Screen {

    private OrthographicCamera camara2D;
    private SpriteBatch spriteBatch;
    private Texture fogos;
    private Texture perdedor;
    private BitmapFont info;
    private String resultado;

    public PantallaFin(String resultado) {

        this.resultado = resultado;
        camara2D = new OrthographicCamera();
        spriteBatch = new SpriteBatch();
        fogos = new Texture(Gdx.files.internal("ganador.png"));
        perdedor = new Texture(Gdx.files.internal("perdedor.png"));

        resize(Mundo.TAMANO_MUNDO_ANCHO,Mundo.TAMANO_MUNDO_ALTO);

        if (resultado.equals("G")) {
            AssetsXogo.sonGanador.play();
        } else {
            AssetsXogo.sonPerdedor.play();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        info = new BitmapFont();

        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        if (resultado.equals("G")) {

            // Debuxar Fondo
            spriteBatch.draw(fogos, 0, 0, Mundo.TAMANO_MUNDO_ANCHO, Mundo.TAMANO_MUNDO_ALTO);

            // Escribir información
            info.setColor(Color.RED);
            info.getData().setScale(3f);
            info.draw(spriteBatch, "¡¡ENHORABUENA!!", 85, 500);
            info.getData().setScale(2f);
            info.draw(spriteBatch, "¡Has eliminado al coronavirus!", 75, 400);

        } else {

            // Debuxar Fondo
            spriteBatch.draw(perdedor, 0, 0, Mundo.TAMANO_MUNDO_ANCHO, Mundo.TAMANO_MUNDO_ALTO);

            // Escribir información
            info.setColor(Color.RED);
            info.getData().setScale(3f);
            info.draw(spriteBatch, "LO SENTIMOS", 100, 600);
            info.getData().setScale(2f);
            info.draw(spriteBatch, "No has vencido al coronavirus.", 60, 500);

        }

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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        spriteBatch.dispose();
        fogos.dispose();
        perdedor.dispose();
        info.dispose();
        resultado = null;
        camara2D = null;
    }

}
