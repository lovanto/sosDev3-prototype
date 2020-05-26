package com.lovanto.sosdev.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.AVATAR
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.COMPANY
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.FAVOURITE
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.FOLLOWERS
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.FOLLOWING
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.LOCATION
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.NAME
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.REPOSITORY
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.TABLE_NAME
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.USERNAME
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
        var fav: Favourite
        if (cursor.count > 0) {
            do {
                fav = Favourite()
                fav.username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
                fav.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                fav.avatar = cursor.getString(cursor.getColumnIndexOrThrow(AVATAR))
                fav.company = cursor.getString(cursor.getColumnIndexOrThrow(COMPANY))
                fav.location = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION))
                fav.repository = cursor.getInt(cursor.getColumnIndexOrThrow(REPOSITORY))
                fav.followers = cursor.getInt(cursor.getColumnIndexOrThrow(FOLLOWERS))
                fav.following = cursor.getInt(cursor.getColumnIndexOrThrow(FOLLOWING))
                fav.fav = cursor.getString(cursor.getColumnIndexOrThrow(FAVOURITE))

                arrayList.add(fav)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun insertFav(fav: Favourite): Long {
        val args = ContentValues()
        args.put(USERNAME, fav.username)
        args.put(NAME, fav.name)
        args.put(AVATAR, fav.avatar)
        args.put(COMPANY, fav.company)
        args.put(LOCATION, fav.location)
        args.put(REPOSITORY, fav.repository)
        args.put(FOLLOWERS, fav.followers)
        args.put(FOLLOWING, fav.following)
        args.put(FAVOURITE, fav.fav)
        return database.insert(DATABASE_TABLE, null, args)
    }

    fun updateFav(fav: Favourite): Int {
        val args = ContentValues()
        args.put(USERNAME, fav.username)
        args.put(NAME, fav.name)
        args.put(AVATAR, fav.avatar)
        args.put(COMPANY, fav.company)
        args.put(LOCATION, fav.location)
        args.put(REPOSITORY, fav.repository)
        args.put(FOLLOWERS, fav.followers)
        args.put(FOLLOWING, fav.following)
        args.put(FAVOURITE, fav.fav)
        return database.update(DATABASE_TABLE, args, USERNAME + "= '" + fav.username + "'", null)
    }

    fun deleteNote(id: String): Int {
        return database.delete(TABLE_NAME, "$USERNAME = '$id'", null)
    }
}