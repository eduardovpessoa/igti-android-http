package br.com.igti.modulo_iv.ui.alunos

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.igti.modulo_iv.R
import br.com.igti.modulo_iv.databinding.FragmentListarAlunosBinding
import br.com.igti.modulo_iv.ui.alunos.adapter.AlunoAdapter
import br.com.igti.modulo_iv.ui.alunos.adapter.AlunoListener
import br.com.igti.modulo_iv.viewmodel.ListarAlunoViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListarAlunosFragment : Fragment() {

    private var _binding: FragmentListarAlunosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListarAlunoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListarAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerAluno.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerAluno.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.listarAlunos()
        }

        /* Implementação Firebase
        val db = FirebaseFirestore.getInstance()

        // Remoção (Firebase):
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("alunos")
        ref.removeValue()

        // Atualização (Firebase):
        update["nome"] = "Eduardo"
        update["sobrenome"] = "Viana Pessoa"
        update["idade"] = 27
        ref.updateChildren(update)

        // Buscar Dados (Firebase):
        val ref = database.getReference("alunos")
        val alunos = ref.child("igti2021")
        mDatabase.child("users").child(userId).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        */

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.listaAlunosFlow.collect { list ->
                if (!list.isNullOrEmpty()) {
                    binding.recyclerAluno.adapter = AlunoAdapter(
                        dataSet = list,
                        alunoListener = object : AlunoListener {
                            override fun onAlunoClickListener(id: String) {
                                viewModel.listarAlunoPorId(id)
                            }
                        }
                    )
                }
            }
        }

        viewModel.alunoDetalhes.observe(viewLifecycleOwner) {
            if (it != null && it.id.isNotEmpty()) {
                val alertDialog = AlertDialog.Builder(view.context)
                alertDialog.setTitle("Detalhes do Aluno")
                alertDialog.setMessage(
                    "ID: ${it.id}" +
                        "\nNome: ${it.nome}" +
                        "\nSobrenome: ${it.sobrenome}" +
                        "\nNascimento: ${it.nascimento}"
                )
                alertDialog.setPositiveButton("Ok", null)
                alertDialog.setNegativeButton("Alterar") { dialogInterface, i ->
                    val bundle = bundleOf(
                        "id" to it.id,
                        "nome" to it.nome,
                        "sobrenome" to it.sobrenome,
                        "nascimento" to it.nascimento?.toString()
                    )
                    findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, bundle)
                }
                alertDialog.setNeutralButton("Excluir") { dialogInterface, i ->
                    viewModel.excluirAluno("6296d25a62e02752fa66b39c")
                }
                alertDialog.show()
            }
        }

        viewModel.alunoExcluido.observe(viewLifecycleOwner) {
            if (it != null && it == true) {
                Toast.makeText(
                    view.context,
                    "Aluno excluído com sucesso!",
                    Toast.LENGTH_LONG
                ).show()
                lifecycleScope.launch {
                    viewModel.alterarStatusExclusao(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}