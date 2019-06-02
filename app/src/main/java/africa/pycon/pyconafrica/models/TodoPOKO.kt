package africa.pycon.pyconafrica.models

import java.util.UUID

class TodoPOKO {
    var title: String? = null
    var description: String? = null
    var uniqueId: String = UUID.randomUUID().toString()
    var date: Long? = null
}