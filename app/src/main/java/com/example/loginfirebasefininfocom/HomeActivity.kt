package com.example.loginfirebasefininfocom

import UserListAdapter
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import com.example.loginfirebasefininfocom.Constants.Companion.UserArrayList
import com.example.loginfirebasefininfocom.databinding.ActivityHomeBinding
import com.example.loginfirebasefininfocom.model.User

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var userListadapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Set the desired color for the three-dot icon
        val iconColor = resources.getColor(R.color.white)
        // Get the overflow icon from the toolbar
        val overflowIcon = binding.toolbar.overflowIcon
        // Apply the color to the overflow icon using DrawableCompat
        overflowIcon?.let { DrawableCompat.setTint(it, iconColor) }
        binding.toolbar.overflowIcon=resources.getDrawable(R.drawable.baseline_sort_by_alpha_24)
        userListadapter = UserListAdapter(UserArrayList)
        binding.recyclerView.adapter = userListadapter

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_name -> {
                userListadapter.sortByName()
                userListadapter.notifyDataSetChanged()
                true
            }

            R.id.action_sort_age -> {
                userListadapter.sortByAge()
                userListadapter.notifyDataSetChanged()

                true
            }

            R.id.action_sort_city -> {
                userListadapter.sortByCity()
                userListadapter.notifyDataSetChanged()

                true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}