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
  fun IsworkLogedIn():Boolean
    {
        return sharedpreferences.getBoolean("IsWorkshopLoggedin",false)
    }
    fun clearworkshopLogin() {
        editor.putBoolean("IsWorkshopLoggedin", false)
        editor.apply()
    }


    fun saveUserDocId( userdocId:String)
    {
        editor.putString("UserDocId",userdocId)
        editor.apply()
    }
    fun saveWorkShopDocId( userdocId:String)
    {
        editor.putString("workShopDocId",userdocId)
        editor.apply()
    }

    fun getUserDocId():String
    {
       return sharedpreferences.getString("UserDocId","Value Not Found")!!
    }

    fun getWorkShopDocId():String
    {
       return sharedpreferences.getString("workShopDocId","Value Not Found")!!
    }



}