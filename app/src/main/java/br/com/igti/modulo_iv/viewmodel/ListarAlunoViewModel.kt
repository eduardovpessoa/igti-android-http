package br.com.igti.modulo_iv.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igti.modulo_iv.data.remote.AlunoRepository
import br.com.igti.modulo_iv.data.remote.dto.AlunoResponseDTO
import br.com.igti.modulo_iv.data.remote.dto.MessageDTO
import java.util.Base64
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class ListarAlunoViewModel(
    private val repository: AlunoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _alunoDetalhes: MutableLiveData<AlunoResponseDTO> = MutableLiveData()
    val alunoDetalhes: LiveData<AlunoResponseDTO> = _alunoDetalhes

    private val _alunoExcluido = MutableLiveData(false)
    val alunoExcluido: LiveData<Boolean> = _alunoExcluido

    private val _listaAlunosFlow = MutableStateFlow<List<AlunoResponseDTO>>(listOf())
    val listaAlunosFlow: StateFlow<List<AlunoResponseDTO>> = _listaAlunosFlow

    fun listarAlunos() {
        viewModelScope.launch(dispatcher) {
            repository.listarAlunos().enqueue(object : Callback<List<AlunoResponseDTO>> {
                override fun onResponse(
                    call: Call<List<AlunoResponseDTO>>,
                    response: retrofit2.Response<List<AlunoResponseDTO>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { list ->
                            _listaAlunosFlow.value = list
                        }
                    }
                }

                override fun onFailure(call: Call<List<AlunoResponseDTO>>, t: Throwable) {
                    _listaAlunosFlow.value = listOf()
                    Log.e(ListarAlunoViewModel::class.java.name, t.toString())
                }
            })
            testDecodeToken()
        }
    }

    fun listarAlunoPorId(id: String) {
        viewModelScope.launch(dispatcher) {
            repository.listarAlunoPorId(id).enqueue(object : Callback<AlunoResponseDTO> {
                override fun onResponse(
                    call: Call<AlunoResponseDTO>,
                    response: retrofit2.Response<AlunoResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _alunoDetalhes.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<AlunoResponseDTO>, t: Throwable) {
                    Log.e(ListarAlunoViewModel::class.java.name, t.toString())
                }
            })
        }
    }

    fun excluirAluno(id: String) {
        viewModelScope.launch(dispatcher) {
            repository.excluirAluno(id).enqueue(object : Callback<MessageDTO> {
                override fun onResponse(
                    call: Call<MessageDTO>,
                    response: retrofit2.Response<MessageDTO>
                ) {
                    if (response.isSuccessful) alterarStatusExclusao(true)
                }

                override fun onFailure(call: Call<MessageDTO>, t: Throwable) {
                    Log.e(ListarAlunoViewModel::class.java.name, t.toString())
                }
            })
        }
    }

    fun alterarStatusExclusao(valor: Boolean) {
        _alunoExcluido.value = valor
    }

    private fun decodeToken(jwt: String): String {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            "$header"
            "$payload"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }

    fun testDecodeToken(){
        val token = "coloque seu token aqui!"
        Log.i("token", decodeToken(token))
    }
}