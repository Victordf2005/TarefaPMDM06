package com.covid19.controlador;

import com.badlogic.gdx.utils.Timer;
import com.covid19.game.AssetsXogo;
import com.covid19.modelo.Mundo;

public class ControladorXogo {

    private static Mundo meuMundo;

    private static Timer.Task infectar;

    private int numVirus;
    private boolean infeccion;
    private int numVirusMaduros;

    // Método que crea unha tarefa a executar cada segundo
    // Esta tarefa encárgase chamar ao método para infectar celdas contiguas a virus maduros
    public  void iniciarInfeccion(float retardo) {

        infectar = new Timer.Task(){

            @Override
            public void run() {
                //meuMundo.infectar();
                infectarAredor();
            }
        };

        Timer.schedule(infectar,retardo,1f);
    }

    public ControladorXogo(Mundo meuMundo) {
        this.meuMundo = meuMundo;
        AssetsXogo.sonInicio.play();
        iniciarInfeccion(2f);
    }

    public void update(float delta) {

    }

    public void pararInfeccion() {
        infectar.cancel();
    }

    /* Método que vai infectando a superficie do xogo segundo a posición e madurez dos virus
    * Número negativo -> posición desinfectada. O número indica os segundos que lle quedan de protección
    * 0 -> posición libre, pero infectable.
    * 1 -> posición dun virus inmuaduro.
    * 2 ou 3 -> Posición dun virus maduro.
    */
    private void infectarAredor() {

            // Repasamos toda a cuadrícula para ver posicións infectables

            numVirus = 0;
            numVirusMaduros = 0;
            infeccion = false;

            for (int x=0; x<15; x++) {
                for (int y=0; y<25;y++) {

                    if (meuMundo.getVirus()[x][y] < 0) {

                        // Superfice desinfectada, perde poder anticontaxio
                        meuMundo.getVirus()[x][y] = meuMundo.getVirus()[x][y] + 1;

                    } else if (meuMundo.getVirus()[x][y] == 0) {

                        //comprobamos se está a carón dun virus maduro
                        // arriba ou á dereita pode ter '2' ou '3'
                        // abaixo ou esquera debe ter '3' xa que se ten '2' acaba de 'madurar'
                        if (meuMundo.getVirus()[Math.max(0,x-1)][y] == 3
                                || meuMundo.getVirus()[Math.min(14,x+1)][y] == 2
                                || meuMundo.getVirus()[x][Math.max(0,y-1)] == 3
                                || meuMundo.getVirus()[x][Math.min(24,y+1)] == 2) {

                            // infectada con virus inmaduro
                            meuMundo.getVirus()[x][y] = 1;
                            numVirus++;
                            infeccion = true;
                        }

                    } else if (meuMundo.getVirus()[x][y] == 1 || meuMundo.getVirus()[x][y] == 2) {

                        // virus madura
                        meuMundo.getVirus()[x][y]++;

                        // comprobamos se é a posición do desinfectador.
                        if (meuMundo.desinfectador.getPosicion().x == x
                                && meuMundo.desinfectador.getPosicion().y == y) {

                            // o desinfectador resultou infectado.
                            // paramos a infección e rematamos esta partida
                            pararInfeccion();
                            rematarPartida();
                        }
                        numVirus++;
                        numVirusMaduros++;
                    } else {
                        numVirus++;
                        numVirusMaduros++;
                    }
                }
            }

        // se houbo algunha celda infectada, provocamos son
        if (infeccion) {AssetsXogo.sonInfeccion.play();}

        //Pasamos información da superficie infectada
        meuMundo.setSuperficieInfectada((float) (numVirusMaduros / 3));   // porcentaxe = numVirusMaduros * 100 / 300

        // se xa non quedan virus, rematamos o xogo gañando
        if (numVirus == 0) {
            finXogo("G");
        } else if (numVirusMaduros >= 255) {
            // Superficie infectada en 85% ou superior
            // paramos a infección e rematamos esta partida
            pararInfeccion();
            rematarPartida();
        }
    }

    // Método que resta unha vida e comproba se xa non lle quedan vidas
    private  void rematarPartida()  {

        meuMundo.restarVida();

        if (meuMundo.getVidas() > 0) {

            // Aínda quedan vidas, provocamos son de nova partida e iniciamos xogo
            AssetsXogo.sonInicio.play();
            meuMundo.iniciarXogo();
            iniciarInfeccion(2f);

        } else {

            // Non quedan vidas, rematamos o xogo perdendo
            finXogo("P");
        }
    }

    // Método para rematar o xogo
    private void finXogo(String resultado) {

        pararInfeccion();
        meuMundo.setResultado(resultado);   // Informamos do resultado: Gañador ou Perdedor

    }

}
