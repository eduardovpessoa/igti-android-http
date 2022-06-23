package br.com.igti.modulo_iv.data.remote

import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.data.remote.dto.AlunoResponseDTO
import br.com.igti.modulo_iv.data.remote.dto.MessageDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path

class AlunoRepository(private val api : IAlunoRepository) {
    fun listarAlunos(): Call<List<AlunoResponseDTO>> = api.listarAlunos()

    fun listarAlunoPorId(id: String): Call<AlunoResponseDTO> =
        api.listarAlunoPorId(id)

    fun cadastrarAluno(aluno: AlunoRequestDTO): Call<MessageDTO> =
        api.cadastrarAluno(aluno)

    fun alterarAluno(id: String, aluno: AlunoRequestDTO): Call<AlunoResponseDTO> =
        api.alterarAluno(id, aluno)

    fun excluirAluno(id: String): Call<MessageDTO> =
        api.excluirAluno(id)
}