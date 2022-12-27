package com.example.test1;

public class User {
    public String name,conatact,email;
    public User(){
    }

    public User(String name, String conatact, String email) {
        this.name = name;
        this.conatact = conatact;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConatact() {
        return conatact;
    }

    public void setConatact(String conatact) {
        this.conatact = conatact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
