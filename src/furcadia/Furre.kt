package furcadia

class Furre(val name: Name) {
    constructor(name: String) : this(Name(name))
    override fun toString() = name.toString()
}
