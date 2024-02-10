package com.example.todoapp.view.mainView

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.utils.CustomTypefaceSpan
import com.example.todoapp.view.about.About
import com.example.todoapp.view.calendar.Calendar
import com.example.todoapp.view.home.Home
import com.example.todoapp.view.menu.MenuAdapter
import com.example.todoapp.view.toDoList.ToDoList
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import java.util.ArrayList



class MainView : AppCompatActivity(), DuoMenuView.OnMenuClickListener {
    private var mMenuAdapter: MenuAdapter? = null
    private var mViewHolder: ViewHolder? = null
    private var typeface: Typeface? = null
    private var mTitles = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        typeface = ResourcesCompat.getFont(this, R.font.galactic)
        mTitles = ArrayList(resources.getStringArray(R.array.menuOptions).toList())
        mTitles = mTitles.map { title ->
            applyTypefaceToTitle(title, typeface)
        } as ArrayList<String>


        // Initialize the views
        mViewHolder = ViewHolder()

        // Handle toolbar actions
        handleToolbar()

        // Handle menu actions
        handleMenu()

        // Handle drawer actions
        handleDrawer()

        // Show main fragment in container
        goToFragment(Home(), false)
        mMenuAdapter!!.setViewSelected(0, true)
        title = mTitles[0]
    }


    private fun applyTypefaceToTitle(title: String, typeface: Typeface?): String {
        val spannableStringBuilder = SpannableStringBuilder(title)
        typeface?.let {
            spannableStringBuilder.setSpan(CustomTypefaceSpan(it), 0, title.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        }
        return spannableStringBuilder.toString()
    }


    private fun handleToolbar() {
        setSupportActionBar(mViewHolder!!.mToolbar)
    }

    private fun handleDrawer() {
        val DrawerToggle = DuoDrawerToggle(
            this,
            mViewHolder!!.mDrawerLayout,
            mViewHolder!!.mToolbar,
            nl.psdcompany.psd.duonavigationdrawer.R.string.navigation_drawer_open,
            nl.psdcompany.psd.duonavigationdrawer.R.string.navigation_drawer_close
        )
        mViewHolder!!.mDrawerLayout.setDrawerListener(DrawerToggle)
        DrawerToggle.syncState()
    }

    private fun handleMenu() {
        mMenuAdapter = MenuAdapter(mTitles)
        mViewHolder!!.mDuoMenuView.setOnMenuClickListener(this)
        mViewHolder!!.mDuoMenuView.adapter = mMenuAdapter
    }

    override fun onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show()
    }

    override fun onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show()
    }

    private fun goToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.container, fragment).commit()
    }


    override fun onOptionClicked(position: Int, objectClicked: Any) {
        // Set the toolbar title
        title = mTitles[position]

        // Set the right options selected
        mMenuAdapter!!.setViewSelected(position, true)
        when (position) {

            1 -> goToFragment(ToDoList("personal"), false)
            2 -> goToFragment(ToDoList("work"), false)
            3 -> goToFragment(Calendar(),false)
            4 -> goToFragment(About(),false)
            else -> goToFragment(Home(), false)
        }

        // Close the drawer
        mViewHolder!!.mDrawerLayout.closeDrawer()
    }

    private inner class ViewHolder internal constructor() {
        val mDrawerLayout: DuoDrawerLayout
        val mDuoMenuView: DuoMenuView
        val mToolbar: Toolbar

        init {
            mDrawerLayout = findViewById<View>(R.id.drawer) as DuoDrawerLayout
            mDuoMenuView = mDrawerLayout.menuView as DuoMenuView
            mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        }
    }
}