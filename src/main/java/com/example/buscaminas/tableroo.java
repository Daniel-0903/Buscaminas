package com.example.buscaminas;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

import static javafx.application.Application.launch;

public class tableroo extends Application {
    static int[][] casillas;

    int numFilas;
    int numColumnas;
    int numMinas;
    //private boolean perdio = false; // Variable que indica si el usuario ha perdido
  //  private int casillasRestantes = casillas.length * casillas[0].length - 10; // Variable que lleva la cuenta de las casillas restantes por descubrir

    public tableroo(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = 8;
        this.numColumnas = 8;
        this.numMinas = numMinas;
        inicializar();

    }

    public tableroo() {

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
    private boolean perdio = false; // Variable que indica si el usuario ha perdido
    private int casillasRestantes = casillas.length * casillas[0].length - 10; // Variable que lleva la cuenta de las casillas restantes por descubrir

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Buscaminas");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);

        // Crea un objeto GridPane para el tablero de juego
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Recorre la matriz de casillas y crea un botón para cada casilla
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[0].length; j++) {
                Button button = new Button();
                button.setPrefSize(40, 40); // Establece el tamaño del botón
                int finalJ = j;
                int finalI = i;
                button.setOnAction(event -> mostrarContenidoCasilla(button, finalI, finalJ)); // Agrega un EventHandler al botón
                gridPane.add(button, j, i); // Agrega el botón al GridPane
            }
        }
        // Asigna el GridPane a la escena y muestra la ventana
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();
    }

    private void mostrarContenidoCasilla(Button button, int fila, int columna) {
        int contenido = casillas[fila][columna];
        button.setText(String.valueOf(contenido));
        // Si el usuario ya perdió el juego, no se hace nada
        if (perdio) {
            return;
        }

        // Si la casilla contiene una mina, el usuario pierde el juego
        if (casillas[fila][columna] == 1) {
            button.setText("X");
            perdio = true;
            mostrarAlerta("Perdiste", "Has encontrado una mina. ¡Perdiste el juego!");
            return;
        }

        // Si la casilla no contiene una mina, se muestra el número de minas adyacentes
        int minasAdyacentes = contarMinasAdyacentes(fila, columna);
        button.setText(Integer.toString(minasAdyacentes));

        // Si el usuario ha descubierto todas las casillas que no son minas, gana el juego
        casillasRestantes--;
        if (casillasRestantes == 0) {
            mostrarAlerta("Ganaste", "¡Felicidades! Has descubierto todas las casillas que no son minas y ganaste el juego.");
        }
    }

    private int contarMinasAdyacentes(int fila, int columna) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int filaActual = fila + i;
                int columnaActual = columna + j;
                if (filaActual >= 0 && filaActual < casillas.length && columnaActual >= 0 && columnaActual < casillas[0].length && casillas[filaActual][columnaActual] == 1) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Muestra una alerta en la pantalla.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

