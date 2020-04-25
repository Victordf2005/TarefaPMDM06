package com.covid19.modelo;

import com.badlogic.gdx.math.Vector2;
import com.covid19.game.AssetsXogo;

public class Desinfectador {

    private Vector2 posicion;

    public Desinfectador() {

    }

    public Desinfectador(Vector2 posicion) {
        this.posicion = posicion;
    }


    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    // Cambiamos a posición do desinfectador
    public void mover(Character direccion) {
        switch (direccion) {
            case 'E':   //esquerda
                if (posicion.x > 0) posicion.x--;
                break;
            case 'D':   //dereita
                if (posicion.x < 14) posicion.x++;
                break;
            case 'A':   //arriba
                if (posicion.y < 24) posicion.y++;
                break;
            case 'B':   //abaixo
                if (posicion.y >0 ) posicion.y--;
        }

        AssetsXogo.sonDesinfeccion.play();

    }

}
