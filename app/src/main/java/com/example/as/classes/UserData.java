package com.example.as.classes;

public class UserData {

    private String lastName;
    private String password;
    private String email;
    private String delegation;
    private String name;
    private String type;
    private String associateNumber;

    public UserData() {

    }

    public UserData(String lastName, String password, String email, String delegation, String name, String type, String associateNumber) {
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.delegation = delegation;
        this.name = name;
        this.type = type;
        this.associateNumber = associateNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociateNumber() {
        return associateNumber;
    }

    public void setAssociateNumber(String associateNumber) {
        this.associateNumber = associateNumber;
    }
}