import pounce.Pounce
import java.text.SimpleDateFormat
import java.util.*

const val NAME_PADDING = 16

fun getTimestamp(): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())

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

    // continuous polling
    while (true) {
        client.update(
                { rec, status -> println("${getTimestamp()} >> ${rec.subject}: ${rec.status} -> $status") },
                { rec, status -> println("${getTimestamp()} >> ${rec.subject}: ${rec.status} -> $status") })

        Thread.sleep(client.requestIntervalMillis.toLong())
    }
}
