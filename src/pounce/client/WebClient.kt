package pounce.client

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

internal class WebClient {
    fun get(url: String): List<String> {
        val conn = URL(url).openConnection()
        val reader = BufferedReader(InputStreamReader(conn.getInputStream()))
        return reader.readLines()
    }
}