package model;

public class Casa extends Imovel {
    private boolean temQuintal;
    private double areaConstruida;

    @Override
    public double calcularValorMetroQuadrado() {
        return 0.0; 
    }

    @Override
    public boolean aprovarProposta(double valor) { return false; }
    
    @Override
    public void rejeitarProposta() {}
}