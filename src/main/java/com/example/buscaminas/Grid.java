package com.example.buscaminas;


public class Grid {
    public Casilla[][] casillaTablero;

    int numFilas;
    int numColumnas;
    int numMinas;

    /**
     * teblero
     * @param numFilas
     * @param numColumnas
     * @param numMinas
     */
    public Grid(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = 8;
        this.numColumnas = 8;
        this.numMinas = numMinas;
        inicializar();

    }

    /**
     * inicializa el tablero
     */
    public void inicializar() {
        casillaTablero = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillaTablero.length; i++) {
            for (int j = 0; j < casillaTablero[i].length; j++) {
                casillaTablero[i][j] = new Casilla(i, j);
            }
        }
        ponerMinas();
    }

    /**
     * coloca las minas de manera aleatoria
     */
    private void ponerMinas() {
        int cantMinas = 0;
        while (cantMinas != numMinas) {
            int posTmpFila = (int) (Math.random() * casillaTablero.length);
            int posTmpCol = (int) (Math.random() * casillaTablero[0].length);
            if (!casillaTablero[posTmpFila][posTmpCol].isMina()) {
                casillaTablero[posTmpFila][posTmpCol].ponerMina();
                cantMinas++;
            }
        }

    }

    /**
     *
     * @param fila
     * @param columna
     */
    public void revelarCeldasSinPistas(int fila, int columna){

        if (fila < 0 || columna < 0 || fila >= 8 || columna >= 8) {  //Si está fuera del tablero retorne nada
            return;
        }

        if (casillaTablero[fila][columna].isAbierta()) {  //Si la celda fue relevada salga de la ejecucion
            return;
        }

        if (casillaTablero[fila][columna].getNumMinasAlrededor() != 0) {  //Si la casilla tiene pistas, termine la ejecución


            casillaTablero[fila][columna].abrir();
            casillaTablero[fila][columna].setText(casillaTablero[fila][columna].getNumMinasAlrededor()+"");
            casillaTablero[fila][columna].setDisable(true);
            return;
        }

        casillaTablero[fila][columna].abrir();  //Se marca la celda como revelada
        casillaTablero[fila][columna].setDisable(true);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                revelarCeldasSinPistas(fila + i, columna + j);
            }
        }

    }


    /**
     * posicion de la casilla
     * @param i
     * @param j
     * @return
     */
    public boolean pos(int i ,int j){
        if(i>=0&&j>=0&&i<=7&&j<=7){
            return true;
        }else {
            return false;
        }
    }


    /**
     * MINAS adyacentes
     */
    public void generarNumAdy() {
        for (int i=0; i<=7; i++) {
            for (int j=0; j<=7; j++) { // indices para recorrer la matriz
                int contador = 0;
                if (!casillaTablero[i][j].isMina()) {
                    if (pos(i-1, j-1)) {
                        if (casillaTablero[i-1][j-1].isMina()) { // arriba  izquierda
                            contador++;
                        }
                    }
                    if (pos(i-1, j)) {
                        if (casillaTablero[i-1][j].isMina()) { //arriba
                            contador++;
                        }
                    }
                    if (pos(i-1, j+1)) {
                        if (casillaTablero[i-1][j+1].isMina()) { //arriba derecha
                            contador++;
                        }
                    }
                    if (pos(i, j-1)) {
                        if (casillaTablero[i][j-1].isMina()) { //izquierda
                            contador++;
                        }
                    }
                    if (pos(i, j+1)) {
                        if (casillaTablero[i][j+1].isMina()) { //derecha
                            contador++;
                        }
                    }
                    if (pos(i+1, j-1)) {
                        if (casillaTablero[i+1][j-1].isMina()) { // abajo  izquierda
                            contador++;
                        }
                    }
                    if (pos(i+1, j)) {
                        if (casillaTablero[i+1][j].isMina()) { // abajo
                            contador++;
                        }
                    }
                    if (pos(i+1, j+1)) {
                        if (casillaTablero[i+1][j+1].isMina()) { // abajo  derecha
                            contador++;
                        }
                    }
                    casillaTablero[i][j].setMinasAlrededor(contador);
                }
                else if (casillaTablero[i][j].isMina()){
                    casillaTablero[i][j].setMinasAlrededor(-1); // se le asigna un -1 para representar que hay una mina
                }
            }
        }
    }


}