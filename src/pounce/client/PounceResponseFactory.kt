package pounce.client

internal class PounceResponseFactory {
    fun parse(lines: List<String>): PounceResponse {
        var numFurresOnline: Int = 0
        var numDreamsOnline: Int = 0
        var pollInterval: Int = 30000
        val furresOnline: HashSet<String> = hashSetOf()
        val dreamsOnline: HashSet<String> = hashSetOf()

        for (line in lines.filter { l -> l.isNotEmpty() }) {
            when (line[0]) {
                'T' -> pollInterval = line.trim().substring(1).toInt()
                'O' -> numFurresOnline = line.trim().substring(1).toInt()
                'D' -> numDreamsOnline = line.trim().substring(1).toInt()
                '@' -> furresOnline.add(line.split(" ")[1])
                '#' -> dreamsOnline.add(line.substring(2))
            }
        }

        return PounceResponse(numFurresOnline, numDreamsOnline, pollInterval, furresOnline, dreamsOnline)
    }
}
