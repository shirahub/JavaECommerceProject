package com.Bank;

public class Bank {

    int id_trans;
    float nominal;
    boolean status;

    public Bank(int id_trans, float nominal) {
        this.id_trans = id_trans;
        this.nominal = nominal;
    }

    public int getId_trans() {
        return id_trans;
    }

    public void setId_trans(int id_trans) {
        this.id_trans = id_trans;
    }

    public float getNominal() {
        return nominal;
    }

    public void setNominal(float nominal) {
        this.nominal = nominal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
