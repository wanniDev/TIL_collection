package practice

class JavaBeanSpec {
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