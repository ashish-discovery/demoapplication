package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class NotificationModel(
    val entity: EntityNotificationModel?,
    val error: Error?
)