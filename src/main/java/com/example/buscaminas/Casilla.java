package com.example.buscaminas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author
 */

public class Casilla  extends Button {
    private int posFila;
    private int posColumna;
    private boolean esMina = false;
    private int MinasAlrededor;
    private boolean estaAbierta;

    public Casilla(int posFila, int posColumna){
        this.posFila = posFila;
        this.posColumna = posColumna;
    }

    public boolean isMina() {
        return esMina;
    }

    public void ponerMina() {
        this.esMina = true;
    }

    public boolean isAbierta(){
        return this.estaAbierta;
    }

    public void abrir(){
        this.estaAbierta = true;
    }

    public int getNumMinasAlrededor() {
        return MinasAlrededor;
    }

    public void setMinasAlrededor(int MinasAlrededor) {
        this.MinasAlrededor = MinasAlrededor;
    }}

