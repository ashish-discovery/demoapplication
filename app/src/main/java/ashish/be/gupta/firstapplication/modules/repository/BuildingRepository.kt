package ashish.be.gupta.firstapplication.modules.repository

import ashish.be.gupta.firstapplication.constants.ConstantKey
import ashish.be.gupta.firstapplication.modules.responseparser.BuildingListResponseParser

import ashish.be.gupta.firstapplication.retrofit.ApiService
import ashish.be.gupta.firstapplication.singleton.AdminDetails

class BuildingRepository(private val apiService: ApiService) {

    suspend fun getBuildingList(): BuildingListResponseParser {
        return apiService.getBuildingList(ConstantKey.CONTENT_TYPE, AdminDetails.token)
    }

}