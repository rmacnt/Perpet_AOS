package pet.perpet.data

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import pet.perpet.data.api.call.ResponseFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    @JvmStatic
    fun create(url: String): Retrofit {
        return Retrofit.Builder()
            .apply {
                baseUrl(url)
                client(OkHttpClient.Builder().apply {
                    addCallAdapterFactory(ResponseFactory.create())
                    addInterceptor(DataConfig.getProvider().createInterceptor())
                    connectTimeout(30 * 2, TimeUnit.SECONDS)
                    readTimeout(30 * 2, TimeUnit.SECONDS)
                }.build())
                addConverterFactory(GsonConverterFactory.create(GsonLoader.gson))
            }.build()
    }

    @JvmStatic
    fun createAddress(url: String): Retrofit {
        return Retrofit.Builder()
            .apply {
                baseUrl(url)
                client(OkHttpClient.Builder().apply {
                    addCallAdapterFactory(ResponseFactory.create())
                    connectTimeout(30 * 2, TimeUnit.SECONDS)
                    readTimeout(30 * 2, TimeUnit.SECONDS)
                }.build())
                addConverterFactory(GsonConverterFactory.create(GsonLoader.gson))
            }.build()
    }
}