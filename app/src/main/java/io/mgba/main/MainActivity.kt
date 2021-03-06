package io.mgba.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.android.material.tabs.TabLayout
import com.mikepenz.aboutlibraries.LibsBuilder
import androidx.appcompat.app.AppCompatActivity
import io.mgba.main.adapters.TabViewPager
import io.mgba.main.MainViewModel.Companion.DEFAULT_PANEL
import io.mgba.R
import io.mgba.base.BaseActivity
import io.mgba.mgba
import kotlinx.android.synthetic.main.activity_library.*

class MainActivity : BaseActivity<MainViewModel>(), TabLayout.OnTabSelectedListener, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener{

    override fun getLayout(): Int = R.layout.activity_library
    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareToolbar()
        prepareViewPager()

        if (savedInstanceState != null) {
            floatingSearchView.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(MAIN_TOOLBAR_STATE))
            pager.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(MAIN_VIEWPAGE_STATE))
        }
    }

    private fun prepareToolbar() {
        floatingSearchView.setOnMenuItemClickListener(this)
        floatingSearchView.setOnSearchListener(this)
        floatingSearchView.setOnQueryChangeListener(this)
    }

    private fun prepareViewPager() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Favorites)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.GBA)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.GBC)))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabViewPager(supportFragmentManager)

        pager.adapter = adapter
        pager.currentItem = DEFAULT_PANEL

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }



    override fun onTabUnselected(p0: TabLayout.Tab?) {}
    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {}
    override fun onSearchAction(currentQuery: String?) {}

    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        //controller.onSearchTextChanged(oldQuery, newQuery)
    }

    override fun onActionMenuItemSelected(item: MenuItem) {
        //controller.onMenuItemSelected(item)
    }

    fun clearSuggestions() {
        floatingSearchView.clearSuggestions()
    }

    fun showSuggestions(list: List<SearchSuggestion>) {
        floatingSearchView.swapSuggestions(list)
        floatingSearchView.hideProgress()
    }

    fun startAboutPanel(aboutPanel: LibsBuilder) {
        aboutPanel.start(this)
    }

    fun startActivityForResult(activity: Class<out AppCompatActivity>, code: Int) {
        val it = Intent(this, activity)
        startActivityForResult(it, code)
    }

    fun showProgress() {
        floatingSearchView.showProgress()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MAIN_TOOLBAR_STATE, floatingSearchView.onSaveInstanceState())
        outState.putParcelable(MAIN_VIEWPAGE_STATE, pager.onSaveInstanceState())
    }


    companion object {
        fun start(context: Context) {
            val it = Intent(context, MainActivity::class.java)
            context.startActivity(it)
        }

        val MAIN_TOOLBAR_STATE = "main_toolbar_state"
        val MAIN_VIEWPAGE_STATE = "main_viewpage_state"
    }
}
