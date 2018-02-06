//package cz.vondr.keb.content
//
//import cz.vondr.keb.at.BasePage
//
//open class BasePage {
//
//
//    open fun at(): Boolean {
//        return true
//    }
//}
//
//
//class FirstPage : BasePage() {
//    val content = hashMapOf(
//            "Element1" to "AAA",
//            "Element2" to "AAA",
//            "Element3" to { println("Closure"); "AAA"}
//    )
//
//
//    fun firstMethod() {
//        println("first method")
//    }
//}
//
//class SecondPage : BasePage() {
//
//    val button = {"Select button"}
//
//    fun secondMethod() {
//        println("second method")
//    }
//}
//
//fun main(args: Array<String>) {
//
//}