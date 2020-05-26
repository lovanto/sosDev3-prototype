package com.lovanto.sosdev.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lovanto.sosdev.R
import com.lovanto.sosdev.model.DataUsers
import com.lovanto.sosdev.viewModel.ViewPagerDetailAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_row_users.username

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val REQUEST_UPDATE = 200
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setData()
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
        username.text = "( " + dataUser.username.toString() + " )"
        company.text = dataUser.company.toString()
        location.text = dataUser.location.toString()
        repo.text = dataUser.repository.toString()
        followerss.text = dataUser.followers.toString()
        followings.text = dataUser.following.toString()
        Glide.with(this)
            .load(dataUser.avatar.toString())
            .into(avatars)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_fav) {
//            val title = edt_title.text.toString().trim()
//            val description = edt_description.text.toString().trim()
//
//            if (title.isEmpty()) {
//                edt_title.error = "Field can not be blank"
//                return
//            }
//
//            val values = ContentValues()
//            values.put(TITLE, title)
//            values.put(DESCRIPTION, description)
//
//            if (isEdit) {
//                // Gunakan uriWithId untuk update
//                // content://com.dicoding.picodiploma.mynotesapp/note/id
//                contentResolver.update(uriWithId, values, null, null)
//                Toast.makeText(this, "Satu item berhasil diedit", Toast.LENGTH_SHORT).show()
//                finish()
//            } else {
//                values.put(DATE, getCurrentDate())
//                // Gunakan content uri untuk insert
//                // content://com.dicoding.picodiploma.mynotesapp/note/
//                contentResolver.insert(CONTENT_URI, values)
//                Toast.makeText(this, "Satu item berhasil disimpan", Toast.LENGTH_SHORT).show()
//                finish()
//            }
                Toast.makeText(this, "Satu item berhasil disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}
