package ashish.be.gupta.firstapplication.modules.repository

import ashish.be.gupta.firstapplication.constants.ConstantKey
import ashish.be.gupta.firstapplication.modules.responseparser.RoomListResponseParser

import ashish.be.gupta.firstapplication.retrofit.ApiService
import ashish.be.gupta.firstapplication.singleton.AdminDetails

class RoomRepository(private val apiService: ApiService) {

//    private val apiService = ApiClient.createService(ApiService::class.java)

    suspend fun getRoomsByBuilding(buildingID: String): RoomListResponseParser {
        return apiService.getRoomsByBuilding(
            ConstantKey.CONTENT_TYPE, AdminDetails.token, buildingID
        )
    }
}