package ashish.be.gupta.firstapplication.model

import ashish.be.gupta.firstapplication.constants.HttpStatus

data class Resource<out T>(val status: HttpStatus, val data: T?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(HttpStatus.SUCCESS, data)
        }

        fun <T> error(data: T?): Resource<T> {
            return Resource(HttpStatus.ERROR, data)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(HttpStatus.LOADING, data)
        }

    }

}