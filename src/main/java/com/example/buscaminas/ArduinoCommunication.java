package com.example.buscaminas;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.OutputStream;

public class ArduinoCommunication {

    private SerialPort arduinoPort;

    public ArduinoCommunication() {
        arduinoPort = SerialPort.getCommPort("COM3"); // Cambia "COM3" por el nombre del puerto de tu Arduino
        arduinoPort.setComPortParameters(9600, 8, 1, 0); // Configura los parámetros del puerto serie
        arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // Configura los tiempos de espera del puerto serie
    }

    public void turnOnLED() {
        try {
            arduinoPort.openPort(); // Abre el puerto serie
            OutputStream outputStream = arduinoPort.getOutputStream(); // Crea un objeto de salida
            outputStream.write('1'); // Envía el caracter '1' al puerto serie
            outputStream.close(); // Cierra el objeto de salida
            arduinoPort.closePort(); // Cierra el puerto serie
        } catch (IOException ex) {
            System.out.println("Error de comunicación con el Arduino: " + ex.getMessage());
        }
    }
}
