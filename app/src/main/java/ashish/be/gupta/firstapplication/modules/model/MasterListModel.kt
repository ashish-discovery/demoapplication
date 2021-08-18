package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class MasterListModel(
    val entity: EntityMasterModel?,
    val error: Error?
)

data class EntityMasterModel(
    val paymentMode: ArrayList<String>,
    val status: ArrayList<String>,
    val complaintStatus : ArrayList<String>
)