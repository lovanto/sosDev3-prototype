package com.lovanto.sosdev.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lovanto.sosdev.R

class FavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
        setActionBarTitle("Favourite Users")
    }


    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }
}
