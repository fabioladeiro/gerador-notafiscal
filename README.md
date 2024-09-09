
# Resolução do Desafio - Gerador de Nota Fiscal


## Descrição

O desafio consistia em resolver problemas estruturais e de desempenho em uma aplicação Java 11 Spring Boot para a geração de notas fiscais.

#### Desafio Nota Fiscal 

* Código difícil de manter e alterar
* Muitas regras diferentes de cálculo e fluxo complexo
* Classe "principal" muito instável .
* Baixa cobertura de teste e alguns estão quebrando

#### Problema funcional

* A primeira execução sempre funciona ok, as demais têm um erro na devolução dos itens acumulando os itens de execuções anteriores
* Para pedidos com mais de 6 itens, o tempo de processamento fica muito elevado
* Após algumas execuções o retorno também fica muito lento
* Existem reclamações de outros sistemas onde estão recebendo dados inconscientes relacionados aos valores da nota e total de itens

#### Premissas

* O Payload de entrada não deve ser modificado
* Algumas integrações têm seu tempo simulando uma chamada, então essa parte não pode simplesmente ser removida
* A solução proposta deve resolver os problemas funcionais mencionados e melhorar a vida do desenvolvedor e mostrar o quão além e a mais você consegue aplicar pensando em TODO o fluxo de desenvolvimento e entrega de software.

## Soluções Aplicadas
1. Problema de Acúmulo de Itens

A classe `CalculadoraAliquotaProduto` tinha uma variável estática `itemNotaFiscalList` que causava o acúmulo de itens entre as execuções.

Para solução, a variável `itemNotaFiscalList` foi movida para dentro do método `calcularAliquota` para garantir que uma nova lista seja criada a cada chamada do método. Isso garante que não haja persistência de dados entre as execuções.

2. Otimização do Tempo de Processamento para Pedidos com Mais de 6 Itens

O tempo de processamento para pedidos com mais de 6 itens era elevado.

Foi identificada a presença de um sleep na classe `EntregaIntegrationPort`. A remoção desse sleep foi realizada, conforme as instruções, para reduzir o tempo de processamento.

3. Desempenho em Execuções
O tempo de resposta da aplicação aumentava após várias execuções.

Apesar de não ter conseguido visualizar claramente o problema em minha máquina, sugiro uma estratégia de monitoramento de performance como um próximo passo para identificar e resolver possíveis problemas de desempenho em execuções.

4. Correção dos Dados Inconsistentes

Dados inconsistentes estavam sendo recebidos por outros sistemas relacionados ao valor da nota e ao total de itens.

Para solução, o valor total da nota fiscal passou a ser calculado com base nos valores dos itens e não apenas no valor fornecido pelo pedido. Isso garante que o valor total dos itens (levando em conta preços unitário e quantidades) e o valor calculado da nota fiscal estejam alinhados. Além disso, foi implementada uma validação do campo valor_total_itens, sendo que a aplicação retorna erro caso receba um valor total inconsistente.

## Melhorias Adicionais
* Separação de Responsabilidades:
As responsabilidades foram separadas em estratégias distintas para cálculo de alíquotas, cálculo de frete e criação de notas fiscais.
Isso reduziu a complexidade e melhorou a legibilidade do código.
* Validação e Tratamento de Dados:
A validação dos dados de entrada foi implementada, garantindo que dados inválidos sejam tratados adequadamente.
* Inclusão de Logs:
Logs foram adicionados para facilitar o monitoramento e a depuração da aplicação.
* Aumento da Cobertura de Testes:
Foram criados e melhorados testes unitários para aumentar a cobertura e garantir a qualidade do código.

## Melhorias Futuras

* Atualização do Java: de Dependências: atualização da linguagem, do Spring Boot e outras bibliotecas para versões mais recentes, para novas funcionalidades e melhorias.
* Testes de Segurança: Adicionar testes de segurança, como testes de penetração, para verificar a robustez da aplicação contra ataques comuns.
* Implementação de Monitoramento: Adicionar ferramentas de monitoramento para rastrear o desempenho da aplicação e detectar problemas proativamente.
* Logs Detalhados: Melhorar a detalhamento dos logs para facilitar a identificação e solução de problemas.
* Documentação da aplicação: criar uma documentação para facilitar a manutenção e o entendimento por novos desenvolvedores.
* Adição do Dockerfile e Deploy do projeto: Possibilitar a containerização da aplicação, garantindo uma configuração mais ágil e consistente dos ambientes de desenvolvimento e produção.