package com.example.buscaminas;

import javafx.scene.control.Button;

public class Nodo {
    private Button casilla;
    private Nodo siguiente;

    public Nodo(Button casilla) {
        this.casilla = casilla;
        this.siguiente = null;
    }

    public Button getCasilla() {
        return casilla;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
