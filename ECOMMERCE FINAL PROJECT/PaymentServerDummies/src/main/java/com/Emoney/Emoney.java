package com.Emoney;

public class Emoney {
    int id_user;
    float balance;

    public Emoney(int id_user, float balance) {
        this.id_user = id_user;
        this.balance = balance;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
