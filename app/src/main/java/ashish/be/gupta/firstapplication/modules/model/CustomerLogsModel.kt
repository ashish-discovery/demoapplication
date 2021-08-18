package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error


data class CustomerLogsModel(
    val entity: List<EntityCustomerLogsModel>?,
    val error: Error?
)

data class EntityCustomerLogsModel(
    val customerLogId: String,
    val customerId: String,
    val logs: String,
    val updatedBy: String,
    val createdAt: String
)