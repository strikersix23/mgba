package io.mgba

import android.annotation.SuppressLint
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class mgba : Application(){
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    fun showProgressDialog(activity: AppCompatActivity) {
        ProgressDialog.show(activity, getString(R.string.Progress_Title), getString(R.string.Progress_desc), true, false)
    }

    fun showDialog(activity: AppCompatActivity, title: String, desc: String, positive_button: String,
                   negative_button: String, positive_click: DialogInterface.OnClickListener,
                   negative_click: DialogInterface.OnClickListener) {

        AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton(positive_button, positive_click)
                .setNegativeButton(negative_button, negative_click)
                .show()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        fun report(error: Throwable) {
            if (BuildConfig.DEBUG) { Log.e(" --- An error occurred:", error.message) }
        }

        fun printLog(tag: String, message: String) {
            if (BuildConfig.DEBUG) { Log.v(tag, message) }
        }
    }
}
