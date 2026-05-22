import model.HangmanModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HangmanModelTest {
    private lateinit var model: HangmanModel

    @BeforeEach
    fun setup() {
        model = HangmanModel(listOf("kotlin"))
    }

    // ========== INICIALIZAÇÃO ==========

    @Test
    fun `deve começar com 6 tentativas restantes`() {
        val tentativasRestantes = model.remainingAttempts
        assertEquals(6, tentativasRestantes)
    }

    @Test
    fun `deve iniciar com lista de letras usadas vazia`() {
        val letrasUsadas = model.usedLetters
        assertEquals("", letrasUsadas)
    }

    @Test
    fun `deve começar com o jogo ativo`() {
        val jogoEstaTerminado = model.isGameOver
        assertFalse(jogoEstaTerminado)
    }

    @Test
    fun `deve começar com jogador não vencedor`() {
        val jogadorVenceu = model.isWordGuessed
        assertFalse(jogadorVenceu)
    }

    // ========== ACERTOS ==========

    @Test
    fun `deve retornar true quando a letra existe na palavra`() {
        val model = HangmanModel(listOf("kotlin"))
        val letraExiste = model.guessLetter('k')
        assertTrue(letraExiste)
    }

    @Test
    fun `deve mostrar a letra acertada no progresso da palavra`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('k')
        val progressoAtual = model.currentWordProgress()

        assertEquals("k _ _ _ _ _", progressoAtual)
    }

    @Test
    fun `deve manter as tentativas restantes ao acertar letra`() {
        val model = HangmanModel(listOf("kotlin"))

        val tentativasAntesDoAcerto = model.remainingAttempts
        model.guessLetter('k')
        val tentativasDepoisDoAcerto = model.remainingAttempts

        assertEquals(tentativasDepoisDoAcerto, tentativasAntesDoAcerto)
    }

    @Test
    fun `deve adicionar letra acertada a lista de letras usadas`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('k')
        val listaDeLetrasUsadas = model.usedLetters

        assertTrue(listaDeLetrasUsadas.contains('K'))
    }

    // ========== ERROS ==========

    @Test
    fun `deve retornar false quando letra NAO existe na palavra`() {
        val model = HangmanModel(listOf("kotlin"))

        val letraNaoExiste = model.guessLetter('z')

        assertFalse(letraNaoExiste)
    }

    @Test
    fun `deve diminuir 1 tentativa a cada erro`() {
        val model = HangmanModel(listOf("kotlin"))

        val tentativasAntesDoErro = model.remainingAttempts
        model.guessLetter('z')
        val tentativasDepoisDoErro = model.remainingAttempts

        assertEquals(5, tentativasDepoisDoErro)
    }

    @Test
    fun `deve adicionar letra errada a lista de letras usadas`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('z')
        val listaDeLetrasUsadas = model.usedLetters

        assertTrue(listaDeLetrasUsadas.contains("Z"))
    }

    @Test
    fun `deve ter 5 tentativas restantes apos 1 erro`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('y')
        val tentativasAposUmErro = model.remainingAttempts

        assertEquals(5, tentativasAposUmErro)
    }

    // ========== REPETIÇÃO DE LETRAS ==========

    @Test
    fun `deve retornar false ao tentar letra repetida`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('x')
        val letraRepetida = model.guessLetter('x')

        assertFalse(letraRepetida)
    }

    @Test
    fun `nao deve alterar tentativas restantes ao repetir letra`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('x')
        val tentativasAposPrimeiraTentativa = model.remainingAttempts

        model.guessLetter('x')
        val tentativasAposSegundaTentativa = model.remainingAttempts

        assertEquals(tentativasAposPrimeiraTentativa, tentativasAposSegundaTentativa)
    }

    @Test
    fun `deve manter 5 tentativas apos tentar letra repetida`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('x')
        model.guessLetter('x')

        val tentativasRestantes = model.remainingAttempts

        assertEquals(5, tentativasRestantes)
    }

    @Test
    fun `nao deve adicionar letra repetida ao conjunto de letras usadas`() {
        val model = HangmanModel(listOf("kotlin"))

        model.guessLetter('x')
        model.guessLetter('x')

        val quantidadeDeVezesQueAparece = model.usedLetters.count { it == 'X' }

        assertEquals(1, quantidadeDeVezesQueAparece)
    }
}