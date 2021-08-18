package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class RoomModel(
    val entity: EntityRoomModel?,
    val error: Error?
)