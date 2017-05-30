package pounce

import java.util.*

/**
 * Represents a single record/entry in a pounce list and its status
 * The record is mostly either for the Furre or Dream type.
 */
class PounceRecord<out T>(val subject: T) {
    enum class Status { UNKNOWN, OFFLINE, ONLINE }

    var status = Status.UNKNOWN
        get
        set(value) {
            if (value != field) {
                field = value
                lastModified = Date()
            }
        }

    var lastModified = Date()
        get
        private set
}
