package com.example.buscaminas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

import static javafx.application.Application.launch;

/**
 * se define la clase y las variables a utilizar, además de instanciar el objeto Grid
 */
public class Advanced extends Application {
    Grid tablero = new Grid(8, 8, 10);

    private Timeline timeline;

    private boolean perdio = false; // Variable que indica si el usuario ha perdido
    private int segundosTranscurridos = 0;
    private int banderasColocadas = 0; // Variable que lleva la cuenta de las banderas colocadas
    private int minasEncontradas = 0; // Variable que lleva la cuenta de las minas encontradas

    private boolean jugandoContraComputador = true;

    private boolean esTurnoDelJugador = false;
    public Button[][] botmatriz = new Button[8][8];
    Lista listaGeneral = new Lista();
    Lista listaSegura = new Lista();
    Lista listaIncertidumbre = new Lista();

    /**
     * crea un vBox con 2 Grid panes, uno con el tablero y otro con el nombre del nivel y el temporizador
     *  se crea un boton para cada casilla de la matriz
     * inicia el temporizador
     * @param primaryStage
     */
    @Override

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Buscaminas");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        Label tiempoLabel = new Label("Tiempo: 0 segundos");
        tablero.inicializar();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundosTranscurridos++;
            tiempoLabel.setText("Tiempo: " + segundosTranscurridos + " segundos");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        GridPane mainGridPane = new GridPane();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Label tituloLabel = new Label("ADVANCED LEVEL");
        vbox.getChildren().addAll(tituloLabel, tiempoLabel);
        mainGridPane.add(vbox, 0, 0, 8, 1);

        // Crea un objeto GridPane para el tablero de juego
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Recorre la matriz de casillas y crea un botón para cada casilla
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button();
                botmatriz[i][j] = button;
                button.setPrefSize(40, 40); // Establece el tamaño del botón
                int finalJ = j;
                int finalI = i;
                button.setOnMousePressed(event -> Turno(tablero, new Random(), esTurnoDelJugador)); // Agrega un EventHandler al botón
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

    /**
     * maneja el turno de cada jugador, poner las banderas y las listas
     * @param tablero
     * @param random
     * @param turno
     */
    public void Turno(Grid tablero, Random random, boolean turno) {
        if (!turno) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    int fila = i;
                    int columna = j;
                    int finalI = i;
                    int finalJ = j;

                    botmatriz[i][j].setOnMouseClicked((MouseEvent event) -> {
                        int Columna = GridPane.getRowIndex(botmatriz[fila][columna]);
                        int Fila = GridPane.getColumnIndex(botmatriz[fila][columna]);
                        if (event.getButton() == MouseButton.PRIMARY) {
                            botmatriz[Fila][Columna].setDisable(true);
                            if (tablero.casillaTablero[Fila][Columna].isMina()) {
                                botmatriz[finalI][finalJ].setGraphic(null);
                                for (int k = 0; k < 8; k++) {
                                    for (int l = 0; l < 8; l++) {
                                        botmatriz[k][l].setDisable(true);
                                    }
                                }
                                mostrarAlerta("Perdiste", "PERDISTE EL JUEGO AL DESCUBRIR UNA MINA");
                                timeline.stop();
                                return;
                            } else {
                                botmatriz[fila][columna].setStyle("-fx-background-color: red");
                                botmatriz[fila][columna].setDisable(true);
                                tablero.revelarCeldasSinPistas(Fila, Columna);
                                esTurnoDelJugador = true;
                                Turno(tablero, random, esTurnoDelJugador);
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            // Realizar acción para clic derecho
                            if (botmatriz[Fila][Columna].getGraphic() instanceof ImageView) {
                                botmatriz[Fila][Columna].setGraphic(null);
                                banderasColocadas--;
                            } else {
                                ImageView bandera = new ImageView(new Image("file:src/bandera.jpg"));
                                botmatriz[Fila][Columna].setGraphic(bandera);
                                banderasColocadas++;
                                // Si se han colocado todas las banderas necesarias para cubrir todas las minas, el jugador gana
                                if (banderasColocadas == 10 && minasEncontradas == 10) {
                                    mostrarAlerta("Ganaste", "¡Felicidades! Has colocado todas las banderas necesarias para cubrir todas las minas y ganaste el juego.");
                                }
                            }
                            return;

                        }
                    });
                }
            }
        }
    else {


            listaGeneral.del();
            listaIncertidumbre.del();
            listaSegura.del();

            for (
                    int i = 0;
                    i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (tablero.casillaTablero[i][j].isAbierta() == false) {
                        int[] elnodo = new int[2];
                        elnodo[0] = i;
                        elnodo[1] = j;
                        Nodo Nodito = new Nodo(elnodo);
                        listaGeneral.add(Nodito);
                    }
                }
            }
            for (
                    int i = 0;
                    i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (listaGeneral.search(i, j) != null) {
                        if (!tablero.casillaTablero[i][j].isMina() && !listaGeneral.search(i, j).get_EsSeguro()) {
                            int[] elnodo = new int[2];
                            elnodo[0] = i;
                            elnodo[1] = j;
                            Nodo Elnodo = new Nodo(elnodo);
                            listaSegura.add(Elnodo);
                            Elnodo.setEsSeguro();
                        } else if (tablero.casillaTablero[i][j].isMina() && !listaGeneral.search(i, j).get_EsPosible()) {
                            int[] nodito = new int[2];
                            nodito[0] = i;
                            nodito[1] = j;
                            Nodo Nodito = new Nodo(nodito);
                            listaIncertidumbre.add(Nodito);
                            Nodito.setEsPosible();
                        }
                    }

                }
            }
            System.out.println("Tamaño lista segura" + listaSegura.getSize());
            System.out.println("No hay minas: " + listaSegura);
            System.out.println("Puede haber minas: " + listaIncertidumbre);

            if (listaSegura.getSize() <= 0) {
                System.out.println("Es mina");
                Nodo Nnodo = listaIncertidumbre.searchRand();
                int[] Nnodo_pos = new int[2];
                Nnodo_pos[0] = Nnodo.get_X();
                Nnodo_pos[1] = Nnodo.get_Y();

                int x = Nnodo_pos[0];
                int y = Nnodo_pos[1];

                if (tablero.casillaTablero[x][y].isMina()) {
                    botmatriz[x][y].setDisable(true);
                    botmatriz[x][y].setGraphic(null);
                    botmatriz[x][y].setStyle("-fx-background-color: green");

                    for (int f = 0; f < 8; f++) {
                        for (int jj = 0; jj < 8; jj++) {
                            botmatriz[f][jj].setDisable(true);


                        }
                    }

                    mostrarAlerta("Ganaste", "¡Felicidades! Has colocado todas las banderas necesarias para cubrir todas las minas y ganaste el juego.");                }

            } else {
                System.out.println("No hay bomba, se supone");
                Nodo Nodo_pos = listaSegura.searchRand();
                int[] node = new int[2];
                node[0] = Nodo_pos.get_X();
                node[1] = Nodo_pos.get_Y();

                int x = node[0];
                int y = node[1];
                botmatriz[x][y].setDisable(true);
                tablero.generarNumAdy();
                if (tablero.casillaTablero[x][y].getNumMinasAlrededor() != 0) {

                    tablero.casillaTablero[x][y].setText(tablero.casillaTablero[x][y].getNumMinasAlrededor() + "");
                }
                tablero.revelarCeldasSinPistas(x, y);
                esTurnoDelJugador=false;
                Turno(tablero, random, esTurnoDelJugador);
            }

        }}

    /**
     *Muestra una alerta en la pantalla cuando el jugador gana o pierde
     * @param titulo
     * @param mensaje
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * inicia el temporizador
     */
    private void iniciarTemporizador() {
        timeline.playFromStart();

    }


    public static void main(String[] args) {
        launch(args);
    }

}

