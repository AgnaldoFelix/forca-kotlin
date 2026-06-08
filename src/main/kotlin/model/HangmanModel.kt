package model

class HangmanModel(wordList: List<String>) {
    private val targetWord: String = wordList.random().lowercase()
    private val guessedLetters = mutableSetOf<Char>()
    private var errors = 0
    val maxErrors = 6

    val isGameOver: Boolean get() = errors >= maxErrors || isWordGuessed
    val isWordGuessed: Boolean get() = targetWord.all { it in guessedLetters }
    val remainingAttempts: Int get() = (maxErrors - errors).coerceAtLeast(0)
    val usedLetters: String get() = guessedLetters.sorted().joinToString(", ").uppercase()

    fun getWord(): String = targetWord

    fun guessLetter(letter: Char): Boolean {
        val lowerLetter = letter.lowercaseChar()

        if (lowerLetter in guessedLetters) {
            return false  // letra repetida
        }

        guessedLetters.add(lowerLetter)  // ← ADICIONA ANTES de verificar!

        return if (lowerLetter in targetWord) {
            true   // acertou
        } else {
            errors++  // errou
            false
        }
    }

    fun currentWordProgress(): String {
        return targetWord.map { if (it in guessedLetters) it else '_' }.joinToString(" ")
    }

    fun getHangmanDrawing(): String {
        val stages = listOf(
            // 0 erros
            """
            +---+
            |   |
                |
                |
                |
                |
            =======
            """.trimIndent(),
            // 1 erro - cabeça
            """
            +---+
            |   |
            0   |
                |
                |
                |
            =======
            """.trimIndent(),
            // 2 erros - tronco
            """
            +---+
            |   |
            0   |
            |   |
                |
                |
            =======
            """.trimIndent(),
            // 3 erros - braço esquerdo
            """
            +---+
            |   |
            0   |
           /|   |
                |
                |
            =======
            """.trimIndent(),
            // 4 erros - braço direito
            """
            +---+
            |   |
            0   |
           /|\  |
                |
                |
            =======
            """.trimIndent(),
            // 5 erros - perna esquerda
            """
            +---+
            |   |
            0   |
           /|\  |
           /    |
                |
            =======
            """.trimIndent(),
            // 6 erros - completo
            """
            +---+
            |   |
            0   |
           /|\  |
           / \  |
                |
            =======
            """.trimIndent()
        )
        return stages[errors]
    }
}