# 📋 Guia de Desenvolvimento de Testes - Jogo da Forca

## Índice
1. [Testes do Model](#testes-do-model)
2. [Testes do Controller](#testes-do-controller)
3. [Testes da View](#testes-da-view)


---

## 🎯 TESTES DO MODEL (HangmanModel)

### 1. Inicialização 

- [x] Quantas tentativas restantes o jogo deve ter ao começar?
- [x] A lista de letras usadas começa vazia ou com algo?
- [x] O jogo está ativo ou já acabou no início?
- [x] O jogador já começa como vencedor?

### 2. Acertos

- [x] O que `guessLetter()` retorna quando a letra existe na palavra?
- [x] Depois de acertar, a letra aparece no `currentWordProgress()`?
- [x] As tentativas restantes mudam quando você acerta?
- [x] A letra acertada vai para `usedLetters`?

### 3. Erros

- [x] O que `guessLetter()` retorna quando a letra NÃO existe na palavra?
- [x] Quantas tentativas restantes são perdidas a cada erro?
- [x] A letra errada vai para `usedLetters`?
- [x] Depois de 1 erro, `remainingAttempts` é 5 ou continua 6?

### 4. Repetição de Letras

- [x] O que acontece se você chutar a MESMA letra errada duas vezes?
- [x] O contador de erros aumenta na segunda vez?
- [x] O que `guessLetter()` retorna na segunda vez?
- [x] A letra é adicionada novamente ao conjunto de letras usadas?

### 5. Vitória
[HangmanModel.kt](src/main/kotlin/model/HangmanModel.kt)
- [x] Como saber se o jogador venceu? (Qual propriedade?)
- [x] Depois de acertar a última letra, `isWordGuessed` vira `true` ou `false`?
- [x] `isGameOver` também muda quando o jogador vence?
- [x] Quantas tentativas restantes tem após vencer?

### 6. Derrota

- [x] Quantos erros são necessários para perder o jogo?
- [x] Depois do último erro, `isGameOver` vira `true` ou `false`?
- [x] `isWordGuessed` vira `true` ou `false` quando perde?
- [x] O que acontece se você tentar chutar depois de perder?

### 7. Case Insensitive

- [x] Chutar 'A' e 'a' são tratados da mesma forma?
- [x] Se a palavra é "KOTLIN" e você chuta 'k', funciona?
- [x] Como a palavra é armazenada internamente? (maiúscula/minúscula?)
- [x] O progresso da palavra mostra letras maiúsculas ou minúsculas?

### 8. Formatação

- [x] `usedLetters()` retorna as letras em ordem alfabética?
- [x] As letras são retornadas em maiúsculo ou minúsculo?
- [x] Qual o separador entre as letras? (vírgula? espaço?)
- [x] `currentWordProgress()` usa espaço entre as letras/underscores?

### 9. Palavras Especiais

- [x] Como se comporta com palavra de 1 letra (ex: "a")?
- [x] Palavra com letras repetidas (ex: "paralelepipedo"): ao chutar 'p', quantos 'p' aparecem?
- [x] O que acontece se a lista de palavras for vazia?
- [x] Palavras com letras maiúsculas no banco são convertidas?

### 10. Limites

- [x] O que acontece se você chutar letra após o jogo ter acabado?
- [x] `remainingAttempts` pode ficar negativo?
- [x] O contador de erros pode ultrapassar 6?
- [x] Depois de perder, ainda é possível mudar o estado do jogo?

---

## 🎮 TESTES DO CONTROLLER (HangmanController)

### 11. Fluxo de Tela

- [x] `showTitle()` é chamado quantas vezes ao iniciar?
- [x] `showGameState()` deve ser chamado antes de cada tentativa?
- [x] `askForLetter()` deve ser chamado para cada tentativa?
- [x] A ordem das chamadas é importante? Qual a ordem correta?

### 12. Validação de Entrada - Vazio

- [x] O que acontece se usuário apertar ENTER sem digitar nada?
- [x] Qual método da View deve ser chamado nesse caso?
- [x] A letra vazia é enviada para o Model?
- [x] O loop continua ou interrompe?

### 13. Validação de Entrada - Múltiplas Letras

- [x] O usuário digita "abc". O que o Controller deve fazer?
- [x] Quantas letras devem ser processadas?
- [x] Qual método da View informa o erro?
- [x] A primeira letra "a" é processada ou tudo é ignorado?

### 14. Validação de Entrada - Números

- [x] Usuário digita "123". Deve aceitar ou rejeitar?
- [x] Caractere '5' é considerado letra? (`isLetter()` retorna o quê?)
- [x] Como a View é informada do erro?

### 15. Validação de Entrada - Caracteres Especiais

- [x] Usuário digita "@#$". Qual a resposta do Controller?
- [x] Deve chamar `showInvalidInput` ou `showFeedback`?
- [x] O Model é consultado nesse caso?

### 16. Letras Repetidas no Controller

- [x] Como o Controller sabe que uma letra já foi tentada?
- [x] Quem detecta a repetição: Controller ou Model?
- [x] Qual método da View deve ser chamado para letra repetida?
- [x] O Model é chamado quando a letra é repetida?

### 17. Finalização do Jogo - Vitória

- [x] Como o Controller sabe que o jogador venceu?
- [x] Qual método da View deve ser chamado ao vencer?
- [x] O loop `while` continua ou para após a vitória?
- [x] `showGameState` é chamado mais uma vez após vencer?

### 18. Finalização do Jogo - Derrota

- [x] Como o Controller sabe que o jogador perdeu?
- [x] Qual método da View mostra a mensagem de derrota?
- [x] A palavra secreta é revelada? Quem fornece essa informação?
- [x] O loop continua depois de perder?

---

## 🖥️ TESTES DA VIEW (HangmanView)

### 19. Exibição de Texto

- [x] `showTitle()` deve imprimir exatamente qual texto?
- [x] `showGameState()` precisa de quais informações do Model?
- [x] O desenho da forca é impresso junto com o estado?
- [x] Existe quebra de linha entre as informações?

### 20. Entrada do Usuário

- [x] `askForLetter()` deve retornar qual tipo de dado?
- [x] O que acontece se o usuário digitar uma string vazia?
- [x] A função deve retornar apenas o primeiro caractere ou todos?
- [x] Espaços em branco devem ser removidos ou mantidos?

### 21. Mensagens de Erro

- [x] `showInvalidInput()` deve receber qual parâmetro?
- [x] A mensagem deve ser genérica ou específica para cada erro?
- [x] Deve incluir instrução para o usuário tentar novamente?

### 22. Mensagens de Feedback

- [x] `showFeedback()` precisa saber se foi acerto ou erro?
- [x] A letra tentada deve ser exibida na mensagem?
- [x] Acerto e erro têm mensagens diferentes?
- [x] Deve pular linha depois da mensagem?

### 23. Mensagens de Letra Repetida

- [x] `showRepeatedLetter()` precisa receber a letra repetida?
- [x] A mensagem deve informar qual letra foi repetida?
- [x] Deve sugerir ao usuário tentar outra letra?

### 24. Mensagens de Fim de Jogo

- [x] `showVictory()` precisa do Model para alguma informação?
- [x] `showDefeat()` precisa revelar a palavra secreta?
- [x] O desenho final da forca é mostrado em vitória e derrota?
- [x] As mensagens têm formatação diferente?
