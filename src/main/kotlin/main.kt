import parser.Parser

fun main(args: Array<String>) {
    val parser = Parser("../wuf-M1/test")
    parser.parse("sample.txt")
}