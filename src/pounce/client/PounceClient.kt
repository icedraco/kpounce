package pounce.client

import java.util.*

internal class PounceClient {
    var url: String = "http://on.furcadia.com/q/"

    private val webClient = WebClient()

    fun request(request: PounceRequest): PounceResponse {
        val lines = webClient.get(createUrl(request))
        return PounceResponseFactory().parse(lines)
    }

    private fun createUrl(request: PounceRequest): String {
        val lookupParams =
                request.furreShortnames.map { s -> "u[]=$s" } +
                        request.dreamShortnames.map { s -> "d[]=$s"}

        val ts = (Date().time / 1000).toInt()
        return "$url?$ts&${lookupParams.joinToString("&")}"
    }
}
