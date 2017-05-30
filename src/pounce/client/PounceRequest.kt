package pounce.client

import furcadia.Dream
import furcadia.Furre

class PounceRequest {
    val furres: HashSet<Furre> = hashSetOf()
    val furreShortnames: Set<String>
        get() = furres.map { f -> f.name.shortname }.toSet()

    val dreams: HashSet<Dream> = hashSetOf()
    val dreamShortnames: Set<String>
        get() = dreams.map { d -> "${d.uploaderName.shortname}:${d.dreamName.shortname}".trimEnd(':') }.toSet()

    fun add(furre: Furre) { furres.add(furre) }
    fun add(dream: Dream) { dreams.add(dream) }
}
