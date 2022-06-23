package br.com.igti.modulo_iv.di

import br.com.igti.modulo_iv.data.remote.AlunoRepository
import br.com.igti.modulo_iv.data.remote.IAlunoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AlunoRepository(api = get())  }
}