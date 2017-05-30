package pounce

import pounce.client.PounceClient
import pounce.client.PounceRequest
import furcadia.Dream
import furcadia.DreamUrl
import furcadia.Furre
import furcadia.Name
import java.util.*

const val DEFAULT_REQUEST_INTERVAL = 30000
const val MIN_REQUEST_INTERVAL = 1000

class Pounce {
    private val furreMap = hashMapOf<String, PounceRecord<Furre>>()
    private val dreamMap = hashMapOf<String, PounceRecord<Dream>>()
    private val client = PounceClient()

    val furres: List<PounceRecord<Furre>>
        get() = furreMap.values.toList()

    val dreams: List<PounceRecord<Dream>>
        get() = dreamMap.values.toList()

    var numFurresOnline: Int = 0
        get
        private set

    var numDreamsOnline: Int = 0
        get
        private set

    var requestIntervalMillis: Int = DEFAULT_REQUEST_INTERVAL
        get
        set(value) { field = if (value >= MIN_REQUEST_INTERVAL) value else DEFAULT_REQUEST_INTERVAL }

    var lastUpdate = Date()
        get
        private set

    fun addFurre(name: String) = addFurre(Furre(name))
    fun addFurre(name: Name) = addFurre(Furre(name))
    fun addFurre(furre: Furre): PounceRecord<Furre> {
        val k = key(furre)
        if (furreMap[k] == null)
            furreMap[k] = PounceRecord(furre)
        return furreMap[k]!!
    }
    fun remove(furre: Furre) = furreMap.remove(key(furre)) != null
    private fun key(furre: Furre) = furre.name.shortname

    fun addDream(dreamUrl: String) = addDream(DreamUrl.UrlParser().parse(dreamUrl))
    fun addDream(dreamUrl: DreamUrl) = addDream(Dream(dreamUrl.uploaderName, dreamUrl.dreamName))
    fun addDream(dream: Dream): PounceRecord<Dream> {
        val k = key(dream)
        if (dreamMap[k] == null)
            dreamMap[k] = PounceRecord(dream)
        return dreamMap[k]!!
    }
    fun remove(dream: Dream) = dreamMap.remove(key(dream)) != null
    private fun key(dream: Dream) = dream.dreamUrl.withoutCode().toString()

    fun update() {
        update({ _, _ -> /* nothing */ }, { _, _ -> /* nothing */ })
    }

    fun update(
            cbFurreStatusChange: (PounceRecord<Furre>, PounceRecord.Status) -> Unit,
            cbDreamStatusChange: (PounceRecord<Dream>, PounceRecord.Status) -> Unit) {

        // prepare request
        val request = PounceRequest()
        furreMap.values.map { rec -> rec.subject }.map { furre -> request.add(furre) }
        dreamMap.values.map { rec -> rec.subject }.map { dream -> request.add(dream) }

        // handle response
        val response = client.request(request)
        val onlineFurres: Set<Name> = response.furresOnline.map { name -> Name(name) }.toSet()
        val onlineDreams: Set<DreamUrl> = response.dreamsOnline.map { url -> DreamUrl.UrlParser().parse(url) }.toSet()

        // send updates off to the callback
        for (rec in furreMap.values) {
            val name = rec.subject.name
            val status = rec.status
            val newStatus = if (name in onlineFurres) PounceRecord.Status.ONLINE else PounceRecord.Status.OFFLINE
            if (status != newStatus) {
                cbFurreStatusChange(rec, newStatus)
                rec.status = newStatus
            }
        }

        for (rec in dreamMap.values) {
            val dreamUrl = rec.subject.dreamUrl
            val status = rec.status
            val newStatus = if (dreamUrl in onlineDreams) PounceRecord.Status.ONLINE else PounceRecord.Status.OFFLINE
            if (status != newStatus) {
                cbDreamStatusChange(rec, newStatus)
                rec.status = newStatus
            }
        }

        numFurresOnline = response.numFurresOnline
        numDreamsOnline = response.numDreamsOnline
        requestIntervalMillis = response.pollInterval
        lastUpdate = Date()
    }
}