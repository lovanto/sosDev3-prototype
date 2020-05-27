package com.lovanto.sosdev.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lovanto.sosdev.R
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.AVATAR
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.COMPANY
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.CONTENT_URI
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.FAVOURITE
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.FOLLOWERS
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.FOLLOWING
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.LOCATION
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.NAME
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.REPOSITORY
import com.lovanto.sosdev.db.DatabaseSosDev.FavColumns.Companion.USERNAME
import com.lovanto.sosdev.db.FavouriteHelper
import com.lovanto.sosdev.model.DataUsers
import com.lovanto.sosdev.model.Favourite
import com.lovanto.sosdev.viewModel.ViewPagerDetailAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_row_users.*
import kotlinx.android.synthetic.main.item_row_users.username


class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAV = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    private var isFavourite = false
    private lateinit var gitHelper: FavouriteHelper
    private var favourites: Favourite? = null
    private lateinit var imageAvatar: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        gitHelper = FavouriteHelper.getInstance(applicationContext)
        gitHelper.open()

        favourites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favourites != null) {
            setDataObject()
            isFavourite = true
            val checked : Int = R.drawable.ic_favorite_black_24dp
            btn_fav.setImageResource(checked)
        } else {
            setData()
        }

        viewPagerConfig()

        btn_fav.setOnClickListener(this)
    }

    private fun viewPagerConfig() {
        val viewPagerDetailAdapter = ViewPagerDetailAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }


    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DATA) as DataUsers
        setActionBarTitle("Detail of "+dataUser.name.toString())
        name.text = dataUser.name.toString()
        username.text = dataUser.username.toString()
        company.text = dataUser.company.toString()
        location.text = dataUser.location.toString()
        repo.text = dataUser.repository.toString()
        followerss.text = dataUser.followers.toString()
        followings.text = dataUser.following.toString()
        Glide.with(this)
            .load(dataUser.avatar.toString())
            .into(avatars)
        imageAvatar = dataUser.avatar.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setDataObject() {
        val favUser = intent.getParcelableExtra(EXTRA_NOTE) as Favourite
        setActionBarTitle("Detail of "+favUser.name.toString())
        name.text = favUser.name.toString()
        username.text = favUser.username.toString()
        company.text = favUser.company.toString()
        location.text = favUser.location.toString()
        repo.text = favUser.repository.toString()
        followerss.text = favUser.followers.toString()
        followings.text = favUser.following.toString()
        Glide.with(this)
            .load(favUser.avatar.toString())
            .into(avatars)
        imageAvatar = favUser.avatar.toString()
    }

    override fun onClick(view: View) {
        val checked : Int = R.drawable.ic_favorite_black_24dp
        val unChecked : Int = R.drawable.ic_favorite_border_black_24dp
        if (view.id == R.id.btn_fav) {
<<<<<<< HEAD
            if (isFavourite) {
                gitHelper.deleteById(dataUser.username.toString())
=======
            if (isFavourite == true) {
                gitHelper.deleteById(favourites?.username.toString())
>>>>>>> 8052c23c9de23625b2334fb8df56658a90e944e8
                Toast.makeText(this, "Deleted from favourite list", Toast.LENGTH_SHORT).show()
                btn_fav.setImageResource(unChecked)
                isFavourite = false
            } else {
                val dataUsername = username.text.toString()
                val dataName = name.text.toString()
                val dataAvatar = imageAvatar
                val datacompany = company.text.toString()
                val dataLocation = location.text.toString()
                val dataRepository = repo.text.toString()
                val dataFollowers = followerss.text.toString()
                val dataFollowing = followings.text.toString()
                val dataFavourite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataName)
                values.put(AVATAR, dataAvatar)
                values.put(COMPANY, datacompany)
                values.put(LOCATION, dataLocation)
                values.put(REPOSITORY, dataRepository)
                values.put(FOLLOWERS, dataFollowers)
                values.put(FOLLOWING, dataFollowing)
                values.put(FAVOURITE, dataFavourite)

                isFavourite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, "Added to favourite list", Toast.LENGTH_SHORT).show()
                btn_fav.setImageResource(checked)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
    }
}
