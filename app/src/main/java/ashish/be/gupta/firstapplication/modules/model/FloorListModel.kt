package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error

data class FloorListModel(
    val entity: ArrayList<EntityFloorModel>?,
    val error: Error?
)

data class EntityFloorModel(
    val floorId: String?,
    val floorName: String?,
    val floorStatus: CustomStatus?
)