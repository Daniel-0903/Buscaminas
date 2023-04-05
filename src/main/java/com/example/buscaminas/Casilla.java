package com.example.buscaminas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author
 */

public class Casilla {
    int posFila;
    int posColumna;
    boolean mina;
    int MinasAlrededor;
    boolean estaAbierta;
    boolean estaMarcada;
    public Casilla(int posFila, int posColumna){
        this.posFila = posFila;
        this.posColumna = posColumna;
        this.mina = false;
        this.MinasAlrededor = 0;
        this.estaAbierta = false;
        this.estaMarcada = false;
    }

    public Casilla() {

    }


    public int getPosFila(){
        return posFila;
    }

    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getMinasAlrededor() {
        return MinasAlrededor;
    }

    public void setMinasAlrededor(int minasAlrededor) {
        this.MinasAlrededor = minasAlrededor;
    }
    public void incrementarMinasAlrededor(){
        this.MinasAlrededor++;
    }
    public boolean estaAbierta() {
        return estaAbierta;
    }

    public void setEstaAbierta(boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
    }

    public boolean estaMarcada() {
        return estaMarcada;
    }

    public void setEstaMarcada(boolean estaMarcada) {
        this.estaMarcada = estaMarcada;
    }
}

