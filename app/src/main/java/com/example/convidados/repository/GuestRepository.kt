package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    //singleton
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstace(context: Context): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    //FUNCAO PARA INSERIR OS DADOS DO CONVIDADO NO BANCO DE DADOS
    fun insert(guest: GuestModel): Boolean {
        try {
            val db = guestDataBase.writableDatabase

            //se presenca for igual a 1 senao 0
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME,guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            //inserindo os valores: nome da tabela, null e os values
            db.insert(DataBaseConstants.GUEST.TABLE_NAME,null,values)

            return true //RETORNAR VERDADE SE NAO HOUVER ERRO

        } catch (e: Exception) {
            return false //RETORNAR FALSO SE HOUVER ERRO
        }
    }

    //FUNCAO PARA ATUALIZAR OS DADOS DO CONVIDADO NO BANCO DE DADOS
    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            //se presenca for igual a 1 senao 0
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME,guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME,values, selection,args)

            true
        } catch (e: Exception) {
            false
        }
    }

    //FUNCAO PARA DELETAR OS DADOS DO CONVIDADO NO BANCO DE DADOS
    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            //deletar do banco segundo a tabelas > selecao do ID > e o argumento do ID
            db.delete(DataBaseConstants.GUEST.TABLE_NAME,selection,args)

            true
        } catch (e: Exception) {
            false
        }
    }

    //FUNCAO PARA BUSCAR OS DADOS DE TODOS OS CONVIDADO NO BANCO DE DADOS
    fun getAll():List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {
            //instanciando o arquivo do DB
            val db = guestDataBase.readableDatabase

            //selecao das colunas da tabela
            val selection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            //consulta do banco, selecionando a tabela toda + selection
            //iniciando cursor
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, selection,
                null,null,null,null,null
            )

            if (cursor != null && cursor.count > 0) {
                //enquanto o cursor mover pro proximo
                while (cursor.moveToNext()) {
                    //                            pega a coluna
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id,name,presence == 1))
                }
            }

            //fechando cursor
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }

    //FUNCAO PARA BUSCAR SOMENTE UM CONVIDADO
    fun get(id: Int):GuestModel? {
        var guest: GuestModel? = null
        try {
            //instanciando o arquivo do DB
            val db = guestDataBase.readableDatabase

            //selecao das colunas da tabela
            val selection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val projection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            //consulta do banco, selecionando a tabela toda + selection
            //iniciando cursor
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, selection,
                projection,args,null,null,null
            )

            if (cursor != null && cursor.count > 0) {
                //enquanto o cursor mover pro proximo
                while (cursor.moveToNext()) {
                    //                            pega a coluna
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    guest = GuestModel(id,name,presence == 1)
                }
            }

            //fechando cursor
            cursor.close()

        } catch (e: Exception) {
            return guest
        }
        return guest
    }

    //FUNCAO PARA BUSCAR OS DADOS DE TODOS OS CONVIDADO PRESENTES NO BANCO DE DADOS
    fun getPresent():List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {
            //instanciando o arquivo do DB
            val db = guestDataBase.readableDatabase

            //iniciando cursor
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                //enquanto o cursor mover pro proximo
                while (cursor.moveToNext()) {
                    //                            pega a coluna
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id,name,presence == 1))
                }
            }

            //fechando cursor
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }

    //FUNCAO PARA BUSCAR OS DADOS DE TODOS OS CONVIDADO AUSENTES NO BANCO DE DADOS
    fun getAbsent():List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {
            //instanciando o arquivo do DB
            val db = guestDataBase.readableDatabase

            //iniciando cursor
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                //enquanto o cursor mover pro proximo
                while (cursor.moveToNext()) {
                    //                            pega a coluna
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id,name,presence == 0))
                }
            }

            //fechando cursor
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }
}