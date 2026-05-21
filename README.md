# 📋 Guia de Desenvolvimento de Testes - Jogo da Forca

## Índice
1. [Testes do Model](#testes-do-model)
2. [Testes do Controller](#testes-do-controller)
3. [Testes da View](#testes-da-view)
4. [Questões de MockK](#questões-de-mockk)
5. [Questões de Depuração](#questões-de-depuração)
6. [Como Pedir Ajuda](#como-pedir-ajuda)

---

## 🎯 TESTES DO MODEL (HangmanModel)

### 1. Inicialização

- [ x ] Quantas tentativas restantes o jogo deve ter ao começar?
- [ x ] A lista de letras usadas começa vazia ou com algo?
- [ x ] O jogo está ativo ou já acabou no início?
- [ x ] O jogador já começa como vencedor?

### 2. Acertos

- [ ] O que `guessLetter()` retorna quando a letra existe na palavra?
- [ ] Depois de acertar, a letra aparece no `currentWordProgress()`?
- [ ] As tentativas restantes mudam quando você acerta?
- [ ] A letra acertada vai para `usedLetters`?

### 3. Erros

- [ ] O que `guessLetter()` retorna quando a letra NÃO existe na palavra?
- [ ] Quantas tentativas restantes são perdidas a cada erro?
- [ ] A letra errada vai para `usedLetters`?
- [ ] Depois de 1 erro, `remainingAttempts` é 5 ou continua 6?

### 4. Repetição de Letras

- [ ] O que acontece se você chutar a MESMA letra errada duas vezes?
- [ ] O contador de erros aumenta na segunda vez?
- [ ] O que `guessLetter()` retorna na segunda vez?
- [ ] A letra é adicionada novamente ao conjunto de letras usadas?

### 5. Vitória

- [ ] Como saber se o jogador venceu? (Qual propriedade?)
- [ ] Depois de acertar a última letra, `isWordGuessed` vira `true` ou `false`?
- [ ] `isGameOver` também muda quando o jogador vence?
- [ ] Quantas tentativas restantes tem após vencer?

### 6. Derrota

- [ ] Quantos erros são necessários para perder o jogo?
- [ ] Depois do último erro, `isGameOver` vira `true` ou `false`?
- [ ] `isWordGuessed` vira `true` ou `false` quando perde?
- [ ] O que acontece se você tentar chutar depois de perder?

### 7. Case Insensitive

- [ ] Chutar 'A' e 'a' são tratados da mesma forma?
- [ ] Se a palavra é "KOTLIN" e você chuta 'k', funciona?
- [ ] Como a palavra é armazenada internamente? (maiúscula/minúscula?)
- [ ] O progresso da palavra mostra letras maiúsculas ou minúsculas?

### 8. Formatação

- [ ] `usedLetters()` retorna as letras em ordem alfabética?
- [ ] As letras são retornadas em maiúsculo ou minúsculo?
- [ ] Qual o separador entre as letras? (vírgula? espaço?)
- [ ] `currentWordProgress()` usa espaço entre as letras/underscores?

### 9. Palavras Especiais

- [ ] Como se comporta com palavra de 1 letra (ex: "a")?
- [ ] Palavra com letras repetidas (ex: "paralelepipedo"): ao chutar 'p', quantos 'p' aparecem?
- [ ] O que acontece se a lista de palavras for vazia?
- [ ] Palavras com letras maiúsculas no banco são convertidas?

### 10. Limites

- [ ] O que acontece se você chutar letra após o jogo ter acabado?
- [ ] `remainingAttempts` pode ficar negativo?
- [ ] O contador de erros pode ultrapassar 6?
- [ ] Depois de perder, ainda é possível mudar o estado do jogo?

---

## 🎮 TESTES DO CONTROLLER (HangmanController)

### 11. Fluxo de Tela

- [ ] `showTitle()` é chamado quantas vezes ao iniciar?
- [ ] `showGameState()` deve ser chamado antes de cada tentativa?
- [ ] `askForLetter()` deve ser chamado para cada tentativa?
- [ ] A ordem das chamadas é importante? Qual a ordem correta?

### 12. Validação de Entrada - Vazio

- [ ] O que acontece se usuário apertar ENTER sem digitar nada?
- [ ] Qual método da View deve ser chamado nesse caso?
- [ ] A letra vazia é enviada para o Model?
- [ ] O loop continua ou interrompe?

### 13. Validação de Entrada - Múltiplas Letras

- [ ] O usuário digita "abc". O que o Controller deve fazer?
- [ ] Quantas letras devem ser processadas?
- [ ] Qual método da View informa o erro?
- [ ] A primeira letra "a" é processada ou tudo é ignorado?

### 14. Validação de Entrada - Números

- [ ] Usuário digita "123". Deve aceitar ou rejeitar?
- [ ] Caractere '5' é considerado letra? (`isLetter()` retorna o quê?)
- [ ] Como a View é informada do erro?

### 15. Validação de Entrada - Caracteres Especiais

- [ ] Usuário digita "@#$". Qual a resposta do Controller?
- [ ] Deve chamar `showInvalidInput` ou `showFeedback`?
- [ ] O Model é consultado nesse caso?

### 16. Letras Repetidas no Controller

- [ ] Como o Controller sabe que uma letra já foi tentada?
- [ ] Quem detecta a repetição: Controller ou Model?
- [ ] Qual método da View deve ser chamado para letra repetida?
- [ ] O Model é chamado quando a letra é repetida?

### 17. Finalização do Jogo - Vitória

- [ ] Como o Controller sabe que o jogador venceu?
- [ ] Qual método da View deve ser chamado ao vencer?
- [ ] O loop `while` continua ou para após a vitória?
- [ ] `showGameState` é chamado mais uma vez após vencer?

### 18. Finalização do Jogo - Derrota

- [ ] Como o Controller sabe que o jogador perdeu?
- [ ] Qual método da View mostra a mensagem de derrota?
- [ ] A palavra secreta é revelada? Quem fornece essa informação?
- [ ] O loop continua depois de perder?

---

## 🖥️ TESTES DA VIEW (HangmanView)

### 19. Exibição de Texto

- [ ] `showTitle()` deve imprimir exatamente qual texto?
- [ ] `showGameState()` precisa de quais informações do Model?
- [ ] O desenho da forca é impresso junto com o estado?
- [ ] Existe quebra de linha entre as informações?

### 20. Entrada do Usuário

- [ ] `askForLetter()` deve retornar qual tipo de dado?
- [ ] O que acontece se o usuário digitar uma string vazia?
- [ ] A função deve retornar apenas o primeiro caractere ou todos?
- [ ] Espaços em branco devem ser removidos ou mantidos?

### 21. Mensagens de Erro

- [ ] `showInvalidInput()` deve receber qual parâmetro?
- [ ] A mensagem deve ser genérica ou específica para cada erro?
- [ ] Deve incluir instrução para o usuário tentar novamente?

### 22. Mensagens de Feedback

- [ ] `showFeedback()` precisa saber se foi acerto ou erro?
- [ ] A letra tentada deve ser exibida na mensagem?
- [ ] Acerto e erro têm mensagens diferentes?
- [ ] Deve pular linha depois da mensagem?

### 23. Mensagens de Letra Repetida

- [ ] `showRepeatedLetter()` precisa receber a letra repetida?
- [ ] A mensagem deve informar qual letra foi repetida?
- [ ] Deve sugerir ao usuário tentar outra letra?

### 24. Mensagens de Fim de Jogo

- [ ] `showVictory()` precisa do Model para alguma informação?
- [ ] `showDefeat()` precisa revelar a palavra secreta?
- [ ] O desenho final da forca é mostrado em vitória e derrota?
- [ ] As mensagens têm formatação diferente?