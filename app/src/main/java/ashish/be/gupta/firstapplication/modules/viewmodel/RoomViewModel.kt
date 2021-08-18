package ashish.be.gupta.firstapplication.modules.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashish.be.gupta.firstapplication.constants.CustomStatus
import ashish.be.gupta.firstapplication.model.Error
import ashish.be.gupta.firstapplication.model.Resource
import ashish.be.gupta.firstapplication.modules.model.*
import ashish.be.gupta.firstapplication.modules.repository.RoomRepository
import ashish.be.gupta.firstapplication.modules.responseparser.EntityRoomResponseParser
import ashish.be.gupta.firstapplication.utilities.Utility
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class RoomViewModel(private val roomRepository: RoomRepository) : ViewModel() {

    val responseRoomList = MutableLiveData<Resource<RoomListModel>>()

    fun getRoomsByBuilding(buildingID: String): MutableLiveData<Resource<RoomListModel>> {
        responseRoomList.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = roomRepository.getRoomsByBuilding(buildingID)
                val listRoomModel = ArrayList<EntityRoomModel>()
                response.payload?.let { payload ->
                    if (payload.entity != null) {
                        payload.entity.forEach { entity ->
                            listRoomModel.add(parseRoomDetail(entity))
                        }
                        responseRoomList.postValue(
                            Resource.success(RoomListModel(listRoomModel, null))
                        )
                    } else {
                        responseRoomList.postValue(
                            Resource.error(
                                RoomListModel(
                                    null,
                                    Error(response.error!!.code, response.error.message)
                                )
                            )
                        )
                    }

                }


            } catch (httpException: HttpException) {

                responseRoomList.postValue(
                    Resource.error(
                        RoomListModel(
                            null, Utility.handleHttpException(httpException)
                        )
                    )
                )


            } catch (e: Exception) {
                val error = Error(-1, e.message.toString())
                responseRoomList.postValue(
                    Resource.error(RoomListModel(null, error))
                )
            }

        }

        return responseRoomList
    }

    private fun parseRoomDetail(entity: EntityRoomResponseParser): EntityRoomModel {
        return EntityRoomModel(
            entity.floorId,
            entity.floorName,
            CustomStatus.valueOf(entity.floorStatus ?: "NA"),
            entity.roomId,
            entity.roomName,
            CustomStatus.valueOf(entity.roomStatus ?: "NA"),
            false
        )
    }

}