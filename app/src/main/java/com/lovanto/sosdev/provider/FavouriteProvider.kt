package com.lovanto.sosdev.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.lovanto.sosdev.db.DatabaseSosDev.AUTHORITY
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.CONTENT_URI
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.TABLE_NAME
import com.lovanto.sosdev.db.FavouriteHelper

class FavouriteProvider : ContentProvider() {

    companion object {

        /*
        Integer digunakan sebagai identifier antara select all sama select by id
         */
        private const val FAV = 1
        private const val FAV_ID = 2
        private lateinit var favHelper: FavouriteHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        /*
        Uri matcher untuk mempermudah identifier dengan menggunakan integer
        misal
        uri com.dicoding.picodiploma.mynotesapp dicocokan dengan integer 1
        uri com.dicoding.picodiploma.mynotesapp/# dicocokan dengan integer 2
         */
        init {
            // content://com.dicoding.picodiploma.mynotesapp/note
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)

            // content://com.dicoding.picodiploma.mynotesapp/note/id
            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_NAME/#",
                FAV_ID)
        }
    }

    override fun onCreate(): Boolean {
        favHelper = FavouriteHelper.getInstance(context as Context)
        favHelper.open()
        return true
    }

    /*
    Method queryAll digunakan ketika ingin menjalankan queryAll Select
    Return cursor
     */
    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAV -> cursor = favHelper.queryAll()
            FAV_ID -> cursor = favHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            sUriMatcher.match(uri) -> favHelper.insert(contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        val updated: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> favHelper.update(uri.lastPathSegment.toString(),contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> favHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
}