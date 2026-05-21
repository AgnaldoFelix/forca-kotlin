import controller.HangmanController
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.HangmanModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import view.HangmanView


class HangmanControllerTest {

    private lateinit var model: HangmanModel
    private lateinit var view: HangmanView
    private lateinit var controller: HangmanController

    @BeforeEach
    fun setup() {
        // Arrange - preparar mocks e objetos reais
        model = HangmanModel(listOf("kotlin"))
        view = mockk(relaxed = true)  // View mockada
        controller = HangmanController(model, view)
    }

    @Test
    fun `nome do teste descritivo`() {
        // Act - executar (pode exigir simular múltiplas entradas)


        // Assert - verificar interações com mock
        verify(exactly = 1) { view.showTitle() }
    }
}