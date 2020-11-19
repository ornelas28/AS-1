package com.example.as.classes;

public class Delegations {
    private String Delegations;
    private int SAR;
    private int RIS;


    public Delegations(String delegations) {
        Delegations = delegations;
        SAR=0;
        RIS=0;

    }


    public void incrementSar(){
        SAR++;
    }

    public void incrementRis(){
        RIS++;
    }

    public String getDelegations() {
        return Delegations;
    }

    public int getSAR() {
        return SAR;
    }

    public int getRIS() {
        return RIS;
    }
}
