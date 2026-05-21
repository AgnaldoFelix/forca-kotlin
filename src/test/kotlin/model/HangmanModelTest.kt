import model.HangmanModel
import org.junit.jupiter.api.Assertions.*  // Para os asserts
import org.junit.jupiter.api.BeforeEach     // Para setup antes de cada teste
import org.junit.jupiter.api.Test           // Para marcar o método como teste



class HangmanModelTest {
    private lateinit var model: HangmanModel

    @BeforeEach
    fun setup() {
        model = HangmanModel(listOf("kotlin"))
    }

//  Inicialização

    @Test
    fun `deve começar com 6 tentativas restantes`() {

        val tentativas = model.remainingAttempts

        assertEquals(6, tentativas)
    }

    @Test
    fun` deve iniciar com lista de letras usadas vazia`() {

        val letrasUsadas = model.usedLetters

        assertEquals("", letrasUsadas)
    }

    @Test
    fun `deve começar com o jogo ativo`() {

        val gameOver = model.isGameOver

        assertFalse(gameOver)
    }

    @Test
    fun` deve começar com jogador não vencedor`() {

        val venceu = model.isWordGuessed

        assertFalse(venceu)
    }


//   Acertos

    @Test
    fun `deve retornar true quando a letra existe na palavra`() {

        val model = HangmanModel(listOf("kotlin"))

        val resultado = model.guessLetter('k')

        assertTrue(resultado)
    }

    @Test
    fun `deve mostrar a letra acertada no progresso da palavra`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('k')
        val progresso = model.currentWordProgress()

        assertEquals("k _ _ _ _ _", progresso)

    }

    @Test
    fun `deve manter as tentativas restantes ao acertar letra`() {
        val model = HangmanModel(listOf("kotlin"))

        val tentativaAntes = model.remainingAttempts
        model.guessLetter('k')

        val tentativasDepois = model.remainingAttempts

        assertEquals(tentativasDepois, tentativaAntes)
    }



    @Test
    fun `deve adicionar letra acertada a lista de letras usadas`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('k')
        val letrasUsadas = model.usedLetters

        assertTrue(letrasUsadas.contains('K'))

    }

}

