package com.example.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.view.viewholder.GuestsViewHolder

class GuestsAdapter: RecyclerView.Adapter<GuestsViewHolder>() {

    private var guestList: List<GuestModel> = listOf()

    //declarando o listener da interface abaixo
    private lateinit var listener: OnGuestListener

    //cria cada layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestsViewHolder {
        //declarando o layout do row_guest
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        //retornando o layout para inflar
        return GuestsViewHolder(item,listener)
    }

    //faz a cola do layout com a lista
    override fun onBindViewHolder(holder: GuestsViewHolder, position: Int) {
        //retorna a posicao
        holder.bind(guestList[position])
    }

    //diz o tamanho da listagem
    override fun getItemCount(): Int {
        return guestList.count()
    }

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged()
    }

    fun attachListener(guestListener: OnGuestListener) {
        listener = guestListener
    }
}