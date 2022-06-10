package br.com.igti.modulo_iv.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

data class AlunoResponseDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("sobrenome")
    val sobrenome: String,
    @SerializedName("nascimento")
    val nascimento: LocalDate
) : Serializable