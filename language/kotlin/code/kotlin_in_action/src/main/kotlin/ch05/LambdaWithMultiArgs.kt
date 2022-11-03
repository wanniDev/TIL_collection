package ch05

import ch01.Person
import ch04.Button


fun main() {
    val sum = {x: Int, y: Int ->
        println("Computing the sum of $x and $y")
        x + y
    } // 람다가 여러 줄로 이뤄진 경우 본문의 맨 마지막에 있는 식이 람다의 결과가 된다.

    println(sum(1,3))

    fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
        messages.forEach {
            println("$prefix $it")
        }
    } // 자바와 마찬가지로 코틀린 람다는 함수의 파라미터를 람다식 내부에 활용할 수 있다.

    printMessageWithPrefix(listOf("403 Forbidden", "404 Not Found"), "Error")

    // 람다 안에서 바깥 함수의 로컬 변수 변경하기.
    // 코틀린의 람다는 자바와는 달리 final 이 아닌 변수도 접근이 가능하며, 따라서 변경도 가능하다.
    fun printProblemCounts(responses: Collection<String>) {
        var clientErrors = 0;
        var serverErrors = 0;
        responses.forEach{
            if (it.startsWith("4"))
                clientErrors++
            else if (it.startsWith("5"))
                serverErrors++
        }
        println("$clientErrors client errors, $serverErrors server errors")
    }

    printProblemCounts(listOf("403 Forbidden", "404 Not Found", "500 Internal Server Error"))

    // 위 두 개의 람다 예제에서 활용된 prefix, clientErrors, serverErrors와 같이 람다 안에서 사용하는 외부 변수를 '람다가 포획한 변수'라고 한다.


    // 한가지 주의할 점은 람다를 이벤트 핸들러와 같이 비동기적으로 실행되는 코드로 활용되는 경우 함수 호출이 끝난 다음에 로컬 변수가 변경될 수도 있다.
    fun tryToCountButtonClicks(button: Button): Int {
        var clicks = 0
//        button.onClick {clicks++}
        clicks++
        return clicks
    } // 따라서 이러한 코드는 버튼 클릭 횟수를 제대로 셀 수 없다.

    var getAge = Person::age // 자바와 마찬가지로 코틀린도 멤버 참조가 가능하다.

    fun salute() = println("Salute!")
    run(::salute) // 멤버 참조는 그 멤버를 호출하는 람다와 같은 타입이고 최상위에 선언된 함수나 프로퍼티를 참조할 수도 있다.

    fun sendEmail(person: Person, message: String): String {
        return "A e-mail sent from ${person.name} and it said '${message}'..."
    }

    val action = {person:Person, message:String -> sendEmail(person, message)}
//    val email = ::sendEmail(Person("wanni", 6), "츄르 내놔")
//    println(email)

    val createPerson = ::Person // ::를 통해 생성자 참조가 가능하고, 이걸 통해, 인스턴스 생성 과정을 직관적으로 수행할 수 있다.
    val w = createPerson("wanni", 6)


    // all, any, count, find
    val p1 = people.find { it.age == 22 }
    val p2 = people.all { it.name.startsWith("a") || it.name.startsWith("b") }
    val p3 = people.count {it.age >= 10}
    val p4 = people.any { it.age <= 99 }

    // groupBy
    val list = listOf("z", "fc", "a", "d")
    println(list.groupBy(String::first))

    println(people.groupBy { it.age })

    // 지연 계산(lazy) 컬렉션 연산
    // filter, map 은 리스트를 반환한다. 따라서 둘을 같이 쓸 경우, 리스트를 두 번 반환하여 리소스를 낭비하는 경우가 생긴다.
    // 이럴 때 시퀀스를 사용하면 이러한 낭비를 줄일 수 있다.
    val toList = people.asSequence()
        .map(Person::name)
        .filter { it.startsWith("T") }
        .toList()

    println(toList)

    // make sequence
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100)
    println(numbersTo100.sum())
}

data class Person(val name: String, val age: Int)
