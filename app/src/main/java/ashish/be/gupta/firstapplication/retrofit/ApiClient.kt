package ashish.be.gupta.firstapplication.retrofit

import ashish.be.gupta.firstapplication.BuildConfig
import ashish.be.gupta.firstapplication.constants.ConstantKey
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//class ApiClient {
//
//    companion object {
//
//        private val httpClient = OkHttpClient.Builder()
//            .connectTimeout(120, TimeUnit.SECONDS)
//            .readTimeout(120, TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level =
//                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
//            }).build()
//
//        private val builder = Retrofit.Builder()
//            .baseUrl(ConstantKey.APP_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//
//        fun <S> createService(serviceClass: Class<S>): S {
//            val retrofit = builder.client(httpClient).build()
//            return retrofit.create(serviceClass)
//        }
//
//    }
//
//}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ConstantKey.APP_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

