package br.com.igti.modulo_iv.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.databinding.FragmentCadastrarAlunosBinding
import br.com.igti.modulo_iv.viewmodel.CadastrarAlunoViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.util.Locale
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastrarAlunoFragment : Fragment() {

    private var _binding: FragmentCadastrarAlunosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CadastrarAlunoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCadastrarAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Implementação API REST
        binding.btnSalvar.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.cadastrarAluno(
                    AlunoRequestDTO(
                        binding.edtNome.text.toString(),
                        binding.edtSobrenome.text.toString(),
                        LocalDate.parse(binding.edtNascimento.text.toString())
                    )
                )
            }
        }

        /* Implementação com Firebase
        binding.btnSalvar.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("alunos")
            val alunos = ref.child("igti2021")
            alunos.setValue(
                AlunoFirebaseDTO(
                    nome = binding.edtNome.text.toString(),
                    sobrenome = binding.edtSobrenome.text.toString(),
                    nascimento = binding.edtNascimento.text.toString(),
                    idade = 30
                )
            ).addOnSuccessListener {
                Toast.makeText(view.context, "Cadastrado com Sucesso!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(view.context, "Problemas ao Cadastrar!", Toast.LENGTH_LONG).show()
            }
        }*/

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.successFlow.collect {
                if (!it.isNullOrEmpty()) {
                    Snackbar.make(
                        view,
                        String.format(Locale.US, "Cadastrado com sucesso! $it"),
                        LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}