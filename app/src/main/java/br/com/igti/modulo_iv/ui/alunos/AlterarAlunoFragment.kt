package br.com.igti.modulo_iv.ui.alunos

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.igti.modulo_iv.data.remote.dto.AlunoRequestDTO
import br.com.igti.modulo_iv.databinding.FragmentAlterarAlunosBinding
import br.com.igti.modulo_iv.viewmodel.AlterarAlunoViewModel
import java.time.LocalDate
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlterarAlunoFragment : Fragment() {

    private var _binding: FragmentAlterarAlunosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlterarAlunoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlterarAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtId.setText(arguments?.getString("id"))
        binding.edtNome.setText(arguments?.getString("nome"))
        binding.edtSobrenome.setText(arguments?.getString("sobrenome"))
        binding.edtNascimento.setText(arguments?.getString("nascimento"))

        binding.btnSalvar.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.alterarAluno(
                    id = binding.edtId.text.toString(),
                    aluno = AlunoRequestDTO(
                        binding.edtNome.text.toString(),
                        binding.edtSobrenome.text.toString(),
                        LocalDate.parse(binding.edtNascimento.text.toString())
                    )
                )
            }
        }

        lifecycleScope.launch {
            viewModel.alunoAlterado.observe(viewLifecycleOwner) {
                if (it != null) {
                    val alertDialog = AlertDialog.Builder(view.context)
                    alertDialog.setTitle("Alterado com Sucesso!")
                    alertDialog.setMessage(
                        "ID: ${binding.edtId.text}" +
                            "\nNome: ${it.nome}" +
                            "\nSobrenome: ${it.sobrenome}" +
                            "\nNascimento: ${it.nascimento}"
                    )
                    alertDialog.setPositiveButton("Ok!", null)
                    alertDialog.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}