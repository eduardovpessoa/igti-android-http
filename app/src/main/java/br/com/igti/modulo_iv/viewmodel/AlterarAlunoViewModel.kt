package br.com.igti.modulo_iv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igti.modulo_iv.data.remote.AlunoRepository
import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.data.remote.dto.AlunoResponseDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlterarAlunoViewModel(
    private val repository: AlunoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _alunoAlterado: MutableLiveData<AlunoResponseDTO> = MutableLiveData()
    val alunoAlterado: LiveData<AlunoResponseDTO> = _alunoAlterado

    fun alterarAluno(id: String, aluno: AlunoRequestDTO) {
        viewModelScope.launch(dispatcher) {
            repository.alterarAluno(id, aluno).enqueue(object : Callback<AlunoResponseDTO> {
                override fun onResponse(
                    call: Call<AlunoResponseDTO>,
                    response: Response<AlunoResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _alunoAlterado.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<AlunoResponseDTO>, t: Throwable) {
                    Log.e(AlterarAlunoViewModel::class.java.name, t.stackTraceToString())
                }
            })
        }
    }
}