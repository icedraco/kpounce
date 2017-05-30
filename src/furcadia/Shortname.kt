package furcadia

/**
 * TODO: Add support for special characters
 * TODO: Add support for HTML entities
 */

object Shortname {
    fun getShortname(name: String): String {
        return name.filter { ch -> furcadia.Shortname.VALID_SHORTNAME_CHARS_MASK[ch.toInt()] }.toLowerCase()
    }

    private val VALID_CHAR_RANGES = arrayOf('a' .. 'z', 'A' .. 'Z', '0' .. '9')
    private val VALID_SHORTNAME_CHARS_MASK = Array(256, { i -> furcadia.Shortname.VALID_CHAR_RANGES.any { r -> i.toChar() in r } })
}
