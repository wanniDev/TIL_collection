package ch04

class Button : Clickable, Focusable {
    override fun click() = println("Button clicked!!")
    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

fun main() {
    val button = Button()
    button.click()
    button.showOff()
    button.setFocus(true)
}