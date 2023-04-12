package com.example.buscaminas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 483, 416);
        stage.setTitle("Buscaminas");
        stage.setScene(scene);
        stage.show();
    }


public static void main(String[] args) {
    launch(args);
    gridint grid1=new gridint(8, 8, 8);
    grid1.imprimir();
    System.out.println("------");
    grid1.imprimirAlrededor();
}
}