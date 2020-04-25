package com.covid19.modelo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Mundo {

    // establecemos ancho e alto
    public static final int TAMANO_MUNDO_ANCHO = 540;
    public static final int TAMANO_MUNDO_ALTO = 960;

    public static Desinfectador desinfectador;

    private static int [][] virus;  //matriz de cuadrículas do xogo
    private static int numVidas;
    private String resultado;
    public static Boolean pausa;
    private static final Vector2 POSINICIAL = new Vector2(7,0); // posición inicial do desinfectador

    public Mundo(int vidas) {

        numVidas = vidas;
        resultado = "";
        desinfectador = new Desinfectador();
        iniciarXogo();

    }

    public void iniciarXogo() {

        pausa = false;
        iniciarVirus(3);
        desinfectador.setPosicion(new Vector2 (POSINICIAL));

    }

    // creamos aleatoriamente as posicións dos virus
    private void iniciarVirus(int cantidade) {
        virus = new int[15][25];
        for (int x=0;x<15;x++) {
            for (int y=0; y<25;y++) {
                virus[x][y] = 0;
            }
        }

        Random rnd = new Random();
        int x, y, intentos;

        for (int i=0;i<cantidade;i++) {

            x = rnd.nextInt(15);
            y= rnd.nextInt(24) + 1; //non permitimos virus na fila do desinfectador

            intentos = 0;
            while (virus[x][y] == 1 && intentos < 5) {
                x = rnd.nextInt(15);
                y= rnd.nextInt(24) + 1; //non permitimos virus na fila do desinfectador
                intentos++;
            }

            virus[x][y] = 1;

        }

    }

    public static int[][] getVirus() {
        return virus;
    }

    public static void desinfectar() {
        virus[(int) desinfectador.getPosicion().x][(int) desinfectador.getPosicion().y] = -10;
    }

    public static void restarVida() { numVidas--;}

    public static int getVidas() { return numVidas;}

    public void setResultado(String result) {this.resultado = result;}

    public String getResultado() { return this.resultado;}

    public void pausarXogo(Boolean sinon) {
        pausa = sinon;
    }

    public Boolean isPausado() {return pausa;}

}
