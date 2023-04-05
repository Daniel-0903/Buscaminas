package com.example.buscaminas;

import java.util.LinkedList;
import java.util.List;

import static javafx.application.Application.launch;

public class gridint {
    static int[][] casillas;

    int numFilas;
    int numColumnas;
    int numMinas;

    public gridint(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = 8;
        this.numColumnas = 8;
        this.numMinas = numMinas;
        inicializar();

    }

    public gridint() {

    }

    public void inicializar() {
        casillas = new int[this.numFilas][this.numColumnas];
        int[][] minas = ponerMinas();

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (contiene(minas, i, j)) {
                    casillas[i][j] = -1;
                } else {
                    casillas[i][j] = 0;
                }
            }
        }
    }
    private boolean contiene(int[][] matriz, int fila, int columna) {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][0] == fila && matriz[i][1] == columna) {
                return true;
            }
        }
        return false;
    }


    private int[][] ponerMinas() {
        int[][] minas = new int[numMinas][2];
        int cantMinas = 0;

        while (cantMinas != numMinas) {
            int posTmpFila = (int) (Math.random() * casillas.length);
            int posTmpCol = (int) (Math.random() * casillas[0].length);
            if (casillas[posTmpFila][posTmpCol] != -1) {
                casillas[posTmpFila][posTmpCol] = -1;
                minas[cantMinas][0] = posTmpFila;
                minas[cantMinas][1] = posTmpCol;
                cantMinas++;
            }
        }

        actualizarMinas();
        return minas;
    }


    public void imprimir() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j] == -1 ? "*" : casillas[i][j]);
            }
            System.out.println("");
        }
    }

    public void imprimirAlrededor() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j]);
            }
            System.out.println("");
        }
    }

    private void actualizarMinas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j] == -1) {
                    for (int k = Math.max(i - 1, 0); k <= Math.min(i + 1, casillas.length - 1); k++) {
                        for (int l = Math.max(j - 1, 0); l <= Math.min(j + 1, casillas[k].length - 1); l++) {
                            if (casillas[k][l] != -1) {
                                casillas[k][l]++;
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Integer[]> minasAlrededor(int posFila, int posCol) {
        List<Integer[]> listaCasillas = new LinkedList<>();
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
                Integer[] pos = {tmpPosFila, tmpPosCol};
                listaCasillas.add(pos);
            }
        }
        return listaCasillas;
    }
    public int[][] getCasillas() {
        return casillas;
    }

    public static void main(String[] args) {
        launch(args);
        gridint grid1=new gridint(8, 8, 8);
        grid1.imprimir();
        System.out.println("------");
        grid1.imprimirAlrededor();
    }
}