import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import view.HangmanView
import model.HangmanModel
import controller.HangmanController
import org.junit.jupiter.api.Assertions.assertTrue

class HangmanControllerTest {

    private lateinit var model: HangmanModel
    private lateinit var view: HangmanView
    private lateinit var controller: HangmanController

    @BeforeEach
    fun setup() {
        model = HangmanModel(listOf("kotlin"))
        view = mockk(relaxed = true)  // View mockada
        controller = HangmanController(model, view)
    }


    @Test
    fun `deve mostrar titulo ao iniciar o jogo`() {
        val model = HangmanModel(listOf("a"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        var chamou = false
        every { view.askForLetterInput() } answers {
            if (!chamou) {
                chamou = true
                "a"
            } else {
                throw RuntimeException("Fim do teste")
            }
        }

        try {
            controller.start()
        } catch (e: RuntimeException) {
            println("Teste interrompido: ${e.message}")
        }

        verify(exactly = 1) { view.showTitle() }
    }

    @Test
    fun `deve mostrar estado e pedir letra durante o jogo`() {
        val model = HangmanModel(listOf("a"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every { view.askForLetterInput() } returnsMany listOf("a")

        controller.start()

        verify(atLeast = 1) { view.showGameState(any()) }
        verify(exactly = 1) { view.askForLetterInput() }
        verify { view.showFeedback(true, 'a') }
    }

    @Test
    fun `deve mostrar feedback de erro quando letra nao existe`() {
        val model = HangmanModel(listOf("xyz"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every { view.askForLetterInput() } returnsMany listOf("a", "b", "c", "d", "e", "f")

        controller.start()

        verify(atLeast = 1) { view.showFeedback(false, any()) }
    }

    //  Validação de Entrada - Vazio

    @Test
    fun `deve mostrar erro quando entrada for vazia`() {
        val model = HangmanModel(listOf("a"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        var chamadas = 0
        every { view.askForLetterInput() } answers {
            chamadas++
            when (chamadas) {
                1 -> ""
                2 -> "a"
                else -> throw RuntimeException("Fim do teste")
            }
        }

        try {
            controller.start()
        } catch (e: RuntimeException) {

        }

        verify { view.showInvalidInput("Nenhuma letra informada. Tente novamente.") }
    }

    //  Validação de Entrada - Múltiplas Letras

    @Test
    fun `deve mostrar erro quando entrada tiver multiplas letras`() {
        val model = HangmanModel(listOf("abc"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every { view.askForLetterInput() } returnsMany listOf("abc", "a", "b", "c")

        controller.start()

        verify { view.showInvalidInput("Digite apenas uma letra por vez.") }
    }


    //  Validação de Entrada - Números

    @Test
    fun `deve mostrar erro quando entrada contiver numeros`() {
        val model = HangmanModel(listOf("abc"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every { view.askForLetterInput() } returnsMany listOf("1", "2", "3", "a", "b", "c")

        controller.start()

        verify(atLeast = 1) { view.showInvalidInput(any()) }

        verify { view.showVictory(any()) }
    }


    //  Validação de Entrada - Caracteres Especiais

    @Test
    fun`deve mostrar erro para qualquer caractere nao letra`(){
        val model = HangmanModel(listOf("casa"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every {view.askForLetterInput() } returnsMany listOf("@", "#", "!" ,"c", "a", "s", "a")

        controller.start()

        verify(atLeast = 3) { view.showInvalidInput(any()) }

        verify { view.showVictory(any()) }
    }


    // Validação de repetição de letras

    @Test
    fun`deve mostrar mensagem quando letra for repetida`(){
        val model = HangmanModel(listOf("caneta"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every {view.askForLetterInput() } returnsMany listOf("c","c", "a", "n", "e" ,"t" ,"a")

        controller.start()

        verify {view.showRepeatedLetter('c')}

        verify { view.showFeedback(true, 'c') }

        verify { view.showVictory(any()) }
    }


    // Finalização do Jogo - Vitória

    @Test
    fun`deve mostrar vitória ao finalizar o jogo com sucesso`(){
        val model = HangmanModel(listOf("winner"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every {view.askForLetterInput() } returnsMany listOf("w", "i", "n" ,"e" ,"r" )

        controller.start()

        verify(atLeast = 1) { view.showVictory(any()) }
    }
    
    
    //  Finalização do Jogo - Derrota

    @Test
    fun`deve mostrar derrota ao finalizar o jogo sem sucesso`(){
        val model = HangmanModel(listOf("kotlin"))
        val view = mockk<HangmanView>(relaxed = true)
        val controller = HangmanController(model, view)

        every {view.askForLetterInput() } returnsMany listOf("j","a","v","s","p", "x","z",)

        controller.start()

        verify(atLeast = 1) { view.showDefeat(any()) }
    }
}