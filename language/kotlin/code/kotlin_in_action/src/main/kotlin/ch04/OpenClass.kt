package ch04

// 코틀린 클래스는 기본적으로 final 이다. 따라서 open 키워드가 없으면 확장이 불가능하다.
open class RichButton : Clickable {
    fun disable() {} // final 함수이다. 따라서, 오버라이드가 불가하다.
    open fun animate() {} // 열려있는 함수이기 때문에 하위 클래스가 오버라이드할 수 있다.
    override fun click() {} // 이미 상위 클래스에서 오버라이드한 메소드라서, 이 메소드는 기본적으로 열려있다.
}

open class RichButton2 : Clickable {
    final override fun click() {} // 기본적으로 열려있는 오버라이드 된 메소드에 확장 불가라는 제약을 걸어주었다.
}

abstract class Animated {
    abstract fun animate() // 이 함수는 추상 함수다. 따라서 하위 클래스에서 반드시 오버라이드 해야한다.

    open fun stopAnimating() {}
    // 추상 클래스에 속했더라도 비추상 함수는 기본적으로 final 이다. 따라서 확장을 원한다면, open 키워드가 있어야 한다.
    fun animateTwice() {}
}

interface InterfaceMember {
    val a: String
    // 인터페이스 멤버의 경우 final, open, abstract 를 사용하지 않는다.
    // 인터페이스 멤버는 항상 열려 있으며 final 로 변경할 수 없다.
}