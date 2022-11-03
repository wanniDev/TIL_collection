package ch05

import ch01.Person

val people = listOf(Person("Ben", 99), Person("Tom", 11), Person("Jack", 12))
fun main() {
    people.maxBy({ p: Person -> p.age }) // 코틀린은 람다 자체를 인자로 할당할 수 있다.
    people.maxBy() { p: Person -> p.age  } // 람다가 마지막 argument 라면, 괄호 뒤에 람다를 둘 수 있다.
    people.maxBy { p:Person -> p.age } // 만약 람다가 마지막 argument 이면서 유일한 argument 라면 괄호를 아예 없앨 수 있다.
    people.maxBy { p -> p.age } // 람다의 파라미터를 제거하면, 컴파일러가 해당 파라미터의 타입을 추론한다.
    people.maxBy { it.age } // 람다 파라미터의 기본 명칭인 it를 활용하면, 파라미터가 하나일 경우에 한하여 해당 코드처럼 파라미터 이름을 지정하지 않은채로 람다를 진행할 수 있다.

    people.joinToString(" ", transform = {p: Person -> p.name}) // 인자로 넘긴 람다에 이름을 명시해놓음으로서, 코드의 의도를 명확히 표시하였다.
    people.joinToString(" ") { p: Person -> p.name } // joinToString api 가 익숙하지 않은 개발자는 이 코드가 오히려 이해하기 어려울 것이다.

    val getAge = {p: Person -> p.age} // 람다를 변수에 저장할 때는 파라미터 타입을 추론할 context 가 없기 때문에 반드시 파라미터 타입을 명시해야 한다.
    people.maxBy(getAge)
}