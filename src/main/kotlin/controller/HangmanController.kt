package controller

import model.HangmanModel
import view.HangmanView

class HangmanController(
    private val model: HangmanModel,
    private val view: HangmanView
) {

    fun start() {
        view.showTitle()

        while (!model.isGameOver) {
            // Mostra estado atual
            view.showGameState(model)

            // Solicita letra
            view.askForLetter()
            val input = readlnOrNull()?.trim() ?: ""

            // Valida entrada
            when {
                input.isEmpty() -> {
                    view.showInvalidInput("Nenhuma letra informada. Tente novamente.")
                    continue
                }
                input.length > 1 -> {
                    view.showInvalidInput("Digite apenas uma letra por vez.")
                    continue
                }
                !input[0].isLetter() -> {
                    view.showInvalidInput("Caractere inválido. Digite uma letra de A a Z.")
                    continue
                }
                else -> {
                    val letter = input[0]
                    val wasCorrect = model.guessLetter(letter)

                    when {
                        !wasCorrect && model.usedLetters.contains(letter.uppercase()) -> {
                            view.showRepeatedLetter(letter)
                        }
                        !wasCorrect -> {
                            view.showFeedback(false, letter)
                        }
                        else -> {
                            view.showFeedback(true, letter)
                        }
                    }
                }
            }
        }

        // Fim do jogo
        if (model.isWordGuessed) {
            view.showVictory(model)
        } else {
            view.showDefeat(model)
        }
    }
}