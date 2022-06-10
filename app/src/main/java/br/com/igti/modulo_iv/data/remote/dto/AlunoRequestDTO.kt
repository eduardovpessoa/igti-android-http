package br.com.igti.modulo_iv.data.remote.dto

import java.time.LocalDate

data class AlunoRequestDTO(
    private val nome : String,
    private val sobrenome : String,
    private val nascimento : LocalDate
)