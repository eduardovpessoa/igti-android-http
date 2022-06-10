package br.com.igti.modulo_iv.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igti.modulo_iv.data.remote.AlunoRepository
import br.com.igti.modulo_iv.data.remote.IAlunoRepository
import br.com.igti.modulo_iv.data.remote.RetrofitClient
import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.data.remote.dto.MessageDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarAlunoViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val repository: IAlunoRepository = AlunoRepository(RetrofitClient())

    private val _successFlow = MutableStateFlow("")
    val successFlow: StateFlow<String> = _successFlow

    fun cadastrarAluno(aluno: AlunoRequestDTO) {
        viewModelScope.launch(dispatcher) {
            repository.cadastrarAluno(aluno).enqueue(object : Callback<MessageDTO> {
                override fun onResponse(call: Call<MessageDTO>, response: Response<MessageDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _successFlow.value = it.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<MessageDTO>, t: Throwable) {
                    Log.e(CadastrarAlunoViewModel::class.java.name, t.stackTraceToString())
                }
            })
        }
    }
}