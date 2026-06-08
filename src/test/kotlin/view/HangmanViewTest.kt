import model.HangmanModel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import view.HangmanView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class HangmanViewTest {

    private lateinit var model: HangmanModel
    private lateinit var view: HangmanView
    private lateinit var outputStream: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        model = HangmanModel(listOf("kotlin"))
        view = HangmanView()
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    private fun getOutput(): String = outputStream.toString()

    private fun resetOutput() {
        outputStream.reset()
    }

    //  EXIBIÇÃO DE TEXTO

    @Test
    fun `deve exibir titulo ao iniciar o jogo`() {

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        val viewReal = HangmanView()

        viewReal.showTitle()

        val output = outputStream.toString()
        assertTrue(output.contains("=== JOGO DA FORCA ==="))

        System.setOut(System.out)
    }


    @Test
    fun `deve exibir estado do jogo corretamente`() {

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        val modelReal = HangmanModel(listOf("kotlin"))
        val viewReal = HangmanView()

        viewReal.showGameState(modelReal)

        val output = outputStream.toString()
        assertTrue(output.contains("+---+"))
        assertTrue(output.contains("Palavra:"))
        assertTrue(output.contains("Letras usadas:"))
        assertTrue(output.contains("Tentativas restantes:"))

        System.setOut(System.out)
    }


    // ENTRADA DO USUÁRIO

    @Test
    fun `deve retornar o primeiro caractere da entrada`() {
        val input = "a\n"
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)

        val view = HangmanView()
        val result = view.askForLetter()

        assertEquals('a', result)

        System.setIn(System.`in`)
    }

    @Test
    fun `deve retornar espaco quando entrada for vazia`() {
        val input = "\n"
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)

        val view = HangmanView()

        val result = view.askForLetter()

        assertEquals(' ', result)

        System.setIn(System.`in`)
    }

    @Test
    fun `deve remover espacos em branco da entrada`() {
        val input = "  a  \n"
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)

        val view = HangmanView()

        val result = view.askForLetter()

        assertEquals('a', result)

        System.setIn(System.`in`)
    }

    // MENSAGEM DE ERRO

    @Test
    fun `deve exibir mensagem de erro para entrada invalida`() {
        val mensagem = "Nenhuma letra informada. Tente novamente."

        view.showInvalidInput(mensagem)

        val output = getOutput()
        assertTrue(output.contains(mensagem))
        assertTrue(output.contains("\n"))
    }

    @Test
    fun `deve exibir erro generico quando mensagem for passada`() {
        val mensagem = "Erro genérico"

        resetOutput()
        view.showInvalidInput(mensagem)

        val output = getOutput()
        assertTrue(output.contains(mensagem))
    }

    //  MENSAGENS DE FEEDBACK

    @Test
    fun `deve exibir mensagem de acerto quando letra correta`() {
        val letter = 'k'

        view.showFeedback(true, letter)

        val output = getOutput()
        assertTrue(output.contains("Boa!"))
        assertTrue(output.contains("'k'"))
        assertTrue(output.contains("está na palavra"))
        assertTrue(output.contains("\n"))
    }

    @Test
    fun `deve exibir mensagem de erro quando letra incorreta`() {
        val letter = 'z'

        view.showFeedback(false, letter)

        val output = getOutput()
        assertTrue(output.contains("não está na palavra"))
        assertTrue(output.contains("'z'"))
        assertTrue(output.contains("!"))
        assertTrue(output.contains("\n"))
    }

    @Test
    fun `deve exibir mensagem de acerto com qualquer letra`() {
        listOf('a', 'm', 'x', 'Z').forEach { letter ->
            resetOutput()
            view.showFeedback(true, letter)
            val output = getOutput()
            assertTrue(output.contains("'$letter'"))
        }
    }

    @Test
    fun `deve exibir mensagem de erro com qualquer letra`() {
        listOf('1', '@', '#', ' ').forEach { letter ->
            resetOutput()
            view.showFeedback(false, letter)
            val output = getOutput()
            assertTrue(output.contains("'$letter'"))
        }
    }

    // MENSAGENS DE LETRA REPETIDA

    @Test
    fun `deve exibir mensagem de letra repetida`() {
        val letter = 'a'

        view.showRepeatedLetter(letter)

        val output = getOutput()
        assertTrue(output.contains("Você já tentou a letra 'a'"))
        assertTrue(output.contains("Tente outra"))
        assertTrue(output.contains("\n"))
    }

    @Test
    fun `deve exibir mensagem de repeticao para qualquer letra`() {
        listOf('a', 'b', 'c', 'z', 'x', 'y').forEach { letter ->
            resetOutput()
            view.showRepeatedLetter(letter)
            val output = getOutput()
            assertTrue(output.contains("'$letter'"))
        }
    }

    // MENSAGENS DE FIM DE JOGO

    @Test
    fun `deve exibir mensagem de vitoria`() {
        // Arrange - precisa de um modelo com palavra completa
        val modelVencedor = HangmanModel(listOf("abc"))
        modelVencedor.guessLetter('a')
        modelVencedor.guessLetter('b')
        modelVencedor.guessLetter('c')

        view.showVictory(modelVencedor)

        val output = getOutput()
        assertTrue(output.contains("Parabéns! Você venceu!"))
        assertTrue(output.contains("+---+"))  // desenho da forca
        assertTrue(output.contains("a b c"))  // palavra completa
    }

    @Test
    fun `deve exibir mensagem de derrota com a palavra revelada`() {
        val modelPerdedor = HangmanModel(listOf("secret"))

        view.showDefeat(modelPerdedor)

        val output = getOutput()
        assertTrue(output.contains("Você perdeu!"))
        assertTrue(output.contains("A palavra era: secret"))
        assertTrue(output.contains("Letras usadas:"))
        assertTrue(output.contains("Tentativas restantes:"))
        assertTrue(output.contains("+---+"))  // desenho da forca
    }

    @Test
    fun `deve exibir o desenho da forca na vitoria`() {
        val model = HangmanModel(listOf("abc"))
        model.guessLetter('a')
        model.guessLetter('b')
        model.guessLetter('c')

        view.showVictory(model)

        val output = getOutput()
        assertTrue(output.contains("+---+"))
        assertTrue(output.contains("==="))
    }

    @Test
    fun `deve exibir o desenho da forca na derrota`() {
        val model = HangmanModel(listOf("abc"))

        view.showDefeat(model)

        val output = getOutput()
        assertTrue(output.contains("+---+"))
        assertTrue(output.contains("==="))
    }

    @Test
    fun `deve exibir palavra completa na vitoria`() {
        val model = HangmanModel(listOf("kotlin"))
        model.guessLetter('k')
        model.guessLetter('o')
        model.guessLetter('t')
        model.guessLetter('l')
        model.guessLetter('i')
        model.guessLetter('n')

        view.showVictory(model)

        val output = getOutput()
        assertTrue(output.contains("k o t l i n"))
    }

    @Test
    fun `deve exibir palavra secreta na derrota`() {
        val model = HangmanModel(listOf("python"))

        view.showDefeat(model)

        val output = getOutput()
        assertTrue(output.contains("A palavra era: python"))
    }

}