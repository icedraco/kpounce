package furcadia

const val DREAM_URL_PREFIX = "furc://"

/**
 * Represents a Furcadia DreamURL
 *
 * A Dream URL may be of the following formats:
 *   furc://uploadername
 *   furc://uploadername:dreamname
 *   furc://uploadername:dreamname/
 *   furc://uploadername:dreamname/123
 *
 * A Dream URL contains the following elements
 *   uploader name   - name of the furre/user who uploaded the dream
 *                     (often treated as dream name itself)
 *
 *   dream name      - (OPTIONAL) actual name of the dream
 *                     (appears underneath the uploader's name in dreams)
 *
 *   code            - (OPTIONAL) DragonSpeak access code for that dream
 *
 *
 * NOTE: In order to instantiate a DreamUrl class from a Dream URL string
 *       instead of its individual components, use the UrlParser subclass
 *       like this: DreamUrl.UrlParser().parse("furc://vinca")
 */
class DreamUrl(val uploaderName: Name, val dreamName: Name, val code: Int? = null) {
    constructor(uploaderName: String, dreamName: String = "", code: Int? = null)
            : this(Name(uploaderName), Name(dreamName), code)

    /**
     * Responsible for translating a dream URL in its string form back into a
     * DreamUrl object
     */
    class UrlParser {
        fun parse(url: String) : furcadia.DreamUrl {
            assert(url.isNotEmpty())
            assert(url.startsWith(furcadia.DREAM_URL_PREFIX))

            val urlComponents = url.substring(furcadia.DREAM_URL_PREFIX.length).split("/")
            val code: Int? = if (urlComponents.size > 1 && urlComponents[1].isNotEmpty()) urlComponents[1].toInt() else null

            val mainComponents = urlComponents[0].split(":")
            val uploaderShortname: String = mainComponents[0]
            val dreamShortname: String = if (mainComponents.size > 1) mainComponents[1] else ""

            return furcadia.DreamUrl(uploaderShortname, dreamShortname, code)
        }
    }

    fun withCode(newCode: Int) = furcadia.DreamUrl(uploaderName, dreamName, newCode)
    fun withoutCode() = if (code == null) this else furcadia.DreamUrl(uploaderName, dreamName)

    override fun toString(): String {
        val codePostfix = if (code != null) "/$code" else ""
        return "${furcadia.DREAM_URL_PREFIX}${uploaderName.shortname}:${dreamName.shortname}".trimEnd(':') + codePostfix
    }

    override fun equals(other: Any?): Boolean {
        when (other) {
            is furcadia.DreamUrl -> return other.withoutCode().toString() == withoutCode().toString()
            is String -> return furcadia.DreamUrl.UrlParser().parse(other).withoutCode().toString() == withoutCode().toString()
            else -> return false
        }
    }

    override fun hashCode(): Int {
        var result = uploaderName.hashCode()
        result = 31 * result + dreamName.hashCode()
        result = 31 * result + (code ?: 0)
        return result
    }
}
