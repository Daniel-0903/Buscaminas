package com.example.buscaminas;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Random;

public class Ventana2Controller extends Application {
    gridint tab = new gridint();
    private int[][] tablero=

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
    private Timeline timeline;
    private int filas;
    private boolean[][] descubierta = new boolean[8][8];

    private boolean perdio = false; // Variable que indica si el usuario ha perdido
    private int casillasRestantes = tablero.length * tablero[0].length - 10; // Variable que lleva la cuenta de las casillas restantes por descubrir
    private int segundosTranscurridos = 0;
    private int banderasColocadas = 0; // Variable que lleva la cuenta de las banderas colocadas
    private int minasEncontradas = 0; // Variable que lleva la cuenta de las minas encontradas

    private boolean jugandoContraComputador = false;

    private int casillasDescubiertas = 0;

    private int casillasDescubiertasComputador = 0;



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Buscaminas");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        Label tiempoLabel = new Label("Tiempo: 0 segundos");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundosTranscurridos++;
            tiempoLabel.setText("Tiempo: " + segundosTranscurridos + " segundos");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        GridPane mainGridPane = new GridPane();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Label tituloLabel = new Label("Título de la aplicación");
        vbox.getChildren().addAll(tituloLabel, tiempoLabel);
        mainGridPane.add(vbox, 0, 0, tablero[0].length, 1);

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
                button.setOnMousePressed(event -> mostrarContenidoCasilla(button, finalI, finalJ, event)); // Agrega un EventHandler al botón

                gridPane.add(button, j, i); // Agrega el botón al GridPane
            }

        }
        // Agregar los elementos al gridPane principal
        mainGridPane.add(tituloLabel, 0, 0);
        mainGridPane.add(gridPane, 0, 1);
        GridPane.setConstraints(gridPane, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);

        iniciarTemporizador();

        // Asigna el GridPane a la escena y muestra la ventana
        primaryStage.setScene(new Scene(mainGridPane));
        primaryStage.show();
    }

    private void mostrarContenidoCasilla(Button button, int fila, int columna, MouseEvent event) {
        descubierta[fila][columna] = true;

        // Si se hizo clic derecho en el botón, se coloca una bandera
        if (event.getButton() == MouseButton.SECONDARY) {
            if (button.getGraphic() instanceof ImageView) {
                button.setGraphic(null);
                banderasColocadas--;
            } else {
                ImageView bandera = new ImageView(new Image("file:src/bandera.jpg"));
                button.setGraphic(bandera);
                banderasColocadas++;
                // Si se han colocado todas las banderas necesarias para cubrir todas las minas, el jugador gana
                if (banderasColocadas == 10 && minasEncontradas == 10) {
                    mostrarAlerta("Ganaste", "¡Felicidades! Has colocado todas las banderas necesarias para cubrir todas las minas y ganaste el juego.");
                }
            }
            return;
        }
        int contenido = tablero[fila][columna];
        button.setText(String.valueOf(contenido));
        // Si el usuario ya perdió el juego, no se hace nada
        if (perdio) {
            return;
        }

        // Si la casilla contiene una mina, el usuario pierde el juego
        if (tablero[fila][columna] == 1) {
            button.setText("X");
            button.setStyle("-fx-background-color: red");
            perdio = true;
            timeline.stop(); // Detiene el temporizador
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
                            mostrarContenidoCasilla(buttonActual, filaActual, columnaActual,event);
                        }
                    }
                }
            }
        }
        if (jugandoContraComputador && !perdio && casillasRestantes > 0) {
            // El computador descubre una casilla aleatoria que no haya sido descubierta antes
            int filaAleatoria, columnaAleatoria;
            do {
                filaAleatoria = (int) (Math.random() * tablero.length);
                columnaAleatoria = (int) (Math.random() * tablero[0].length);
            } while (tablero[filaAleatoria][columnaAleatoria] == 1 || descubierta[filaAleatoria][columnaAleatoria]);
            Button buttonAleatorio = (Button) getNodeFromGridPane(columnaAleatoria, filaAleatoria, (GridPane) button.getParent());
            mostrarContenidoCasilla(buttonAleatorio, filaAleatoria, columnaAleatoria, null);
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
     * Muestra una alerta en la pantalla.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private void iniciarTemporizador() {
        timeline.playFromStart();

    }


    public static void main(String[] args) {
        launch(args);
    }

}




