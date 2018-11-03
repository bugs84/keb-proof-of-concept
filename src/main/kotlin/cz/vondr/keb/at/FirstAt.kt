package cz.vondr.keb.at

import kotlin.reflect.KClass

fun main(args: Array<String>) {
    println("Start")
    val myTest = MyTest()
    myTest.firstTest()
    myTest.secondImprovedTest()
}



open class BasePage {
    open fun at(): Boolean {
        return true
    }
}

class FirstPage : BasePage() {
    fun firstMethod() {
        println("first method")
    }
}

class SecondPage : BasePage() {
    fun secondMethod() {
        println("second method")
    }
}

open class TestBase {
    lateinit var page: Any

    fun <P : BasePage> at(pageClass: KClass<P>, body: (P) -> Unit = {}) {
        val page = createInstance(pageClass)
        validateAtPage(page, pageClass)
        body.invoke(page)
    }

    private fun <P : BasePage> createInstance(pageClass: KClass<P>): P {
        return pageClass.java.newInstance()
    }

    private fun <P : BasePage> validateAtPage(page: P, pageClass: KClass<P>) {
        if (!page.at()) {
            throw IllegalStateException("Page validation failed. '$pageClass'")
        }
    }
}

class MyTest : TestBase() {
    fun firstTest() {

        page = FirstPage();
        {
            val page = page
            if (page is FirstPage) {
                page.firstMethod()
            }
        }.invoke()

        page = SecondPage();
        {
            val page = page
            if (page is SecondPage) {
                page.secondMethod()
            }
        }.invoke()

    }

    fun secondImprovedTest() {


        at(FirstPage::class) { page ->
            page.firstMethod()
        }

        at(SecondPage::class) { secondPage ->
            secondPage.secondMethod()
        }

        at(SecondPage::class) {
            //TODO kez by slo nadefinovat, ze to neni it, ale page
            it.secondMethod()
        }

        at(SecondPage::class) // just validate, that i am on correct page
    }


}
