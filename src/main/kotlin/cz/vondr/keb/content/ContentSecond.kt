package cz.vondr.keb.content

import kotlin.LazyThreadSafetyMode.NONE
import kotlin.reflect.KClass


//class ContentWrapper<T>(clos:T) :BasePage by clos{
//}

//class ContentWrapper<T>(clos:T) :T{
//}

//TODO dalsi napad, jestli by to neslo nejak pres gettery

//TODO tohle zni slibne z toho by mohlo jit neco vykresat
//https://kotlinlang.org/docs/reference/delegated-properties.html


open class BasePage {
    open fun at(): Boolean {
        return true
    }
}


class FirstPage : BasePage() {

    val button1 = { println("Button1Invoked"); "button1" }

    fun firstMethod() {
        println("first method")
    }
}

class SecondPage : BasePage() {


    val button2: String
        get() {
            println("Button2Invoked"); return "button2"
        }

    val button3: String by lazy {
        println("Button3Invoked"); "button3"
    }

    val button4: String by lazy(mode = NONE) {
        println("Button4Invoked"); "button4"
    }

    fun secondMethod() {
        println("second method")
    }
}

open class TestBase {
    lateinit var page: Any

    fun <P : BasePage> at(pageClass: KClass<P>, body: P.() -> Unit = {}) {
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


        at(FirstPage::class) {
            firstMethod()
            println(button1.invoke())
        }

        at(SecondPage::class) {
            secondMethod()

            println("BUTTON 2")
            println(button2)
            println(button2)
            println("BUTTON 3")
            println(button3)
            println(button3)
        }

        at(SecondPage::class) {
            secondMethod()
        }

        at(SecondPage::class) // just validate, that i am on correct page

    }


}


fun main(args: Array<String>) {
    println("Start")
    val myTest = MyTest()
    myTest.firstTest()
    myTest.secondImprovedTest()
}
