package com.covid19.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.covid19.game.AssetsXogo;
import com.covid19.modelo.Mundo;

public class RenderXogo  {

    private Mundo meuMundo;
    private OrthographicCamera camara2D;
    private SpriteBatch spriteBatch;

    private BitmapFont vidasTexto;
    private BitmapFont porcentaxeInfeccion;
    private BitmapFont pausado;

    public RenderXogo(Mundo meuMundo) {

        this.meuMundo = meuMundo;

        camara2D = new OrthographicCamera();
        spriteBatch = new SpriteBatch();

        vidasTexto = new BitmapFont();
        porcentaxeInfeccion = new BitmapFont();
        pausado = new BitmapFont();

    }


    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
            // Debuxar Fondo
            spriteBatch.draw(AssetsXogo.imaxeFondo, 0, 0, Mundo.TAMANO_MUNDO_ANCHO,Mundo.TAMANO_MUNDO_ALTO);

            // Indicar vidas restantes
            vidasTexto.setColor(Color.BLACK);
            vidasTexto.getData().setScale(1.5f);
            vidasTexto.draw(spriteBatch, "VIDAS:", 10, 955);
            for (int i=0; i<Mundo.getVidas();i++) {
                spriteBatch.draw(AssetsXogo.imaxeDesinfectador, 100+i*26, 930, 25, 25);
            }

            // Informar da superficie infectada con distintas cores
            // segundo a porcentaxe de infección
            if (meuMundo.getSuperficieInfectada() <= 30) {
                porcentaxeInfeccion.setColor(Color.GREEN);
            } else if (meuMundo.getSuperficieInfectada() >= 60) {
                porcentaxeInfeccion.setColor(Color.RED);
            } else {
                porcentaxeInfeccion.setColor(Color.YELLOW);
            }

            porcentaxeInfeccion.getData().setScale(1.5f);
            porcentaxeInfeccion.draw(spriteBatch, meuMundo.getSuperficieInfectada() + "% infectado", 250,955);

            // Debuxar virus
            debuxarVirus();

            // Debuxar desinfectador
            spriteBatch.draw(AssetsXogo.imaxeDesinfectador, Mundo.desinfectador.getPosicion().x*32+31, Mundo.desinfectador.getPosicion().y*32+131, 32, 32);

            if (Mundo.pausa) {

                // se o xogo está pausado, indicámolo cunha imaxe e texto
                spriteBatch.draw(AssetsXogo.imaxePlay,121,381, 300,300);
                pausado.setColor(Color.YELLOW);
                pausado.getData().setScale(3f);
                pausado.draw(spriteBatch, "JUEGO EN PAUSA", 80, 600);
                pausado.getData().setScale(2f);
                pausado.draw(spriteBatch, "pulsa para continuar", 140, 500);
            }

        spriteBatch.end();

    }

    // Método para debuxar os virus presentes
    // Repasamos o array comprobando se ten un 1 (virus inmaduro) ou 2/3 (virus maduro)
    private void debuxarVirus() {

        for (int x=0; x<15;x++) {
            for (int y=0; y<25; y++) {

                if (Mundo.getVirus()[x][y] == 1) {
                    // virus inmaduro
                    spriteBatch.draw(AssetsXogo.imaxeVirus1, x*32+31, y*32+131, 32, 32);
                } else if (Mundo.getVirus()[x][y] >= 2) {
                    // virus maduro
                    spriteBatch.draw(AssetsXogo.imaxeVirus2, x*32+31, y*32+131, 32, 32);
                }
            }
        }
    }

    public OrthographicCamera getCamara2D() {
        return camara2D;
    }

    public void resize(int ancho, int alto) {

        camara2D.setToOrtho(false, Mundo.TAMANO_MUNDO_ANCHO, Mundo.TAMANO_MUNDO_ALTO);
        camara2D.update();

        spriteBatch.setProjectionMatrix(camara2D.combined);
    }

    public void dispose() {

        Gdx.input.setInputProcessor(null);
        vidasTexto.dispose();
        spriteBatch.dispose();
        pausado.dispose();
        camara2D = null;

    }

}
