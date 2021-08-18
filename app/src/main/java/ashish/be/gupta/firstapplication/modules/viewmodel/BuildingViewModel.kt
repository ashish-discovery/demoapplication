package ashish.be.gupta.firstapplication.modules.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error
import ashish.be.gupta.firstapplication.model.Resource
import ashish.be.gupta.firstapplication.modules.model.BuildingListModel
import ashish.be.gupta.firstapplication.modules.model.BuildingModel
import ashish.be.gupta.firstapplication.modules.model.EntityBuildingModel
import ashish.be.gupta.firstapplication.modules.repository.BuildingRepository
import ashish.be.gupta.firstapplication.modules.responseparser.EntityBuildingResponseParser
import ashish.be.gupta.firstapplication.utilities.Utility
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class BuildingViewModel(private val buildingRepository: BuildingRepository) : ViewModel() {

    val responseBuildingList = MutableLiveData<Resource<BuildingListModel>>()

    fun getBuildingList(): MutableLiveData<Resource<BuildingListModel>> {
        responseBuildingList.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = buildingRepository.getBuildingList()
                val data = ArrayList<EntityBuildingModel>()
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        payload.entity.forEach { entity ->
                            data.add(parseBuildingDetail(entity))
                        }
                        responseBuildingList.postValue(
                            Resource.success(
                                BuildingListModel(data, null)
                            )
                        )
                    } else {
                        responseBuildingList.postValue(
                            Resource.error(
                                BuildingListModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )
                    }

                }


            } catch (httpException: HttpException) {

                responseBuildingList.postValue(
                    Resource.error(
                        BuildingListModel(null, Utility.handleHttpException(httpException))
                    )
                )


            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseBuildingList.postValue(
                    Resource.error(BuildingListModel(null, error))
                )
            }

        }

        return responseBuildingList
    }

    private fun parseBuildingDetail(entity: EntityBuildingResponseParser): EntityBuildingModel {

        return EntityBuildingModel(
            entity.createdBy,
            entity.createdAt,
            entity.updatedBy,
            entity.updatedOn,
            entity.buildingId,
            entity.buildingName,
            entity.buildingNumber,
            entity.floorCount,
            CustomStatus.valueOf(entity.status ?: "NA")
        )

    }

}