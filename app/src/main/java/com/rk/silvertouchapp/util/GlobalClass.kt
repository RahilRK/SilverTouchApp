package com.rk.silvertouchapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.rk.silvertouchapp.R
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

public class GlobalClass private constructor(mcontext: Context) {

    private var context: Context = mcontext
    private var preferences: SharedPreferences = mcontext.getSharedPreferences(
        mcontext.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )

    companion object{
        @Volatile private var INSTANCE: GlobalClass? = null

        fun getInstance(context: Context): GlobalClass {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = GlobalClass(context)
                }
            }

            return INSTANCE!!
        }
    }

    //todo preferences
    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun getIntWithD(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    fun setIntData(key: String?, setvalue: Int) {
        preferences.edit().putInt(key, setvalue).apply()
    }

    fun getString(key: String): String {
        return preferences.getString(key, "")!!
    }

    fun getStringWithD(key: String, defValue: String): String {
        return preferences.getString(key, defValue)!!
    }

    fun setStringData(key: String?, setvalue: String) {
        preferences.edit().putString(key, setvalue).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun setBooleanData(key: String?, setvalue: Boolean) {
        preferences.edit().putBoolean(key, setvalue).apply()
    }

    fun clearAllPreference(){
        preferences.edit().clear().commit()
    }

    public fun log(tag: String,msg: String) {
        Log.e(tag,msg)
    }

    public fun toastshort(message: String) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    public fun toastlong(message: String) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    public fun showSnackBar(rootView : View, message: String) {
        val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
//        snack.setAction("Click Me") {
//            // TODO when you tap on "Click Me"
//        }
        snack.show()
    }

    public fun hideKeyboard(view: Activity) {
        val view = view.currentFocus
        if (view != null) {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

/*
    fun showDialogue(activity: Activity?, title: String?, message: String?) {
        MaterialAlertDialogBuilder(activity!!, R.style.RoundShapeTheme)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(
                "Ok"
            ) { dialog, which -> dialog.dismiss() } //                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            //                    @Override
            //                    public void onClick(DialogInterface dialog, int which) {
            //
            //                    }
            //                })
            .show()
    }
*/

    fun changedateTimeFormat(
        defaultPattern: String,
        neededPattern: String,
        input: String?
    ): String? {

//        String inputPattern = "yyyy-mm-dd";
//        String outputPattern = "dd/mm/yyyy";
        val inputFormat = SimpleDateFormat(defaultPattern)
        val outputFormat = SimpleDateFormat(neededPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(input)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }
}