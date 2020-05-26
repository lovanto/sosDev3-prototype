package com.lovanto.sosdev.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbSosDev"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " ${DatabaseSosDev.NoteColumns.USERNAME} TEXT PRIMARY KEY  NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.AVATAR} TEXT NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.REPOSITORY} INTEGER NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.FOLLOWERS} INTEGER NOT NULL," +
                " ${DatabaseSosDev.NoteColumns.FOLLOWING} INTEGER NOT NULL)" +
                " ${DatabaseSosDev.NoteColumns.FAVOURITE} TEXT NOT NULL,"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}