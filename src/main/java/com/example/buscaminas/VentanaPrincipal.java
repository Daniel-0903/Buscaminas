package com.example.buscaminas;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class VentanaPrincipal extends Application {

    /**
     * crea la ventana princiapl con 2 botones para jugar en dummy o advanced
     * @param primaryStage
     */
    public void start(Stage primaryStage) {

        // Configuración de la ventana principal
        primaryStage.setTitle("Ventana principal");
        primaryStage.setWidth(200);
        primaryStage.setHeight(200);

        // Creación de los botones
        Button boton1 = new Button("DUMMY");
        Button boton2 = new Button("ADVANCED");

        // Asignación de los EventHandlers a los botones
        boton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dummy dummy = new Dummy();
                dummy.start(primaryStage);
            }
        });

        boton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Advanced advanced = new Advanced();
                advanced.start(primaryStage);
            }
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10); // Espacio entre los botones
        vbox.setPadding(new Insets(50)); // Margen interior del VBox
        vbox.getChildren().addAll(boton1, boton2); // Agregar los botones al VBox

        // Configuración de la escena y asignación al Stage principal
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);


        // Mostrar la ventana principal
        primaryStage.show();
    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

