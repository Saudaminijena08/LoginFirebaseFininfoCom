package com.example.loginfirebasefininfocom.ui

import com.example.loginfirebasefininfocom.adapter.UserListAdapter
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.DrawableCompat
import com.example.loginfirebasefininfocom.Constants.Companion.UserArrayList
import com.example.loginfirebasefininfocom.R
import com.example.loginfirebasefininfocom.databinding.ActivityHomeBinding

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
        binding.toolbar.overflowIcon = resources.getDrawable(R.drawable.baseline_sort_by_alpha_24)
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
                showAlertDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this@HomeActivity)
        val icon: Drawable? = getDrawable(R.drawable.baseline_warning_24)
        builder.setIcon(icon)
        builder.setTitle("Warning !!!")
        builder.setMessage("Are you sure ,Do you want to logout ?")
        builder.setCancelable(false)
        builder.setPositiveButton("YES") { _, _ ->
            this@HomeActivity.finish()
        }
        builder.setNegativeButton("NO") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        showAlertDialog()
    }
}