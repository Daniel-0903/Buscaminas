package com.example.buscaminas;

import javafx.scene.control.Button;
public class Nodo {
    private int[] data;
    private Nodo next;
    private boolean estaSeguro;
    private boolean esPosible;
    int x;
    int y;



    public Nodo(int[] data) {
        this.x = data[1];
        this.y = data[0];
        this.next = null;
        this.data = data;
        this.estaSeguro = false;
        this.esPosible = false;
        System.out.println("Messi Messi Messi Messi Messi Messi Messi Messi");
    }


    public int[] getData() {
        return this.data;
    }



    public Nodo getNext() {
        return this.next;
    }


    public void setNext(Nodo node) {
        this.next = node;
    }


    public int get_X(){
        return this.x;
    }


    public int get_Y(){
        return this.y;
    }


    public void setEsSeguro(){
        this.estaSeguro = true;
    }


    public boolean get_EsSeguro(){
        return this.estaSeguro;
    }


    public void setEsPosible(){
        this.esPosible = true;
    }


    public boolean get_EsPosible(){
        return this.esPosible;
    }

}