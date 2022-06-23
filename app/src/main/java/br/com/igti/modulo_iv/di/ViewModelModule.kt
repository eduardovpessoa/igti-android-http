package br.com.igti.modulo_iv.di

import br.com.igti.modulo_iv.viewmodel.AlterarAlunoViewModel
import br.com.igti.modulo_iv.viewmodel.CadastrarAlunoViewModel
import br.com.igti.modulo_iv.viewmodel.ListarAlunoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AlterarAlunoViewModel(repository = get()) }
    viewModel { CadastrarAlunoViewModel(repository = get()) }
    viewModel { ListarAlunoViewModel(repository = get()) }
}