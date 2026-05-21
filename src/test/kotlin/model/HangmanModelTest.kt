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

}

