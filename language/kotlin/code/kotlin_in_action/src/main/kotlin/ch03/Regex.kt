package ch03

fun parsePath(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val result = regex.matchEntire(path)
    if (result != null) {
        val (directory, filename, extension) = result.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}