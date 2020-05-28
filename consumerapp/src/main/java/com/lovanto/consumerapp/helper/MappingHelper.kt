package com.lovanto.sosdev.helper

import android.database.Cursor
import com.lovanto.sosdev.db.DatabaseSosDev
import com.lovanto.sosdev.model.Favourite
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favourite> {
        val favList = ArrayList<Favourite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.AVATAR))
                val company = getString(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.LOCATION))
                val repository = getInt(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.REPOSITORY))
                val followers = getInt(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.FOLLOWING))
                val favourite = getString(getColumnIndexOrThrow(DatabaseSosDev.FavColumns.FAVOURITE))
                favList.add(Favourite(username, name, avatar, company, location, repository, followers, following, favourite))
            }
        }
        return favList
    }
}