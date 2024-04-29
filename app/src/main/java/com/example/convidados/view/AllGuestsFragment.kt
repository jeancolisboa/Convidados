package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.viewmodel.GuestsViewModel
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!

    //declarando a viewmodel
    private lateinit var viewModel: GuestsViewModel

    //instacia do adapter
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GuestsViewModel::class.java]
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        //LAYOUT
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        //classe anonima
        val listener = object : OnGuestListener {

            //quando clicado, inicia o formulario de convidados
            override fun onClick(id: Int) {

                val intent = Intent(context,GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID ,id)
                intent.putExtras(bundle)

                //chamando a activity com algo extra
                startActivity(intent)
            }

            //deletou o convidado
            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }
        }

        //interacao do usuario com o ouvinte para os cliques
        adapter.attachListener(listener)

        //adapter faz a cola
        binding.recyclerAllGuests.adapter = adapter

        //chamando a funcao desde a criacao
        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        //chamando a funcao allGet da ViewModel
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //funcao para observar a ViewModel
    fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}