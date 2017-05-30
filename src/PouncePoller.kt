import pounce.Pounce

const val NAME_PADDING = 16

fun main(args: Array<String>) {
    val client = Pounce()
    with (client) {
        addFurre("Artex")
        addFurre("CPU")
        addFurre("Winter")
        addDream("furc://naiagreen")
        addDream("furc://vinca")
    }

    client.update(
            { rec, status -> println(">> ${rec.subject}: ${rec.status} -> $status") },
            { rec, status -> println(">> ${rec.subject}: ${rec.status} -> $status") })

    println()
    println("Furres:")
    for (rec in client.furres) {
        println("  * ${rec.subject.toString().padEnd(NAME_PADDING)}  [${rec.status}]")
    }

    println()
    println("Dreams:")
    for (rec in client.dreams) {
        println("  * ${rec.subject.toString().padEnd(NAME_PADDING)}  [${rec.status}]")
    }
}
