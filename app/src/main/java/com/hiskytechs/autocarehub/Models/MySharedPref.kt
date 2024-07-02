package com.hiskytechs.autocarehub.Models

import android.content.Context

class MySharedPref(var context: Context) {

    var sharedpreferences=context.getSharedPreferences("MyAppPref",Context.MODE_PRIVATE)
    var editor=sharedpreferences.edit()




    fun saveuserLogin()
    {
        editor.putBoolean("IsUserLoggedin",true)
        editor.apply()
    }

    fun saveworkshopLogin()
    {
        editor.putBoolean("IsWorkshopLoggedin",true)
        editor.apply()
    }
    fun IsUserLoggedIn():Boolean
    {
        return sharedpreferences.getBoolean("IsUserLoggedin",false)
    }


}