package ch01

data class Person(val name:String, val age:Int? = null)

fun main(args: Array<String>) { // 최상위 함수
    val persons = listOf(Person("Sam"), Person("Choi", age = 32)) // 이름 붙은 파라미터

    val oldest = persons.maxBy { it.age ?: 0 } // 람다식, 엘비스 연산자
    println("Oldest : $oldest") // 자동 toString
}
