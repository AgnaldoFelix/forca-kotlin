import controller.HangmanController
import model.HangmanModel
import view.HangmanView

fun main() {
    val wordBank = listOf(
        "paralelepipedo",
        "desenvolvimento",
        "programacao",
        "computador",
        "kotlin",
        "android",
        "github",
        "teclado",
        "mouse",
        "internet"
    )

    val model = HangmanModel(wordBank)
    val view = HangmanView()
    val controller = HangmanController(model, view)

    controller.start()
}