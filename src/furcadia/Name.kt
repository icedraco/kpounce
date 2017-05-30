package furcadia

/**
 * Represents a Furcadian name
 *
 * Names in Furcadia consist of:
 *   * display name - the regular name we see
 *   * shortname    - alphanumeric string that represents this name
 *   * protoname    - same as display name, but has pipes characters instead of
 *                    spaces in order to fit in a single "word" ("Protocol Name")
 *
 * Two Furcadia names are said to be equal if they have the same shortname.
 */
class Name(val name: String) {
    val shortname = Shortname.getShortname(name)
    val protoname = name.replace(' ', '|')

    override fun toString(): String = name

    override fun equals(other: Any?): Boolean {
        when (other) {
            is furcadia.Name -> return other.shortname == shortname
            is String -> return furcadia.Name(other).shortname == shortname
            else -> return false
        }
    }

    override fun hashCode(): Int = shortname.hashCode()
}
