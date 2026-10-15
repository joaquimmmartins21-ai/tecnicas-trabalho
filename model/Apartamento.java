package model;

public class Apartamento extends Imovel {
    private int andar;
    private double valorCondominio;

    @Override
    public double calcularValorMetroQuadrado() {
        return 0.0; 
    }

    @Override
    public boolean aprovarProposta(double valor) { return false; }
    
    @Override
    public void rejeitarProposta() {}
}