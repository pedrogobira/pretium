package com.pedrogobira.pretium.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pedrogobira.pretium.service.constants.DatabaseConstants

class ServiceDatabaseHelper(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    /**
     * Método executado somente uma vez quando o acesso ao banco de dados é feito pela primeira vez
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SERVICES)
    }

    /**
     * Método executado quando a versão do DATABASE_VERSION é alterada
     * Dessa maneira, a aplicação sabe que o banco de dados foi alterado e é necessário rodar o script de update
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val VERSION = 1
        private const val NAME = "Services.db"

        private const val CREATE_TABLE_SERVICES =
            ("create table " + DatabaseConstants.SERVICE.TABLE_NAME + " ("
                    + DatabaseConstants.SERVICE.COLUMNS.ID + " integer primary key autoincrement, "
                    + DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR + " real not null, "
                    + DatabaseConstants.SERVICE.COLUMNS.HOURS + " integer not null, "
                    + DatabaseConstants.SERVICE.COLUMNS.MINUTES + " integer not null, "
                    + DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION + " text not null, "
                    + DatabaseConstants.SERVICE.COLUMNS.DAY + " integer not null, "
                    + DatabaseConstants.SERVICE.COLUMNS.MONTH + " integer not null, "
                    + DatabaseConstants.SERVICE.COLUMNS.YEAR + " integer not null);")
    }
}