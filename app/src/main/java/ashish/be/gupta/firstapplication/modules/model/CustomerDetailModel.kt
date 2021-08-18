package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class CustomerDetailModel(
    val entity: EntityCustomerModel?,
    val error: Error?
)