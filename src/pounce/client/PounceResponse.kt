package pounce.client;

internal class PounceResponse(
        val numFurresOnline: Int,
        val numDreamsOnline: Int,
        val pollInterval: Int,
        val furresOnline: Set<String>,
        val dreamsOnline: Set<String>)
