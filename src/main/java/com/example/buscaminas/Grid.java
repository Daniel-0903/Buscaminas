package com.example.buscaminas;

import javafx.event.ActionEvent;

import java.util.LinkedList;
import java.util.List;

public class Grid {
    static Casilla[][] casillas;

    int numFilas;
    int numColumnas;
    int numMinas;

    public Grid(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = 8;
        this.numColumnas = 8;
        this.numMinas = numMinas;
        inicializar();

    }

    public void inicializar() {
        casillas = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
        ponerMinas();
    }

    private void ponerMinas() {
        int cantMinas = 0;
        while (cantMinas != numMinas) {
            int posTmpFila = (int) (Math.random() * casillas.length);
            int posTmpCol = (int) (Math.random() * casillas[0].length);
            if (!casillas[posTmpFila][posTmpCol].isMina()) {
                casillas[posTmpFila][posTmpCol].setMina(true);
                cantMinas++;
            }
        }
        actualizarMinas();
    }

    public void imprimir() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina() ? "*" : "0");
            }
            System.out.println("");
        }
    }

    public void imprimirAlrededor() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getMinasAlrededor());
            }
            System.out.println("");
        }
    }

    private void actualizarMinas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    List<Casilla> casillasAlrededor = minasAlrededor(i, j);
                    casillasAlrededor.forEach((c) -> c.incrementarMinasAlrededor()); //Aplicar metodo a cada una de las casillas
                }
            }
        }

    }

    private List<Casilla> minasAlrededor(int posFila, int posCol) {
        List<Casilla> listaCasillas = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosCol = posCol;

            switch (i) {
                case 0:
                    tmpPosFila--;
                    break;                //Arriba
                case 1:
                    tmpPosFila--;
                    tmpPosCol++;
                    break;   //Arriba Derecha
                case 2:
                    tmpPosCol++;
                    break;                 //Derecha
                case 3:
                    tmpPosCol++;
                    tmpPosFila++;
                    break;    //Derecha Abajo
                case 4:
                    tmpPosFila++;
                    break;                //Abajo
                case 5:
                    tmpPosFila++;
                    tmpPosCol--;
                    break;    //Abajo Izquierda
                case 6:
                    tmpPosCol--;
                    break;                 //Izquierda
                case 7:
                    tmpPosFila--;
                    tmpPosCol--;
                    break;                //Izquierda Abajo
            }
            if (tmpPosFila >= 0 && tmpPosFila < this.casillas.length && tmpPosCol >= 0 && tmpPosCol < this.casillas[0].length) {
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosCol]);
            }
        }
        return listaCasillas;
    }


}