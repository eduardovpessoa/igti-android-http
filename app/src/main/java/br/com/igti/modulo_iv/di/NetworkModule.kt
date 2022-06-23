package br.com.igti.modulo_iv.di

import br.com.igti.modulo_iv.util.LocalDateAdapter
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideHttpClient(): OkHttpClient {
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

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            LocalDateAdapter().nullSafe()
        ).create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://igtiandroid.ddns.net:8080")
            .client(okHttpClient)
            .build()
    }

    single { provideHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
}