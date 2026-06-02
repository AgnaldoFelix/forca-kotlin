import model.HangmanModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import org.junit.jupiter.api.assertThrows

class HangmanModelTest {
    private lateinit var model: HangmanModel

    @BeforeEach
    fun setup() {
        model = HangmanModel(listOf("kotlin"))
    }

    // ========== INICIALIZAÇÃO ==========
    @Test
    fun `deve inicializar corretamente`() {
        assertEquals(6, model.remainingAttempts)
        assertEquals("", model.usedLetters)
        assertFalse(model.isGameOver)
        assertFalse(model.isWordGuessed)
    }

    // ========== ACERTO ==========
    @Test
    fun `deve processar acerto de letra corretamente`() {
        val model = HangmanModel(listOf("kotlin"))

        val acertou = model.guessLetter('k')

        assertTrue(acertou)
        assertEquals("k _ _ _ _ _", model.currentWordProgress())
        assertEquals(6, model.remainingAttempts)
        assertTrue(model.usedLetters.contains("K"))
        assertFalse(model.isGameOver)
        assertFalse(model.isWordGuessed)
    }

    // ========== ERRO ==========
    @Test
    fun `deve processar erro de letra corretamente`() {
        val model = HangmanModel(listOf("kotlin"))

        val acertou = model.guessLetter('x')

        assertFalse(acertou)
        assertEquals("_ _ _ _ _ _", model.currentWordProgress())
        assertEquals(5, model.remainingAttempts)
        assertTrue(model.usedLetters.contains("X"))
        assertFalse(model.isGameOver)
        assertFalse(model.isWordGuessed)
    }

    // ========== REPETIÇÃO ==========
    @Test
    fun `nao deve processar letra repetida`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('x')
        val tentativasAposPrimeiro = model.remainingAttempts
        val letrasUsadasAposPrimeiro = model.usedLetters

        val resultadoRepetido = model.guessLetter('x')

        assertFalse(resultadoRepetido)
        assertEquals(tentativasAposPrimeiro, model.remainingAttempts)
        assertEquals(letrasUsadasAposPrimeiro, model.usedLetters)
        assertEquals(5, model.remainingAttempts)
        assertEquals(1, model.usedLetters.count { it == 'X' })
    }

    // ========== VITÓRIA ==========
    @Test
    fun `deve vencer ao acertar todas as letras`() {
        val model = HangmanModel(listOf("abc"))

        model.guessLetter('a')
        model.guessLetter('b')
        val tentativasAntes = model.remainingAttempts
        val acertouUltima = model.guessLetter('c')

        assertTrue(acertouUltima)
        assertTrue(model.isWordGuessed)
        assertTrue(model.isGameOver)
        assertEquals(tentativasAntes, model.remainingAttempts)
        assertEquals("a b c", model.currentWordProgress())
    }

    // ========== DERROTA ==========
    @Test
    fun `deve perder ao atingir 6 erros`() {
        val model = HangmanModel(listOf("xyz"))

        listOf('a', 'b', 'c', 'd', 'e', 'f').forEach { model.guessLetter(it) }

        assertTrue(model.isGameOver)
        assertFalse(model.isWordGuessed)
        assertEquals(0, model.remainingAttempts)

        val tentativasAntes = model.remainingAttempts
        val resultado = model.guessLetter('x')
        val tentativasDepois = model.remainingAttempts

        assertTrue(resultado)
        assertEquals(tentativasAntes, tentativasDepois)
    }

    // ========== CASE INSENSITIVE ==========
    @Test
    fun `deve tratar letras maiusculas e minusculas de forma case-insensitive`() {
        val model = HangmanModel(listOf("kotlin"))

        val acertouMaiusculo = model.guessLetter('K')
        assertTrue(acertouMaiusculo)

        val progressoAposK = model.currentWordProgress()
        assertEquals("k _ _ _ _ _", progressoAposK)
        assertTrue(model.usedLetters.contains("K"))

        val acertouMinusculo = model.guessLetter('o')
        assertTrue(acertouMinusculo)

        val progressoAposO = model.currentWordProgress()
        assertEquals("k o _ _ _ _", progressoAposO)
        assertTrue(model.usedLetters.contains("O"))
    }

    // ========== FORMATAÇÃO ==========
    @Test
    fun `deve formatar saidas corretamente`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('c')
        model.guessLetter('a')
        model.guessLetter('b')

        val letrasUsadas = model.usedLetters
        assertEquals("A, B, C", letrasUsadas)

        model.guessLetter('k')
        model.guessLetter('o')

        val progresso = model.currentWordProgress()
        assertEquals("k o _ _ _ _", progresso)
    }


    // ========== PALAVRAS ==========
    @Test
    fun `deve funcionar com palavra de uma letra`() {
        val model = HangmanModel(listOf("a"))

        val acertou = model.guessLetter('a')

       assertTrue(acertou)
       assertTrue(model.isWordGuessed)
       assertTrue(model.isGameOver)
       assertEquals("a", model.currentWordProgress())
    }

    @Test
    fun `deve revelar todas as ocorrencias de uma letra repetida`() {
        val model = HangmanModel(listOf("paralelepipedo"))

        model.guessLetter('p')
        val progresso = model.currentWordProgress()

        val quantidade = progresso.count { it == 'p' }
         assertEquals(3, quantidade)

        assertEquals("p _ _ _ _ _ _ _ p _ p _ _ _", progresso)
    }


    @Test
    fun `deve lancar excecao quando lista de palavras for vazia`() {
        assertThrows<NoSuchElementException> {
            HangmanModel(emptyList())
        }
    }

    @Test
    fun `deve converter palavras maiusculas para minusculas`() {
        val model = HangmanModel(listOf("KOTLIN"))

        val acertou = model.guessLetter('k')

        assertTrue(acertou)
        assertEquals("k _ _ _ _ _", model.currentWordProgress())
    }

// ========== LIMITES ==========

    @Test
    fun `nao deve alterar estado ao tentar jogar depois de perder`() {
        val model = HangmanModel(listOf("abc"))

        listOf('z', 'x', 'y', 'w', 'v', 'u').forEach { model.guessLetter(it) }

        val remainingAntes = model.remainingAttempts
        val gameOverAntes = model.isGameOver

        model.guessLetter('t')
        model.guessLetter('r')
        model.guessLetter('s')

        assertEquals(remainingAntes, model.remainingAttempts)
        assertEquals(gameOverAntes, model.isGameOver)
    }

    @Test
    fun `nao deve vencer ao acertar letra depois de perder`() {
        val model = HangmanModel(listOf("abc"))

        listOf('z', 'x', 'y', 'w', 'v', 'u').forEach { model.guessLetter(it) }

        val acertou = model.guessLetter('a')

        assertTrue(acertou)
        assertFalse(model.isWordGuessed)
        assertTrue(model.isGameOver)
        assertEquals(0, model.remainingAttempts)
    }
}