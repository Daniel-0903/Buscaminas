package com.example.buscaminas;


public class Grid {
    public Casilla[][] casillaTablero;

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
        casillaTablero = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillaTablero.length; i++) {
            for (int j = 0; j < casillaTablero[i].length; j++) {
                casillaTablero[i][j] = new Casilla(i, j);
            }
        }
        ponerMinas();
    }

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

    public void revelarCeldasSinPistas(int fila, int columna){

        if (fila < 0 || columna < 0 || fila >= 8 || columna >= 8) {  //Si está fuera del tablero retorne nada
            System.out.println("termina1");
            return;
        }

        if (casillaTablero[fila][columna].isAbierta()) {  //Si la celda fue relevada salga de la ejecucion
            System.out.println("termina2");
            return;
        }

        if (casillaTablero[fila][columna].getNumMinasAlrededor() != 0) {  //Si la casilla tiene pistas, termine la ejecución


            casillaTablero[fila][columna].abrir();
            casillaTablero[fila][columna].setText(casillaTablero[fila][columna].getNumMinasAlrededor()+"");
            casillaTablero[fila][columna].setDisable(true);
            System.out.println("termina3");
            return;
        }

        casillaTablero[fila][columna].abrir();  //Se marca la celda como revelada
        casillaTablero[fila][columna].setStyle("-fx-background-color: #FFFF00");  //Se le asigna un color
        casillaTablero[fila][columna].setDisable(true);
        System.out.println("termina4");

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                revelarCeldasSinPistas(fila + i, columna + j);
            }
        }
        //System.out.println("tambien entra aqui");

    }




    public boolean pos(int i ,int j){
        if(i>=0&&j>=0&&i<=7&&j<=7){
            return true;
        }else {
            return false;
        }
    }


    /**
     * Se generan los numeros de minas
     * adyacentes y se agregan a la variable
     * numrev en la matriz de valores. Esto
     * se hace para todos los espacios en la matriz.
     */
    public void generarNumAdy() { // genera los numeros de minas adyacentes y los añade a matrizvalores.numrev
        for (int i=0; i<=7; i++) {
            for (int j=0; j<=7; j++) { // indices para recorrer la matriz
                int contador = 0;
                if (!casillaTablero[i][j].isMina()) { // si no hay una mina en el espacio
                    if (pos(i-1, j-1)) { // si el espacio de arriba a la izquierda no se sale del arreglo
                        if (casillaTablero[i-1][j-1].isMina()) { // si el espacio de arriba a la izquierda es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i-1, j)) { // si el espacio de arriba no se sale del arreglo
                        if (casillaTablero[i-1][j].isMina()) { // si el espacio de arriba es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i-1, j+1)) { // si el espacio de arriba a la derecha no se sale del arreglo
                        if (casillaTablero[i-1][j+1].isMina()) { // si el espacio de arriba a la derecha es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i, j-1)) { // si el espacio de la izquierda no se sale del arreglo
                        if (casillaTablero[i][j-1].isMina()) { // si el espacio de la izquierda es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i, j+1)) { // si el espacio de la derecha no se sale del arreglo
                        if (casillaTablero[i][j+1].isMina()) { // si el espacio de la derecha es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i+1, j-1)) { // si el espacio de abajo a la izquierda no se sale del arreglo
                        if (casillaTablero[i+1][j-1].isMina()) { // si el espacio de abajo a la izquierda es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i+1, j)) { // si el espacio de abajo no se sale del arreglo
                        if (casillaTablero[i+1][j].isMina()) { // si el espacio de abajo es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    if (pos(i+1, j+1)) { // si el espacio de abajo a la derecha no se sale del arreglo
                        if (casillaTablero[i+1][j+1].isMina()) { // si el espacio de abajo a la derecha es una mina
                            contador++; // se aumenta el contador
                        }
                    }
                    casillaTablero[i][j].setMinasAlrededor(contador); // se asigna el contador a la variable numrev en la matriz de valores
                }
                else if (casillaTablero[i][j].isMina()){ // si el espacio es una mina
                    casillaTablero[i][j].setMinasAlrededor(-333); // se le asigna un -333 para representar que hay una mina
                }
            }
        }
    }


}