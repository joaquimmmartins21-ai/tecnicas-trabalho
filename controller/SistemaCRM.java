package controller;

import model.Cliente;
import model.Imovel;
import model.Corretor;
import exception.DadosImovelInvalidosException;
import exception.ImovelIndisponivelException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SistemaCRM {
    
    private Map<Integer, Imovel> catalogoImoveis;
    private Set<Corretor> equipeCorretores;
    private List<Proposta> historicoPropostas;

    public void cadastrarImovel(Imovel imovel) throws DadosImovelInvalidosException {
    }

    public void registrarProposta(Proposta proposta) throws ImovelIndisponivelException {
    }
}