package view

import model.HangmanModel

class HangmanView {

    fun showTitle() {
        println("=== JOGO DA FORCA ===\n")
    }

    fun showGameState(model: HangmanModel) {
        println(model.getHangmanDrawing())
        println("\nPalavra: ${model.currentWordProgress()}")
        println("Letras usadas: [${model.usedLetters}]")
        println("Tentativas restantes: ${model.remainingAttempts}\n")
    }

    fun askForLetter(): Char {
        print("Digite uma letra: ")
        val input = readlnOrNull()?.trim() ?: ""
        return input.getOrNull(0) ?: ' '
    }

    fun showInvalidInput(message: String) {
        println("$message\n")
    }

    fun showFeedback(wasCorrect: Boolean, letter: Char) {
        if (wasCorrect) {
            println("Boa! A letra '$letter' está na palavra.\n")
        } else {
            println("A letra '$letter' não está na palavra!\n")
        }
    }

    fun showRepeatedLetter(letter: Char) {
        println("Você já tentou a letra '$letter'. Tente outra.\n")
    }

    fun showVictory(model: HangmanModel) {
        println(model.getHangmanDrawing())
        println("\nPalavra: ${model.currentWordProgress()}")
        println("Parabéns! Você venceu!")
    }

    fun showDefeat(model: HangmanModel) {
        println(model.getHangmanDrawing())
        println("\nPalavra: ${model.currentWordProgress()}")
        println("Letras usadas: [${model.usedLetters}]")
        println("Tentativas restantes: ${model.remainingAttempts}")
        println("Você perdeu! A palavra era: ${model.getWord()}")
    }
}