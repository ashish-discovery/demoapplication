package ashish.be.gupta.firstapplication.modules.model

import ashish.be.gupta.firstapplication.model.Error

data class NotificationListModel(
    val entity: ArrayList<EntityNotificationModel>?,
    val error: Error?
)

data class EntityNotificationModel(
    val createdBy: String?,
    val createdAt: String?,
    val updatedBy: String?,
    val updatedOn: String?,
    val buildingId: String?,
    val customerId: String?,
    val description: String?,
    val notificationId: String?
)