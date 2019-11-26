import kotlin.reflect.KProperty

fun main(){
    val baseInstance = Example(10)
    baseInstance.print()
    Derived(baseInstance).print()
    println(lazyValue)
    println(lazyValue)
}

//Lazy
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

// inline function explanation
// https://medium.com/@agrawalsuneet/inline-function-kotlin-3f05d2ea1b59

interface Base {
    fun print()
}


class Example(val x: Int): Base {
    override fun print() {
        println(x)
    }
}

class Derived(baseInstance: Base) : Base by baseInstance

class Delegate {
    operator fun getValue(example: Example, property: KProperty<*>): String {
        return "$example, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(example: Example, property: KProperty<*>, s: String) {
        println("$s has been assigned to '${property.name}' in $example.")
    }
}