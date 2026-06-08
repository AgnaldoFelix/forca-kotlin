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
            view.showGameState(model)

            val input = view.askForLetterInput()

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
                    val isRepeated = model.usedLetters.contains(letter.uppercase())
                    val wasCorrect = if (!isRepeated) model.guessLetter(letter) else false

                    when {
                        wasCorrect -> {
                            view.showFeedback(true, letter)
                        }
                        isRepeated -> {
                            view.showRepeatedLetter(letter)
                        }
                        else -> {
                            view.showFeedback(false, letter)
                        }
                    }
                }
            }
        }

        if (model.isWordGuessed) {
            view.showVictory(model)
        } else {
            view.showDefeat(model)
        }
    }

}