package br.com.igti.modulo_iv.data.remote

import br.com.igti.modulo_iv.util.LocalDateAdapter
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private var instance: RetrofitClient? = null
    }

    private var alunoRepository: IAlunoRepository

    init {
        val gson = GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            LocalDateAdapter().nullSafe()
        ).create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://igtiandroid.ddns.net:8080")
            .client(createOkHttpClient())
            .build()

        alunoRepository = retrofit.create(IAlunoRepository::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor { msg ->
                println("LOG APP: $msg")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addNetworkInterceptor(HttpLoggingInterceptor { msg ->
                println("LOG NTW: $msg")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Synchronized
    fun getInstance(): RetrofitClient {
        if (instance == null) {
            instance = RetrofitClient()
        }
        return instance as RetrofitClient
    }

    fun getAlunoApi() = alunoRepository
}