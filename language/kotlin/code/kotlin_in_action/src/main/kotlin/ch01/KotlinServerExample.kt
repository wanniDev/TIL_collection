package ch01

import kotlinx.html.stream.createHTML
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.tr

class KotlinServerExample {
    fun renderPersonList(persons: Collection<Person>) = createHTML().table {
        // 일반적인 코틀린 루프
        for (person in persons) {
            // HTML 태그로 변환되는 함수들
            tr {
                td { +person.name }
                td { +person.age!! }
            }
        }
    }
}