package br.com.igti.modulo_iv.data.remote

import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.data.remote.dto.AlunoResponseDTO
import br.com.igti.modulo_iv.data.remote.dto.MessageDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path

class AlunoRepository(private val retrofitClient: RetrofitClient) : IAlunoRepository {
    override fun listarAlunos(): Call<List<AlunoResponseDTO>> =
        retrofitClient.getInstance().getAlunoApi().listarAlunos()

    override fun listarAlunoPorId(@Path(value = "id") id: String): Call<AlunoResponseDTO> =
        retrofitClient.getInstance().getAlunoApi().listarAlunoPorId(id)

    override fun cadastrarAluno(aluno: AlunoRequestDTO): Call<MessageDTO> =
        retrofitClient.getInstance().getAlunoApi().cadastrarAluno(aluno)

    override fun alterarAluno(
        @Path(value = "id") id: String,
        @Body aluno: AlunoRequestDTO
    ): Call<AlunoResponseDTO> =
        retrofitClient.getInstance().getAlunoApi().alterarAluno(id, aluno)

    override fun excluirAluno(@Path(value = "id") id: String): Call<MessageDTO> =
        retrofitClient.getInstance().getAlunoApi().excluirAluno(id)
}