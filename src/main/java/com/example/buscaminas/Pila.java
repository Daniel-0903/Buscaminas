package com.example.buscaminas;

import java.util.LinkedList;

public class Pila {
    private int maxsize;
    private Object[] stackArray;
    private int top;

    public void push(Object newObject) throws Exception {
        if (top<maxsize){
            this.stackArray[++top]= newObject;
        }else{
            throw new Exception("La pila esta llena");
        }
    }
    public Object pop(){
        return this.stackArray[top--];
    }
}