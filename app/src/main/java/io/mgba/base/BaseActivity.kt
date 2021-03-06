package io.mgba.base

import android.os.Bundle
import android.view.View
import android.app.Activity
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.mgba.R
import io.mgba.utilities.device.ResourcesManager
import io.mgba.widgets.MaterialSnackbar

abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {

    lateinit var vm: T

    override fun onCreate(savedInstanceState: Bundle?){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        vm = ViewModelProviders.of(this).get(getViewModel())
        colorizeStatusbar()
        onCreate()
    }

    private fun colorizeStatusbar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ResourcesManager.getColor(R.color.statusbarColor)
    }

    open fun onCreate(){}

    abstract fun getLayout(): Int
    abstract fun getViewModel(): Class<T>

    open fun close() { finish() }

    fun showSnackbarAlert(view: View, text: String) {
        MaterialSnackbar.notify(view)
                .title(text)
                .build()
                .show()
    }

    fun showSnackbarAlert(view: View, text: String, onClickText: String, onClick: (View) -> Unit, onDismiss: () -> Unit) {
        MaterialSnackbar.notify(view)
                .title(text)
                .onClick(onClickText, onClick)
                .build()
                .also {
                    it.addCallback(object: Snackbar.Callback() {

                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if(event == DISMISS_EVENT_TIMEOUT) {
                                onDismiss.invoke()
                            }
                        }
                    })
                }
                .show()
    }


    fun setThemeLightMode() {
        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun setThemeDarkMode() {
        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun setThemeAutoMode() {
        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}



