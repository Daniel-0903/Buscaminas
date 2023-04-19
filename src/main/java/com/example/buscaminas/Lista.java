package com.example.buscaminas;

public class Lista {

    private Nodo head;
    private int size;


    public Lista() {
        this.head = null;
        this.size = 0;
    }


    public boolean isEmpty() {
        return this.head == null;
    }


    public int getSize() {
        return this.size;
    }


    public void add(Nodo nodo) {
        if (isEmpty()) {
            this.head = nodo;
        } else {
            Nodo tmp = this.head;
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
            }
            tmp.setNext(nodo);
            System.out.println(tmp.getNext());
        }
        this.size++;
    }


    public Nodo search(int i, int j) {
        Nodo tmp = this.head;
        while (tmp != null) {
            if (tmp.get_X() == i && tmp.get_Y() == j) {
                return tmp;
            } else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }


    public Nodo searchRand() {

        int rand = (int) (Math.random() * 8);
        int rand2 = (int) (Math.random() * 8);
        while (search(rand, rand2) == null) {
            rand = (int) (Math.random() * 8);
            rand2 = (int) (Math.random() * 8);
        }
        int[] list = new int[2];
        list[0] = rand;
        list[1] = rand2;
        Nodo tmp = new Nodo(list);
        return tmp;
    }


    public void del() {
        head = null;

        Nodo current = head;
        while (current != null) {
            Nodo siguiente = current.getNext();
            current.setNext(null);
            current = siguiente;
        }
        this.size = 0;
    }
}

