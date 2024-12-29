package com.example.restapi

import android.app.Application
import com.example.restapi.dependenciesinjection.AppContainer
import com.example.restapi.dependenciesinjection.MahasiswaContainer

class MahasiswaApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}