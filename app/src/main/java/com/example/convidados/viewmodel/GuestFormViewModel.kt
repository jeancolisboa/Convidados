package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

//                       faz o papel de contexto                     tem o contexto
class GuestFormViewModel(application: Application): AndroidViewModel(application) {

    //instanciando o repositorio aqui
    private val repository = GuestRepository.getInstace(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    //matar a activity depois de atualizar o formulario
    private val _saveGuest = MutableLiveData<String>()
    val saveGuest: LiveData<String> = _saveGuest

    //tipo da funcao é guest que usa o GuestModel como parametro
    //FUNCAO PARA A INSERCAO DO CONVIDADO
    fun save(guest: GuestModel) {
        //chamando o repositorio aqui para a insercao
        if (guest.id == 0) {
            if(repository.insert(guest)) {
                    _saveGuest.value = "Inserção com sucesso"
            } else {
                _saveGuest.value = "Falha"
            }
        } else  {
            if(repository.update(guest)) {
                _saveGuest.value = "Atualização com sucesso"
            } else {
                _saveGuest.value = "Falha"
            }
        }

    }

    //logica para verificar se a activity foi iniciada com informacoes para a edicao do convidado
    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }
}