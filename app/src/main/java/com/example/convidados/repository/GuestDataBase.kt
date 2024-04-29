package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    //chamar a palavra que tem o valor
    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    //funcao para criar a tabela no banco de dados
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + " (" +
                DataBaseConstants.GUEST.COLUMNS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataBaseConstants.GUEST.COLUMNS.NAME + " TEXT," +
                DataBaseConstants.GUEST.COLUMNS.PRESENCE + " INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
