package com.example.redis.model;

import java.io.Serializable;

public class MeuObjeto implements Serializable {
    private String campo1;
    private int campo2;

    // Construtores, getters e setters

    public MeuObjeto() {
    }

    public MeuObjeto(String campo1, int campo2) {
        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public int getCampo2() {
        return campo2;
    }

    public void setCampo2(int campo2) {
        this.campo2 = campo2;
    }
}
