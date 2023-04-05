package com.example.buscaminas;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Ventana2Controller extends Application {
    gridint tab = new gridint();
    private int[][] tablero=
            /**
            tab.imprimir();
             **/

    { // Esta es la matriz de casillas que contiene la información del tablero
            {0, 1, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    private boolean perdio = false; // Variable que indica si el usuario ha perdido
    private int casillasRestantes = tablero.length * tablero[0].length - 10; // Variable que lleva la cuenta de las casillas restantes por descubrir

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
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
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

    /**
     * Muestra el contenido de una casilla cuando se hace clic en ella.
     */

    /**
    private void mostrarContenidoCasilla(Button button, int fila, int columna) {
        int contenido = tablero[fila][columna];
        button.setText(String.valueOf(contenido));
        // Si el usuario ya perdió el juego, no se hace nada
        if (perdio) {
            return;
        }

        // Si la casilla contiene una mina, el usuario pierde el juego
        if (tablero[fila][columna] == 1) {
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
**/

    private void mostrarContenidoCasilla(Button button, int fila, int columna) {
        int contenido = tablero[fila][columna];
        button.setText(String.valueOf(contenido));
        // Si el usuario ya perdió el juego, no se hace nada
        if (perdio) {
            return;
        }

        // Si la casilla contiene una mina, el usuario pierde el juego
        if (tablero[fila][columna] == 1) {
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

        // Si la casilla no tiene minas adyacentes, se llama la función recursivamente para mostrar las casillas adyacentes
        if (minasAdyacentes == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int filaActual = fila + i;
                    int columnaActual = columna + j;
                    if (filaActual >= 0 && filaActual < tablero.length && columnaActual >= 0 && columnaActual < tablero[0].length) {
                        Button buttonActual = (Button) getNodeFromGridPane(columnaActual, filaActual, (GridPane) button.getParent());
                        if (!buttonActual.getText().equals(Integer.toString(contarMinasAdyacentes(filaActual, columnaActual)))) {
                            mostrarContenidoCasilla(buttonActual, filaActual, columnaActual);
                        }
                    }
                }
            }
        }
    }

    // Función auxiliar para obtener un nodo (en este caso un botón) desde un GridPane
    private Node getNodeFromGridPane(int col, int row, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private int contarMinasAdyacentes(int fila, int columna) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int filaActual = fila + i;
                int columnaActual = columna + j;
                if (filaActual >= 0 && filaActual < tablero.length && columnaActual >= 0 && columnaActual < tablero[0].length && tablero[filaActual][columnaActual] == 1) {
                    contador++;
                }
            }
        }
        return contador;
    }
/**
    private void cascada(int fila, int columna) {
        // Comprobar si la casilla actual tiene minas adyacentes
        int minasAdyacentes = contarMinasAdyacentes(fila, columna);
        if (minasAdyacentes > 0) {
            Button button = (Button) getNodeFromGridPane(gridPane, fila, columna);
            button.setText(Integer.toString(minasAdyacentes));
            return;
        }

        // Marcar la casilla actual como visitada
        visitadas[fila][columna] = true;
        System.out.println("Visiting cell (" + fila + ", " + columna + ")");

        // Recorrer las casillas adyacentes que no tienen minas ni han sido visitadas
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (i >= 0 && i < tablero.length && j >= 0 && j < tablero[0].length && !visitadas[i][j] && tablero[i][j] == 0) {
                    // Llamar recursivamente al método para cada casilla adyacente sin minas
                    cascada(i, j);
                }
            }
        }
    }

    public static Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

**/




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




