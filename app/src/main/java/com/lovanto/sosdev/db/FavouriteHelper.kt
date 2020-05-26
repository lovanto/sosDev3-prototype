package com.lovanto.sosdev.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.AVATAR
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.COMPANY
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.FAVOURITE
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.FOLLOWERS
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.FOLLOWING
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.LOCATION
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.NAME
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.REPOSITORY
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.TABLE_NAME
import com.lovanto.sosdev.db.DatabaseSosDev.NoteColumns.Companion.USERNAME
import com.lovanto.sosdev.model.Favourite
import java.util.*

class FavouriteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase = dataBaseHelper.writableDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavouriteHelper? = null

        fun getInstance(context: Context): FavouriteHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: FavouriteHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$USERNAME DESC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$USERNAME = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }

    fun getAllFav(): ArrayList<Favourite> {
        val arrayList = ArrayList<Favourite>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
                "$USERNAME DESC", null)
        cursor.moveToFirst()
        var note: Favourite
        if (cursor.count > 0) {
            do {
                note = Favourite()
                note.username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
                note.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                note.avatar = cursor.getString(cursor.getColumnIndexOrThrow(AVATAR))
                note.company = cursor.getString(cursor.getColumnIndexOrThrow(COMPANY))
                note.location = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION))
                note.repository = cursor.getInt(cursor.getColumnIndexOrThrow(REPOSITORY))
                note.followers = cursor.getInt(cursor.getColumnIndexOrThrow(FOLLOWERS))
                note.following = cursor.getInt(cursor.getColumnIndexOrThrow(FOLLOWING))
                note.fav = cursor.getString(cursor.getColumnIndexOrThrow(FAVOURITE))

                arrayList.add(note)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun insertFav(note: Favourite): Long {
        val args = ContentValues()
        args.put(USERNAME, note.username)
        args.put(NAME, note.name)
        args.put(AVATAR, note.avatar)
        args.put(COMPANY, note.company)
        args.put(LOCATION, note.location)
        args.put(REPOSITORY, note.repository)
        args.put(FOLLOWERS, note.followers)
        args.put(FOLLOWING, note.following)
        args.put(FAVOURITE, note.fav)
        return database.insert(DATABASE_TABLE, null, args)
    }

    fun updateFav(note: Favourite): Int {
        val args = ContentValues()
        args.put(USERNAME, note.username)
        args.put(NAME, note.name)
        args.put(AVATAR, note.avatar)
        args.put(COMPANY, note.company)
        args.put(LOCATION, note.location)
        args.put(REPOSITORY, note.repository)
        args.put(FOLLOWERS, note.followers)
        args.put(FOLLOWING, note.following)
        args.put(FAVOURITE, note.fav)
        return database.update(DATABASE_TABLE, args, USERNAME + "= '" + note.username + "'", null)
    }

    fun deleteNote(id: String): Int {
        return database.delete(TABLE_NAME, "$USERNAME = '$id'", null)
    }
}