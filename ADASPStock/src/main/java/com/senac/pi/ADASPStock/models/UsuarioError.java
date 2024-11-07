package com.senac.pi.ADASPStock.models;

public class UsuarioError {
    private String message;

    public UsuarioError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
