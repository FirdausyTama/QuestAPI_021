package com.example.restapi

import android.app.Application
import com.example.restapi.di.AppContainer
import com.example.restapi.di.MahasiswaContainer

class MahasiswaApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container=MahasiswaContainer()
    }
}