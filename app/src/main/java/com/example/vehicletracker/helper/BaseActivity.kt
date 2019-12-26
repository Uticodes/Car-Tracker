package com.example.vehicletracker.helper

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehicletracker.utils.App


open class BaseActivity : AppCompatActivity(){

    protected fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun checkNetworkState(): Boolean {
        if (!App.checkNetwork(this)) {
            showToast("You do not have a network connection")
            return false
        }

        return true
    }
}