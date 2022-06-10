package br.com.igti.modulo_iv.data.remote

import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.data.remote.dto.AlunoResponseDTO
import br.com.igti.modulo_iv.data.remote.dto.MessageDTO
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface IAlunoRepository {

    @GET("/alunos")
    fun listarAlunos(): Call<List<AlunoResponseDTO>>

    @GET("/alunos/{id}")
    fun listarAlunoPorId(@Path("id") id: String): Call<AlunoResponseDTO>

    @POST("/alunos")
    fun cadastrarAluno(@Body aluno: AlunoRequestDTO): Call<MessageDTO>

    @PUT("/alunos/{id}")
    fun alterarAluno(@Path("id") id: String, @Body aluno: AlunoRequestDTO): Call<AlunoResponseDTO>

    @DELETE("/alunos/{id}")
    fun excluirAluno(@Path("id") id : String): Call<MessageDTO>
}