package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class BuildingModel(
    val entity: EntityBuildingModel?,
    val error: Error?
)