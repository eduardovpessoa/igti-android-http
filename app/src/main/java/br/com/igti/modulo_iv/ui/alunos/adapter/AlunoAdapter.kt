package br.com.igti.modulo_iv.ui.alunos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.igti.modulo_iv.R
import br.com.igti.modulo_iv.data.remote.dto.AlunoFirebaseDTO
import br.com.igti.modulo_iv.data.remote.dto.AlunoResponseDTO

class AlunoAdapter(
    private val dataSet: List<AlunoResponseDTO>, //Ou AlunoFirebaseDTO
    private val alunoListener: AlunoListener
) :
    RecyclerView.Adapter<AlunoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNome: TextView
        val txtSobrenome: TextView

        init {
            txtNome = view.findViewById(R.id.txtNome)
            txtSobrenome = view.findViewById(R.id.txtSobrenome)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.aluno_item_row, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.txtNome.text = dataSet[position].nome
        viewHolder.txtSobrenome.text = dataSet[position].sobrenome
        viewHolder.itemView.setOnClickListener {
            alunoListener.onAlunoClickListener(dataSet[position].id)
        }
    }

    override fun getItemCount() = dataSet.size
}