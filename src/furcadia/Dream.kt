package furcadia

class Dream(val uploaderName: Name, val dreamName: Name = Name("")) {
    constructor(uploaderName: String, dreamName: String = "") : this(Name(uploaderName), Name(dreamName))

    val dreamUrl = DreamUrl(uploaderName, dreamName)
    val url
        get() = dreamUrl.toString()

    override fun toString() =
            if (dreamName.toString().isEmpty()) uploaderName.toString() else "$uploaderName ($dreamName)"

    override fun equals(other: Any?): Boolean {
        when (other) {
            is furcadia.Dream -> return other.dreamUrl == dreamUrl
            is DreamUrl -> return other == dreamUrl
            else -> return false
        }
    }

    override fun hashCode(): Int = dreamUrl.hashCode()
}
