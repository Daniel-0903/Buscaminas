package com.example.buscaminas;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.LinkedList;
public class CeldasDisponibles {
    public class ListaEnlazada {
        private Nodo primero;

        public ListaEnlazada() {
            primero = null;
        }

        public void agregarNodo(Button casilla) {
            Nodo nuevoNodo = new Nodo(casilla);

            if (primero == null) {
                primero = nuevoNodo;
            } else {
                Nodo actual = primero;
                while (actual.getSiguiente() != null) {
                    actual = actual.getSiguiente();
                }
                actual.setSiguiente(nuevoNodo);
            }
        }

        public Nodo obtenerNodo(int indice) {
            int contador = 0;
            Nodo actual = primero;

            while (actual != null) {
                if (contador == indice) {
                    return actual;
                }
                contador++;
                actual = actual.getSiguiente();
            }

            return null;
        }
    }

}
