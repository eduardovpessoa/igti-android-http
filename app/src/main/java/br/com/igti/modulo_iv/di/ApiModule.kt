package br.com.igti.modulo_iv.di

import br.com.igti.modulo_iv.data.remote.IAlunoRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideIgtiAlunoAPI(retrofit : Retrofit) : IAlunoRepository {
        return retrofit.create(IAlunoRepository::class.java)
    }

    single { provideIgtiAlunoAPI(retrofit = get()) }
}