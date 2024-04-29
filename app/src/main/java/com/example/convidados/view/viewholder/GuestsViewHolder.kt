package com.example.convidados.view.viewholder

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestsViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textNameGuest.text = guest.name

        //evento de quando clicar no convidado
        bind.textNameGuest.setOnClickListener {
            listener.onClick(guest.id)
        }

        //evento acionado quando é pressionado para excluir o convidado
        bind.textNameGuest.setOnLongClickListener {

            //dialogo aberto para confirmar a exclusao do usuario
            AlertDialog.Builder(itemView.context).setTitle("Remoção do convidado")
                .setMessage("Tem certeza que deseja remover o convidado?")
                .setPositiveButton("Sim") { dialog, which ->
                    //exclue se apertar o botao sim
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("Não",null)
                    .create()
                    .show()

            true
        }
    }
}