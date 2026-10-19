# CRM Imobiliário

Projeto em Java que serve de base para um sistema de CRM (Customer Relationship
Management) voltado ao mercado imobiliário. A proposta é organizar informações
de imóveis, clientes e corretores e, futuramente, acompanhar propostas de compra.

> **Estado atual:** o projeto está em fase de estruturação. Os pacotes e os
> principais tipos do domínio já foram criados, mas várias operações ainda
> estão vazias ou retornam valores provisórios. Este README descreve o código
> existente e não pressupõe funcionalidades que ainda não foram implementadas.

## Objetivo do sistema

Um CRM imobiliário pode ajudar uma imobiliária a:

- manter um catálogo de imóveis;
- registrar clientes e seus perfis de busca;
- relacionar clientes a imóveis de interesse;
- acompanhar propostas e a disponibilidade dos imóveis;
- organizar a equipe de corretores;
- sugerir imóveis compatíveis com as necessidades de cada cliente.

No código atual, esses objetivos aparecem como classes, atributos e contratos
iniciais. O cadastro, a pesquisa e o fluxo de negociação ainda precisam ser
implementados.

## Organização do projeto

Os arquivos estão separados em diretórios que correspondem aos pacotes Java:

```text
tecnicas-trabalho-main/
├── controller/
│   ├── Proposta.java
│   └── SistemaCRM.java
├── exception/
│   ├── DadosImovelInvalidosException.java
│   └── ImovelIndisponivelException.java
├── interfaces/
│   ├── IAssistenteSDR.java
│   └── INegociavel.java
└── model/
    ├── Apartamento.java
    ├── Casa.java
    ├── Cliente.java
    ├── Corretor.java
    ├── Imovel.java
    └── Pessoa.java
```

### `model` — entidades do domínio

Este pacote reúne os objetos que representam os conceitos do negócio.

- **`Pessoa`** é uma classe abstrata com os dados comuns `nome`, `cpf` e
  `telefone`.
- **`Cliente`** estende `Pessoa` e acrescenta `orcamentoMaximo` e
  `perfilBusca`.
- **`Corretor`** estende `Pessoa` e acrescenta `creci` e `taxaComissao`.
- **`Imovel`** é uma classe abstrata que implementa `INegociavel`. Mantém `id`,
  `endereco`, `preco` e `status`, e declara o cálculo do valor por metro
  quadrado como operação abstrata. Atualmente expõe getters para `id` e
  `status` e um setter para `status`.
- **`Apartamento`** estende `Imovel` e acrescenta `andar` e
  `valorCondominio`.
- **`Casa`** estende `Imovel` e acrescenta `temQuintal` e
  `areaConstruida`.

`Apartamento` e `Casa` sobrescrevem o cálculo do valor por metro quadrado e os
métodos de negociação. No momento, porém, o cálculo retorna `0.0`, a aprovação
retorna `false` e a rejeição não executa nenhuma ação. Portanto, esses métodos
ainda não expressam regras de negócio reais.

### `controller` — coordenação das operações

- **`SistemaCRM`** declara as estruturas para armazenar o catálogo de imóveis
  (`Map<Integer, Imovel>`), a equipe de corretores
  (`Set<Corretor>`) e o histórico de propostas (`List<Proposta>`). Também
  declara `cadastrarImovel` e `registrarProposta`, mas os corpos desses métodos
  ainda estão vazios. As coleções ainda não são inicializadas.
- **`Proposta`** representa a intenção de negociação por meio dos campos
  `cliente`, `imovel`, `valorOferecido` e `data`. Ainda não possui construtor,
  getters, validações nem métodos para aceitar ou rejeitar a oferta.

### `interfaces` — contratos de comportamento

- **`INegociavel`** define dois comportamentos para um objeto negociável:
  `aprovarProposta(double valor)`, que informa se uma oferta foi aprovada, e
  `rejeitarProposta()`.
- **`IAssistenteSDR`** define operações para classificar um lead
  (`classificarLead`) e buscar imóveis pelo perfil informado
  (`buscarImovelIdeal`). A interface ainda não tem uma classe de implementação.

SDR é a sigla de *Sales Development Representative*. Neste projeto, a interface
representa uma possível camada de apoio à qualificação de clientes e à busca de
imóveis.

### `exception` — situações excepcionais do domínio

- **`DadosImovelInvalidosException`** é uma exceção verificada (*checked*), que
  estende `Exception`. Foi prevista para sinalizar dados inválidos ao cadastrar
  um imóvel.
- **`ImovelIndisponivelException`** é uma exceção não verificada (*unchecked*),
  que estende `RuntimeException`. Foi prevista para sinalizar tentativas de
  registrar propostas para imóveis indisponíveis.

As exceções já possuem construtores que recebem uma mensagem, mas as regras que
as lançam ainda precisam ser implementadas.

## Fluxo de negócio esperado

Um fluxo central que pode orientar a implementação é:

1. Criar um imóvel com dados válidos e cadastrá-lo no `SistemaCRM`.
2. Registrar um cliente com orçamento e perfil de busca.
3. Consultar imóveis disponíveis compatíveis com esse perfil.
4. Criar uma proposta associando cliente, imóvel, valor e data.
5. Verificar se o imóvel está disponível antes de registrar a proposta.
6. Aprovar ou rejeitar a oferta e atualizar o estado do imóvel conforme a regra
   de negócio definida.
7. Consultar o histórico de propostas.

Esse fluxo descreve o comportamento desejável; ele **não está completo no
código atual**.

## Como executar

No momento, o projeto não inclui:

- uma classe com método `public static void main(String[] args)`;
- configuração de build, como Maven (`pom.xml`) ou Gradle;
- dependências ou instruções de execução automatizadas;
- testes automatizados.

Assim, ainda não há um comando documentado para executar o CRM como aplicação.
Como próximo passo técnico, é possível criar uma classe de entrada para
demonstrar o fluxo principal e, em seguida, escolher uma ferramenta de build e
configurar testes.

## Próximos passos recomendados

1. **Completar o modelo:** adicionar construtores, getters e setters necessários
   e definir validações para os dados obrigatórios.
2. **Definir os estados do imóvel:** substituir valores livres em `status` por
   estados claramente definidos, por exemplo, disponível, reservado e vendido.
3. **Especificar as regras de negociação:** estabelecer quais valores podem ser
   aprovados e quando uma proposta altera a disponibilidade do imóvel.
4. **Implementar `SistemaCRM`:** inicializar as coleções e concluir cadastro,
   consulta, validação e registro do histórico.
5. **Implementar o cálculo por metro quadrado:** garantir que cada tipo de
   imóvel tenha uma área utilizável para o cálculo. `Casa` declara
   `areaConstruida`; `Apartamento` ainda não declara um campo de área.
6. **Criar testes:** cobrir dados inválidos, IDs repetidos, busca de imóveis,
   propostas para imóveis indisponíveis e aprovação/rejeição.
7. **Adicionar uma entrada executável e persistência:** depois de validar o
   fluxo em memória, decidir se os dados serão salvos em arquivo ou banco de
   dados.

## Conceitos de orientação a objetos presentes

- **Abstração:** `Pessoa` e `Imovel` reúnem características compartilhadas e
  não podem ser instanciadas diretamente.
- **Herança:** `Cliente` e `Corretor` herdam os dados comuns de `Pessoa`;
  `Casa` e `Apartamento` herdam de `Imovel`.
- **Polimorfismo:** cada tipo concreto de imóvel fornece sua própria
  implementação dos métodos abstratos e do contrato `INegociavel`.
- **Interfaces:** `INegociavel` e `IAssistenteSDR` descrevem comportamentos sem
  exigir que a lógica esteja concentrada em uma única classe.
- **Exceções de domínio:** as classes em `exception` permitem representar
  situações específicas do negócio com mensagens explicativas.
