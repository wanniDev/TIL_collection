package practice

import java.io.File
import javax.sql.DataSource

// class
/**
 * 코틀린에서 클래스는 class 라는 예약어와 타입을 식별하게 해주는 텍스트를 작성함으로서 선언을 하고 사용할 수 있다.
 * 만약 내용물이 비어있다면, 클래스의 바디에 해당하는 {} 대괄호를 입력할 필요가 없다.
 */
class Person {/**/}
class Empty

// 생성자
// 코틀린에서 생성자는 하나 이상으로 선언이 가능하다. 주요 생성자는 클래스 헤더에 constructor 라는 예약어와 () 괄호를 통해 선언이 가능하다.
// 만약 클래스에 생성자를 하나만 할당한다면, 다음과 같이 constructor 예약어를 생략할 수 있다.
class Sample1(value: String) {/**/}

class JavaBeanSpecInKotlin {
    var value1: String? = null
    var value2: String? = null
    var int1 = 0

    constructor() {}
    constructor(value1: String?, value2: String?, int1: Int) {
        this.value1 = value1
        this.value2 = value2
        this.int1 = int1
    }
}

class DtoSpecInKotlin {
    var constructorString1: String? = null
        private set
    var constructorInt1 = 0
        private set

    var varStringGetterSetter: String? = null
    var varIntGetterSetter = 0

    val varStringGetter: String? = null
    val vatIntGetter = 0

    private constructor() {}
    constructor(finalVal: String?, finalInt: Int) {
        this.constructorString1 = finalVal
        this.constructorInt1 = finalInt
    }
}

data class Dto(val value1: String) {
    var age: Int? = 0

}

fun main() {
    val dto = Dto("value1")
    dto.age = 9
}
// 클래스 멤버
/**
 * - private : 클래스 내부에서만 공유
 * - protected : 클래스 내부 혹은 해당 클래스를 확장하는 서브 클래스만 공유
 * - internal : 모듈내에서 internal 이라는 예약어를 선언한 멤버 끼리만 공유
 * - public : 말 그대로 퍼블릭
 * - open : 코틀린은 기본적으로 final 키워드를 내장하고 있기에 만약 확장을 원한다면, 해당 키워드를 입력해야 한다.
 * - data : 데이터를 홀딩할 때, 사용하는 클래스 키워드
 */
internal class MemberVal {
    var hello: Int? = 0
}

// data class
/**
 * 데이터를 홀딩할 때 사용하는 클래슨데, 다음과 같은 제약이 있다.
 * - primary 생성자는 적어도 하나의 파라미터를 가져야한다.
 * - 모든 파라미터는 var이나 val 키워드를 가지고 있어야 한다.
 * - Data 클래스는 abstract, open, sealed, inner 로 활용될 수 없다.
 */
data class SampleDataClass(val name: String, val age: Int)

// sealed class
/**
 * sealed 키워드가 있는 클래스와 인터페이스는 클래스 상속을 좀 더 엄격하게 제어한다.
 * 이 키워드는 해당 클래스를 해당 모듈에서만 상속하게한다. 즉 third-party client가 sealed 처리된 클래스를 확장하지 못하도록 제어한다.
 */

sealed interface Error
sealed class IOError : Error

class FileReadError(val file: File): IOError()

object RuntimeError : Error

// object
/**
 * 익명 객체 혹은 함수나 프로퍼티
 */

// open class
