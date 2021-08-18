package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class STBListModel(
    val data: ArrayList<EntitySTBModel>?,
    val error: Error?
)