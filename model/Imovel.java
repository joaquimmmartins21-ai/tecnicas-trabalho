package model;

import interfaces.INegociavel;

public abstract class Imovel implements INegociavel {
    private int id; 
    protected String endereco; 
    protected double preco;
    protected String status; 

    public abstract double calcularValorMetroQuadrado();
    
    public int getId() { return id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}